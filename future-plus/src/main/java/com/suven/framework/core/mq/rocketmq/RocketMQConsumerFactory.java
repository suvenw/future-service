package com.suven.framework.core.mq.rocketmq;

import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.exception.SystemRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Title: RocketMQConsumerFactory.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明)RocketMQConsumerFactory 消费端工厂管理标签实现业务类
 * @Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

@ConditionalOnClass(RocketMQAutoConfigSetting.class)
public class RocketMQConsumerFactory implements FactoryBean<DefaultMQPushConsumer> {
    private Logger logger = LoggerFactory.getLogger(RocketMQConsumerFactory.class);

    public static Map<String,DefaultMQPushConsumer> mqConsumerMap = new HashMap<>();

    public RocketMQConsumerFactory(RocketMQAutoConfigSetting rocketMQConfig, RocketMQListenerFactory rocketMQListenerFactory){
        Map<String,RocketMqTopic> rocketMqTopicMap = Arrays.stream(RocketMqTopic.values()).collect(Collectors.toMap(e->e.getTopicName(), e->e));
        if(rocketMqTopicMap == null || rocketMqTopicMap.isEmpty()){
            return;
        }
        for (Map.Entry<String, RocketMqTopic> topicEntry : rocketMqTopicMap.entrySet()){
            RocketMqTopic topic = topicEntry.getValue();
            if (StringUtils.isBlank(topic.getConsumerGroupName())){
                throw new SystemRuntimeException(SysResultCodeEnum.MQ_ROCKET_CONSUMER_GROUPNAME_IS_NULL);
            }
            if (StringUtils.isBlank(rocketMQConfig.getNameSrvAdds())){
                throw new SystemRuntimeException(SysResultCodeEnum.MQ_ROCKET_ADDRESS_IS_NULL);
            }
            if (StringUtils.isBlank(topic.getTopicName())){
                throw new SystemRuntimeException(SysResultCodeEnum.MQ_ROCKET_TOPIC_NAME_IS_NULL);
            }
            if (StringUtils.isBlank(topic.getTag())){
                throw new SystemRuntimeException(SysResultCodeEnum.MQ_ROCKET_TAG_IS_NULL);
            }
            RocketMQListener rocketMQListener = rocketMQListenerFactory.getMQListener(topic.getTopicName());
            if(rocketMQListener == null){
                continue;
            }
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(topic.getConsumerGroupName());
            consumer.setNamesrvAddr(rocketMQConfig.getNameSrvAdds());
            consumer.setConsumeThreadMin(rocketMQConfig.getConsumeThreadMin());
            consumer.setConsumeThreadMax(rocketMQConfig.getConsumeThreadMax());
            consumer.registerMessageListener(rocketMQListener);
            consumer.setVipChannelEnabled(false);
            consumer.setMessageModel(MessageModel.CLUSTERING);
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            try {
                consumer.subscribe(topic.getTopicName(),topic.getTag());
                consumer.start();
                logger.info("consumer is start !!! groupName:{},topic:{},namesrvAddr:{}",topic.getConsumerGroupName(),topic,rocketMQConfig.getNameSrvAdds());
            }catch (MQClientException e){
                logger.error(String.format("consumer start error groupName:%s,topic:%s,namesrvAddr:%s",topic.getConsumerGroupName(),topic,rocketMQConfig.getNameSrvAdds()),e);
            }
            mqConsumerMap.put(topic.getTopicName(), consumer);
        }
    }

    public static DefaultMQPushConsumer getMQConsumer(String topicName){
        return mqConsumerMap.get(topicName);
    }

    @Override
    public DefaultMQPushConsumer getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return DefaultMQProducer.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
