package com.suven.framework.cat.dubbo;

import com.suven.framework.cat.CatConstants;
import com.suven.framework.cat.CatUtils;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * 全局日志跟踪类型
 *
 * @author vincentdeng
 */
public class GlobalLogbackThread {

   private static Logger logger = LoggerFactory.getLogger(GlobalLogbackThread.class);

    public static void setGlobalLogbackTraceId(){
        try{
            String traceId = RpcContext.getServiceContext()
                    .getAttachment(CatConstants.LOGBACK_TRACE_ID);
            if(null == traceId || "".equals(traceId)){
                traceId =  MDC.get(CatConstants.LOGBACK_TRACE_ID);
            }
            if(null != traceId){
                MDC.put(CatConstants.LOGBACK_TRACE_ID,traceId);
                RpcContext.getServiceContext().setAttachment(CatConstants.LOGBACK_TRACE_ID, traceId);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Exception by setGlobalLogbackTraceId error[{}]",e);
        }
    }


    public static void setLogbackTraceId(String traceId){
        traceId = traceId == null ? "" : traceId;
        MDC.put( CatConstants.LOGBACK_TRACE_ID,traceId);
    }

    public static String getLogbackTraceIdInMDC(String module){
        try{
            String traceId =  MDC.get( CatConstants.LOGBACK_TRACE_ID);
            if(traceId == null){
                traceId = initGlobalLogbackTraceId(module);
            }
            return traceId;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Exception by setGlobalLogbackTraceId error[{}]",e);
        }
        return "";
    }

    public static String initGlobalLogbackTraceId(String module){
        if(module == null){
            module =  "";
        }
        String traceId = new StringBuilder("[")
                .append(module)
                .append("_")
                .append(CatUtils.getNowToNumFormat())
                .append("_")
                .append(CatUtils.halfUUID())
                .append("]").toString();
        MDC.put( CatConstants.LOGBACK_TRACE_ID,traceId);
        logger.info("MDC server global logback info logback_trace_id ");
        return traceId;
    }

    public static void removeTraceId(){
        MDC.remove( CatConstants.LOGBACK_TRACE_ID);
    }



}


