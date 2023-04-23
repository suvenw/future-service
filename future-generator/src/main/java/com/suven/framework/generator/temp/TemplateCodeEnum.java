package com.suven.framework.generator.temp;

import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author 作者 : suven.wang
 * @CreateDate 创建时间: 2022-03-29
 * @WeeK 星期: 星期四
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description (说明):  模板工具类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Copyright: (c) 2021 gc by https://www.suven.top
 **/
@Component
public class TemplateCodeEnum {
    public static  Map<String, Class<?>> getTemplateEnum() {
        /**
         * 搜索所有类
         */
        Reflections reflections = new Reflections("com");
        Set<Class<? extends CreateCodeEnum>> classList = reflections.getSubTypesOf(CreateCodeEnum.class);
        Map<String, Class<?>> map = new HashMap<>();
        if(null == classList || classList.isEmpty()){
            return map;
        }
        for (Class<?> clazz : classList){
            try {
                if (!clazz.isEnum()) {
                    continue;
                }
//                Class<E> enumClass = (Class<E>)clazz;
                map.put(clazz.getSimpleName(),clazz);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return map;
    }
}
