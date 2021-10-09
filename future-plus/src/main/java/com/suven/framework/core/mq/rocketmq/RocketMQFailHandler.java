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
 * @Description: (说明)RocketMQConsumerMsgFail 消费端异常错误接口类
 * @Copyright: (c) 2018 gc by https://www.suven.top
 *
 */
public interface RocketMQFailHandler {
    public void handleFailInfo(RocketMQConsumerMsgFail msgFail);
}
