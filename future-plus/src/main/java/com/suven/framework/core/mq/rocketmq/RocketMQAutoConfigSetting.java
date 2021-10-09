package com.suven.framework.core.mq.rocketmq;

import com.suven.framework.common.constants.GlobalConfigConstants;
import org.apache.rocketmq.client.log.ClientLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Title: RocketMQAutoConfig.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明)RocketMQ 初始化启动Bean 工厂,配置初始化等业务实现类
 * @Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

@Configuration
@ConfigurationProperties(prefix = GlobalConfigConstants.ROCKT_MQ_CONFIG_PREFIX)
@EnableConfigurationProperties({RocketMQAutoConfigSetting.class})
@ConditionalOnProperty(name = GlobalConfigConstants.ROCKT_MQ_CONFIG_ENABLED,  matchIfMissing = false)
//@NacosConfigurationProperties( groupId= GlobalConfigConstants.SERVICE_NAME , dataId = GlobalConfigConstants.SERVICE_MQ_CONFIG_NAME,prefix= GlobalConfigConstants.ROCKT_MQ_CONFIG_ENABLED, autoRefreshed = true)
public class RocketMQAutoConfigSetting {

    private Logger logger = LoggerFactory.getLogger(RocketMQAutoConfigSetting.class);

    private RocketMQListenerFactory rocketMQListenerFactory;
    private RocketMQProducerFactory rocketMQProducerFactory;


    @Bean
    public RocketMQAutoConfigSetting rocketMQConfig() {
        RocketMQAutoConfigSetting config =  new RocketMQAutoConfigSetting();
//        config.getClient().systemProperty();
        logger.info("RocketMQAutoConfig init rocketMQConfiguration =[{}]", config.toString());
        return config;
    }


    @Bean
    public RocketMQListenerFactory rocketMQListenerFactory() {
        rocketMQListenerFactory = new RocketMQListenerFactory();
        return rocketMQListenerFactory;
    }

    @Bean
    public RocketMQConsumerFactory rocketMQConsumerFactory() {
        return new RocketMQConsumerFactory(rocketMQConfig(),rocketMQListenerFactory);
    }

    @Bean
    public RocketMQProducerFactory rocketMQProducerFactory() {
        rocketMQProducerFactory =  new RocketMQProducerFactory(rocketMQConfig());
        return rocketMQProducerFactory;
    }


    public RocketMQListenerFactory getRocketMQListenerFactory(){
        return rocketMQListenerFactory;
    }

    public RocketMQProducerFactory getRocketMQProducerFactory(){
        return rocketMQProducerFactory;
    }


    private String nameSrvAdds;
    private String instanceName;
    private int maxMessageSize;
    private int sendMsgTimeout;
    private int retryTimesWhenSendFailed;
    private int consumeThreadMin;
    private int consumeThreadMax;

    private ClientLog client = new ClientLog().systemProperty();


    public String getNameSrvAdds() {
        return nameSrvAdds;
    }

    public void setNameSrvAdds(String nameSrvAdds) {
        this.nameSrvAdds = nameSrvAdds;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public int getMaxMessageSize() {
        return maxMessageSize;
    }

    public void setMaxMessageSize(int maxMessageSize) {
        this.maxMessageSize = maxMessageSize;
    }

    public int getSendMsgTimeout() {
        return sendMsgTimeout;
    }

    public void setSendMsgTimeout(int sendMsgTimeout) {
        this.sendMsgTimeout = sendMsgTimeout;
    }

    public int getConsumeThreadMin() {
        return consumeThreadMin;
    }

    public void setConsumeThreadMin(int consumeThreadMin) {
        this.consumeThreadMin = consumeThreadMin;
    }

    public int getConsumeThreadMax() {
        return consumeThreadMax;
    }

    public void setConsumeThreadMax(int consumeThreadMax) {
        this.consumeThreadMax = consumeThreadMax;
    }

    public int getRetryTimesWhenSendFailed() {
        return retryTimesWhenSendFailed;
    }

    public void setRetryTimesWhenSendFailed(int retryTimesWhenSendFailed) {
        this.retryTimesWhenSendFailed = retryTimesWhenSendFailed;
    }

    public ClientLog getClient() {
        return client;
    }

    public void setClient(ClientLog client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "RocketMQConfig{" +
                "nameSrvAdds='" + nameSrvAdds + '\'' +
                ", instanceName='" + instanceName + '\'' +
                ", maxMessageSize=" + maxMessageSize +
                ", sendMsgTimeout=" + sendMsgTimeout +
                ", retryTimesWhenSendFailed=" + retryTimesWhenSendFailed +
                ", consumeThreadMin=" + consumeThreadMin +
                ", consumeThreadMax=" + consumeThreadMax +
                ", client=" + client.toString() +
                '}';
    }

    public static class ClientLog{

        private String logRoot = "/data/server/rocketMq/log";
        private String logLevel = "INFO";
        private int logFileMaxIndex = 10;


        @PostConstruct
        public ClientLog systemProperty(){
            System.setProperty(ClientLogger.CLIENT_LOG_ROOT,logRoot);
            System.setProperty(ClientLogger.CLIENT_LOG_LEVEL,logLevel);
            System.setProperty(ClientLogger.CLIENT_LOG_MAXINDEX,logFileMaxIndex+"");
            return this;
        }

        public String getLogRoot() {
            return logRoot;
        }

        public void setLogRoot(String logRoot) {
            this.logRoot = logRoot;
        }

        public String getLogLevel() {
            return logLevel;
        }

        public void setLogLevel(String logLevel) {
            this.logLevel = logLevel;
        }

        public int getLogFileMaxIndex() {
            return logFileMaxIndex;
        }

        public void setLogFileMaxIndex(int logFileMaxIndex) {
            this.logFileMaxIndex = logFileMaxIndex;
        }

        @Override
        public String toString() {
            return "ClientLog{" +
                    "logRoot='" + logRoot + '\'' +
                    ", logLevel='" + logLevel + '\'' +
                    ", logFileMaxIndex=" + logFileMaxIndex +
                    '}';
        }
    }
}
