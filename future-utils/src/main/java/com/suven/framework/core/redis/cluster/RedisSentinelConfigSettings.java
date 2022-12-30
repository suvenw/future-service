package com.suven.framework.core.redis.cluster;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.suven.framework.common.constants.GlobalConfigConstants.SPRING_REDIS_AUTO_CONFIG;
import static com.suven.framework.common.constants.GlobalConfigConstants.SPRING_REDIS_AUTO_CONFIG_ENABLED;


@Configuration
@ConfigurationProperties(prefix = SPRING_REDIS_AUTO_CONFIG)
@ConditionalOnWebApplication
@ConditionalOnProperty(name = SPRING_REDIS_AUTO_CONFIG_ENABLED, havingValue = "true", matchIfMissing = false)
public class RedisSentinelConfigSettings {

    private String host;
    private int database;
    private String password;
    private String clientName;
    private int port;
    private int timeout = 1000;
    private int maxRedirections = 2;
    private RedisPoolSettings pool = new RedisPoolSettings();


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getMaxRedirections() {
        return maxRedirections;
    }

    public void setMaxRedirections(int maxRedirections) {
        this.maxRedirections = maxRedirections;
    }

    public RedisPoolSettings getPool() {
        return pool;
    }

    public void setPool(RedisPoolSettings pool) {
        this.pool = pool;
    }





    public static class RedisPoolSettings{
        private int maxTotal = 1000; //可用连接实例的最大数目，默认值为8；如果赋值为-1，则表示不限制；
        private int maxIdle = 100 ; //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值是8。
        private int minIdle = 10; //控制一个pool最少有多少个状态为idle(空闲的)的jedis实例，默认值是0。
        private long maxWaitMillis = 1000;//等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出

        private boolean testOnBorrow = true;
        private boolean testOnReturn = true;

        public int getMaxTotal() {
            return maxTotal;
        }

        public void setMaxTotal(int maxTotal) {
            if(maxTotal > 0){
                this.maxTotal = maxTotal;
            }
        }

        public int getMaxIdle() {
            return maxIdle;
        }

        public void setMaxIdle(int maxIdle) {
            if(maxIdle > 0)
            this.maxIdle = maxIdle;
        }

        public int getMinIdle() {
            return minIdle;
        }

        public void setMinIdle(int minIdle) {
            if(minIdle > 0)
            this.minIdle = minIdle;
        }

        public long getMaxWaitMillis() {
            return maxWaitMillis;
        }

        public void setMaxWaitMillis(long maxWaitMillis) {
            if(maxWaitMillis > 0)
            this.maxWaitMillis = maxWaitMillis;
        }

        public boolean isTestOnBorrow() {
            return testOnBorrow;
        }

//        public void setTestOnBorrow(boolean testOnBorrow) {
//            this.testOnBorrow = testOnBorrow;
//        }

        public boolean isTestOnReturn() {
            return testOnReturn;
        }

//        public void setTestOnReturn(boolean testOnReturn) {
//            this.testOnReturn = testOnReturn;
//        }


    }


}
