package com.suven.framework.core.redis.cluster;


import com.suven.framework.util.json.JsonUtils;
import redis.clients.util.JedisClusterCRC16;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ReflectionUtils;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.*;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ConnectException;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author summerao
 */
public class RedisClusterClientFactory implements FactoryBean<RedisClusterInterface>, InitializingBean, DisposableBean {

    private final int CONNECTION_TIMEOUT = 2000;//连接超时（默认2000ms）
    private static Logger logger = LoggerFactory.getLogger(RedisClusterClientFactory.class);
    private JedisCluster jedisCluster;
    private RedisConfigSettings settings = new RedisConfigSettings();
    private Map<Integer, JedisPool> slots = new HashMap<>();
    

    private String server;
    private String password = null;

    public void setServer(String server) {
        logger.warn("RedisClusterClientFactory init redis client server[{}]", server);
        this.server = server;
    }
    public void setPassword(String password){
        if(null != password){
            this.password = password;
        }
    }
    
    public void setSettings(RedisConfigSettings settings){
        if(settings != null){
            this.settings = settings;
        }
    }




    @Override
    public void afterPropertiesSet() {
        //Jedis Cluster will attempt to discover cluster nodes automatically
        String[] servers = server.split(";|,");
        try {
            Set<HostAndPort> jedisClusterNodes = new HashSet<>();
            for (String str : servers) {
                String[] ap = str.split(":");
                HostAndPort hp = new HostAndPort(ap[0], Integer.valueOf(ap[1]));
                jedisClusterNodes.add(hp);
            }
            GenericObjectPoolConfig config = new GenericObjectPoolConfig();
            config.setMaxTotal(settings.getPool().getMaxTotal()); //可用连接实例的最大数目，默认值为8；如果赋值为-1，则表示不限制；
            config.setMaxIdle(settings.getPool().getMaxIdle()); //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值是8。
            config.setMinIdle(settings.getPool().getMinIdle()); //控制一个pool最少有多少个状态为idle(空闲的)的jedis实例，默认值是0。
            config.setMaxWaitMillis(settings.getPool().getMaxWaitMillis());//等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出
            config.setTestOnBorrow(settings.getPool().isTestOnBorrow());
            config.setTestOnReturn(settings.getPool().isTestOnReturn());

            // 节点，超时时间，最多重定向次数，链接池
//            jedisCluster = new JedisCluster(jedisClusterNodes, settings.getTimeout(),settings.getMaxRedirections(), config);
            if(null != password){
                jedisCluster = new JedisCluster(jedisClusterNodes, CONNECTION_TIMEOUT, settings.getTimeout(), settings.getMaxRedirections(), password, config);
            }else{
                jedisCluster = new JedisCluster(jedisClusterNodes, settings.getTimeout(),settings.getMaxRedirections(), config);
            }
            initJedisPool();
        }catch (Exception e){
            logger.warn("initJedisPoo redis  Exception from servers[{}], Exception[{}]", JsonUtils.toJson(servers),e.getMessage());
            e.printStackTrace();
        }

    }

    public RedisClusterInterface getJedis() {
        RedisClusterInterface service = (RedisClusterInterface) creatProxyInstance();
        return service;
    }

    @Override
    public void destroy() throws Exception {
        jedisCluster.close();
    }

    @Override
    public RedisClusterInterface getObject() {
        return getJedis();
    }

