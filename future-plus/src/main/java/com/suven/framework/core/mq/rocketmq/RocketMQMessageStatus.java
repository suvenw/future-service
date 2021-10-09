package com.suven.framework.core.mq.rocketmq;


/**
 * @Title: RocketMQMessage.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明)RocketMQ Message 返回状态枚举类,包括(成功,失败,等待)
 * @Copyright: (c) 2018 gc by https://www.suven.top
 *
 */


public enum RocketMQMessageStatus {
    SUCCESS,WAIT,FAIL
}
