package com.suven.framework.core.redis;

import com.suven.framework.common.cat.CatCacheKeySign;
import com.suven.framework.common.cat.CatCacheSign;
import com.suven.framework.common.constants.CatTopConstants;
import com.suven.framework.core.redis.cluster.RedisClusterInterface;
import com.suven.framework.util.json.SerializableUtil;
import redis.clients.util.SafeEncoder;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Author Joven.wang
 * Version V1.0
 * Title: RedisClusterServer.java
 * Date 2016年7月27日
 * Description: TODO(说明)
 */
@Component
public class RedisClusterServer extends RedisClusterFactoryRouter {




    /**
     * 获取前缀key 实现
     *
     * @param clazz 类对象
     * @return String 字符串
     */
    protected String getPrefixKey(Class<?> clazz) {
        return null;
    }



	/*================== write ================================*/

    /**
     * 添加到缓存列表
     *
     * @param key 缓存key
     * @param value 缓存值
     * @return String 返回字符串
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public String set(@CatCacheKeySign final String key, byte[] value) {
        if (null == key || !isOpenWrite()) {
            return null;
        }
        try {
            String result =   this.getRedisClusterFactoryBykey(key).set(key.getBytes(), value);
            return result;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by set Modifier key:[{}]  , value:[{}] , Exception: [{}] ",key,value, e);
        }
       return null;

    }

    /**
     * 添加到缓存列表
     *
     * @param key 缓存key
     * @param value 缓存值
     * @return String 返回字符串
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public String set(@CatCacheKeySign final String key, String value) {
        if (null == key || !isOpenWrite()) {
            return null;
        }
        try {
            String result =   this.getRedisClusterFactoryBykey(key).set(key, value);
            return result;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by set Modifier key:[{}]  , value:[{}] , Exception: [{}] ",key,value, e);
        }
        return null;


    }


    /**
     * 返回给定 key 的旧值。 当 key 没有旧值时，即 key 不存在时，返回 nil 。
     * 当 key 存在但不是字符串类型时，返回一个错误。
     * @param key 缓存key值
     * @param value 缓存value值
     * @return String 返回字符串
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public String getSet(@CatCacheKeySign final String key, String value) {
        if (null == key || !isOpenWrite()) {
            return null;
        }
        try {
            String result =   this.getRedisClusterFactoryBykey(key).getSet(key, value);
            return result;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by getSet Modifier key:[{}]  , value:[{}] , Exception: [{}] ",key,value, e);
        }
        return null;

    }
	/**
	 * （SET if Not eXists） 命令在指定的 key 不存在时，为 key 设置指定的值。
	 *  设置成功，返回 1 。 设置失败，返回 0 。
	 * @param key 缓存key
	 * @param value 类型：NX | XX,NX ：只在键不存在时，才对键进行设置操作。 SET key value NX 效果等同于 SETNX key value 。
	 * @param second 缓存时间为秒 EX = seconds; PX = milliseconds
	 * @return 返回是否设置成功
     * Set the string value as value of the key. The string can't be longer than 1073741824 bytes (1
     * GB).
     * param nxxx NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key
     *          if it already exist.
     * param expx EX|PX, expire time units: EX = seconds; PX = milliseconds
     * param time expire time in the units of <code>expx</code>
     * @return Status code reply
     */
	@CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public boolean setNx(@CatCacheKeySign final String key, String value, int second){
        if (null == key || !isOpenWrite()) {
            return false;
        }
        try {
            String result = getRedisClusterFactoryBykey(key).setex(key,second, value );
            return isOK(result);
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by getSet Modifier key:[{}]  , value:[{}] , Exception: [{}] ",key,value, e);
        }
        return false;
    }



