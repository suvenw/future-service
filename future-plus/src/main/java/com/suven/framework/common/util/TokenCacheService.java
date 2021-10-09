package com.suven.framework.common.util;

import com.suven.framework.core.redis.RedisClusterServer;
import com.suven.framework.core.redis.RedisConstants;
import com.suven.framework.core.redis.RedisKeys;
import com.suven.framework.core.redis.RedisUtil;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TokenCacheService {

    @Autowired(required = false)
    private RedisClusterServer redisClusterServer;
    /**
     * 校验用户checkUserCode信息, 是否有效,有效返回true,否则为false;
     *
     * @param parameter
     * @return
     */
    public boolean saveAuthorizationCode(OAuthParameter parameter) {

        String codeKey = RedisUtil.formatKey(RedisKeys.OAUTH_CODE, parameter.getUserId(), parameter.getClientId());
        boolean result = redisClusterServer.setex(codeKey, parameter.getCode(), RedisConstants.HALF_HOUR);
        return result;
    }

    protected OAuthParameter buildToken(long userId,String refreshToken,String  redirectUri) throws OAuthSystemException {
        OAuthIssuer oauthIssuer = new OAuthIssuerImpl(new MD5Generator());
        final String accessToken = oauthIssuer.accessToken();
        if(refreshToken == null || "".equals(refreshToken) ){
            refreshToken = oauthIssuer.refreshToken();
        }
        OAuthParameter token = OAuthParameter
                .build()
                .setUserId(userId)
                .setAccessToken(accessToken)
                .setTokenType(TokenType.BEARER.toString())
                .setRefreshToken(refreshToken)
                ;
        return token;
    }

    /**
     * 校验用户checkUserCode信息, 是否有效,有效返回true,否则为false;
     *
     * @param oAuthToken
     * @return
     */
    public boolean checkAuthorizationCode(OAuthParameter oAuthToken) {

        String codeKey = RedisUtil.formatKey(RedisKeys.OAUTH_CODE, oAuthToken.getUserId(), oAuthToken.getClientId());
        String code = redisClusterServer.get(codeKey);
        if (code != null && code.equals(oAuthToken.getCode())) {
            redisClusterServer.del(codeKey);
            return true;
        }
        return false;
    }

    /**
     * 刷新RefreshToken 一个用户仅且只有一个,过期时间为1个月,但token有多少,过期时间为7天
     * 创建token,并更新到缓存中
     *
     * @param oAuthToken
     * @return
     */
    public boolean saveUserToken(OAuthParameter oAuthToken) {
        String codeKey = RedisUtil.formatKey(RedisKeys.OAUTH_USER_ID_TOKEN_MAP, oAuthToken.getUserId());
        String refreshKey = RedisUtil.formatKey(RedisKeys.OAUTH_USER_REFRESH_TOKEN_MAP, oAuthToken.getUserId());
        String refreshToken = redisClusterServer.get(refreshKey);
        if (refreshToken != null) {
            oAuthToken.setRefreshToken(refreshToken);
        }
        boolean tokenResult = redisClusterServer.hmset(codeKey, oAuthToken.getAccessToken(), oAuthToken.getExpires());
        boolean refreshResult = redisClusterServer.setex(refreshKey, oAuthToken.getRefreshToken(), RedisConstants.ONE_MONTH);
        if (tokenResult && refreshResult) {
            redisClusterServer.expire(codeKey, RedisConstants.ONE_MINUTE);
            return true;
        }
        return false;
    }

    /**
     * 收集用户的原来token,并校验通过后,返回更新后的token,否则返回空;
     *
     * @param oAuthToken
     * @return
     */
    public OAuthParameter refreshToken(OAuthParameter oAuthToken) {
        String codeKey = RedisUtil.formatKey(RedisKeys.OAUTH_USER_ID_TOKEN_MAP, oAuthToken.getUserId());
        String refreshKey = RedisUtil.formatKey(RedisKeys.OAUTH_USER_REFRESH_TOKEN_MAP, oAuthToken.getUserId());
        String refreshToken = redisClusterServer.get(refreshKey);
        if (null != refreshToken && refreshToken.equals(oAuthToken.getRefreshToken())) {
            //增加token和过期时间
            redisClusterServer.hmset(codeKey, oAuthToken.getAccessToken(), oAuthToken.getExpires());
            redisClusterServer.expire(codeKey, RedisConstants.ONE_MINUTE);
            return oAuthToken;
        }
        return null;

    }

    /**
     * 校验用户token信息, 是否有效,有效返回true,否则为false;
     *
     * @param oAuthToken
     * @return
     */
    public boolean checkUserToken(OAuthParameter oAuthToken) {
        String codeKey = RedisUtil.formatKey(RedisKeys.OAUTH_USER_ID_TOKEN_MAP, oAuthToken.getUserId());
        long expire = System.currentTimeMillis();
        Map<String, String> result = redisClusterServer.getMapCacheAndDelExpire(codeKey, expire);
        if (null != result && result.containsKey(oAuthToken.getAccessToken())) {
            return true;
        }
        return false;
    }
}
