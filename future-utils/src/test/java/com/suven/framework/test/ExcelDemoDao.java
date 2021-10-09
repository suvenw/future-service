package com.suven.framework.test;


import com.suven.framework.common.api.IBaseExcelData;

import java.util.List;

/**
 * @Author dongxie
 * @Description //TODO
 * @CreateDate 2019-11-15  11:23
 **/

public class ExcelDemoDao implements IBaseExcelData {

    @Override
    public void saveData(List<Object> list) {
        System.out.println("dao 来了");
        for(Object data:list){
            ExcelDemoData d=(ExcelDemoData)data;
            System.out.println(d.getDate());
        }
    }
}