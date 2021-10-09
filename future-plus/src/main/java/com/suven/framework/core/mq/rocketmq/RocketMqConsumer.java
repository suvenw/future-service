package com.suven.framework.core.mq.rocketmq;

import java.lang.annotation.*;

/**
 * @Title: RocketMqConsumer.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明)RocketMQ 消费端标签实现业务类
 * @Copyright: (c) 2018 gc by https://www.suven.top
 *
 */


@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Documented
public @interface RocketMqConsumer {
    RocketMqTopic topic();
}