    /**
     * 按过期时间添加到缓存列表,默认是3天
     *
     * @param key 缓存key
     * @param value 缓存value
     * @return String
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public String setex(@CatCacheKeySign final String key, String value) {
        if (null == key || !isOpenWrite()) {
            return null;
        }
        try {
            String result =   this.getRedisClusterFactoryBykey(key).set(key, value);//RedisConstants.ONE_DAY;
            return result;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by setex Modifier key:[{}]  , value:[{}] , Exception: [{}] ",key,value, e);
        }
        return null;
    }

    /**
     * 按过期时间添加到缓存列表
     *
* @param key 缓存key值
* @param value 缓存value值
     * @return boolean 结果
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public boolean setex(@CatCacheKeySign final String key, String value, int secondsTime) {
        if (null == key || !isOpenWrite()) {
            return false;
        }
        try {
            String result =  this.getRedisClusterFactoryBykey(key).setex(key, secondsTime, value);
            return isOK(result);
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by setex Modifier key:[{}]  , value:[{}] , Exception: [{}] ",key,value, e);
        }
        return false;
    }

    /**
     * 按过期时间添加到缓存列表,默认是3天
     *
* @param key 缓存key值
* @param value 缓存value值
     * @return String 结果
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public String setex(@CatCacheKeySign final  byte[] key, byte[] value, int secondsTime) {
        if (null == key || !isOpenWrite()) {
            return null;
        }
        try {
            String result =   this.getRedisClusterFactoryBykey(key).setex(key, secondsTime, value);
            return result;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by setex Modifier key:[{}]  , value:[{}] , secondsTime:[{}] , Exception: [{}] "
                    ,key,value,secondsTime, e);
        }
        return null;
    }
    /**
     * 按过期时间添加到缓存列表,默认是3天
     *
    * @param key 缓存key值
    * @param value 缓存value值
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public String setex(@CatCacheKeySign final  String key, byte[] value, int secondsTime) {
        if (null == key || !isOpenWrite()) {
            return null;
        }
        try {
            String result =  this.getRedisClusterFactoryBykey(key).setex(key.getBytes(), secondsTime, value);
            return result;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by setex Modifier key:[{}]  , value:[{}] , Exception: [{}] ",key,value, e);
        }
        return null;

    }

    /**
     * 删除指定key 的缓存值;
     * @param key
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public long del(@CatCacheKeySign final  String key) {
        if (null == key || !isOpenWrite()) {
            return 0;
        }
        try {
            long result =  this.getRedisClusterFactoryBykey(key).del(key.getBytes());
            return result;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by del Modifier key:[{}] , Exception: [{}] ",key, e);
        }
        return 0;
    }

    /**
     * 批量删除redis key,已使用重写3.0分析实现key,实现功能
     * @param keys
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public void del(@CatCacheKeySign final  String... keys) {
        if (null == keys || !isOpenWrite()) {
            return;
        }
        // 已接入批量 3.0批量删除底层暂不支持,由时进度,先批量删除;
        if (keys.length > 0) {
            try {
                List listKey = Arrays.asList(keys);
                RedisClusterInterface rc = getRedisClusterFactoryBykey(keys[0]);
                rc.refinedDel(listKey);
            }catch ( Exception e){
                logger.warn(" Redis Cluster Server by del Modifier key:[{}] , Exception: [{}] ",Arrays.asList(keys), e);
            }

        }
    }

    /**
     *  当 key 不存在时，返回 -2 。
     *  当 key 存在但没有设置剩余生存时间时，返回 -1 。
     *  否则，以秒为单位，返回 key 的剩余生存时间。
     * @param key 缓存key
     * @return long 结果
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public long ttl(@CatCacheKeySign final  String key) {
        if (null == key || !isOpenWrite()) {
            return 0;
        }
        try {
            Long data = getRedisClusterFactoryBykey(key).ttl(key);
            data = (data == null) ? 0L : data;
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by ttl Modifier key:[{}] , Exception: [{}] ",key, e);
        }
       return 0;
    }

    /**
     * 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。
     * 如果 key 已经存在， SETEX 命令将覆写旧值。
     * @param data 缓存数据
     * @param seconds  缓存时间
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public void multiSetex(@CatCacheKeySign final  Map<byte[], byte[]> data, int seconds) {
        if (data == null || !isOpenWrite()) {
            return;
        }
        //快速开发问题,暂时使用循环实现
        String key = new String(data.keySet().iterator().next());
        RedisClusterInterface rc = getRedisClusterFactoryBykey(key);
        Map<String, byte[]> kvMap = new HashMap();
        data.entrySet().stream().forEach(kv -> {
            kvMap.put(new String(kv.getKey()), kv.getValue());
        });
        rc.refinedMset(kvMap,seconds);


    }

    /**
     *
     * 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。
     * 如果 key 已经存在， SETEX 命令将覆写旧值。
     * @param prefix  自定义的前缀key
     * @param kvMap   原始数据map集合
     * @param seconds 设置过期秒数;
     * @param <V>     泛型,map 可以传任意类型
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public <V> void  multiSetex(@CatCacheKeySign final  String prefix, Map<String, V> kvMap, int seconds) {
        if (prefix == null || kvMap == null || !isOpenWrite()) {
            return;
        }

        final RedisClusterInterface redisCluster = getRedisClusterFactoryBykey(prefix);
        Map<String, byte[]> map = new HashMap();
        kvMap.entrySet().stream().forEach(kv -> {
            map.put(prefix + kv.getKey(), SerializableUtil.toBytes(kv.getValue()));
        });
        redisCluster.refinedMset(map,seconds);
    }

    /**
     * 如果某个给定 key 已经存在，那么 MSET 会用新值覆盖原来的旧值，
     * 如果这不是你所希望的效果，请考虑使用 MSETNX 命令：它只会在所有给定 key 都不存在的情况下进行设置操作。
     * @param prefix  自定义的前缀key
     * @param kvMap   原始数据map集合
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public <K, V> void mset(@CatCacheKeySign final  String prefix, Map<K, V> kvMap, int seconds) {
        if (null == prefix || !isOpenWrite()) {
            return;
        }
        RedisClusterInterface rc = getRedisClusterFactoryBykey(prefix);
        Map<String, byte[]> map = new HashMap();
        kvMap.entrySet().stream().forEach(kv -> {
            K k = kv.getKey();
            String key = prefix + k;
            if(k instanceof byte[]){
                key = prefix + new String((byte[]) k);
            }
            map.put(key, SerializableUtil.toBytes(kv.getValue()));
        });
        rc.refinedMset(map,seconds);

    }

    /**
     * 如果某个给定 key 已经存在，那么 MSET 会用新值覆盖原来的旧值，
     * 如果这不是你所希望的效果，请考虑使用 MSETNX 命令：它只会在所有给定 key 都不存在的情况下进行设置操作。
     * @param kvMap   组装后的数据map集合
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public void mset(@CatCacheKeySign final  Map<String, byte[]> kvMap,int seconds) {
        if (null == kvMap || !isOpenWrite()) {
            return;
        }
        String key = kvMap.keySet().iterator().next();
        RedisClusterInterface rc = getRedisClusterFactoryBykey(key);
        if(seconds > 0){
            rc.refinedMset(kvMap,seconds);
        }else {
            rc.refinedMset(kvMap);
        }
        
    }

    /**
     * 设置key多少秒后失效
     * @param key 如果生存时间设置成功，返回 1 。当 key 不存在或没办法设置生存时间，返回 0 。
     * @param unixTime 设置失效秒量 RedisConstants.属性为值
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public void expireAt(@CatCacheKeySign final  String key, long unixTime) {
        if (null == key || !isOpenWrite()) {
            return;
        }
        try {
            getRedisClusterFactoryBykey(key).expireAt(key, unixTime);
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by expireAt Modifier key:[{}] ,unixTime:[{}] , Exception: [{}] ",key, unixTime,e);
        }

    }


    /**
     * 设置key多少秒后失效
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
     * @param key
     * @param
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public long expire(@CatCacheKeySign final  String key, int unixTime) {
        if (null == key || !isOpenWrite()) {
            return 0;
        }
        try {
           return getRedisClusterFactoryBykey(key).expire(key, unixTime);
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by expireAt Modifier key:[{}] ,unixTime:[{}] , Exception: [{}] ",key, unixTime,e);
        }
        return 0;
    }




    /**
     * 批量设置key多少秒后失效
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
     * @param expireMap  组装好的数据map集合
     */

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public void multiExpire(@CatCacheKeySign final  Map<String, Integer> expireMap) {
        if (null == expireMap || !isOpenWrite()) {
            return;
        }
        String key = expireMap.keySet().iterator().next();
        RedisClusterInterface rc = getRedisClusterFactoryBykey(key);
        rc.refinedExpire(expireMap);
    }

