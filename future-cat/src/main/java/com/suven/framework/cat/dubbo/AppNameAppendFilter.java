package com.suven.framework.cat.dubbo;

import com.suven.framework.cat.CatConstants;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by bieber on 2015/11/12.
 */
@Activate(group = {CommonConstants.CONSUMER})
public class AppNameAppendFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(AppNameAppendFilter.class);
    
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
//        GlobalLogbackThread.setLogbackGlobalTraceId();
        String interfaceName = invoker.getInterface().getName();
        if(interfaceName.startsWith(CatConstants.COMPONENT_SCAN_BASE_PACKAGES)){
            logger = LoggerFactory.getLogger(interfaceName);
            logger.info("send server method_name = {}",invocation.getMethodName());
        }
        RpcContext.getServiceContext().setAttachment(CommonConstants.APPLICATION_KEY,invoker.getUrl().getParameter(CommonConstants.APPLICATION_KEY));

        return invoker.invoke(invocation);
    }


}
