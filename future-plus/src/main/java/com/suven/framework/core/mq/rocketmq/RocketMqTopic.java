package com.suven.framework.core.mq.rocketmq;

import com.suven.framework.util.ids.GlobalIdEnum;
import com.suven.framework.util.ids.IGlobalId;

/**
 * @ClassName: ROCKET_MQ队列配置
 * @Description:
 * @Author lixiangling
 * @Date 2018/5/23 9:36
 * @Copyright: (c) 2018 gc by https://www.suven.top
 * @Version : 1.0.0
 * --------------------------------------------------------
 * modifyer    modifyTime                 comment
 *
 * --------------------------------------------------------
 */


/**
 * @Title: RocketMQMessage.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明)RocketMQ MQ队列配置状态枚举类,包括(topicName,tag,producerGroupName,consumerGroupName,全局唯一id)
 * @Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

public enum RocketMqTopic {
    //用户登录完成邀请活动
    USER_LOGIN("OAUTH_LOGIN","OAUTH_LOGIN","OAUTH_LOGIN_PR_GROUP","OAUTH_LOGIN_CR_GROUP", GlobalIdEnum.OAUTH,3),

    //设备上传文件
//    USER_UPLOAD("PAD_USER_UPLOAD","PAD_USER_UPLOAD","PAD_USER_UPLOAD_PR_GROUP","PAD_USER_UPLOAD_CR_GROUP",GlobalIdGenerator.GloballIdEnum.PAD,3),

    //火树接口游戏任务校验状态
    ACTIVITY_TASK_GAME("ACTIVITY_TASK_GAME","ACTIVITY_TASK_GAME","ACTIVITY_TASK_GAME_PR_GROUP","ACTIVITY_TASK_GAME_CR_GROUP", GlobalIdEnum.ACTIVITY,3),

    //完成任务奖励
    ACTIVITY_TASK_AWARD("ACTIVITY_TASK_AWARD","ACTIVITY_TASK_AWARD","ACTIVITY_TASK_AWARD_PR_GROUP","ACTIVITY_TASK_AWARD_CR_GROUP", GlobalIdEnum.ACTIVITY,1),
    //发送短信
    SEND_SMS("SEND_SMS","SEND_SMS","SEND_SMS_PR_GROUP","SEND_SMS_CR_GROUP", GlobalIdEnum.RESOURCE,3),
    //发送语音
    SEND_VOICE("SEND_VOICE","SEND_VOICE","SEND_VOICE_PR_GROUP","SEND_VOICE_CR_GROUP", GlobalIdEnum.RESOURCE,3),
    ;

    String topicName;
    String tag;
    String producerGroupName;
    String consumerGroupName;
    int failedRetryCount;
    IGlobalId module;

//    private static Map<String,RocketMqTopic> topicMap = new HashMap<>();
//
//    static {
//
//        for(RocketMqTopic topic : values()) {
//            topicMap.put(topic.getTopicName(), topic);
//        }
//    }



    RocketMqTopic(String topName, String tag, String producerGroupName, String consumerGroupName, IGlobalId module, int retryCount){
        this.topicName = topName;
        this.tag = tag;
        this.producerGroupName = producerGroupName;
        this.consumerGroupName = consumerGroupName;
        this.failedRetryCount = retryCount;
        this.module = module;
    }

//    public static RocketMqTopic getTopName(String topicName){
//        return topicMap.get(topicName);
//    }

    public String getTopicName() {
        return topicName;
    }

    public String getTag() {
        return tag;
    }

    public String getProducerGroupName() {
        return producerGroupName;
    }

    public String getConsumerGroupName() {
        return consumerGroupName;
    }

    public int getFailedRetryCount() {
        return failedRetryCount;
    }

    public IGlobalId getModule() {
        return module;
    }
}
