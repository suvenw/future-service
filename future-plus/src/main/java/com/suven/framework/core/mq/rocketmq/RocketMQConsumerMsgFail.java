package com.suven.framework.core.mq.rocketmq;

/**
 * @Title: RocketMQConsumerMsgFail.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明)RocketMQConsumerMsgFail 消费端异常错误收集封装实现类
 * @Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

public class RocketMQConsumerMsgFail {
    private RocketMQMessage rocketMQMessage;
    private RocketMQMessageStatus mqMessageStatus;
    private String topicName;
    private String failMsg;

    public RocketMQMessage getRocketMQMessage() {
        return rocketMQMessage;
    }

    public void setRocketMQMessage(RocketMQMessage rocketMQMessage) {
        this.rocketMQMessage = rocketMQMessage;
    }

    public RocketMQMessageStatus getMqMessageStatus() {
        return mqMessageStatus;
    }

    public void setMqMessageStatus(RocketMQMessageStatus mqMessageStatus) {
        this.mqMessageStatus = mqMessageStatus;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getFailMsg() {
        return failMsg;
    }

    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg;
    }
}