    /**
     * 批量设置key多少秒后失效
     * 为给定 key 设置生存时间，当 key 过期时(生存时间为 0 )，它会被自动删除。
     * @param prefix  自定义的前缀key
     * @param expireMap  原始数据map集合
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public void multiExpire(@CatCacheKeySign final  String prefix, Map<String, Integer> expireMap) {
        if (null == expireMap || !isOpenWrite()) {
            return;
        }
        Map<String,Integer> kvMap = new HashMap();
        expireMap.entrySet().stream().forEach(kv -> {
            kvMap.put(prefix + kv.getKey(), kv.getValue());
        });
        final RedisClusterInterface redisCluster = getRedisClusterFactoryBykey(prefix);
        redisCluster.refinedExpire(kvMap);
    }


    /**
     * 将 key 的值设为 value ，当且仅当 key 不存在。
     * 若给定的 key 已经存在，则 SETNX 不做任何动作。
     * @param key 设置到缓存key值
     * @param value
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public long setnx(@CatCacheKeySign final  String key, String value) {
        if (null == key || !isOpenWrite()) {
            return 0;
        }
        try {
            Long data = getRedisClusterFactoryBykey(key).setnx(key, value);
            data = (data == null) ? 0L : data;
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by setnx Modifier key:[{}] , value:[{}] , Exception: [{}] ",key,value, e);
        }
        return 0;
    }


    /**
     * 将 key 中储存的数字值增一。
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
     * @param key 设置到缓存key值
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public long incr(@CatCacheKeySign final  String key) {
        if (null == key || !isOpenWrite()) {
            return 0;
        }
        try {
            Long data = getRedisClusterFactoryBykey(key).incr(key);
            data = (data == null) ? 0L : data;
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by incr Modifier key:[{}] ,  Exception: [{}] ",key, e);
        }
        return 0;
    }

    /**
     * 将 key 中储存的数字值减一。
     * 如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
     * @param key 设置到缓存key值
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public long decr(@CatCacheKeySign final  String key) {
        if (null == key || !isOpenWrite()) {
            return 0;
        }
        try {
            Long data = getRedisClusterFactoryBykey(key).decr(key);
            data = (data == null) ? 0L : data;
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by decr Modifier key:[{}] ,  Exception: [{}] ",key, e);
        }
        return 0;
    }


    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public long incr(@CatCacheKeySign final  String key,int time,boolean refreshTime) {
        if (null == key || !isOpenWrite()) {
            return 0;
        }
        try {
            Long data = this.getRedisClusterFactoryBykey(key).refinedIncrByExpire(key,1,time,false);
            data = (data == null) ? 0L : data;
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by incr Modifier key:[{}] , time:[{}] ,  Exception: [{}] ",key,time, e);
        }
        return 0;
    }

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public long decr(@CatCacheKeySign final  String key,int time,boolean refreshTime) {
        if (null == key || !isOpenWrite()) {
            return 0;
        }
        try {
            long data = this.getRedisClusterFactoryBykey(key).refinedIncrByExpire(key,-1,time,false);
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by decr Modifier key:[{}] , time:[{}] ,  Exception: [{}] ",key,time, e);
        }
        return 0;
    }



    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。
     * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表尾
     * @param key 缓存key
     * @param value  增加到缓存中的字符串集合数组
     * @return 成功的数量
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public long rpush(@CatCacheKeySign final  String key, String... value) {
        if (null == key || !isOpenWrite()) {
            return 0;
        }
        try {
            Long data = getRedisClusterFactoryBykey(key).rpush(key, value);
            data = (data == null) ? 0L : data;
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by rpush Modifier key:[{}] , value:[{}] ,  Exception: [{}] ",key,Arrays.asList(value), e);
        }
        return 0;

    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。
     * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表尾
     * @param key 缓存key
     * @param value  增加到缓存中的字节数组
     * @return 成功的数量
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public long rpushByte(@CatCacheKeySign final  String key, byte[]... value) {
        if (null == key || !isOpenWrite()) {
            return 0;
        }
        try {
            Long data = getRedisClusterFactoryBykey(key).rpush(key.getBytes(), value);
            data = (data == null) ? 0L : data;
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by rpushByte Modifier key:[{}] , value:[{}] ,  Exception: [{}] ",key,Arrays.asList(value), e);
        }
        return 0;
    }


    /**
     * 将一个或多个值 value 插入到列表 key 的表尾(最右边)。
     * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表尾
     * @param key 缓存key
     * @param expireSeconds  缓存key的有效时间,单位是秒;使用 RedisConstants 属性参数
     * @param value  增加到缓存中的字节数组
     * @return 成功的数量
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public long rpushWithExpire(@CatCacheKeySign final  String key, int expireSeconds, Object value) {
        if (null == key || null == value || !isOpenWrite()) {
            return 0;
        }
        try {
            RedisClusterInterface rc = getRedisClusterFactoryBykey(key);
            Long data = rc.rpush(key.getBytes(), SerializableUtil.toBytes(value));
            rc.expire(key, expireSeconds);
            data = data == null ? 0 : data;
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by rpushWithExpire Modifier key:[{}] , value:[{}] ,  Exception: [{}] ",key,value, e);
        }
        return 0;


    }

    /**
     * 将一个或多个值 value 插入到列表 key 的表头
     * 如果有多个 value 值，那么各个 value 值按从左到右的顺序依次插入到表头：
     **/
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public long lpush(@CatCacheKeySign final  byte[] key, byte[] value) {
        if (null == key || !isOpenWrite()) {
            return 0l;
        }
        try {
            Long data = getRedisClusterFactoryBykey(key).lpush(key, value);
            data = data == null ? 0 : data;
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by lpush Modifier key:[{}] , value:[{}] ,  Exception: [{}] ",key,value, e);
        }
        return 0;
    }

