package com.suven.framework.core.mq.rocketmq;

import com.suven.framework.common.aop.GlobalRpcLogbackThread;
import com.suven.framework.common.constants.GlobalConfigConstants;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.util.ids.GlobalId;
import com.suven.framework.util.json.JsonUtils;
import com.suven.framework.core.redis.RedisClusterServer;
import com.suven.framework.core.redis.RedisKeys;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @Title: RocketMQProducerHandler.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明)RocketMQ 生产端发送消息处理的接口
 * @Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

@Component
@ConditionalOnClass(RocketMQAutoConfigSetting.class)
public class RocketMQProducerHandler<T> {
    private Logger logger = LoggerFactory.getLogger(RocketMQProducerHandler.class);


    @Autowired
    private RedisClusterServer redisClusterServer;
    @Autowired
    private RocketThreadPool rocketThreadPool;
    /**
     * @Title: 处理消息的接口
     * @Description:
     * @param
     * @return
     * @throw
     * @author lixiangling
     * @date 2018/5/22 17:18
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    public void sendMessage(String topicName,T msg){
        this.sendMessage(topicName,msg,"");
    }

    public void sendCompensateMessage(String topicName,long globalId,T msg){
        String prefixKeys = "CMT_";
        this.sendMessage(topicName,msg,prefixKeys);
    }


    private void sendMessage(String topicName,T msg,String prefixKeys){
        DefaultMQProducer defaultMQProducer = RocketMQProducerFactory.getMQProducer(topicName);
        if(defaultMQProducer == null){
            throw new SystemRuntimeException(SysResultCodeEnum.MQ_ROCKET_PRODUCER_NOT_FOUND);
        }
        RocketMqTopic topic = RocketMQProducerFactory.getTopName(topicName);
        if(topic == null){
            throw new SystemRuntimeException(SysResultCodeEnum.MQ_ROCKET_TOPIC_NOT_FOUND);
        }

        long globalId = GlobalId.get(topic.getModule()).nextId();
        String messageKeys = new  StringBuilder(prefixKeys).append(topicName).append(globalId).toString();

        RocketMQMessage<T> rocketMQMessage = buildRocketMQMessage(msg, topic.getFailedRetryCount(), globalId);
        //byte[] prefixByte = SerializableUtil.toBytes(rocketMQMessage);
        String jsonRocketMqMessage = JsonUtils.toJson(rocketMQMessage);

        Message message = buildMqMessageAndGlobalId(topic,jsonRocketMqMessage, messageKeys, globalId);

        redisClusterServer.setex(RedisKeys.MQ_CONSUMER + globalId, String.valueOf(topic.getFailedRetryCount()),RocketMQListener.CACHE_TIME);
        logger.info("send rocketMq nameSrv=【{}】 message= 【{}】", defaultMQProducer.getNamesrvAddr(),jsonRocketMqMessage);
        RocketMQThread rocketMQThread = new RocketMQThread(message, defaultMQProducer);
        rocketThreadPool.run(rocketMQThread);



    }

    @NotNull
    private RocketMQMessage<T> buildRocketMQMessage(T msg, int failedRetryCount, long globalId) {
        //拼装消息队列
        RocketMQMessage<T> rocketMQMessage = new RocketMQMessage<T>();
        rocketMQMessage.setGlobalId(globalId);
        rocketMQMessage.setRetryCount(failedRetryCount);
        rocketMQMessage.setBody(msg);
        return rocketMQMessage;
    }


    private Message buildMqMessageAndGlobalId(RocketMqTopic topic, String jsonRocketMqMessage, String messageKeys,long globalId) {
        String traceId = GlobalRpcLogbackThread.getLogbackTraceIdInMDC(String.valueOf(globalId));
        Message message = new Message();
        message.setTopic(topic.getTopicName());
        message.setTags(topic.getTag());
        message.setDelayTimeLevel(0);
        message.setKeys(messageKeys);
        message.setBody(jsonRocketMqMessage.getBytes());
        message.putUserProperty(GlobalConfigConstants.LOGBACK_TRACE_ID,traceId);
        return message;
    }




}
