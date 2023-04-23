package com.suven.framework.core.redis.cluster;


import java.util.HashMap;
import java.util.Map;

public enum RedisClientModeEnum {

    CLUSTER(1,"cluster","redisCluster","集群模式"),
    SENTINEL(2,"sentinel","redisSentinel","哨兵模式"),
    TEMPLATE(3,"template","redisClient","单结点模式"),
    CLUSTER_GROUP(4,"group","redisClusterGroup","多集群组模式")
    ;


    private int id;
    private String value;
    private String desc;
    private String beanName;

    private  static Map<String, RedisClientModeEnum> typeMap = new HashMap<>();
    static {
        for(RedisClientModeEnum msgType : values()) {
            typeMap.put(msgType.value, msgType);
        }
    }
    RedisClientModeEnum(int id , String value,String beanName, String desc){
        this.id = id;
        this.value = value;
        this.beanName = beanName;
        this.desc = desc;
    }

    public static RedisClientModeEnum getEnum(String index){
        return typeMap.get(index);
    }

    public static Boolean isCluster(String cluster){
        if(null == cluster || "".equals(cluster)){
            cluster = CLUSTER.value;
        }
        if (cluster.equals(CLUSTER.value)){
            return true;
        }
        return false;
    }
    public static Boolean isGroup(String cluster){
        if(null == cluster || "".equals(cluster)){
            return false;
        }
        if (cluster.equals(CLUSTER_GROUP.value)){
            return true;
        }
        return false;
    }
    public static Boolean isSentinel(String cluster){
        if(null == cluster || "".equals(cluster)){
            return false;
        }
        if (cluster.equals(SENTINEL.value)){
            return true;
        }
        return false;
    }

    public int index() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public String getBeanName() {
        return beanName;
    }
}
