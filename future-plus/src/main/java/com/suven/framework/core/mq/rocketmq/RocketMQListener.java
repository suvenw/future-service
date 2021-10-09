package com.suven.framework.core.mq.rocketmq;

import com.suven.framework.common.constants.GlobalConfigConstants;
import com.suven.framework.util.bean.BeanUtil;
import com.suven.framework.util.json.JsonUtils;
import com.suven.framework.core.redis.RedisClusterServer;
import com.suven.framework.core.redis.RedisKeys;
import com.suven.framework.core.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Title: RocketMQListener.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明)RocketMQ Listener 消费端消息处理实现类
 * @Copyright: (c) 2018 gc by https://www.suven.top
 *
 */
public class RocketMQListener implements MessageListenerConcurrently {
    private Logger logger = LoggerFactory.getLogger(RocketMQListener.class);

    private RocketMQConsumerHandler rocketMQConsumerHandler;

    private RedisClusterServer redisClusterServer;

    private RocketMQFailHandler rocketMQFailHandler;

    @Autowired
    private RocketThreadPool rocketThreadPool;
    /* 全局唯一消息标识的缓存时间*/
    public static final int CACHE_TIME = 60*60*24;
    private static final String METHOD_GETNAME = "handleMessage";

    public RocketMQListener(){
        Collection<RocketMQFailHandler> rocketMQFailHandlers = new LinkedList<>(SpringUtil.getBeansOfType(RocketMQFailHandler.class));
        if(rocketMQFailHandlers == null || rocketMQFailHandlers.isEmpty()){
            return;
        }
        for (RocketMQFailHandler rocketMQFailHandlerInfo : rocketMQFailHandlers){
            rocketMQFailHandler = (RocketMQFailHandler) BeanUtil.AopTargetUtils.getTarget(rocketMQFailHandlerInfo);
            break;
        }
        rocketThreadPool = SpringUtil.getBean("rocketThreadPool", RocketThreadPool.class);
    }

    public void setMessageProcessor(RocketMQConsumerHandler rocketMQConsumerHandler) {
        this.rocketMQConsumerHandler = rocketMQConsumerHandler;
    }

    public void setRedisClusterServer(RedisClusterServer redisClusterServer) {
        this.redisClusterServer = redisClusterServer;
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        Thread.currentThread().setName("rocketMq-"+Thread.currentThread().getId());
        for (MessageExt msg : msgs){
            RocketMQMessageStatus messageStatus = RocketMQMessageStatus.SUCCESS;
            String failMsg = "";
            int retryFailCount = 0;
            RocketMQMessage rocketMQMessage = null;
            final String nsqCatTransactionName = new StringBuffer("rocketmq-consume-").append(msg.getTopic()).append("-handleMessage").toString();
//            Transaction t = Cat.newTransaction(CatTopConstants.TYPE_NSQ, nsqCatTransactionName);
//            t.setStatus(Transaction.SUCCESS);
            try {
                String traceId = msg.getUserProperty(GlobalConfigConstants.LOGBACK_TRACE_ID);
//                GlobalLogbackThread.setLogbackTraceId(traceId);
                rocketMQMessage = JsonUtils.parseObject(msg.getBody(),RocketMQMessage.class);
                if (rocketMQMessage == null) {
//                    t.setStatus(String.format("处理rocket-mq消息失败,TOPIC=%s的信息为空",msg.getTopic()));
                    logger.error("处理rocket-mq消息失败,TOPIC=【{}】的信息为空",msg.getTopic());
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                String retryCount = redisClusterServer.get(RedisKeys.MQ_CONSUMER+rocketMQMessage.getGlobalId());
                if (StringUtils.isBlank(retryCount)) {
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                retryFailCount = Integer.parseInt(retryCount);
                this.executeConsumer(rocketMQMessage);
                logger.info("consumer rocketMq success thread=【{}】,topic=【{}】,param=【{}】",Thread.currentThread().getName(),msg.getTopic(),JsonUtils.toJson(rocketMQMessage));
                //防止重复消费，将已经消费的队列删除
                redisClusterServer.del(RedisKeys.MQ_CONSUMER+rocketMQMessage.getGlobalId());

            }catch (Exception e){
                messageStatus = RocketMQMessageStatus.WAIT;
//                t.setStatus(e);
                if(msg.getReconsumeTimes() == retryFailCount){
                    messageStatus = RocketMQMessageStatus.FAIL;
                    failMsg = e.getMessage();
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }finally {
//                t.complete();
                RocketMQConsumerMsgFail msgFail = new RocketMQConsumerMsgFail();
                msgFail.setFailMsg(failMsg.length()>500?failMsg.substring(0,500):failMsg);
                msgFail.setMqMessageStatus(messageStatus);
                msgFail.setRocketMQMessage(rocketMQMessage);
                msgFail.setTopicName(msg.getTopic());
                RocketMQFailExecThread rocketMQFailExecThread = new RocketMQFailExecThread(msgFail, rocketMQFailHandler);
                rocketThreadPool.run(rocketMQFailExecThread);
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    private void executeConsumer(RocketMQMessage rocketMQMessage)throws Exception{
        Object obj = null;
        Object body = rocketMQMessage.getBody();
        Method[] methods = rocketMQConsumerHandler.getClass().getDeclaredMethods();
        for(Method method : methods){
            Class[] parameterClasses = method.getParameterTypes();
            if(!"handleMessage".equals(method.getName())||parameterClasses==null||parameterClasses.length<1){
                continue;
            }
            Class parameterType = parameterClasses[0];
            if(parameterType.getName().equals(java.lang.Object.class.getName())){
                continue;
            }
            if(body instanceof Map){
                obj = JsonUtils.parseByMap((Map)body, parameterType);
            }else if(body instanceof JSONObject){
                obj = JsonUtils.parseJson((JSONObject) body, parameterType);
            }else{
                obj = JsonUtils.parseObject(body.toString(), parameterType);
            }
            break;
        }
        logger.info("rocketmq invoke class={} method={} param={}",rocketMQConsumerHandler.getClass().getSimpleName(),"handleMessage",body);
        rocketMQConsumerHandler.handleMessage(obj);
    }

}