    @Override
    public Class<?> getObjectType() {
        return RedisClusterInterface.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public Object creatProxyInstance() {
        return Proxy.newProxyInstance(RedisClusterInterface.class.getClassLoader(), new Class[]{RedisClusterInterface.class}, new ProxyFactory(jedisCluster));
    }

    class ProxyFactory implements InvocationHandler {
        private JedisCluster jedisCluster;

        private static final String REFINED_DEL = "refinedDel";
        private static final String REFINED_MGET = "refinedMget";
        private static final String REFINED_MSET = "refinedMset";
        private static final String REFINED_INCR = "refinedIncr";
        private static final String REFINED_DECR = "refinedDecr";
        private static final String REFINED_ZADD = "refinedZadd";
        private static final String REFINED_ZADDSTR = "refinedZaddStr";
        private static final String REFINED_ZRANK = "refinedZrank";
        private static final String REFINED_EXPIRE = "refinedExpire";
        private static final String REFINED_INCRBYEXPIRE = "refinedIncrByExpire";


        public ProxyFactory(JedisCluster jedisCluster) {
            this.jedisCluster = jedisCluster;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // 扩展api
            switch (method.getName()) {
                case "toString":
                    return this.toString();
                case REFINED_DEL:
                    return executeDel(args);
                case REFINED_MGET:
                    return executeMget(args);
                case REFINED_MSET:
                    return executeMset(args);
                case REFINED_ZADD:
                    return executeZadd(args);
                case REFINED_ZRANK:
                    return executeZrank(args);
                case REFINED_EXPIRE:
                    return executeExpire(args);
                case REFINED_INCRBYEXPIRE:
                    return executeIncrByExpire(args);
            }

            // 原生api做cat埋点
            Object result = null;
//            Timer.Context context = null;
//            Transaction t = Cat.newTransaction(CatGcConstants.TYPE_REDIS, method.getName());
            try {
//                Timer timer = MetricHelper.timer(RedisClusterInterface.class, method.getName());
//                context = timer.time();
                result = method.invoke(jedisCluster, args);
//                Cat.logEvent(CatGcConstants.TYPE_REDIS, method.getName());
//                if (result == null) {
//                    Cat.logEvent(CatGcConstants.TYPE_REDIS, method.getName() + CatGcConstants.TYPE_REDIS_MISS);
//                }
//                t.setStatus(com.dianping.cat.message.Transaction.SUCCESS);
            } catch (Exception e) {
//                t.setStatus(e);
                throw e;
            } finally {
//                if (context != null) {
//                    context.close();
//                }
//                 t.complete();
            }
            return result;
        }
    }

    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    //private final Lock r = rwl.readLock();
    private final Lock w = rwl.writeLock();

    private static final Cache<String, String> renewSlotCache = CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.SECONDS).build();
    private final static String _key = "renew";
    private final static String _value = "1";

    private List<String> toList(Collection<String> col) {
        List<String> list = new ArrayList<>();
        for (String str : col) {
            list.add(str);
        }
        return list;
    }


    public Map<String, byte[]> executeMget(Object[] args) {
        List<String> obj = toList((Collection<String>) args[0]);
        Map<JedisPool, List<byte[]>> polyMap = new HashMap<JedisPool, List<byte[]>>(16);
        Map<String, byte[]> resMap = new HashMap<String, byte[]>(obj.size());
        boolean needRenewSlot = false;
        try {
            if (_value.equals(renewSlotCache.getIfPresent(_key))) {
                return origalGet(obj);
            }
            for (String key : obj) {
                int slot = JedisClusterCRC16.getSlot(key);
                JedisPool jp = getJedisPoolBySlot(slot);
                if (jp == null) {
                    logger.error("dangerous. jedis is died which this slot:{} been managered .key:{}", slot, key);
                    needRenewSlot = true;
                    return origalGet(obj);
                }
                List<byte[]> list = polyMap.get(jp);
                if (list == null) {
                    list = new ArrayList<byte[]>();
                }
                list.add(key.getBytes());
                polyMap.put(jp, list);
            }
            for (Entry<JedisPool, List<byte[]>> entry : polyMap.entrySet()) {
                Map<String, byte[]> map = executeMget(entry.getKey(), entry.getValue());
                if (map == null) {
                    map = origalGet(entry.getValue());
                    needRenewSlot = true;
                }
                resMap.putAll(map);
            }
        } catch (Exception e) {
            logger.error("executeMget e:{}", e);
            throw e;
        } finally {
            if (needRenewSlot) {
                renewSlotCache.put(_key, _value);
                initJedisPool();
            }
        }
        return resMap;
    }

