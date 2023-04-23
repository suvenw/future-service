package com.suven.framework.generator.enums;

/**
 * 支持的语言类型
 * Created by suven on 2017/9/21.
 */
public enum LanguageEnum {
    JAVA("java");

    private String lan;

    LanguageEnum(String lan) {
        this.lan = lan;
    }

    public String getLan() {
        return lan;
    }

    public static LanguageEnum getLanguageType(String lan) {
        LanguageEnum[] types = values();
        for (int i = 0; i < types.length; i++) {
            if (types[i].getLan().equalsIgnoreCase(lan)) {
                return types[i];
            }
        }
        return null;
    }
}