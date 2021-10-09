package com.suven.framework.core.rocketmq;

import com.suven.framework.core.redis.RedisClusterServer;
import com.suven.framework.core.redis.RedisKeys;
import com.suven.framework.util.json.JsonUtils;
import com.suven.framework.util.json.SerializableUtil;
import org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@ConditionalOnClass(RocketMQAutoConfiguration.class)
public abstract class RocketMQConsumerAbstractHandler<T> implements RocketMQListener<RocketMQConsumerWithPayload>  {

    private Logger logger = LoggerFactory.getLogger(RocketMQConsumerAbstractHandler.class);

    private static final int CACHE_TIME = 60*60*24;

    @Autowired
    private RedisClusterServer redisClusterServer;

    private Class<T> entityClass;

    /** 业务人员自己的业务操作 **/
    protected abstract  void onSuccessService(T dataMessage);



    /** 异常情况执行的方法,业务方根据自己的业务情况决定是否要编写业务 **/
    protected void onExceptionService(RocketMQConsumerWithPayload payload){}

    @Override
    public void onMessage(RocketMQConsumerWithPayload product) {
        boolean isRetry = true;
        while (isRetry){
            //只运行一次
            isRetry = false;
            try {
                if(product == null){//结束下一次循环
                    logger.error("处理rocket-mq消息失败,RocketMQConsumerWithPayload的信息为空");
                    return;
                }
                long globalId = product.getGlobalId();
                int retryCount = product.getRetryCount();
                String topic = product.getTopic();

                boolean isRun =  doubleCheck(globalId,retryCount);
                if(!isRun || globalId <= 0){//结束下一次循环
                    return;
                }
                Object object = product.getBody();
                if(object == null){//结束下一次循环
                    logger.error("处理rocket-mq消息失败,TOPIC=:[{}]的信息为空, 全局唯一id=:[{}]",topic,globalId);
                    return;
                }
                byte[] body =  converter(object);

                Type genType = getClass().getGenericSuperclass();
                Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
                entityClass =  (Class)params[0];
                T data = serializable(body,entityClass);
                onSuccessService(data);
                delCheckRedisKey(globalId);
                logger.info("{} : 消费完成，对应消息体为data= :[{}],全局唯一id=[{}]",topic,object,globalId);
            }catch (Exception e){
                logger.error("类型转换异常{}",e);
                if(null != product){
                    /**异常重试次数减一**/
                    boolean  fla =  decrCheckCount(product.getGlobalId());
                    if(fla){//重试实现
                        isRetry = true;
                    }else {/**重试完了,还是异常,执行异常处理业务方法*/
                        onException(product);
                        return;
                    }
                }

            }
        }

    }



    public byte[] converter(Object object){
        return   JsonUtils.toJson(object).getBytes();
    }

    /**
     * data 数据反映操作实体对象object,可以继承重写,架构目前提供两种序返序列化实现,默认是json, 可选protobuf序列化;
     * 或者实现重写该方法 实现自己的序列化实现
     * @param data
     * @param clazz
     * @return
     */
    public T serializable(byte[] data,Class<T> clazz){
        if(data == null || null == clazz){
            return null;
        }
        return jsonSerializable(data,clazz);
    }

    protected T jsonSerializable(byte[] data ,Class<T> clazz) {
        if(data == null || null == clazz){
            return null;
        }
        if (String.class == clazz) {
            return (T)new String(data);
        }

        T josn = JsonUtils.parseObject(data, clazz);
        return josn;
    }

    /**
     * 失败异常时处理实现
     * @param payload
     */
    protected   void onException(RocketMQConsumerWithPayload payload){

        onExceptionService(payload);
    }

    protected T pbSerializable(byte[] data ,Class<T> clazz){
        if(data == null || null == clazz){
            return null;
        }
        T pb = SerializableUtil.parseValue(clazz, data);
        return pb;
    }



    protected boolean doubleCheck(long globalId, int failedRetryCount){
        return redisClusterServer.setex(RedisKeys.MQ_CONSUMER + globalId,
                String.valueOf(failedRetryCount), getCacheTime());
    }

    /**
     * 失败时重试次数统计,异常重试次数减一
     * @param globalId
     * @return
     */
    protected boolean decrCheckCount(long globalId){
        logger.info("RocketMQ redis double check message Consumer...");
        long retryCount = redisClusterServer.decr(RedisKeys.MQ_CONSUMER + globalId);
        if (  retryCount > 0) {//删除失败
            return true;
        }
        return false;

    }

    /**默认缓存时间为一天 CACHE_TIME = 60*60*24; **/
    protected int getCacheTime(){
        return CACHE_TIME;
    }
    /**
     * 执行成功后,删除唯一值
     * @param globalId
     * @return
     */
    protected boolean delCheckRedisKey(long globalId){
        logger.info("RocketMQ redis double check message Consumer...");
        long retryCount = redisClusterServer.del(RedisKeys.MQ_CONSUMER + globalId);
        if (retryCount >  0 ) {//删除
            return true;
        }
        return false;

    }


}
