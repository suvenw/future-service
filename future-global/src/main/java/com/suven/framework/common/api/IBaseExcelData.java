package com.suven.framework.common.api;

import java.util.List;

/**
 * dao基础类
 *
 * @author dongxie
 **/
public interface IBaseExcelData {

    /**
     * 每一次批量插入的数量
     */
    int BATCH_SIZE = 100;
    /**
     * excel导入数据 保存到数据的方法
     * @param list
     */
  default void saveData(List<Object> list){}



}