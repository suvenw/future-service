package com.suven.framework.core.rocketmq;


/**
 * @Title: RocketMQMessage.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明)RocketMQ Message 封装接口实现处理类
 * @Copyright: (c) 2018 gc by https://www.suven.top
 *
 */
public class RocketMQProductWithPayload<T>{
    //全局ID
    private long globalId;

    //全局ID
    private String topic;
    //重试次数
    private int retryCount;

    private T body;

    public RocketMQProductWithPayload(){}

    public static <T> RocketMQProductWithPayload build(T body, long globalId, int retryCount) {
        RocketMQProductWithPayload mq = new RocketMQProductWithPayload();
        mq.globalId = globalId;
        mq.retryCount = retryCount;
        mq.body = body;
        return mq;
    }

    public long getGlobalId() {
        return globalId;
    }

    public void setGlobalId(long globalId) {
        this.globalId = globalId;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
