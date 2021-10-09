package com.suven.framework.core.rocketmq.starter;

import com.suven.framework.common.constants.GlobalConfigConstants;
import com.suven.framework.core.rocketmq.starter.core.RocketMQTemplate;
import com.suven.framework.core.mq.rocketmq.RocketMQListener;
import com.suven.framework.core.mq.rocketmq.RocketMQMessage;
import com.suven.framework.core.mq.rocketmq.RocketMqTopic;
import com.suven.framework.core.redis.RedisClusterServer;
import com.suven.framework.core.redis.RedisKeys;
import com.suven.framework.util.ids.GlobalId;
import com.suven.framework.util.json.JsonUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;


@Service
@ConditionalOnClass({DefaultMQProducer.class, RocketMQTemplate.class,RocketMQAutoConfiguration.class})
@ConditionalOnMissingBean({DefaultMQProducer.class, RocketMQTemplate.class})
@ConditionalOnProperty(name = GlobalConfigConstants.ROCKT_MQ_START_CONFIG_ENABLED,  matchIfMissing = false)
public class RocketMQProducer<T> {



    @Autowired
    @Qualifier("rocketMQTemplateService")
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private RedisClusterServer redisClusterServer;


    /**
     * @Title: 处理消息的接口
     * @Description:
     * @param
     * @return
     * @throw
     * @author suven.wang
     * @date 2018/5/22 17:18
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    public void sendMessage(String topicName,T msg){

        RocketMqTopic topic = RocketMqTopic.valueOf(topicName);
        if(topic == null){
//            throw new SystemRuntimeException(SysResultCodeEnum.MQ_ROCKET_TOPIC_NOT_FOUND);
        }
        long globalId = GlobalId.get(topic.getModule()).nextId();
        Message message = new Message();
        message.setTopic(topic.getTopicName());
        message.setTags(topic.getTag());
        message.setDelayTimeLevel(0);
        message.setKeys(topicName+globalId);
        //拼装消息队列
        RocketMQMessage<T> rocketMQMessage = new RocketMQMessage<T>();
        rocketMQMessage.setGlobalId(globalId);
        rocketMQMessage.setRetryCount(topic.getFailedRetryCount());
        rocketMQMessage.setBody(msg);
        //pb序列化对象
        message.setBody(JsonUtils.toJson(rocketMQMessage).getBytes());
        redisClusterServer.setex(RedisKeys.MQ_CONSUMER+rocketMQMessage.getGlobalId(), String.valueOf(topic.getFailedRetryCount()), RocketMQListener.CACHE_TIME);

        rocketMQTemplate.convertAndSend(topicName,message);
    }
}