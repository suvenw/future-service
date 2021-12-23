package com.suven.framework.sys.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.suven.framework.core.redis.RedisClusterServer;
import com.suven.framework.core.redis.RedisKeys;
import com.suven.framework.core.redis.RedisUtil;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleTokenOutService {


    @Autowired
    private RedisClusterServer redisClusterServer;


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public boolean delUserTokenInRedisByUserIds(long userId){
        String codeKey = RedisUtil.formatKey(RedisKeys.OAUTH_USER_ID_TOKEN_MAP,userId);
        String refreshKey = RedisUtil.formatKey(RedisKeys.OAUTH_USER_REFRESH_TOKEN_MAP, userId);
        redisClusterServer.del(codeKey,refreshKey);
        logger.info("delUserTokenInRedisByUserIds accessToken: [{}] , refreshToken: [{}] " ,  codeKey,refreshKey);

        return true;
    }


    public boolean delUserTokenInRedisByUserIds(List<Long> userIds){
        List<String> tokenList = new ArrayList<>();
        List<String> refreshList = new ArrayList<>();

        if(null != userIds && !userIds.isEmpty()){
            userIds.forEach(userId ->{
                String codeKey = RedisUtil.formatKey(RedisKeys.OAUTH_USER_ID_TOKEN_MAP,userId);
                String refreshKey = RedisUtil.formatKey(RedisKeys.OAUTH_USER_REFRESH_TOKEN_MAP, userId);
                tokenList.add(codeKey);
                refreshList.add(refreshKey);
            });
            redisClusterServer.del(tokenList.toArray(new String[0]));
            redisClusterServer.del(refreshList.toArray(new String[0]));
            logger.info("delUserTokenInRedisByUserIds AccessTokenList: [{}] , refreshTokenList: [{}] " ,  tokenList.toString(),refreshList.toString());

        }



        return true;
    }
}
