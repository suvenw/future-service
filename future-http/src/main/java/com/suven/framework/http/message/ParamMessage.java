package com.suven.framework.http.message;

import com.suven.framework.http.processor.url.UrlExplain;
import com.suven.framework.util.json.JsonUtils;
import org.springframework.stereotype.Component;

import java.util.Map;



/**
 * @Title: IRequestRemote.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) http get/post 接口对应参数解释,通过当前线程安装实现参数缓存;
 */


@Component
public class ParamMessage {

    /**请求参数**/
    private static ThreadLocal<HttpRequestPostMessage> param = new ThreadLocal<>();
    /**url参数 类型**/
    private static ThreadLocal<Map> json = new ThreadLocal<>();
    /**url参数 类型**/
    private static ThreadLocal<HttpRequestRemote> remote = new ThreadLocal<>();

    /**url参数 类型**/
    private static ThreadLocal<Object> responseResultVo = new ThreadLocal<>();



    /**请求参数**/
    public static HttpRequestPostMessage getRequestMessage() {
        HttpRequestPostMessage result = param.get();
        return result == null ? new HttpRequestPostMessage() : result;
    }

    /**请求参数**/
    public static void setRequestMessage(HttpRequestPostMessage cmssage) {
        if(cmssage == null){
            cmssage = new HttpRequestPostMessage();
        }
        param.set(cmssage);
    }
    /**url参数 类型**/
    public static HttpRequestRemote getRequestRemote() {
        HttpRequestRemote result = remote.get();
        return result == null ? new HttpRequestRemote() : result;
    }

    /**url参数 类型**/
    public static void setRequestRemote(HttpRequestRemote requestRemote) {
        if(requestRemote == null){
            requestRemote = new HttpRequestRemote();
        }
        remote.set(requestRemote);
    }


    public static String getJsonParseString(){
        Map result = json.get();
       return JsonUtils.toJson(result);
    }
    public static Map getJsonParseMap(){
        Map result = json.get();
        return result;
    }

    public static Object getResponseResultVo() {
        return responseResultVo.get();
    }

    public static void setResponseResultVo(Object responseResult) {
        if(responseResult == null){
           return;
        }
        responseResultVo.set(responseResult);
    }

    /**url参数 类型**/
    public static void setJsonExcludeParamMap(String url, Map paramMap) {
        if(remote == null){
            return;
        }
      try{
          UrlExplain.excludeParam(url,paramMap);
          json.set(paramMap);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void clear(){
        param.remove();
        json.remove();
        remote.remove();
        responseResultVo.remove();
    }

}


