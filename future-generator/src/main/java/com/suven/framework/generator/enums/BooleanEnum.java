
/**
 * 内部字段类型
 *
 * @author JueYue on 2017/9/21.
 */

package com.suven.framework.generator.enums;

/**
 * @author by suven on 18-5-20.
 */
public enum BooleanEnum {

    YES(1, "1"), NO(2, "2");

    private int intD;
    private String str;

    BooleanEnum(int intD, String str) {
        this.intD = intD;
        this.str = str;
    }

    public int getIntD() {
        return intD;
    }

    public void setIntD(int intD) {
        this.intD = intD;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
