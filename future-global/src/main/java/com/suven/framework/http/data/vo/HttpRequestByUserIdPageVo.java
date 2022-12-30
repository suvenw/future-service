package com.suven.framework.http.data.vo;

import com.suven.framework.common.api.ApiDesc;

public class HttpRequestByUserIdPageVo extends HttpRequestByPageVo {

    @ApiDesc(value= "用户id" )
    private long userId;

    @ApiDesc(value= "id")
    private long id;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        if(userId == 0){
            userId =  getId();
        }
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
