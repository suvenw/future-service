package com.suven.framework.http.interceptor;

import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.exception.BusinessLogicException;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.http.validator.VersionHandlerValidator;
import com.suven.framework.http.validator.VersionHandlerVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * @Title: VersionHandlerInterceptor.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) http 接口拦截器,主要是校验接口使用软件的版本号业务,排序值为4;
 */


@Component
@InterceptorOrder(order = InterceptorOrderValue.HANDEL_ORDER_VERSION)
public class VersionHandlerInterceptor extends BaseHandlerInterceptorAdapter implements IHandlerInterceptor,InterceptorConstants{

    private static Logger logger = LoggerFactory.getLogger(VersionHandlerInterceptor.class);



    private VersionHandlerValidator versionValidator;

    public VersionHandlerInterceptor(){

    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        VersionHandlerValidator  validator = applicationContext.getBean(VERSION_HANDLER_VALIDATOR, VersionHandlerValidator.class);
        if(null != validator && validator instanceof  IHandlerValidator){
            versionValidator = validator;
        }
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws BusinessLogicException {
        logger.info(" url request VersionHandlerInterceptor  preHandle  =================== ");

        if( null == versionValidator ){
            return true;
        }if(!versionValidator.isPostRequest(request)){
            return true;
        }

        if(versionValidator.validatorData()){
            VersionHandlerVo newVersion =  versionValidator.getNewVersionVo();
            //提示强制升级信息 todo;
            throw new SystemRuntimeException(SysResultCodeEnum.SYS_VERSION_FORCE_UPDATE).setResponse(newVersion);
        }
        return true;


    }






}
