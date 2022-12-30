package com.suven.framework.http.validator;


import com.suven.framework.http.interceptor.IHandlerValidator;
//import org.apache.dubbo.config.ReferenceConfig;
//import org.apache.dubbo.config.RegistryConfig;
import com.suven.framework.http.message.ParamMessage;
import com.suven.framework.http.message.HttpRequestPostMessage;
import com.suven.framework.common.constants.GlobalConfigConstants;
import com.suven.framework.util.tool.ReflectionUtilTool;
import com.suven.framework.util.tool.TopStringUtils;
import com.suven.framework.util.validate.ValidatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author suven.wang@ XXX.net
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) 基于 Gaven LoadingCache 缓存实现软件版本号校验实现方法
 */


@Component("versionHandlerValidator")
public class VersionHandlerValidator extends ValidatorCache<Map<String, VersionHandlerVo>>  implements IHandlerValidator {


    private Logger logger = LoggerFactory.getLogger(VersionHandlerValidator.class);



    private final static int LOADING_CACHE_DEFAULT_SECONDS = 60*2;

    /**
     * 抽象设置临时缓存时间值
     * @return
     */
    @Override
    protected  int refreshAfterWriteTimeSeconds(){
        return LOADING_CACHE_DEFAULT_SECONDS;
    }

    private boolean serverIsDubbo = false;

    private Map<String, VersionHandlerVo> newVersionVoMap;

    @Override
    protected Map<String, VersionHandlerVo> validator() {
        try {
            Object object = null;
            if(serverIsDubbo){
//                ReferenceConfig<ValidatorService> referenceConfig = new ReferenceConfig<>();
//                referenceConfig.setRegistry(new RegistryConfig(registryAddress));
//                referenceConfig.setInterface(ValidatorService.class);
//                ValidatorService validatorService = referenceConfig.get();
//                object = ReflectionUtilTool.invokeBeanMethod( validatorService, GlobalConfigConstants.VERSION_HANDLER_METHOD_NAME,null);

            }else {
                object = ReflectionUtilTool.invokeClassMethod(
                        GlobalConfigConstants.VERSION_INFO_SERVICE_NAME,
                        GlobalConfigConstants.VERSION_HANDLER_METHOD_NAME);

            }
            if(null == object ||  !(object instanceof Map)){
                return null;
            }

            return copyMap(object);
        }catch (Exception e){
            logger.error("",e);
        }
        return null;
    }

    private Map<String, VersionHandlerVo> copyMap(Object object){
        if(null == object ||  !(object instanceof Map)){
            return Collections.emptyMap();
        }
        Map<String,Object> map = (Map)object;
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        Map<String, VersionHandlerVo> versionVoMap = new HashMap<>();
        while(it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            VersionHandlerVo versionVo =  VersionHandlerVo.build().clone(entry.getValue());
            versionVoMap.put(entry.getKey(),versionVo);
        }
        return versionVoMap;
    }

    @Override
    protected boolean cacheValidatorValue(Map<String, VersionHandlerVo> validatorValueMap){
        int currentVersion =   ParamMessage.getRequestMessage().getVersion();
        newVersionVoMap = validatorValueMap;
        String updateKey = cacheUpdateVersionKey();
        VersionHandlerVo updateVersion  = validatorValueMap.get(updateKey);
        if(null!=updateVersion && updateVersion.forceUpdate()  && updateVersion.getVersion() >= currentVersion){//强制
            return true;//强制更新
        }
        return false;
    }

    /**
     * 获取最新版本信息
     * platform_channel_forceUpdate
     * @return
     */

    public VersionHandlerVo getNewVersionVo(){
        String newVersionKey = cacheNewVersionKey();
        VersionHandlerVo versionVo = newVersionVoMap.get(newVersionKey);
        if (versionVo != null){
            return versionVo;
        }newVersionKey = cacheNewVersionKey0();
        return newVersionVoMap.get(newVersionKey);
    }


    /** 全量刷新版本信息*/
    @Override
    protected String cacheKey() {
        return ValidatorEnum.VERSION.name();
    }

    /** platform_channel_forceUpdate 强更平台渠道*/
    private String cacheUpdateVersionKey() {
        HttpRequestPostMessage message = ParamMessage.getRequestMessage();
        String updateKey = TopStringUtils.toStringBuilder(
                message.getPlatform(),message.getChannel(),1).toString();
        return updateKey;
    }
    /** platform_channel_forceUpdate 指定渠道*/
    private String cacheNewVersionKey() {
        HttpRequestPostMessage message = ParamMessage.getRequestMessage();
        String updateKey = TopStringUtils.toStringBuilder(
                message.getPlatform(),message.getChannel(),0).toString();
        return updateKey;
    }

    /** platform_channel_forceUpdate 官方渠道*/
    private String cacheNewVersionKey0() {
        HttpRequestPostMessage message = ParamMessage.getRequestMessage();
        String updateKey = TopStringUtils.toStringBuilder(
                message.getPlatform(),0,0).toString();
        return updateKey;
    }




    @Override
    public boolean validatorData() {
        try {
            return validatorCache();
        }catch (Exception e){

        }return false;

    }
}
