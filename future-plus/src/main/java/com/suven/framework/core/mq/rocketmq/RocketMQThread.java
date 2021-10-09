package com.suven.framework.core.mq.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title: RocketMQThread.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明)RocketMQ 生产端发送消息线程处理的实现类
 * @Copyright: (c) 2018 gc by https://www.suven.top
 *
 */
public class RocketMQThread implements Runnable {
    private Logger logger = LoggerFactory.getLogger(RocketMQThread.class);
    private Message message;
    private DefaultMQProducer defaultMQProducer;

    public RocketMQThread(Message message, DefaultMQProducer defaultMQProducer){
        this.message = message;
        this.defaultMQProducer = defaultMQProducer;
    }
    @Override
    public void run() {
        try {
             defaultMQProducer.send(message);
        }catch (Exception e) {
            logger.error(String.format("producer send message is error topic=%s,tags=%s,msg=%s", message.getTopic(),message.getTags(),new String(message.getBody())),e);
        }
    }
}
