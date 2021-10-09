package com.suven.framework.common.api;


/**
 * 框架操作实现entity 参数克隆实现接口类
 */
public interface IBeanClone {
    <T extends IBeanClone> T clone(Object source) ;


}