    /**
     * 多key删除
     *
     * @param args
     * @return
     */
    public Map<String, Object> executeDel(Object[] args) {
        List<String> obj = toList((Collection<String>) args[0]);
        Map<JedisPool, List<byte[]>> polyMap = new HashMap<JedisPool, List<byte[]>>(16);
        Map<String, Object> resMap = new HashMap<String, Object>(obj.size());
        boolean needRenewSlot = false;
        try {
            if (_value.equals(renewSlotCache.getIfPresent(_key))) {
                return origalDel(obj);
            }
            for (String key : obj) {
                int slot = JedisClusterCRC16.getSlot(key);
                JedisPool jp = getJedisPoolBySlot(slot);
                if (jp == null) {
//                    Cat.logError(new JedisClusterException("dangerous. jedis is empty which this slot(" + slot + ") been managered ."));
                    logger.error("dangerous. jedis is died which this slot:{} been managered .key:{}", slot, key);
                    needRenewSlot = true;
                    return origalDel(obj);
                }
                List<byte[]> list = polyMap.get(jp);
                if (list == null) {
                    list = new ArrayList<byte[]>();
                }
                list.add(key.getBytes());
                polyMap.put(jp, list);
            }
            for (Entry<JedisPool, List<byte[]>> entry : polyMap.entrySet()) {
                Map<String, Object> map = executeDel(entry.getKey(), entry.getValue());
                if (map == null) {
                    map = origalDel(entry.getValue());
                    needRenewSlot = true;
                }
                resMap.putAll(map);
            }
        } catch (Exception e) {
            logger.error("executeDel e:{}", e);
            throw e;
        } finally {
            if (needRenewSlot) {
                renewSlotCache.put(_key, _value);
                initJedisPool();
            }
        }
        return resMap;
    }

    /**
     * mset操作
     *
     * @param args
     * @return
     */
    public Map<String, Object> executeMset(Object[] args) {
        Map<String, byte[]> keysvalues = (Map<String, byte[]>) args[0];

        Integer time = null;
        if (args.length > 1) {
            time = (Integer) args[1];
        }
        Map<JedisPool, Map<String, byte[]>> polyMap = new HashMap<JedisPool, Map<String, byte[]>>(16);
        Map<String, Object> resultMap = new HashMap<>();
        boolean needRenewSlot = false;
        try {
            if (_value.equals(renewSlotCache.getIfPresent(_key))) {
                return origalSet(keysvalues, time);
            }
            for (String key : keysvalues.keySet()) {
                int slot = JedisClusterCRC16.getSlot(key);
                JedisPool jp = getJedisPoolBySlot(slot);
                if (jp == null) {
                    logger.error("dangerous. jedis is died which this slot:{} been managered .key:{}", slot, key);
                    needRenewSlot = true;
                    return origalSet(keysvalues, time);
                }
                resultMap.put(key, null);
                Map<String, byte[]> map = polyMap.get(jp);
                if (map == null) {
                    map = new HashMap<String, byte[]>();
                }
                map.put(key, keysvalues.get(key));
                polyMap.put(jp, map);
            }
            for (Entry<JedisPool, Map<String, byte[]>> entry : polyMap.entrySet()) {
                Map<String, Object> tempMap = executeMset(entry.getKey(), entry.getValue(), time);
                if (tempMap == null) {
                    tempMap = origalSet(entry.getValue(), time);
                    needRenewSlot = true;
                }
                resultMap.putAll(tempMap);
            }
        } catch (Exception e) {
            logger.error("executeMset e:{}", e);
            throw e;
        } finally {
            if (needRenewSlot) {
                renewSlotCache.put(_key, _value);
                initJedisPool();
            }
        }
        return resultMap;
    }

