package com.suven.framework.common.aop;


import com.suven.framework.common.GlobalLogbackThread;
import com.suven.framework.common.constants.GlobalConfigConstants;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * 全局日志跟踪类型
 *
 * @author vincentdeng
 */
public class GlobalRpcLogbackThread  extends GlobalLogbackThread {

   private static Logger logger = LoggerFactory.getLogger(GlobalRpcLogbackThread.class);

    public static void setGlobalLogbackTraceId(){
        try{
            String traceId = RpcContext.getContext()
                    .getAttachment(GlobalConfigConstants.LOGBACK_TRACE_ID);
            if(null == traceId || "".equals(traceId)){
                traceId =  MDC.get(GlobalConfigConstants.LOGBACK_TRACE_ID);
            }
            if(null != traceId){
                MDC.put(GlobalConfigConstants.LOGBACK_TRACE_ID,traceId);
                RpcContext.getContext().setAttachment(GlobalConfigConstants.LOGBACK_TRACE_ID, traceId);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("Exception by setGlobalLogbackTraceId error[{}]",e);
        }
    }



}


