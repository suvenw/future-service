package com.suven.framework.generator.enums;

import java.util.*;

/**
 * 显示类型,1:文本;2:日期时间;3:下拉框,4:单选框;5:多选框;6:多行文本;7:文件;8:树型;9:图片
 */
public enum PageShowTypeEnum {


    TEXT(1,"文本"),
    DATA_TIME(2,"日期时间"),
    SELECT(3,"下拉框"),
    radio(4,"单选框"),
    CHECKBOX(5,"多选框"),
    TEXTAREA(6,"多行文本"),
    FILE(7,"文件"),
    TREE(8,"树型"),
    IMG(9,"图片"),

    ;



    private int id;
    private String desc;

    private static Map<String,PageShowTypeEnum> enumMap = new HashMap<>();
    static {
        for(PageShowTypeEnum enums : values()) {
            enumMap.put(enums.desc, enums);
        }

    }


    private PageShowTypeEnum(int id ,String desc){
        this.id = id;
        this.desc = desc;
    }

    public static int getId(String des){
        PageShowTypeEnum enums =  enumMap.get(des);
        if(null == enums){
            return 0;
        }return enums.id;
    }

}
