package com.suven.framework.http;

import com.suven.framework.common.constants.GlobalConfigConstants;
import com.suven.framework.http.data.vo.RequestParserVo;
import com.suven.framework.http.handler.OutputMessage;
import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.http.processor.url.SysURLCommand;
import com.suven.framework.util.createcode.swagger.SwaggerReflectionsDoc;
import com.suven.framework.util.createcode.swagger.SwaggerResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;


@Controller
public class MsgController {



    @RequestMapping(value = SysURLCommand.sys_get_error_no, method = RequestMethod.GET)
    public void getSysMsgEnumError(OutputMessage out, JsonParse jsonParse)  {
       Map<Integer, IResultCodeEnum> error =  IResultCodeEnum.MsgEnumType.getMsgTypeMap();
       Collection<IResultCodeEnum> list =   error.values();
       Map<Integer, String> map = new TreeMap<>();
        list.forEach(code ->{
            map.put(code.getCode(),code.getMsg());
        });
        out.write(map);
    }

    @RequestMapping(value = SysURLCommand.sys_get_service_api_doc, method = RequestMethod.GET)
    public void getServiceApiDoc(OutputMessage out,ApiDocJsonParse jsonParse)  {
        SwaggerResultBean api =  SwaggerReflectionsDoc.getApiDoc(jsonParse.getSearch());
        if(null != api){
            out.writeApi(api);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("please go to  application.properties file  system config info : ");
        sb.append(GlobalConfigConstants.TOP_SERVER_API_ENABLED);
        sb.append(" = true");
        out.writeApi(sb.toString());

    }


    public static class ApiDocJsonParse extends RequestParserVo {
        private String search;

        public String getSearch() {
            return search;
        }

        public void setSearch(String search) {
            this.search = search;
        }
    }


}