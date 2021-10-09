package com.suven.framework.core.mq.rocketmq;

import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.exception.SystemRuntimeException;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * @Title: RocketMQProducerFactory.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明)RocketMQ ROCKET_MQ生产者工厂
 * @Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

@ConditionalOnClass({RocketMQAutoConfigSetting.class})
public class RocketMQProducerFactory implements FactoryBean<DefaultMQProducer> {
    private Logger logger = LoggerFactory.getLogger(RocketMQProducerFactory.class);

    private static Map<String,DefaultMQProducer> mqProducerMap = new HashMap<>();

    private static Map<String,RocketMqTopic> topicMap = new HashMap<>();

    public RocketMQProducerFactory(RocketMQAutoConfigSetting rocketMQConfig){
        topicMap.clear();
        mqProducerMap.clear();
        Map<String,RocketMqTopic> rocketMqTopicMap = Arrays.stream(RocketMqTopic.values()).collect(Collectors.toMap(e->e.getTopicName(), e->e));
        if(rocketMqTopicMap == null || rocketMqTopicMap.isEmpty() || !RocketMQConsumerFactory.mqConsumerMap.isEmpty()){
            return;
        }
        for (Map.Entry<String, RocketMqTopic> topicEntry : rocketMqTopicMap.entrySet()){
            RocketMqTopic topic = topicEntry.getValue();
            if (StringUtils.isBlank(rocketMQConfig.getNameSrvAdds())) {
                throw new SystemRuntimeException(SysResultCodeEnum.MQ_ROCKET_ADDRESS_IS_NULL);
            }
            if (StringUtils.isBlank(topic.getProducerGroupName())) {
                throw new SystemRuntimeException(SysResultCodeEnum.MQ_ROCKET_PRODUCER_GROUPNAME_IS_NULL);
            }
            DefaultMQProducer producer = new DefaultMQProducer(topic.getProducerGroupName());
            producer.setNamesrvAddr(rocketMQConfig.getNameSrvAdds());
            producer.setInstanceName(UUID.randomUUID().toString());
            producer.setMaxMessageSize(rocketMQConfig.getMaxMessageSize());
            producer.setSendMsgTimeout(rocketMQConfig.getSendMsgTimeout());
            producer.setRetryTimesWhenSendFailed(rocketMQConfig.getRetryTimesWhenSendFailed());
            producer.setVipChannelEnabled(false);
            try {
                producer.start();
                logger.info("producer is start ! groupName:[{}],namesrvAddr:[{}]", topic.getProducerGroupName(), rocketMQConfig.getNameSrvAdds());
            } catch (MQClientException e) {
                logger.error("producer start error {}",e);
            }
            mqProducerMap.put(topic.getTopicName(), producer);
            topicMap.put(topic.getTopicName(), topic);
        }
    }

    public static DefaultMQProducer getMQProducer(String topicName){
        return mqProducerMap.get(topicName);
    }

    public static RocketMqTopic getTopName(String topicName){
        return topicMap.get(topicName);
    }

    @Override
    public DefaultMQProducer getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