    public Map<String, Long> executeZadd(Object[] args) {
        Map<String, Map<String, Double>> keysvalues = (Map<String, Map<String, Double>>) args[0];
        Map<JedisPool, Map<String, Map<String, Double>>> polyMap =
                new HashMap<JedisPool, Map<String, Map<String, Double>>>(16);
        Map<String, Long> resultMap = new HashMap<>();
        boolean needRenewSlot = false;
        try {
            if (_value.equals(renewSlotCache.getIfPresent(_key))) {
                return origalZadd(keysvalues);
            }
            for (String key : keysvalues.keySet()) {
                int slot = JedisClusterCRC16.getSlot(key);
                JedisPool jp = getJedisPoolBySlot(slot);
                if (jp == null) {
                    logger.error("dangerous. jedis is died which this slot:{} been managered .key:{}", slot, key);
                    needRenewSlot = true;
                    return origalZadd(keysvalues);
                }
                resultMap.put(key, null);
                Map<String, Map<String, Double>> map = polyMap.get(jp);
                if (map == null) {
                    map = new HashMap<>();
                }
                map.put(key, keysvalues.get(key));
                polyMap.put(jp, map);
            }
            for (Entry<JedisPool, Map<String, Map<String, Double>>> entry : polyMap.entrySet()) {
                Map<String, Long> tempMap = executeZadd(entry.getKey(), entry.getValue());
                resultMap.putAll(tempMap);
                if (tempMap == null) {
                    tempMap = origalZadd(entry.getValue());
                    needRenewSlot = true;
                }
                resultMap.putAll(tempMap);
            }
        } catch (Exception e) {
            logger.error("executeZadd e:{}", e);
            throw e;
        } finally {
            if (needRenewSlot) {
                renewSlotCache.put(_key, _value);
                initJedisPool();
            }
        }
        return resultMap;
    }

    public Map<String, Map<String, Long>> executeZrank(Object[] args) {
        Map<String, List<String>> keysvalues = (Map<String, List<String>>) args[0];
        Map<JedisPool, Map<String, List<String>>> polyMap =
                new HashMap<JedisPool, Map<String, List<String>>>(16);
        Map<String, Map<String, Long>> resultMap = new HashMap<>();
        boolean needRenewSlot = false;
        try {
            if (_value.equals(renewSlotCache.getIfPresent(_key))) {
                return origalZrank(keysvalues);
            }
            for (String key : keysvalues.keySet()) {
                int slot = JedisClusterCRC16.getSlot(key);
                JedisPool jp = getJedisPoolBySlot(slot);
                if (jp == null) {
                    logger.error("dangerous. jedis is died which this slot:{} been managered .key:{}", slot, key);
                    needRenewSlot = true;
                    return origalZrank(keysvalues);
                }
                resultMap.put(key, null);
                Map<String, List<String>> map = polyMap.get(jp);
                if (map == null) {
                    map = new HashMap<String, List<String>>();
                }
                map.put(key, keysvalues.get(key));
                polyMap.put(jp, map);
            }
            for (Entry<JedisPool, Map<String, List<String>>> entry : polyMap.entrySet()) {
                Map<String, Map<String, Long>> tempMap = executeZrank(entry.getKey(), entry.getValue());
                resultMap.putAll(tempMap);
                if (tempMap == null) {
                    tempMap = origalZrank(entry.getValue());
                    needRenewSlot = true;
                }
                resultMap.putAll(tempMap);
            }
        } catch (Exception e) {
            logger.error("executeZrank e:{}", e);
            throw e;
        } finally {
            if (needRenewSlot) {
                renewSlotCache.put(_key, _value);
                initJedisPool();
            }
        }
        return resultMap;
    }

