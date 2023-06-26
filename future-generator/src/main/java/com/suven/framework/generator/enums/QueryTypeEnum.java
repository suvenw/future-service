package com.suven.framework.generator.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * "1" =
 * "2" !=
 * "3" &gt;
 * "4" &gt;=
 * "5" &lt;
 * "6" &lt;=
 * "7" like
 * "8" lift like
 * "9" right like
 *
 * @author by suven on 18-8-7.
 */
public enum QueryTypeEnum {

    EMPTY(0, "","为空"),
    EQ(1, "=","等于"),
    NOT_EQ(2, "&lt;&gt;","不等于"),
    GT(3, "&gt;","大于"),
    GT_EQ(4, "&gt;=","大于等于"),
    LT(5, "&lt;","小于"),
    LT_EQ(6, "&lt;=","小于等于"),
    LIKE(7, "like concat('%',concat(key,'%')) ","like模糊查询"),
    LEFT_LIKE(8, "like concat('%', key)","左模糊查询"),
    RIGHT_LIKE(9, "like concat(key, '%')","右模糊查询");


    private Integer code;
    private String key;
    private String desc;

    QueryTypeEnum(Integer code, String key, String desc) {
        this.code = code;
        this.key = key;
        this.desc=desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    private static Map<String, QueryTypeEnum> enumMap = new HashMap<>();
    static {
        for(QueryTypeEnum enums : values()) {
            enumMap.put(enums.desc, enums);
        }

    }


    public static int getId(String des){
        QueryTypeEnum enums =  enumMap.get(des);
        if(null == enums){
            return 0;
        }return enums.code;
    }

}
