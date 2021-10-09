package com.suven.framework.http.data.vo;

import com.suven.framework.common.api.ApiDesc;

public class HttpRequestByIdVo extends RequestParserVo {

    @ApiDesc(value= "对应的数据列表的主键属性,用于操作删除,修改 " )
    private long id;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
