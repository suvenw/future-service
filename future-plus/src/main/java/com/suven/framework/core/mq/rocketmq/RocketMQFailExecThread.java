package com.suven.framework.core.mq.rocketmq;

import com.suven.framework.util.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title: RocketMQFailExecThread.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明)RocketMQFailExecThread 消费端异常处理线程队列线程实现类
 * @Copyright: (c) 2018 gc by https://www.suven.top
 *
 */
public class RocketMQFailExecThread implements Runnable {
    private Logger logger = LoggerFactory.getLogger(RocketMQFailExecThread.class);
    private RocketMQConsumerMsgFail msgFail;
    private RocketMQFailHandler rocketMQFailHandler;

    public RocketMQFailExecThread(RocketMQConsumerMsgFail msgFail, RocketMQFailHandler rocketMQFailHandler){
        this.msgFail = msgFail;
        this.rocketMQFailHandler = rocketMQFailHandler;
    }
    @Override
    public void run() {
        try {
            rocketMQFailHandler.handleFailInfo(msgFail);
        }catch (Exception e) {
            logger.error(String.format("consumer rocketMq message record error topic=%s,msg=%s", msgFail.getTopicName(), JsonUtils.toJson(msgFail.getRocketMQMessage())),e);
        }
    }
}
