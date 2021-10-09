//package com.xlibao.test;
//
//import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
//import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
//import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
//import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
//import com.aliyun.openservices.shade.com.alibaba.rocketmq.client.exception.MQClientException;
//import com.aliyun.openservices.shade.com.alibaba.rocketmq.common.message.MessageExt;
//import com.xlibao.base.core.rocketmq.MqCallback;
//import com.xlibao.base.util.Env;
//import com.xlibao.base.util.constants.PropertiesParam;
//import org.apache.commons.lang3.StringUtils;
//import org.reflections.Reflections;
//import org.reflections.scanners.MethodAnnotationsScanner;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.lang.reflect.Method;
//import java.util.*;
//
///**
// * Created by joven on 2017/7/3.
// */
//@Component
//public class RocketMqConsumeHandler4 {
//
//    private Logger logger = LoggerFactory.getLogger(getClass());
//    private final  String REFLECTIONS_PACKAGE= "com.xlibao";
//
//    @Autowired
//    ApplicationContext applicationContext;
//    
//    private final String MQ_CONSUMER_GROUP_NAME = PropertiesParam.mQConsumerGroupName();
//    private final String MQ_CONSUMER_NAME_ADDRESS = PropertiesParam.mQConsumerNameServerAddress();
//    private final String MQ_CONSUMER_INSTANCE_NAME = PropertiesParam.mQConsumerInstanceName();
//    private final String MQ_CONSUMER_PROJECT_NAME = PropertiesParam.mqConsumerProjectName();
//    
//    private final Map<String,MqCallback>  mqMap = new HashMap<>();
//    private final Map<String,Method>  methodMap = new HashMap<>();
//
//    /**
//     * 当前例子是PushConsumer用法，使用方式给用户感觉是消息从RocketMQ服务器推到了应用客户端。<br> 
//     * 但是实际PushConsumer内部是使用长轮询Pull方式从MetaQ服务器拉消息，然后再回调用户Listener方法<br> 
//     *     
//     * 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例<br> 
//     * 注意：ConsumerGroupName需要由应用来保证唯一
//     * 
//     * Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br> 
//     *     
//     */
//    @PostConstruct
//    public void init() {
//        initMqCallbackMethod();
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(MQ_CONSUMER_GROUP_NAME);
//        consumer.setNamesrvAddr(MQ_CONSUMER_NAME_ADDRESS);  //120.25.202.69:10911
//        consumer.setInstanceName(MQ_CONSUMER_INSTANCE_NAME);
//
//        initMqTopicConsumer(consumer);
//        initMethodInvoke(consumer);
//        
//        try {
//            consumer.start();
//        } catch (MQClientException e) {
//            logger.warn("DefaultMQPushConsumer consumer start MQClientException: " + e.getMessage());
//            e.printStackTrace();
//        }
//        System.out.println("Consumer Started.");
//        
//    }
//    private void initMqCallbackMethod(){
//        Reflections reflections = new Reflections(REFLECTIONS_PACKAGE, new MethodAnnotationsScanner());
//        Set<Method> set = reflections.getMethodsAnnotatedWith(MqCallback.class);
//        for(final Method method : set) {
//            MqCallback callback = method.getAnnotation(MqCallback.class);
//            String key = callback.topic() + "_" + callback.tags();
//            if(mqMap.containsKey(key)){
//                logger.warn("initMqCallbackMethod MqCallback topic tags exist " );
//                throw new RuntimeException("initMqCallbackMethod MqCallback topic tags exist ,key = : " + key);
//            }
//            methodMap.put(key,method);
//            mqMap.put(key,callback);
//        }
//    }
//    /**
//     * 订阅指定topic下tags分别等于Env_TagA或Env_TagC或Env_TagD 
//     * 订阅指定topic下所有消息 注意：一个consumer对象可以订阅多个topic 
//     */
//    private void initMqTopicConsumer(final DefaultMQPushConsumer consumer){
//        try {
//            String[] servers = MQ_CONSUMER_PROJECT_NAME.split(";|,");
//            boolean isAll = false;
//            if(null != servers && servers.length == 1 && "ALL".equalsIgnoreCase(servers[0])){
//                isAll = true;
//            }
//            List serverList = Arrays.asList(servers);
//            if(null != mqMap && mqMap.size() > 0){
//                Map<String,Set<String>> topicMap = new HashMap<>();//topic-tags ,一个topic 对应多个标签；
//                for(String key : mqMap.keySet()){
//                    MqCallback callback = mqMap.get(key);
//                    if(callback == null ){//启动有效的配置项目
//                        continue;
//                    }
//                    if( isAll || serverList.contains(callback.project())){
//                        Set<String> set = topicMap.get(callback.topic());
//                        if(null == set ){
//                            set = new HashSet<>();
//                        }
//                        set.add(callback.tags());
//                        topicMap.put(callback.topic(),set);
//                    }
//                }
//                for (String key : topicMap.keySet()){
//                    Set<String> tags = topicMap.get(key);
//                    String tag = StringUtils.join(tags, " || ");
//                    String topic = key;
//                    if(!Env.isProd()){//启动topic 非正式环境，增加环境前缀
//                        topic = Env.getEnv() + "_"+ key;
//                    }
//                    consumer.subscribe(topic,tag);
//                }
//
//            }
//
//        } catch (MQClientException e) {
//            logger.warn("MqCallback info : subscribe topic tags Messages Exception: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//    
//    private void methodInvoke(MessageExt message){
//        String topicKey = message.getTopic() + "_" + message.getTags();
//        if(!Env.isProd()){//过滤掉环境前缀，映射成对应类方法
//            topicKey = topicKey.substring(topicKey.indexOf("_") +1 );
//        }
//        Method method = methodMap.get(topicKey);
//        if(method == null)return;
//        Class<?> clazz = method.getDeclaringClass();
//        Collection<?> beans = applicationContext.getBeansOfType(clazz).values();
////        Collection beans = new ArrayList<>();
////        beans.add(new RocketMqConsumeTest());
//        int size = beans.size();
//        if(size == 0) {
//            logger.error("find RocketMq Callback but it's not spring bean: {}.{}", clazz, method.getName());
//            return;
//        } else if(size > 1) {
//            logger.error("find RocketMq Callback but more than one spring bean: {}.{}", clazz, method.getName());
//            return;
//        }
//
//        final Object bean = beans.iterator().next();
//        try {
//            method.invoke(bean, message.getBody());
//        } catch (Exception e) {
//            logger.error("consume error, topic:{}, Tags:{}", message.getTopic(), message.getTags());
//            logger.error("", e);
//        }
//    }
//
//    /**
//     * 默认msgs里只有一条消息，可以通过设置 consumeMessageBatchMaxSize 参数来批量接收消息 
//     * final String topic, final String tags
//     */
//    private void initMethodInvoke(final DefaultMQPushConsumer consumer) {
//        consumer.registerMessageListener(new MessageListenerConcurrently() {
//           
//            @Override
//            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
//                logger.info("ConsumeConcurrentlyStatus info :" + Thread.currentThread().getName()  + " Receive New Messages: " + msgs.toString());
//                if(null == msgs || msgs.size() != 1){
//                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
//                }
//                MessageExt msg = msgs.get(0);
//                methodInvoke(msg);//映射对应的类的方法实现
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            }
//        });
//       
//    }
//    
//    public static void main(String agr[] ){
////        RocketMqConsumeHandler handler = new RocketMqConsumeHandler();
////        handler.init();
//        String topic = "test_Topic_life_TagA";
//        System.out.print(topic.substring(topic.indexOf("_")+1));
//    }
//   
//        
//}
