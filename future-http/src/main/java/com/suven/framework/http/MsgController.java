package com.suven.framework.http;

import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.common.api.ApiDoc;
import com.suven.framework.common.api.DocumentConst;
import com.suven.framework.common.constants.GlobalConfigConstants;
import com.suven.framework.http.data.vo.RequestParserVo;
import com.suven.framework.http.handler.OutputResponse;
import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.http.message.HttpMsgEnumError;
import com.suven.framework.http.message.HttpRequestGetMessage;
import com.suven.framework.http.message.HttpRequestPostMessage;
import com.suven.framework.http.processor.url.SysURLCommand;
import com.suven.framework.util.createcode.swagger.SwaggerReflectionsDoc;
import com.suven.framework.util.createcode.swagger.SwaggerResultBean;
import com.suven.framework.util.crypt.CryptUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;


@ApiDoc(
        group = DocumentConst.GLobal.API_DOC_BASE_GROUP,
        groupDesc= DocumentConst.GLobal.API_DOC_BASE_DES,
        module = "API 接口公共文档", isApp = true
)
@Controller
public class MsgController {



    @ApiDoc(
            value = "错误信息查询",
            request = RequestParserVo.class,
            response = HttpMsgEnumError.class
    )
    @RequestMapping(value = SysURLCommand.sys_get_error_no, method = RequestMethod.GET)
    public void getSysMsgEnumError(OutputResponse out, RequestParserVo jsonParse)  {
       Map<Integer, IResultCodeEnum> error =  IResultCodeEnum.MsgEnumType.getMsgTypeMap();
       Collection<IResultCodeEnum> list =   error.values();
        List<HttpMsgEnumError>  voList = new ArrayList<>();
        list.forEach(code ->{
            voList.add(HttpMsgEnumError.build().init(code.getCode(),code.getMsg()));
        });
        Collections.sort(voList);
        out.write(voList);
    }

    @ApiDoc(
            value = "文档数据搜索数据",
            request = ApiDocJsonParse.class,
            response = String.class
    )
    @RequestMapping(value = SysURLCommand.sys_get_service_api_doc, method = RequestMethod.GET)
    public void getServiceApiDoc(OutputResponse out, ApiDocJsonParse jsonParse)  {
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

    @ApiDoc(
            value = "接口服务基本响应",
            request = RequestParserVo.class,
            response = long.class
    )
    @RequestMapping(value = SysURLCommand.sys_get_framework, method = RequestMethod.GET)
    public void getFrameworkApi(OutputResponse out, RequestParserVo jsonParse)  {
        out.writeSuccess();
    }

    @ApiDoc(
            value = "POST请求的公共参数",
            request = HttpRequestPostMessage.class,
            response = long.class
    )
    @RequestMapping(value = SysURLCommand.sys_get_post_param, method = RequestMethod.GET)
    public void getSystemPostParam(OutputResponse out, ApiDocJsonParse jsonParse)  {
        out.writeSuccess();
    }

    @ApiDoc(
            value = "Get请求的公共参数",
            request = HttpRequestGetMessage.class,
            response = long.class
    )
    @RequestMapping(value = SysURLCommand.sys_get_get_param, method = RequestMethod.GET)
    public void getSystemGetParam(OutputResponse out, ApiDocJsonParse jsonParse)  {
        out.writeSuccess();
    }


    @ApiDoc(
            value = "请求参数加密例子",
            request = SystemParamSignParse.class,
            response = long.class
    )
    @RequestMapping(value = SysURLCommand.sys_get_sign_param, method = RequestMethod.GET)
    public void getSystemParamSign(OutputResponse out, SystemParamSignParse signParse)  {
        String param = "";
        if(signParse.getSalt() == 1){
            param = CryptUtil.md5(param + GlobalConfigConstants.TOP_SERVER_APPKEY).toLowerCase();
        }else{
            param  = CryptUtil.md5(signParse.getCliSign()).toLowerCase();
        }

        String result =  "原始参数:["+signParse.getCliSign()+"]\n  md5 加密后的结果:[" + param +"]";
        out.write(result);
    }

    public static class ApiDocJsonParse extends RequestParserVo {
        @ApiDesc(value= "模糊搜索中文描述或url " )
        private String search;

        public String getSearch() {
            return search;
        }

        public void setSearch(String search) {
            this.search = search;
        }
    }

    public static class SystemParamSignParse extends RequestParserVo {
        @ApiDesc(value= "参与加密的字符串:eg: appId=10000&accessToken=abcedeeee&device=abcdeee123 " )
        private String cliSign;
        @ApiDesc(value= "是否拼接盐,1.后台拼接,0.直接md5,不拼接盐信息" )
        private int salt;

        public String getCliSign() {
            return cliSign;
        }

        public void setCliSign(String cliSign) {
            this.cliSign = cliSign;
        }

        public int getSalt() {
            return salt;
        }

        public void setSalt(int salt) {
            this.salt = salt;
        }
    }

}