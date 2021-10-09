//package cn.com.gc.test;
//
//
///* @FileName: Producer.java
// * @Package:com.test
// * @Description: TODO
// * @author: LUCKY
// * @date:2015年12月28日 下午2:32:22
// * @version V1.0
// */
//
//
//
//import com.aliyun.openservices.ons.api.*;
//
//import java.util.Date;
//import java.util.Properties;
//
///**
// * MQ发送普通消息示例 Demo
// */
//public class ProducerTest {
//
//
//    public static void main(String[] args) {
//        Properties producerProperties = new Properties();
//        producerProperties.setProperty(PropertyKeyConst.ProducerId, MqConfigTest.MQ_ORDER_PRODUCER_ID);
//        producerProperties.setProperty(PropertyKeyConst.AccessKey, MqConfigTest.MQ_ACCESS_KEY);
//        producerProperties.setProperty(PropertyKeyConst.SecretKey, MqConfigTest.MQ_SECRET_KEY);
//        producerProperties.setProperty(PropertyKeyConst.ONSAddr, MqConfigTest.MQ_ONSADDR);
//        Producer producer = ONSFactory.createProducer(producerProperties);
//        producer.start();
//        System.out.println("Producer Started");
//
//        for (int i = 0; i < 10; i++) {
//            Message message = new Message(MqConfigTest.MQ_ORDER_TOPIC, MqConfigTest.MQ_ORDER_TAG, "mq send transaction message test".getBytes());
//            SendResult sendResult = producer.send(message);
//            if (sendResult != null) {
//                System.out.println(new Date() + " Send mq message success! Topic is:" + MqConfigTest.TOPIC + " msgId is: " + sendResult.getMessageId());
//            }
//        }
//    }
//}
