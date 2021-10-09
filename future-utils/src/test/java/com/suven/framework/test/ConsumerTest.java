//package cn.com.gc.test;
//
//import com.aliyun.openservices.ons.api.*;
//
//import java.util.Properties;
//
//public class ConsumerTest {
//    public static void main(String[] args) {
//        Properties properties = new Properties();
//        properties.setProperty(PropertyKeyConst.ConsumerId, "CID_LIFE_ORDER_MSG");
//        properties.put(PropertyKeyConst.AccessKey, "请输入AccessKey");
//        properties.put(PropertyKeyConst.SecretKey, "请输入SecretKey");
//        Consumer consumer = ONSFactory.createConsumer(properties);
//        consumer.subscribe("LIFE_TOPIC_ORDER_MSG", "*", new MessageListener() {
//            public Action consume(Message message, ConsumeContext context) {
//                System.out.println("Receive: " + message);
//                return Action.CommitMessage;
//            }
//        });
//        consumer.start();
//        System.out.println("Consumer Started");
//    }
//}
