package com.suven.framework.common.util;

public class OAuthParameter{
    private String refreshToken;
    private String accessToken;
    private long userId;
    private String code;
    private String client_id;
    private String state;

    private String tokenType;
    private Long expiresIn;
    private String scope;

    public static OAuthParameter build(){
        return new OAuthParameter();
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public OAuthParameter setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public OAuthParameter setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public String getClientId() {
        return client_id;
    }

    public OAuthParameter setClientId(String client_id) {
        this.client_id = client_id;
        return this;
    }

    public String getState() {
        return state;
    }

    public OAuthParameter setState(String state) {
        this.state = state;
        return this;
    }

    public String getCode() {
        return code;
    }

    public OAuthParameter setCode(String code) {
        this.code = code;
        return this;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public OAuthParameter setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getClient_id() {
        return client_id;
    }

    public OAuthParameter setClient_id(String client_id) {
        this.client_id = client_id;
        return this;
    }

    public String getTokenType() {
        return tokenType;
    }

    public OAuthParameter setTokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public String getExpires() {
        return String.valueOf(expiresIn);
    }
    public OAuthParameter setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public String getScope() {
        return scope;
    }

    public OAuthParameter setScope(String scope) {
        this.scope = scope;
        return this;
    }
}