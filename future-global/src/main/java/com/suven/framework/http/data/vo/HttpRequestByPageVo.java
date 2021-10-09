package com.suven.framework.http.data.vo;

import com.suven.framework.common.api.ApiDesc;

public class HttpRequestByPageVo extends RequestParserVo {


    @ApiDesc(value= "分页请求使用,对应为页码: 1,2,3 ...; 0/1 都是1")
    private int pageNo;
    @ApiDesc(value= "分页请求使用,对应为大小:默认是100,超过100条的接口需要重写page的方法")
    private int pageSize;
    /** status */
    @ApiDesc(value= "对应的数据列表的状态属性: 1.上架(显示), 0.下架(不显示/删除) ")
    private int status;
    /** sort */
    @ApiDesc(value= "对应的数据列表的排序属性 ")
    private int sort;



    public <T extends HttpRequestByPageVo> T toPageNo(int pageNo) {
        this.pageNo = pageNo;
        return (T) this;
    }



    public <T extends HttpRequestByPageVo> T toPageSize(int pageSize) {
        this.pageSize = pageSize;
        return (T)this;
    }




    public <T extends HttpRequestByPageVo> T  toStatus(int status) {
        this.status = status;
        return (T)this;
    }



    public <T extends HttpRequestByPageVo> T  toSort(int sort) {
        this.sort = sort;
        return (T)this;
    }
    public int getPageNo() {
        return pageNo;
    }
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSort() {
        return sort;
    }
    public void setSort(int sort) {
        this.sort = sort;
    }


}
