package com.suven.framework.sys.vo.response;


import java.util.List;

public class SysLoginResponseVo {

    private int multi_depart;
    private SysUserResponseVo userInfo;

    private String token;

    private List<SysDepartResponseVo> departs;

    public int getMulti_depart() {
        return multi_depart;
    }

    public SysLoginResponseVo setMulti_depart(int multi_depart) {
        this.multi_depart = multi_depart;
        return this;
    }

    public SysUserResponseVo getUserInfo() {
        return userInfo;
    }

    public SysLoginResponseVo setUserInfo(SysUserResponseVo userInfo) {
        this.userInfo = userInfo;
        return this;
    }

    public String getToken() {
        return token;
    }

    public SysLoginResponseVo setToken(String token) {
        this.token = token;
        return this;
    }

    public List<SysDepartResponseVo> getDeparts() {
        return departs;
    }

    public SysLoginResponseVo setDeparts(List<SysDepartResponseVo> departs) {
        this.departs = departs;
        return this;
    }
}