    /**
     * 根据参数 count 的值，移除列表中与参数 value 相等的元素。
     *  count 的值可以是以下几种：
     *  count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
     *  count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
     *  count = 0 : 移除表中所有与 value 相等的值。
     * @param key 缓存key
     * @param count 移除列表中与参数个数
     * @param value
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public long lrem(@CatCacheKeySign final  String key, long count, String value) {
        if (null == key || !isOpenWrite()) {
            return 0;
        }
        try {
            Long data = getRedisClusterFactoryBykey(key).lrem(key, count, value);
            data = data == null ? 0 : data;
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by lrem Modifier key:[{}] , value:[{}] ,  Exception: [{}] ",key,value, e);
        }
        return 0;
    }

    /**
     * @param key  将列表 key 下标为 index 的元素的值设置为 value 。
     * @param index 当 index 参数超出范围，或对一个空列表( key 不存在)进行 LSET 时，返回一个错误。
     * @param value
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public String lset(@CatCacheKeySign final  String key, int index, String value) {
        if (null == key || !isOpenWrite()) {
            return null;
        }
        try {
            String result = getRedisClusterFactoryBykey(key).lset(key, index, value);
            return result;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by lset Modifier key:[{}] , value:[{}] ,  Exception: [{}] ",key,value, e);
        }
        return null;
    }


     // ================= redis SortedSet（有序集合） ===================


    /**
     * 移除有序集 key 中的一个或多个成员，不存在的成员将被忽略。
     * 当 key 存在但不是有序集类型时，返回一个错误。
     * @param key
     * @param
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public long zrem(@CatCacheKeySign final  String key, String... members) {
        if (null == key || !isOpenWrite()) {
            return 0;
        }
        try {
            Long data = this.getRedisClusterFactoryBykey(key).zrem(key, members);
            data = data == null ? 0 : data;
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by zrem Modifier key:[{}] , value:[{}] ,  Exception: [{}] ",key,Arrays.asList(members), e);
        }
        return 0;
    }

    /**
     * 移除有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。
     * @param key
     * @param start
     * @param end
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public long zremrangeByScore(@CatCacheKeySign final  String key, double start, double end) {
        if (null == key || !isOpenWrite()) {
            return 0;
        }
        try {
            Long data = getRedisClusterFactoryBykey(key).zremrangeByScore(key, start, end);
            data = data == null ? 0 : data;
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by zrem Modifier key:[{}] , start:[{}] ,end:[{}] ,  Exception: [{}] ",key,start,end, e);
        }
        return 0;
    }


    /**
     * 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。
     * 假如 key 不存在，则创建一个只包含 member 元素作成员的集合。
     * @param key
     * @param member
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
     public Long sadd(@CatCacheKeySign final String key, String... member) {
        if (null == key || !isOpenWrite()) {
            return 0l;
        }
        try {
            Long data = getRedisClusterFactoryBykey(key).sadd(key, member);
            data = data == null ? 0 : data;
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by zrem Modifier key:[{}] , member:[{}],  Exception: [{}] ",key,Arrays.asList(member), e);
        }
        return 0L;
    }

    /**
     * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。
     * 当 key 不是集合类型，返回一个错误。
     * @param key
     * @param member
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public Long srem(@CatCacheKeySign final String key, String... member) {
        if (null == key || !isOpenWrite()) {
            return 0l;
        }
        try {
            Long data = getRedisClusterFactoryBykey(key).srem(key, member);
            data = data == null ? 0 : data;
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by zrem Modifier key:[{}] , member:[{}],  Exception: [{}] ",key,Arrays.asList(member), e);
        }
        return 0L;
    }

    /**
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
     如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
     score 值可以是整数值或双精度浮点数。
     如果 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
     * @param key
     * @param score
     * @param member
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public int zadd(@CatCacheKeySign final  String key, long score, String member) {
        if (null == key || !isOpenWrite()) {
            return 0;
        }
        try {
            Long data = getRedisClusterFactoryBykey(key).zadd(key, score, member);
            int result = (data == null) ? 0 : data.intValue();
            return result;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by zadd Modifier key:[{}] ,score:[{}], member:[{}],  Exception: [{}] ",key,score,member, e);
        }
        return 0;
    }

    /**
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
     如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
     score 值可以是整数值或双精度浮点数。
     如果 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
     * @param key
     * @param scoreMembers 由member 和score 组成的集合map
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public int zadd(@CatCacheKeySign final String key, Map<String, Double> scoreMembers) {
        if (null == key || !isOpenWrite()) {
            return 0;
        }
        try {
            Integer data = getRedisClusterFactoryBykey(key).zadd(key, scoreMembers).intValue();
            int result = (data == null) ? 0 : data.intValue();
            return result;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by zadd Modifier key:[{}] ,scoreMembers:[{}],   Exception: [{}] ",key,scoreMembers, e);
        }
        return 0;
    }

    /**
     * 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
     如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
     score 值可以是整数值或双精度浮点数。
     如果 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
     * @param key
     * @param scoreMembers 由member 和score 组成的集合map
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public int zadd(@CatCacheKeySign final  byte[] key, Map<byte[], Double> scoreMembers) {
        if (null == key || !isOpenWrite()) {
            return 0;
        }
        try {
            Integer data = getRedisClusterFactoryBykey(key).zadd(key, scoreMembers).intValue();
            int result = (data == null) ? 0 : data.intValue();
            return result;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by zadd Modifier key:[{}] ,scoreMembers:[{}],   Exception: [{}] ",key,scoreMembers, e);
        }
        return 0;
    }



    /**
     * 批量将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
     * 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该 member 在正确的位置上。
     * score 值可以是整数值或双精度浮点数。
     * @param sortSetMap
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public void multiZSet(@CatCacheKeySign final Map<String, Map<String, Double>> sortSetMap) {
        if (sortSetMap == null || sortSetMap.isEmpty() || !isOpenWrite()) {
            return;
        }
        sortSetMap.keySet().stream().filter(StringUtils::isNotEmpty).findAny()
                .ifPresent(key -> getRedisClusterFactoryBykey(key).refinedZadd(sortSetMap));
    }

	/*================== read ================================*/

