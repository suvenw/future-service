package com.suven.framework.http.data.vo;

import com.alibaba.fastjson.JSON;
import com.suven.framework.common.api.IRequestVo;
import com.suven.framework.http.JsonParse;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

public class RequestParserVo implements IRequestVo {


    public static RequestParserVo build(){
        return new RequestParserVo();
    }


    @Override
    public <T> T parseFrom(Map<String, Object>  map, Class<T> clazz) throws Exception{
        return JsonParse.parseFrom(map,clazz);
    }

    @Override
    public <T> T parseJson(String json, Class<T> clazz) {
        T object =   JSON.parseObject(json, clazz);
        return object;
    }



    /**
     * 将map 转换成类型对象
     * @param headersMap
     * @param clazz
     * @return
     */
    @Override
    public <T>T parseHeader(Map<String,String> headersMap, Class<T> clazz)  {
        if (headersMap == null){
            return null;
        }
        T obj = null;
        try{
            obj = clazz.newInstance();
            Field[] fields = FieldUtils.getAllFields(clazz.getClass());
            for (Field field : fields) {
                try {
                    int mod = field.getModifiers();
                    if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){
                        continue;
                    }
                    field.setAccessible(true);
                    String value = headersMap.get(field.getName());
                    field.set(obj, value);
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
        }
        return obj;
    }



}