    public Map<String, Object> executeExpire(Object[] args) {
        Map<String, Integer> keysvalues = (Map<String, Integer>) args[0];
        Map<JedisPool, Map<String, Integer>> polyMap =
                new HashMap<JedisPool, Map<String, Integer>>(16);
        Map<String, Object> resultMap = new HashMap<>();
        boolean needRenewSlot = false;
        try {
            if (_value.equals(renewSlotCache.getIfPresent(_key))) {
                return origalExpire(keysvalues);
            }
            for (String key : keysvalues.keySet()) {
                int slot = JedisClusterCRC16.getSlot(key);
                JedisPool jp = getJedisPoolBySlot(slot);
                if (jp == null) {
                    logger.error("dangerous. jedis is died which this slot:{} been managered .key:{}", slot, key);
                    needRenewSlot = true;
                    return origalExpire(keysvalues);
                }
                resultMap.put(key, null);
                Map<String, Integer> map = polyMap.get(jp);
                if (map == null) {
                    map = new HashMap<String, Integer>();
                }
                map.put(key, keysvalues.get(key));
                polyMap.put(jp, map);
            }
            for (Entry<JedisPool, Map<String, Integer>> entry : polyMap.entrySet()) {
                Map<String, Object> tempMap = executeExpire(entry.getKey(), entry.getValue());
                resultMap.putAll(tempMap);
                if (tempMap == null) {
                    tempMap = origalExpire(entry.getValue());
                    needRenewSlot = true;
                }
                resultMap.putAll(tempMap);
            }
        } catch (Exception e) {
            logger.error("executeExpire e:{}", e);
            throw e;
        } finally {
            if (needRenewSlot) {
                renewSlotCache.put(_key, _value);
                initJedisPool();
            }
        }
        return resultMap;
    }

    public long executeIncrByExpire(Object[] args) {
        String key = (String) args[0];
        long value = (long) args[1];
        int expireTime = (int) args[2];
        boolean refreshTime = (boolean) args[3];
        boolean needRenewSlot = false;
        try {
            return executeIncrByExpire(key,value, expireTime,refreshTime);
        } catch (Exception e) {
            logger.error("executeIncr e:{}", e);
            throw e;
        } finally {
            if (needRenewSlot) {
                renewSlotCache.put(key, String.valueOf(value));
                initJedisPool();
            }
        }
    }

    private Map<String, byte[]> origalGet(List<?> obj) {
        Map<String, byte[]> resMap = new HashMap<String, byte[]>(obj.size());
        for (Object key : obj) {
            if (key instanceof String) {
                String k = (String) key;
                resMap.put(k, jedisCluster.get(k.getBytes()));
            } else {
                byte[] k = (byte[]) key;
                resMap.put(new String(k), jedisCluster.get(k));
            }
        }
        return resMap;
    }

    /**
     * 逐个逐个删除key
     *
     * @param obj
     * @return
     */
    private Map<String, Object> origalDel(List<?> obj) {
        Map<String, Object> resMap = new HashMap<String, Object>(obj.size());
        for (Object key : obj) {
            if (key instanceof String) {
                String k = (String) key;
                resMap.put(k, jedisCluster.del(k.getBytes()));
            } else {
                byte[] k = (byte[]) key;
                resMap.put(new String(k), jedisCluster.get(k));
            }
        }
        return resMap;
    }

    /**
     * 效率最差做法，在找不到key对应的hash槽位时这么做
     *
     * @param keysvalues
     * @param time
     * @return
     */
    private Map<String, Object> origalSet(Map<String, byte[]> keysvalues, Integer time) {
        Map<String, Object> resultMap = new HashMap<>();
        for (String key : keysvalues.keySet()) {
            if (time == null) {
                resultMap.put(key, jedisCluster.set(key.getBytes(), keysvalues.get(key)));
            } else {
                resultMap.put(key,jedisCluster.setex(key.getBytes(), time, keysvalues.get(key)));
            }
        }
        return resultMap;
    }

    private Map<String, Long> origalZadd(Map<String, Map<String, Double>> keysvalues) {
        Map<String, Long> resultMap = new HashMap<>();
        for (String key : keysvalues.keySet()) {
            Map<String, Double> values = keysvalues.get(key);
            resultMap.put(key, jedisCluster.zadd(key, values));
        }
        return resultMap;
    }

    private Map<String, Map<String, Long>> origalZrank(Map<String, List<String>> keysvalues) {
        Map<String, Map<String, Long>> resultMap = new HashMap<>();
        for (String key : keysvalues.keySet()) {
            List<String> list = keysvalues.get(key);
            Map<String, Long> map = new HashMap<String, Long>();
            for(String mem : list) {
                Long index = jedisCluster.zrank(key, mem);
                map.put(key, index);
            }
            resultMap.put(key, map);
        }
        return resultMap;
    }



