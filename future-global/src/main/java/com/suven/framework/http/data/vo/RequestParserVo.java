package com.suven.framework.http.data.vo;

import com.suven.framework.common.api.IRequestVo;
import com.suven.framework.common.data.BaseBeanClone;
import com.suven.framework.http.JsonParse;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Map;

public class RequestParserVo extends BaseBeanClone implements IRequestVo,Serializable {


    public static RequestParserVo build(){
        return new RequestParserVo();
    }



    @Override
    public <T> T parseFrom(Map<String, Object>  map, Class<T> clazz) throws Exception{
        T iRequestVo =  JsonParse.parseFrom(map,clazz);
        return iRequestVo;
    }


    @Override
    public <T> T parseJson(String json, Class<T> clazz) {
        T iRequestVo =   JsonParse.parseJson(json,clazz);
        return iRequestVo;
    }




}

