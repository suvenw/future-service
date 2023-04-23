package com.suven.framework.core.redis.cluster;

import redis.clients.jedis.JedisPubSub;

public class JedisPubSubListener extends JedisPubSub {


    //1.监听到订阅模式接受到消息时的回调 (onPMessage) 取得按表达式的方式订阅的消息后的处理
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println(pattern + "=" + channel + "=" + message);
    }
    //2.监听到订阅频道接受到消息时的回调 (onMessage )  取得订阅的消息后的处理
    public void onMessage(String channel, String message) {  

        System.out.println(channel + "=" + message);
    }  

    // 3.订阅频道时的回调( onSubscribe ) 初始化订阅时候的处理
    public void onSubscribe(String channel, int subscribedChannels) {  
        System.out.println(channel + "=" + subscribedChannels);  
    }  

    // 4.取消订阅频道时的回调( onUnsubscribe ) 取消订阅时候的处理
    public void onUnsubscribe(String channel, int subscribedChannels) {  
        System.out.println(channel + "=" + subscribedChannels);  
    }  

    // 5.订阅频道模式时的回调 ( onPSubscribe ) 初始化按表达式的方式订阅时候的处理
    public void onPSubscribe(String pattern, int subscribedChannels) {  
        System.out.println(pattern + "=" + subscribedChannels);  
    }  

    // 6.取消订阅模式时的回调( onPUnsubscribe ) 取消按表达式的方式订阅时候的处理
    public void onPUnsubscribe(String pattern, int subscribedChannels) {  
        System.out.println(pattern + "=" + subscribedChannels);  
    }  


} 