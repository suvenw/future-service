package com.suven.framework.core.rocketmq.starter;

import com.suven.framework.core.rocketmq.starter.core.RocketMQListener;
import com.suven.framework.core.rocketmq.starter.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.io.Serializable;
import java.math.BigDecimal;

//@Service
//@RocketMQMessageListener(topic = "test-topic-1", consumerGroup = "my-consumer_test-topic-1")
//@ConditionalOnProperty(prefix = "spring.rocketmq", value ="testTop.enabled",matchIfMissing = false)
public class MyConsumer1 implements RocketMQListener<String> {
    private Logger log = LoggerFactory.getLogger(MyConsumer1.class);

    public void onMessage(String message) {
            log.info("received message: {}", message);
    }

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void sendRocketMq(String... args) throws Exception {
        rocketMQTemplate.convertAndSend("test-topic-1", "Hello, World!");
        rocketMQTemplate.send("test-topic-1", MessageBuilder.withPayload("Hello, World! I'm from spring message").build());
        rocketMQTemplate.convertAndSend("test-topic-2", new OrderPaidEvent("T_001", new BigDecimal("88.00")));

//        rocketMQTemplate.destroy(); // notes:  once rocketMQTemplate be destroyed, you can not send any message again with this rocketMQTemplate
    }

    public class OrderPaidEvent implements Serializable {
        private String orderId;

        private BigDecimal paidMoney;

        public OrderPaidEvent(){}

        public OrderPaidEvent(String orderId, BigDecimal paidMoney) {
            this.orderId = orderId;
            this.paidMoney = paidMoney;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public BigDecimal getPaidMoney() {
            return paidMoney;
        }

        public void setPaidMoney(BigDecimal paidMoney) {
            this.paidMoney = paidMoney;
        }
    }


}
