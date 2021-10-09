package com.suven.framework.http.interceptor;

import com.suven.framework.common.api.IRequestVo;
import com.suven.framework.core.jetty.settings.SystemParamSettings;
import com.suven.framework.http.JsonParse;
import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.http.validator.ParamValidatorConstant;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.http.handler.OutputMessage;
import com.suven.framework.http.message.ParamMessage;
import com.suven.framework.http.message.HttpRequestPostMessage;
import com.suven.framework.http.message.HttpRequestRemote;
import com.suven.framework.http.processor.url.UrlExplain;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;
import java.util.Set;

import static com.suven.framework.common.enums.SysResultCodeEnum.*;

/**
 * @Title: ParameterHandlerInterceptor.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) http 接口拦截器,主要是实现请求接口参数业务校验业务,排序值为7;
 */



@Component("parameterHandlerInterceptor")
@InterceptorOrder(order = InterceptorOrderValue.HANDEL_ORDER_PARAMETER)
public class ParameterHandlerInterceptor extends BaseHandlerInterceptorAdapter implements IHandlerInterceptor, InterceptorConstants{//HandlerInterceptorAdapter
    private Logger logger = LoggerFactory.getLogger(ParameterHandlerInterceptor.class);

    private boolean isCheckParamSign = false;
    private SystemParamSettings systemParamSettings;

    public ParameterHandlerInterceptor(){
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        systemParamSettings = applicationContext.getBean(SYSTEM_PARAM_SETTINGS, SystemParamSettings.class);
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info(" url request ParameterHandlerInterceptor  preHandle ===================");
        HttpRequestRemote remote = ParamMessage.getRequestRemote();
        HttpRequestPostMessage message = ParamMessage.getRequestMessage();
        if(remote == null){
            logger.warn(" TokenHandlerInterceptor RequestRemote is null remote: [{}] ", remote );
            return false;
        }//接口是否做加密处理,默认是加密的,配置为非加密,跳过
        if(!UrlExplain.isParamSign(remote.getUrl())){
            return true;
        }//通过配置文件,配置系统级别是否验证加密;
        if(null != systemParamSettings && systemParamSettings.isCheckParamSign()){//参数窜改验证
            if (!remote.getSrvMd5Sign().equals( remote.getCliMd5Sign())){
                logger.warn("非法请求client_sign={},server_sign={}",remote.getCliMd5Sign(),remote.getSrvMd5Sign());
                throw new SystemRuntimeException(SYS_PARAM_CHECK);
            }
        }
        this.checkRequestParam(message);//基本请求参数验证
        if(remote.isPostReq()){ //post或get 基础参数验证;
            checkPostParam(message,remote);
        }else{
            checkGetParam(message);
        }
        //具体业务参数验证;
        if(null != systemParamSettings && systemParamSettings.isCheckBody()){
            this.checkOnlyParameter((HandlerMethod) handler);
        }

        return true;
    }

