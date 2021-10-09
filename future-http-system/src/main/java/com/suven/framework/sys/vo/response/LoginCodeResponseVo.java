package com.suven.framework.sys.vo.response;


public class LoginCodeResponseVo {

    private String key;
    private String code;

    public static LoginCodeResponseVo build(){
        return new LoginCodeResponseVo();
    }

    public String getKey() {
        return key;
    }

    public LoginCodeResponseVo setKey(String key) {
        this.key = key;
        return this;
    }

    public String getCode() {
        return code;
    }

    public LoginCodeResponseVo setCode(String code) {
        this.code = code;
        return this;
    }
}
