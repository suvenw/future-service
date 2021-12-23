package com.suven.framework.common.api;


/**
 * 框架操作实现entity 实现接口类
 */
public interface IBaseApi extends IBeanClone {

    public long getId();

    public String getEsId();

    public void setId(long id);

    public default long groupId(){
        return 0L;
    };

}