    /**
     * 获取返回 key 所关联的字符串值。
     * @param key
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public String get(@CatCacheKeySign final String key) {
        if (null == key || !isOpenRead()) {
            return "";
        }
        try {
            String result =   this.getRedisClusterFactoryBykey(key).get(key);
            return result;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by get Modifier key:[{}]  ,  Exception: [{}] ",key, e);
        }
        return null;

    }

    /**
     * 获取返回 key 所关联的字符串值。
     * @param key
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public byte[] getByte(@CatCacheKeySign final String key) {
        if (null == key || !isOpenRead()) {
            return null;
        }
        try {
            byte[] data = getRedisClusterFactoryBykey(key).get(key.getBytes());
            if (data == null || data.length == 0) {//设置默认初始值
                return null;
            }
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by getByte Modifier key:[{}]  ,  Exception: [{}] ",key, e);
        }
        return null;

    }

    /**
     * 通过key获取返回指定的对象
     *
     * @param fildKey
     * @param clazz
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public <T> T getT(@CatCacheKeySign final String fildKey, Class<T> clazz) {
        if (null == fildKey || !isOpenRead()) {
            return null;
        }
        try {
            byte[] data = this.getByte(fildKey);
            if(null == data){
                return null;
            }
            return SerializableUtil.parseValue(clazz, data);
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by getT Modifier key:[{}],clazz:[{}] , Exception: [{}] ",fildKey,clazz, e);
        }
        return null;

    }

    /**
     * 批量获取kv数据，传入多个key
     * list参数为所需查询的kv结构的key后接参数集合，最终key为api内部循环拼接prefix+list.get(i); clazz为返回的类型
     * eg: String.class ,list return : Map<String,String> map为可以查询到的数据map对应KV集合,
     * 外部调用者可关注list余留数据是否为0，等于0则全部匹配到，否则list中数据即为未匹配到的.
     *
     * @param clazz
     * @param
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public <K, V> Map<K, V> mget(Class<V> clazz, @CatCacheKeySign final  String prefix, Collection<K> set, boolean isRemove) {

        if (set == null || set.isEmpty()) {
            return new HashMap<K, V>();
        }
        if (!isOpenRead()) {
            return new HashMap<K, V>();
        }

        Map<K, V> map = new LinkedHashMap<>(set.size());
        List<String> list = new ArrayList<>();
        for (K k : set) {
            list.add(prefix + k);
        }
        RedisClusterInterface rc = getRedisClusterFactoryBykey(prefix);
        Map<String, byte[]> rMap = rc.refinedMget(list);
        Iterator<K> it = set.iterator();
        while (it.hasNext()) {
            K k = it.next();
            byte[] bytes = rMap.get(prefix + k);
            if (bytes != null && bytes.length > 0) {
                V v = SerializableUtil.parseValue(clazz, bytes);
                if (null != v) {
                    map.put(k, v);
                    if (isRemove) {
                        it.remove();
                    }
                }
            }
        }
        return map;

    }

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public Map<Long, Long> mgetLong(@CatCacheKeySign final  String prefix, Collection<Long> keys) {
        if (prefix == null || keys == null || !isOpenRead()) {
            return new HashMap<>();
        }

        List<String> keysWithPrefix = keys.stream().map(key -> prefix + key).collect(Collectors.toList());
        Map<String, byte[]> kvMap = getRedisClusterFactoryBykey(prefix).refinedMget(keysWithPrefix);
        if (kvMap == null || kvMap.isEmpty()) {
            return new HashMap<>();
        }

        Map<Long, Long> resultMap = new HashMap<>();
        for (Long key: keys) {
            byte[] bytes = kvMap.get(prefix + key);
            if (bytes != null) {
                try {
                    Long longValue = Long.valueOf(SafeEncoder.encode(bytes));
                    resultMap.put(key, longValue);
                } catch (NumberFormatException e) {
                    logger.error("error read long value for redis key:{}", prefix + key);
                }
            }
        }

        return resultMap;
    }

    /**
     * 检查给定 key 是否存在。若 key 存在，返回 1 ，否则返回 0 。
     * @param key
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public boolean exists(@CatCacheKeySign final String key) {
        if (null == key || !isOpenRead()) {
            return false;
        }
        try {
            boolean data = getRedisClusterFactoryBykey(key).exists(key);
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by exists Modifier key:[{}], Exception: [{}] ",key, e);
        }
        return false;

    }

    /**
     * 返回列表 key 中，下标为 index 的元素。
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     * @param key 缓存key
     * @param index 索引数据
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public String lindex(@CatCacheKeySign final  String key, long index) {
        if (null == key || !isOpenRead()) {
            return null;
        }
        try {
            String data = getRedisClusterFactoryBykey(key).lindex(key, index);
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by lindex Modifier key:[{}], Exception: [{}] ",key, e);
        }
        return null;
    }


    /**
     * 返回指定key的list的长度
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public long llen(@CatCacheKeySign final  String key) {
        if (null == key || !isOpenRead()) {
            return 0;
        }
        try {
            long data = getRedisClusterFactoryBykey(key).llen(key);
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by llen Modifier key:[{}], Exception: [{}] ",key, e);
        }
        return 0;
    }


    /**
     * 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
     * @param key
     * @param start
     * @param end
     * @param clazzType
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public <T> List<T> lrange(@CatCacheKeySign final  byte[] key, long start, long end, Class<T> clazzType) {
        List<T> resultList = new ArrayList<>();
        if (null == key || !isOpenRead()) {
            return resultList;
        }
        try {
            List<byte[]> list = getRedisClusterFactoryBykey(key).lrange(key, start, end);
            if (null == list || list.isEmpty()) {
                return resultList;
            }
            if (byte[].class.equals(clazzType)) {
                return (List<T>) list;
            }
            for (byte[] bytes : list) {
                if (bytes != null && bytes.length > 0) {
                    T t = SerializableUtil.parseValue(clazzType, bytes);
                    resultList.add(t);
                }
            }
            return resultList;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by lrange Modifier key:[{}], Exception: [{}] ",key, e);
        }
        return resultList;
    }


    /**
     * 返回有序集 key 中，指定区间内的成员。
     * 其中成员的位置按 score 值递减(从大到小)来排列。
     * 具有相同 score 值的成员按字典序的逆序(reverse lexicographical order)排列。
     * @param key
     * @param start
     * @param end
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public Set<String> zrevRange(@CatCacheKeySign final  String key, long start, long end) {
        if (null == key || !isOpenRead()) {
            return new HashSet<>();
        }
        try {
            Set<String> data = this.getRedisClusterFactoryBykey(key).zrevrange(key, start, end);
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by zrevRange Modifier key:[{}], Exception: [{}] ",key, e);
        }
        return null;

    }

    /***
     * 返回有序集合中member的得分
     * @param key
     * @param member
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public Double zscore(@CatCacheKeySign final  String key, String member) {
        if (null == key || !isOpenRead()) {
            return 0d;
        }
        try {
            Double data = this.getRedisClusterFactoryBykey(key).zscore(key, member);
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by zrevRange Modifier key:[{}], member:[{}], Exception: [{}] ",key,member, e);
        }
        return 0D;
    }


    /***
     * 返回有序集合的长度
     * @param key
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public int zcard(@CatCacheKeySign final  byte[] key) {
        if (null == key || !isOpenRead()) {
            return 0;
        }
        try {
            Long data = getRedisClusterFactoryBykey(key).zcard(key);
            int result = (data == null) ? 0 : data.intValue();
            return result;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by zcard Modifier key:[{}], Exception: [{}] ",key, e);
        }
        return 0;

    }

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public Map<String, Double> zrevRangeWithScore(@CatCacheKeySign final  String key, long start, long end) {
        Map<String, Double> map = new LinkedHashMap<>();
        if (null == key || !isOpenRead()) {
            return map;
        }
        try {
            Set<Tuple> data = getRedisClusterFactoryBykey(key).zrevrangeWithScores(key, start, end);
            for (Tuple t : data) {
                map.put(t.getElement(), t.getScore());
            }
            return map;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by zrevRangeWithScore Modifier key:[{}] , Exception: [{}] ",key, e);
        }
        return map;


    }


    /***
     * 按得分返回返回元素及元素的得分
     * @param key
     * @param min
     * @param max
     * @param offset
     * @param count
     * @param clazzKey
     * @param <K>
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public <K> Map<K, Double> zrangeByScoreWithScore(@CatCacheKeySign final  String key, double min, double max, int offset, int count,
                                                     Class<K> clazzKey) {
        Map<K, Double> map = new LinkedHashMap<>();
        if (null == key || !isOpenRead()) {
            return map;
        }
        try {
            Set<Tuple> data = getRedisClusterFactoryBykey(key).zrangeByScoreWithScores(key, min, max, offset, count);
            for (Tuple t : data) {
                K k = SerializableUtil.parseValue(clazzKey, t.getBinaryElement());
                map.put(k, t.getScore());
            }
            return map;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by zrangeByScoreWithScore Modifier key:[{}], Exception: [{}] ",key, e);
        }
        return map;

    }

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public Set<byte[]> zrange(byte[] key, long start, long end) {
        if (null == key || !isOpenRead()) {
            return new HashSet<>();
        }
        try {
            Set<byte[]> data =  getRedisClusterFactoryBykey(key).zrange(key, start, end);
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by zrange Modifier key:[{}] , Exception: [{}] ",key, e);
        }
        return new HashSet<>();
    }

    /***
     * 按位置范围获取有序集合中的元素
     * @param key
     * @param start
     * @param end
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public Set<String> zrange(@CatCacheKeySign final String key, long start, long end) {
        if (null == key || !isOpenRead()) {
            return new HashSet<>();
        }
        try {
            Set<String> data = getRedisClusterFactoryBykey(key).zrange(key, start, end);
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by zrange Modifier key:[{}] , Exception: [{}] ",key, e);
        }
        return null;
    }

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public Map<byte[], Double> zrangewithScore(@CatCacheKeySign final String key, long start, long end) {
        if (null == key || !isOpenRead()) {
            return new HashMap<>();
        }
        try {
            Set<Tuple> set =  getRedisClusterFactoryBykey(key).zrangeWithScores(key, start, end);
            Map<byte[], Double> map = new LinkedHashMap<byte[], Double>();
            for(Tuple t : set) {
                map.put(t.getBinaryElement(), t.getScore() );
            }
            return map;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by zrangewithScore Modifier key:[{}] , Exception: [{}] ",key, e);
        }
        return new HashMap<>();

    }

    /***
     * 为有序集合中的member的得分增加score
     * @param key
     * @param score
     * @param member
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public Double zincrby(@CatCacheKeySign final String key, long score, String member) {
        if (null == key || !isOpenRead()) {
            return 0d;
        }
        try {
            Double data = getRedisClusterFactoryBykey(key).zincrby(key, score, member);
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by zincrby Modifier key:[{}] , Exception: [{}] ",key, e);
        }
        return null;

    }

    /***
     * 按得分范围获取有序集合中的元素
     * @param key
     * @param min
     * @param max
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public Set<String> zrangeByScore(@CatCacheKeySign final String key, double min, double max) {
        if (null == key || !isOpenRead()) {
            return new HashSet<>();
        }
        try {
            Set<String>  data = getRedisClusterFactoryBykey(key).zrangeByScore(key, min, max);
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by zrangeByScore Modifier key:[{}] , Exception: [{}] ",key, e);
        }
        return new HashSet<>();
    }

    /***
     * 按得分范围倒序获取有序集合元素
     * @param key
     * @param min
     * @param max
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public Set<String> zrevrangeByScore(@CatCacheKeySign final String key, double min, double max) {
        if (null == key || !isOpenRead()) {
            return new HashSet<>();
        }
        try {
            Set<String>  data = getRedisClusterFactoryBykey(key).zrevrangeByScore(key, max, min);
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by zrangeByScore Modifier key:[{}] , Exception: [{}] ",key, e);
        }
        return new HashSet<>();
    }

    /***
     * 返回有序集合中得分在min-max之间的成员数量
     * @param key
     * @param min
     * @param max
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public Long zcount(@CatCacheKeySign final String key, double min, double max) {
        if (null == key || !isOpenRead()) {
            return 0l;
        }
        try {
           Long  data = getRedisClusterFactoryBykey(key).zcount(key, min, max);
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by zcount Modifier key:[{}] , Exception: [{}] ",key, e);
        }
        return 0L;
    }


    /***
     * 返回set的长度
     * @param key
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public Long scard(@CatCacheKeySign final String key) {
        if (null == key || !isOpenRead()) {
            return 0l;
        }
        try {
            Long  data = getRedisClusterFactoryBykey(key).scard(key);
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by zcount Modifier key:[{}] , Exception: [{}] ",key, e);
        }
        return 0L;
    }

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public ScanResult<String> sscan(@CatCacheKeySign final String key, String cursor) {
        if (null == key || !isOpenRead()) {
            return null;
        }
        try {
            ScanResult<String>  data = getRedisClusterFactoryBykey(key).sscan(key, cursor);
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by sscan Modifier key:[{}] , Exception: [{}] ",key, e);
        }
        return  null;
    }

    /***
     * 判断member是否存在set中
     * @param key
     * @param member
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public boolean sismember(@CatCacheKeySign final String key, String member) {
        if (null == key || !isOpenRead()) {
            return false;
        }
        try {
            boolean  data = getRedisClusterFactoryBykey(key).sismember(key, member);
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by sismember Modifier key:[{}] , Exception: [{}] ",key, e);
        }
        return  false;
    }

    /***
     * 随机返回集合中的几个元素
     * @param key
     * @param count
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public List<String> srandmember(@CatCacheKeySign final String key, int count) {
        if (null == key || !isOpenRead()) {
            return new ArrayList<>();
        }
        try {
            List<String>   data = getRedisClusterFactoryBykey(key).srandmember(key, count);
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by srandmember Modifier key:[{}] , Exception: [{}] ",key, e);
        }
        return  new ArrayList<>();
    }

    /***
     * 返回set中的所有成员
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public Set<String> smembers(@CatCacheKeySign final String key) {
        if (null == key || !isOpenRead()) {
            return new HashSet<>();
        }
        try {
            Set<String>   data = getRedisClusterFactoryBykey(key).smembers(key);
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by srandmember Modifier key:[{}] , Exception: [{}] ",key, e);
        }
        return  new HashSet<>();
    }


    /***
     * 按倒序返回有序集合中的元素
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public Set<byte[]> zrevrange(@CatCacheKeySign final byte[] key, long start, long end) {
        if (null == key || !isOpenRead()) {
            return new HashSet<>();
        }
        try {
            Set<byte[]>   data = getRedisClusterFactoryBykey(key).zrevrange(key, start, end);
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by zrevrange Modifier key:[{}] , Exception: [{}] ",key, e);
        }
        return  new HashSet<>();
    }

    /***
     * 按倒序返回有序集合中的元素
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public Set<byte[]> zrevrangeByScore(@CatCacheKeySign final byte[] key, double max, double min, int pos, int count) {
        if (null == key || !isOpenRead()) {
            return new HashSet<>();
        }
        try {
            Set<byte[]>   data = getRedisClusterFactoryBykey(key).zrevrangeByScore(key, max, min, pos, count);
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by zrevrangeByScore Modifier key:[{}] , Exception: [{}] ",key, e);
        }
        return  new HashSet<>();
    }

    /***
     * 在哈希中放置一个字段
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public int hset(@CatCacheKeySign final String key, byte[] field, byte[] value) {
        if (null == key || !isOpenWrite()) {
            return 0;
        }
        try {
            Integer   data = getRedisClusterFactoryBykey(key).hset(key.getBytes(),field,value).intValue();
            return data;
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by zrevrangeByScore Modifier key:[{}] , Exception: [{}] ",key, e);
        }
        return 0;
    }

    /***
     * 在哈希中放置多个字段
     * @param key redis 对应的key -> map
     * @param fieldKey   redis 缓存中 map->key
     * @param fieldValue redis 缓存中 map->Value
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public boolean hmset(@CatCacheKeySign final String key, String fieldKey, String fieldValue) {
        if (null == key || !isOpenWrite()) {
            return false;
        }
        try {
            long result = getRedisClusterFactoryBykey(key).hset(key,fieldKey,fieldValue);
            return isOK(result);
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by hmset Modifier key:[{}] , Exception: [{}] ",key, e);
        }
        return false;

    }

    /***
     * 在哈希中放置多个字段
     * @param key
     * @param hash
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public void hmset(@CatCacheKeySign final String key, Map<String, String> hash) {
        if (null == key || !isOpenWrite()) {
            return;
        }
        try {
            getRedisClusterFactoryBykey(key).hmset(key,hash);
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by hmset Modifier key:[{}] , Exception: [{}] ",key, e);
        }
    }

    /***
     * 在哈希中放置多个字段
     * @param key
     * @param hash
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public void hmset(@CatCacheKeySign final byte[] key, Map<byte[], byte[]> hash) {
        if (null == key || !isOpenWrite()) {
            return;
        }
        try {
            getRedisClusterFactoryBykey(key).hmset(key,hash);
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by hmset Modifier key:[{}] , Exception: [{}] ",key, e);
        }
    }

    /***
     * 返回哈希的键值对个数
     * @param key
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public int hlen(@CatCacheKeySign final String key) {
        if (null == key || !isOpenRead()) {
            return 0;
        }
        try {
            return getRedisClusterFactoryBykey(key).hlen(key).intValue();
        }catch ( Exception e){
            logger.warn(" Redis Cluster Server by hmset Modifier key:[{}] , Exception: [{}] ",key, e);
        }
        return 0;
    }


    /***
     * 返回哈希中的某个字段值
     * @param key
     * @param field
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public byte[] hget(@CatCacheKeySign final String key, String field) {
        try {
            if (null == key || null == field || !isOpenRead()) {
                return null;
            }
            return getRedisClusterFactoryBykey(key).hget(key.getBytes(), field.getBytes());
        } catch (Exception e) {
            logger.warn(" Redis Cluster Server by hget Modifier key:[{}] , field:[{}] , Exception: [{}] ",key,field, e);
            return null;
        }
    }


    /***
     * 返回哈希中的某个字段值
     * @param key
     * @param field
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public <T>T hgetT(@CatCacheKeySign final String key, String field, Class<T> clazz) {
        try {
            if (null == key || !isOpenRead()) {
                return null;
            }
            byte[] bytes =  getRedisClusterFactoryBykey(key).hget(key.getBytes(), field.getBytes());
            if(null == bytes ){
                return  null;
            }if(byte[].class == clazz){
                return (T)bytes;
            }
            return SerializableUtil.parseValue(clazz, bytes);
        } catch (Exception e) {
            logger.warn(" Redis Cluster Server by hgetT Modifier key:[{}] , field:[{}] , Exception: [{}] ",key,field, e);
            return null;
        }
    }

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public Map<String, String> hmget(@CatCacheKeySign final String key, String...fields) {
        if (null == key || !isOpenRead()) {
            return new HashMap<>();
        }
        try {
            List<String> list= getRedisClusterFactoryBykey(key).hmget(key,fields);
            if (list!=null && !list.isEmpty()) {
                Map<String, String> map = Maps.newHashMapWithExpectedSize(list.size());
                for(int i=0,size=fields.length; i<size; i++) {
                    String value = list.get(i);
                    if (StringUtils.isNotBlank(value)) {
                        map.put(fields[i], value);
                    }
                }
                return map;
            }
        } catch (Exception e) {
            logger.warn(" Redis Cluster Server by hmget Modifier key:[{}] , fields:[{}] , Exception: [{}] ",key,Arrays.asList(fields), e);
            return null;
        }
        return new HashMap<>();
    }

    /***
     * 返回哈希中的多个字段值
     * @param key
     * @param fields
     * @param clazz
     * @param <K>
     * @param <V>
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public <K, V> Map<K,V> hmget(@CatCacheKeySign final String key, Collection<K> fields, Class<V> clazz) {
        if (null == key || !isOpenRead()) {
            return new HashMap<>();
        }
        try {
            byte[][] fieldBytes = SerializableUtil.tranformStrTobyte("",fields);
            List<byte[]> list=getRedisClusterFactoryBykey(key).hmget(SafeEncoder.encode(key),fieldBytes);
            if(list==null){
                return new HashMap<>();
            }
            Map<K,V> map=new HashMap<>();
            int i=0;
            for(K mk : fields){
                if(list.get(i) == null){
                    i++;
                    continue;
                }
                map.put(mk, SerializableUtil.parseValue(clazz,list.get(i)));
                i++;
            }
            return map;
        } catch (Exception e) {
            logger.warn(" Redis Cluster Server by hmget Modifier key:[{}] , fields:[{}] , Exception: [{}] ",key,fields, e);
            return null;
        }
    }

    /***
     * 返回哈希所有键值对
     * @param key
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public Map<byte[],byte[]> hgetAll(@CatCacheKeySign final byte[] key) {
        if (null == key || !isOpenRead()) {
            return new HashMap<>();
        }
        try {
            return getRedisClusterFactoryBykey(key).hgetAll(key);
        } catch (Exception e) {
            logger.warn(" Redis Cluster Server by hgetAll Modifier key:[{}] , Exception: [{}] ",key, e);
            return null;
        }

    }

    /***
     * 返回哈希所有键值对
     * @param key
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public Map<String,String> hgetAll(@CatCacheKeySign final String key) {
        if (null == key || !isOpenRead()) {
            return new HashMap<>();
        }
        try {
            return getRedisClusterFactoryBykey(key).hgetAll(key);
        } catch (Exception e) {
            logger.warn(" Redis Cluster Server by hgetAll Modifier key:[{}] , Exception: [{}] ",key, e);
            return null;
        }

    }

    /***
     * 删除hasn中的某些字段
     * @param key
     * @param field
     * @return
     */
    public int hdel(@CatCacheKeySign final String key,String...field) {
        if (null == key || !isOpenWrite() || field==null || field.length==0) {
            return 0;
        }
        try {
            return getRedisClusterFactoryBykey(key).hdel(key,field).intValue();
        } catch (Exception e) {
            logger.warn(" Redis Cluster Server by hgetAll Modifier key:[{}] , Exception: [{}] ",key, e);
            return 0;
        }

    }

