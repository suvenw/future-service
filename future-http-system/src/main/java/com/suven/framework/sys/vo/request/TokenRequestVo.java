package com.suven.framework.sys.vo.request;

import com.suven.framework.http.data.vo.RequestParserVo;

public class TokenRequestVo  extends RequestParserVo {

    private String token;

    public String getToken() {
        return token;
    }

    public TokenRequestVo setToken(String token) {
        this.token = token;
        return this;
    }
}
