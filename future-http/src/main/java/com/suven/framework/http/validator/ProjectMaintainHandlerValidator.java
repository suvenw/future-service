package com.suven.framework.http.validator;

import com.suven.framework.http.interceptor.IHandlerValidator;
import com.suven.framework.core.redis.RedisClusterServer;
import com.suven.framework.core.redis.RedisKeys;
import com.suven.framework.http.inters.ProjectModuleEnum;
import com.suven.framework.http.message.ParamMessage;
import com.suven.framework.http.message.HttpRequestRemote;
import com.suven.framework.util.tool.TopStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectMaintainHandlerValidator extends ValidatorCache<Boolean> implements IHandlerValidator {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private RedisClusterServer redisClusterServer;

    @Override
    protected Boolean validator() {

        String url = ParamMessage.getRequestRemote().getUrl();

        String serverName = ParamMessage.getRequestRemote().getModule();
        List<String> projectNameList = Arrays.stream(ProjectModuleEnum.values()).filter(e->e.getModule().endsWith(serverName.toUpperCase())).
                map(e->e.getModule()).collect(Collectors.toList());
        if(projectNameList == null || projectNameList.isEmpty()){
            return false;
        }
        if(this.isMaintainModule(projectNameList.get(0))){
            return true;
        }
        return false;
    }

    @Override
    protected String cacheKey() {
        HttpRequestRemote remote = ParamMessage.getRequestRemote();
        String cacheKey = TopStringUtils.toStringBuilder(
                ValidatorEnum.MAINTAIN.name(),
                remote.getModule()).toString();
        return cacheKey;
    }



    /**
     * @Title: 验证模块是否维护模式
     * @Description:
     * @param
     * @return
     * @throw
     * @author lixiangling
     * @date 2018/7/9 19:18
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    private boolean isMaintainModule(String serverProjectName) {
        String projectNames = redisClusterServer.get(RedisKeys.OAUTH_MAINTAIN + "ALL");
        List<String> projectNameList = new ArrayList<>();
        if(!StringUtils.isBlank(projectNames)) {
            projectNameList.addAll(Arrays.asList(StringUtils.split(projectNames, ",")));
        }
        logger.info("origin serverNames={},request serverName={}",projectNames, serverProjectName);
        if(!projectNameList.isEmpty()&&projectNameList.contains(serverProjectName)){
            return true;
        }
        return false;
    }



    @Override
    public boolean validatorData() {
        return validator();
    }
}