    private long origalIncr(String key, int time) {
        long val = jedisCluster.incr(key);
        jedisCluster.setex(key, time, jedisCluster.get(key));
        return val;
    }

    private long origalDecr(String key, int time) {
        long val = jedisCluster.decr(key);
        jedisCluster.setex(key, time, jedisCluster.get(key));
        return val;
    }

    private Map<String, Object> origalExpire(Map<String, Integer> keysvalues) {
        Map<String, Object> resultMap = new HashMap<>();
        for (String key : keysvalues.keySet()) {
            resultMap.put(key, jedisCluster.expire(key, keysvalues.get(key)));
        }
        return resultMap;
    }

    /**
     * get jedisPool by slot.
     *
     * @param solt
     * @return
     */
    private JedisPool getJedisPoolBySlot(int solt) {
        JedisPool jp = null;
//        r.tryLock();
        try {
            jp = slots.get(solt);
        } catch (Exception e) {
        } finally {
//            r.unlock();
        }
        return jp;
    }
    private void jedisPoolResource(JedisPool jedisPool,Jedis jedis){
        jedisPool.returnResource(jedis);
//        jedisPool.getResource().close();
    }

    //现有mget方案是定位到对应Redis节点采用管道实现，但仍需考虑2种场景
    //1、节点管理槽位关系变更
    //2、主节点挂了，也无从节点介入，槽位无节点管理
    @SuppressWarnings("unchecked")
    private Map<String, byte[]> executeMget(JedisPool jedisPool, List<byte[]> keys) {
        Map<String, byte[]> resMap = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pipeline = jedis.pipelined();
            for (byte[] b : keys) {
                pipeline.get(b);
            }
            List<Object> resList = pipeline.syncAndReturnAll();
            resMap = new HashMap<String, byte[]>(keys.size());
            for (int i = 0; i < keys.size(); i++) {
                Object obj = resList.get(i);
                if (obj != null && obj instanceof JedisDataException) {
                    renewSlotCache.put(_key, _value);
                    return null;
                }
                resMap.put(new String(keys.get(i)), (byte[]) resList.get(i));
            }
            return resMap;
        } catch (Exception e) {
            if (e instanceof JedisMovedDataException || e instanceof JedisConnectionException || e instanceof JedisAskDataException || e instanceof ConnectException) {
                renewSlotCache.put(_key, _value);
            }
            logger.error("mget pipeline error.e:{}", e);
//            Cat.logError(e);
        } finally {
            if (jedis != null) {
                //  jedisPool.close();
                jedisPoolResource(jedisPool,jedis);
            }
        }
        return null;
    }



    private Map<String, Object> executeDel(JedisPool jedisPool, List<byte[]> keys) {
        Map<String, Object> resMap = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pipeline = jedis.pipelined();
            for (byte[] b : keys) {
                pipeline.del(b);
            }
            List<Object> resList = pipeline.syncAndReturnAll();
            resMap = new HashMap<String, Object>(keys.size());
            for (int i = 0; i < keys.size(); i++) {
                Object obj = resList.get(i);
                if (obj != null && obj instanceof JedisDataException) {
                    renewSlotCache.put(_key, _value);
                    return null;
                }
                resMap.put(new String(keys.get(i)), obj);
            }
            return resMap;
        } catch (Exception e) {
            if (e instanceof JedisMovedDataException || e instanceof JedisConnectionException || e instanceof JedisAskDataException || e instanceof ConnectException) {
                renewSlotCache.put(_key, _value);
            }
            logger.error("mget pipeline error.e:{}", e);
//            Cat.logError(e);
        } finally {
            if (jedis != null) {
                //  jedisPool.close();
                jedisPoolResource(jedisPool,jedis);
            }
        }
        return null;
    }

    private Map<String, Object> executeMset(JedisPool jedisPool, Map<String, byte[]> keysvalues, Integer time) {
        Map<String, Object> resultMap = new HashMap<String, Object>(keysvalues.size());
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pipeline = jedis.pipelined();
            for (String key : keysvalues.keySet()) {
                Response<String> res = null;
                if (time == null) {
                    res = pipeline.set(key.getBytes(), keysvalues.get(key));
                } else {
                    res = pipeline.setex(key.getBytes(), time, keysvalues.get(key));
                }
                resultMap.put(key, res);
            }
            pipeline.sync();
            for (String key : resultMap.keySet()) {
                Response<String> res = (Response<String>) resultMap.get(key);
                resultMap.put(key, res.get());
            }
            return resultMap;
        } catch (Exception e) {
            if (e instanceof JedisMovedDataException || e instanceof JedisConnectionException || e instanceof JedisAskDataException || e instanceof ConnectException) {
                renewSlotCache.put(_key, _value);
            }
            logger.error("mset pipeline error.e:{}", e);
//            Cat.logError(e);
        } finally {
            if (jedis != null) {
                //  jedisPool.close();
                jedisPoolResource(jedisPool,jedis);
            }
        }
        return resultMap;
    }

    private Map<String, Long> executeZadd(JedisPool jedisPool, Map<String, Map<String, Double>> map) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        Map<String, Long> resultMap = new HashMap<String, Long>();
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pipeline = jedis.pipelined();
            for(Entry<String, Map<String, Double>> entry : map.entrySet()) {
                Response<Long> res = pipeline.zadd(entry.getKey(), entry.getValue());
                resMap.put(entry.getKey(), res);
            }
            pipeline.sync();
            for (String key : resMap.keySet()) {
                Response<Long> res = (Response<Long>) resMap.get(key);
                resultMap.put(key, res.get());
            }
            return resultMap;
        } catch (Exception e) {
            if (e instanceof JedisMovedDataException || e instanceof JedisConnectionException || e instanceof JedisAskDataException || e instanceof ConnectException) {
                renewSlotCache.put(_key, _value);
            }
            logger.error("zadd pipeline error.e:{}", e);
//            Cat.logError(e);
        } finally {
            if (jedis != null) {
                //  jedisPool.close();
                jedisPoolResource(jedisPool,jedis);
            }
        }
        return null;
    }

    private Map<String, Map<String, Long>> executeZrank(JedisPool jedisPool, Map<String, List<String>> map) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        Map<String, Map<String, Long>> resultMap = new HashMap<String, Map<String, Long>>();
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pipeline = jedis.pipelined();
            for(Entry<String, List<String>> entry : map.entrySet()) {
                List<String> list = entry.getValue();
                Map<String, Object> tmpMap = new HashMap<String, Object>();
                for(String mem : list) {
                    Response<Long> res = pipeline.zrank(entry.getKey(), mem);
                    tmpMap.put(mem, res);
                }
                resMap.put(entry.getKey(), tmpMap);
            }
            pipeline.sync();
            for (String key : resMap.keySet()) {
                Map<String, Object> tmpMap = ( Map<String, Object>) resMap.get(key);
                Map<String, Long> indexMap = new HashMap<String, Long>();
                for(Entry<String, Object> entry : tmpMap.entrySet()) {
                    Response<Long> res = (Response<Long>) entry.getValue();
                    indexMap.put(entry.getKey(), res.get());
                }
                resultMap.put(key, indexMap);
            }
            return resultMap;
        } catch (Exception e) {
            if (e instanceof JedisMovedDataException || e instanceof JedisConnectionException || e instanceof JedisAskDataException || e instanceof ConnectException) {
                renewSlotCache.put(_key, _value);
            }
            logger.error("zrank pipeline error.e:{}", e);
//            Cat.logError(e);
        } finally {
            if (jedis != null) {
                //  jedisPool.close();
                jedisPoolResource(jedisPool,jedis);
            }
        }
        return null;
    }

    private Map<String, Object> executeExpire(JedisPool jedisPool, Map<String, Integer> map) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pipeline = jedis.pipelined();
            for(Entry<String, Integer> entry : map.entrySet()) {
                resMap.put(entry.getKey(), pipeline.expire(entry.getKey(), entry.getValue()));
            }
            pipeline.sync();
            for (String key : resMap.keySet()) {
                Response<Long> res = (Response<Long>) resMap.get(key);
                resultMap.put(key, res.get());
            }
            return resultMap;
        } catch (Exception e) {
            if (e instanceof JedisMovedDataException || e instanceof JedisConnectionException || e instanceof JedisAskDataException || e instanceof ConnectException) {
                renewSlotCache.put(_key, _value);
            }
            logger.error("expire pipeline error.e:{}", e);
//            Cat.logError(e);
        } finally {
            if (jedis != null) {
                //  jedisPool.close();
                jedisPoolResource(jedisPool,jedis);
            }
        }
        return null;
    }

    /**
     * 设置指定key,在有效时间里增加、或减少 指定值；
     * @param key
     * @param value
     * @param expireTime 到期有效时间
     * @param refreshTime 刷新时间，为true时，每次的设置时间为expireTime时间，为false时，即是expireTime为首次设置到过期的时间
     * @return
     */
    private long executeIncrByExpire( String key, long value, int expireTime, boolean refreshTime) {
        Jedis jedis = null;
        int slot = JedisClusterCRC16.getSlot(key);
        JedisPool jedisPool = getJedisPoolBySlot(slot);
        Pipeline pipeline = null;
        try {
            if(expireTime < 0 || value <= 0){
                throw new RuntimeException("redis method executeIncrByExpire from param error, by key["+key+"] by expireTime["+expireTime+"]" );
            }
            if(!refreshTime ){
                jedis = jedisPool.getResource();
                long time =  jedis.ttl(key);
                if(expireTime > time && time > 0 ){
                    expireTime = (int)time;
                }
            }
            pipeline = jedis.pipelined();
            pipeline.incrBy(key,value);
            pipeline.expire(key,expireTime);

            List<Object> resList = pipeline.syncAndReturnAll();
            if(null != resList && resList.size() > 1){
                for (int i = 0, j = resList.size() ; i < j; i++) {
                    Object obj = resList.get(i);
                    if (obj != null && obj instanceof JedisDataException) {
                        renewSlotCache.put(_key, _value);
                        return 0;
                    }
                }
                return Long.parseLong(resList.get(0).toString());
            }
        } catch (Exception e) {
            if (e instanceof JedisMovedDataException || e instanceof JedisConnectionException || e instanceof JedisAskDataException || e instanceof ConnectException) {
                renewSlotCache.put(_key, _value);
            }
            logger.error("mget pipeline error.e:{}", e);
        } finally {
            if(pipeline != null){
                try {
                    pipeline.close();
                }catch (Exception e){
                    logger.warn("redis pipeline close error:",e);
                }
            }
            if (jedis != null) {
               //  jedisPool.close();
                jedisPoolResource(jedisPool,jedis);
            }
        }
        return 0;
    }

    /**
     * 重新反射获取类cache.
     */
    private void initJedisPool() {
        if (!w.tryLock()) {
            logger.info("initJedisPool trylock fail.");
            return;
        }
        try {
            Field connHandler = ReflectionUtils.findField(BinaryJedisCluster.class, "connectionHandler");
            connHandler.setAccessible(true);
            JedisClusterConnectionHandler jc = (JedisClusterConnectionHandler) ReflectionUtils.getField(connHandler, jedisCluster);

            Field infoCache = ReflectionUtils.findField(JedisClusterConnectionHandler.class, "cache");
            infoCache.setAccessible(true);
            JedisClusterInfoCache jtest = (JedisClusterInfoCache) ReflectionUtils.getField(infoCache, jc);

            Field soltMap = ReflectionUtils.findField(JedisClusterInfoCache.class, "slots");
            soltMap.setAccessible(true);
            slots = (Map<Integer, JedisPool>) ReflectionUtils.getField(soltMap, jtest);
            logger.warn("initJedisPool success.");
        } catch (Exception e) {
            logger.error("重新反射或者类cache异常", e);
        } finally {
            w.unlock();
        }
    }


}
