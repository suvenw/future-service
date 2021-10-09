package com.suven.framework.core.mq.rocketmq;


import com.suven.framework.util.bean.BeanUtil;
import com.suven.framework.core.redis.RedisClusterServer;
import com.suven.framework.core.spring.SpringUtil;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


/**
 * @Title: RocketMQListenerFactory.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明)RocketMQ Listener 对FactoryBean api 接口实现处理类
 * @Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

@ConditionalOnClass(RocketMQAutoConfigSetting.class)
public class RocketMQListenerFactory implements FactoryBean<RocketMQListener> {
    private Logger logger = LoggerFactory.getLogger(RocketMQProducerFactory.class);

    private Map<String,RocketMQListener> mqListenerMap;

    public RocketMQListenerFactory(){
        mqListenerMap = new HashMap<>();
        RedisClusterServer redisClusterServer = SpringUtil.getBean(RedisClusterServer.class);
        Collection<RocketMQConsumerHandler> rocketMQConsumerHandlers = new LinkedList<>(SpringUtil.getBeansOfType(RocketMQConsumerHandler.class));
        if(rocketMQConsumerHandlers == null || rocketMQConsumerHandlers.isEmpty()){
            return;
        }
        for (RocketMQConsumerHandler rocketMQConsumerHandler : rocketMQConsumerHandlers){
            rocketMQConsumerHandler = (RocketMQConsumerHandler) BeanUtil.AopTargetUtils.getTarget(rocketMQConsumerHandler);
            RocketMqConsumer rocketMqConsumer = rocketMQConsumerHandler.getClass().getAnnotation(RocketMqConsumer.class);
            RocketMQListener rocketMQListener = new RocketMQListener();
            rocketMQListener.setMessageProcessor(rocketMQConsumerHandler);
            rocketMQListener.setRedisClusterServer(redisClusterServer);
            mqListenerMap.put(rocketMqConsumer.topic().getTopicName(), rocketMQListener);
        }
    }

    public RocketMQListener getMQListener(String topicName){
        return mqListenerMap.get(topicName);
    }

    @Override
    public RocketMQListener getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return DefaultMQProducer.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
