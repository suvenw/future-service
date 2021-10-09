package com.suven.framework.core.redis.cluster;

import com.suven.framework.common.constants.GlobalConfigConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = GlobalConfigConstants.REDIS_AUTO_CONFIG)
@ConditionalOnWebApplication
@ConditionalOnProperty(name = GlobalConfigConstants.REDIS_AUTO_CONFIG_ENABLED, havingValue = "true", matchIfMissing = true)
public class RedisConfigSettings {

    private String servers;
    private String serverName;
    private String database;
    private String password;
    private int port;
    private int timeout = 1000;
    private int maxRedirections = 2;
    private RedisPoolSettings pool = new RedisPoolSettings();
    private RedisClientSettings client = new RedisClientSettings();


    public String getServers() {
        return servers;
    }

    public void setServers(String servers) {
        this.servers = servers;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
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

    public RedisClientSettings getClient() {
        return client;
    }

    public void setClient(RedisClientSettings client) {
        this.client = client;
    }

    public static class RedisClientSettings{
        private boolean write;
        private boolean read;
        private boolean daoOpen;

        public boolean isWrite() {
            return write;
        }

        public void setWrite(boolean write) {
            this.write = write;
        }

        public boolean isRead() {
            return read;
        }

        public RedisClientSettings setRead(boolean read) {
            this.read = read;
            return this;
        }

        public boolean isDaoOpen() {
            return daoOpen;
        }

        public void setDaoOpen(boolean daoOpen) {
            this.daoOpen = daoOpen;
        }
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