    /**
     * @Title: 公共参数校验
     * @Description:
     * @param
     * @return
     * @throw
     * @author lixiangling
     * @date 2018/5/28 14:06
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    private void checkRequestParam(HttpRequestPostMessage message)throws SystemRuntimeException {
        IResultCodeEnum sysMsgEnumType =  SYS_PARAM_ERROR;
        if(message.getAppId() <= 0){
            throw new SystemRuntimeException(sysMsgEnumType, ParamValidatorConstant.System.CLIENT_IS_NOT_NULL);
        }
//        else if(null == ProjectModuleName.getType(message.getPlatform())){
//            logger.warn("非法请求platform={}",message.getPlatform());
//            throw new SystemRuntimeException(SysMsgEnumType.SYS_INVALID_REQUEST);
//        }
        else if(message.getVersion() == 0){
            logger.warn("非法请求version={}",message.getVersion());
            throw new SystemRuntimeException(SYS_INVALID_REQUEST);
        }

    }
    /**
     * @Title: GET请求参数校验
     * @Description:
     * @param
     * @return
     * @throw
     * @author lixiangling
     * @date 2018/5/28 14:06
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    private void checkGetParam(HttpRequestPostMessage message){

    }
    /**
     * @Title: POST请求参数校验
     * @Description:
     * @param
     * @return
     * @throw
     * @author lixiangling
     * @date 2018/5/28 14:06
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    private void checkPostParam(HttpRequestPostMessage message, HttpRequestRemote remote)throws SystemRuntimeException{
        IResultCodeEnum sysMsgEnumType =  SYS_PARAM_ERROR;
        //post 接口验证用户登陆参数
        if(!remote.isWhite()){
            if(message.getUserId() <= 0 || StringUtils.isBlank(message.getAccessToken()) ){
                logger.warn("login faild userId={},accessToken={}",message.getUserId(),message.getAccessToken());
                throw new SystemRuntimeException(SYS_TOKEN_NULL);
            }
        }

        if(StringUtils.isBlank(message.getSysVersion())){
            throw new SystemRuntimeException(sysMsgEnumType).format( ParamValidatorConstant.System.SYS_VERSION_IS_NOT_NULL);
        }else if(message.getTimes() == 0) {
            throw new SystemRuntimeException(sysMsgEnumType).format(ParamValidatorConstant.System.TIMES_IS_NOT_NULL);
        }

    }

    /**
     * 具体实现业务参数验证;
     * 原始类型：一般意义上的java类，由class类实现
     * 参数化类型：ParameterizedType接口的实现类
     * 数组类型：GenericArrayType接口的实现类
     * 类型变量：TypeVariable接口的实现类
     * 基本类型：int，float等java基本类型，其实也是class
     * @param handlerMethod
     * @throws Exception
     */
    private void checkOnlyParameter(HandlerMethod handlerMethod) throws Exception{
        Type[] parameterTypes = handlerMethod.getMethod().getGenericParameterTypes();
        if(parameterTypes == null || parameterTypes.length <1){
            return;
        }
        for (Type parameterType : parameterTypes){
            if(parameterType == OutputMessage.class || parameterType == HttpServletRequest.class
                    || parameterType == HttpServletResponse.class || parameterType == HttpSession.class){
                continue;
            }
            Class genericClazz = null;
            if (parameterType instanceof ParameterizedType) {
                ParameterizedType pt = (ParameterizedType) parameterType;
                Type rawType =  pt.getRawType();
                if(rawType == java.util.List.class || rawType == Set.class || rawType == java.util.Arrays.class
                        || rawType == java.util.Queue.class || rawType == java.util.Iterator.class ){
                    genericClazz = (Class<?>)pt.getActualTypeArguments()[0];
                }else if(rawType == Map.class){
                    genericClazz = (Class<?>)pt.getActualTypeArguments()[1];
                }else {
                    genericClazz = (Class)rawType;
                }
            } else if (parameterType instanceof TypeVariable) {
                TypeVariable tType = (TypeVariable) parameterType;
                genericClazz = tType.getGenericDeclaration().getClass();
            }
            else {
                genericClazz = (Class) parameterType;
            }
            if(genericClazz == null){
                return;
            }
            if(IRequestVo.class.isAssignableFrom(genericClazz) |  JsonParse.class.isAssignableFrom(genericClazz)){
                checkValidatoParameter(genericClazz);
            }

        }
    }

    //换行符
    private static String lineSeparator = System.lineSeparator();

    public void checkValidatoParameter( Class  clazz ) throws Exception{
        Map body =  ParamMessage.getJsonParseMap();
        Method method = clazz.getMethod("parseFrom", Map.class, Class.class);
        Object  object =  method.invoke(clazz.newInstance(), body, clazz);
//        checkValidatoParameter(object);
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        //获取validator实例
        Validator validator = validatorFactory.getValidator();
        //调用调用，得到校验结果信息 Set
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(object);
        //StringBuilder组装异常信息
        StringBuilder builder = new StringBuilder();
        //遍历拼装
        constraintViolationSet.forEach(violationInfo -> {
            builder.append( lineSeparator + violationInfo.getPropertyPath()+" : "+violationInfo.getMessage() );
        });
        if (builder.length() > 0){
            throw new SystemRuntimeException(SYS_PARAM_ERROR.formatMsg(builder.toString()));

        }
    }


}
