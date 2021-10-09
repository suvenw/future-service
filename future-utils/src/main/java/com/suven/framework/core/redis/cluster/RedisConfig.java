package com.suven.framework.core.redis.cluster;

import com.suven.framework.common.constants.GlobalConfigConstants;

public interface RedisConfig {

    String REDIS_AUTO_CONFIG_PREFIX = GlobalConfigConstants.REDIS_AUTO_CONFIG;
    String REDIS_CLUSTER_SERVERS = GlobalConfigConstants.REDIS_CONFIG_CLUSTER_SERVERS;
    String REDIS_CLUSTER_PASSWORD =  GlobalConfigConstants.REDIS_CONFIG_CLUSTER_PASSWORD;

    String REDIS_CLUSTER_PARAM_SERVER = "server";
    String REDIS_CLUSTER_PARAM_PASSWORD = "password";
    String REDIS_CLUSTER_PARAM = "settings";
}