    /***
     * 判断哈希中是否存在某个字段
     * @param key
     * @param field
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public boolean hexists(@CatCacheKeySign final String key,String field) {
        if (null == key || !isOpenRead()) {
            return false;
        }
        try {
            return getRedisClusterFactoryBykey(key).hexists(key,field);
        } catch (Exception e) {
            logger.warn(" Redis Cluster Server by hgetAll Modifier key:[{}] , Exception: [{}] ",key, e);
            return false;
        }
    }

    /***
     * 将hash中的某个字段原子递增
     * @param key
     * @param field
     * @param value
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public long hincrBy(String key,String field,int value) {
        if (null == key || !isOpenWrite()) {
            return 0;
        }
        try {
            return getRedisClusterFactoryBykey(key).hincrBy(key,field,value);
        } catch (Exception e) {
            logger.warn(" Redis Cluster Server by hgetAll Modifier key:[{}] ,value:[{}] , Exception: [{}] ",key,value, e);
            return 0L;
        }
    }

    /***
     * 获取key的所有哈希字段
     * @param key
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public Set<String> hkeys(String key) {
        if (null == key || !isOpenRead()) {
            return new HashSet<>();
        }
        try {
            return getRedisClusterFactoryBykey(key).hkeys(key);
        } catch (Exception e) {
            logger.warn(" Redis Cluster Server by hgetAll Modifier key:[{}] , Exception: [{}] ",key, e);
            return new HashSet<>();
        }

    }

    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public <T> Map<T, Long> multiZrank(@CatCacheKeySign final  String key, Collection<T> collection) {
        if (null == key || null == collection || !isOpenRead()) {
            return new HashMap<>(0);
        }

        final RedisClusterInterface redisCluster = getRedisClusterFactoryBykey(key);

        List<String> bytesMembers = collection.stream().map(String::valueOf).collect(Collectors.toList());

        Map<String, List<String>> keyMembersMap = new HashMap<>();
        keyMembersMap.put(key, bytesMembers);


        Map<String, Map<String, Long>> keysRankMap = redisCluster.refinedZrank(keyMembersMap);
        if (keysRankMap == null || keyMembersMap.isEmpty()) {
            return new HashMap<>();
        }
        Map<String, Long> rankMap = keysRankMap.get(key);
        if (rankMap == null || rankMap.isEmpty()) {
            return new HashMap<>();
        }

        Map<T, Long> resultMap = new HashMap<>();
        for (T member : collection) {
            Long rank = rankMap.get(member);
            if (rank != null) {
                resultMap.put(member, rank);
            }
        }
        return resultMap;
    }

    /**
     * 按mapPrefixKey和指定时间delMapCacheByExpireMsTime, 删除过期的k,v
     * @param mapPrefixKey redis map 的前缀key
     * @param expireMsTime 过期时间
     * @return
     */
    public Map<String, String> getMapCacheAndDelExpire(String mapPrefixKey,long expireMsTime) {

        if (null == mapPrefixKey) {
            logger.warn("Cached model findMapCacheByKV Object mapPrefixKey is null ");
            return null;
        }
        try {
        Map<String, String> map = this.getRedisClusterFactoryBykey(mapPrefixKey).hgetAll(mapPrefixKey);
        if (null != map && !map.isEmpty()) {
            List<String> list = new ArrayList();
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                if (null != entry.getKey() && entry.getKey().length() > 0) {
                    String k = entry.getKey();
                    Long v = SerializableUtil.parseValue(Long.class, entry.getValue());
                    if (null != k && null != v && (v - expireMsTime) > 0) {
                        continue;//未过期的
                    }
                    list.add(k);
                    it.remove();
                }
            }
            if(list.size() > 0){
                getRedisClusterFactoryBykey(mapPrefixKey).hdel(mapPrefixKey,list.toArray(new String[list.size()]));
            }
        }
        return map;
        } catch (Exception e) {
            logger.warn(" Redis Cluster Server by getMapCacheAndDelExpire Modifier key:[{}] , Exception: [{}] ",mapPrefixKey, e);
            return null;
        }
    }
    /***
     * 按位置范围获取有序列表中的元素并返回元素的分数
     * @param key
     * @param start 开始位置
     * @param end 结束位置
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public Set<Tuple> zrangeWithScores(@CatCacheKeySign final String key, long start, long end) {
        if (null == key || !isOpenRead()) {
            return new HashSet<>();
        }
        try {
            return getRedisClusterFactoryBykey(key).zrangeWithScores(key,start,end);
        } catch (Exception e) {
            logger.warn(" Redis Cluster Server by zrangeWithScores Modifier key:[{}] , Exception: [{}] ",key, e);
            return new HashSet<>();
        }

    }


    /**
     * 根据Score值范围 倒序获取值   取指定数量个值
     *
     * @param key
     * @param max	score 范围
     * @param min
     * @author xiaogenliu
     * @return
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public Set<byte[]> zrevRangeByScore(@CatCacheKeySign final  byte[] key, double max, double min) {
        if (null == key || !isOpenRead()) {
            return new HashSet<>();
        }
        try {
            return getRedisClusterFactoryBykey(key).zrevrangeByScore(key,max,min);
        } catch (Exception e) {
            logger.warn(" Redis Cluster Server by zrevRangeByScore Modifier key:[{}] , Exception: [{}] ",key, e);
            return new HashSet<>();
        }
    }


    /**
     * 发送消息到指定的通道
     * @param channel - 要发送的消息通道
     * @param message - 消息内容
     * @return 返回该channel的订阅数量
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public long publish(@CatCacheKeySign final  String key, String channel, String message) {
        if (null == key || !isOpenRead()) {
         return   0l;
        }
        try {
            return getRedisClusterFactoryBykey(key).publish(channel,message);
        } catch (Exception e) {
            logger.warn(" Redis Cluster Server by publish Modifier key:[{}] , Exception: [{}] ",key, e);
            return 0L;
        }
    }

    /**
     * 监听消息通道
     * @param jedisPubSub - 监听任务
     * @param channels - 要监听的消息通道
     */
    public  void subscribe(String key, JedisPubSub jedisPubSub, String... channels) {
        try {
            getRedisClusterFactoryBykey(key).subscribe(jedisPubSub,channels);
        } catch (Exception e) {
            logger.warn(" Redis Cluster Server by subscribe Modifier key:[{}] , Exception: [{}] ",key, e);
        }

    }
    /**
     * 尝试获取分布式锁
     * @param lockKey 锁
     * @param uuid 请求标识uuid
     * @param expireTime expireTime 超期时间（秒）
     * @return 是否获取成功
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public boolean getSet(@CatCacheKeySign final String lockKey, String uuid, int expireTime) {
        if (null == lockKey || !isOpenWrite()) {
            return false;
        }
        String result =  this.getRedisClusterFactoryBykey(lockKey).set(lockKey, uuid, "NX", "EX", (long)expireTime);
        return isOK(result);
    }


    /**
     *  释放分布式锁
     * @param lockKey 锁
     * @param uuid 请求标识
     * @return 是否释放成功
     */
    @CatCacheSign(service = CatTopConstants.TYPE_REDIS)
    public boolean evalLock(@CatCacheKeySign final String lockKey, String uuid) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        //释放分布式锁
        Object result = this.getRedisClusterFactoryBykey(lockKey).eval(script, Collections.singletonList(lockKey), Collections.singletonList(uuid));
        if (null != result && isOK(result.toString())) {
            return true;
        }
        return false;

    }

}
