/**
 * Copyright(c)  XXX-Inc.All Rights Reserved. 
 */
package com.suven.framework.util.http;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author suven  pf_qq@163.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class BaseRequestHelper {
	/**
	 * 获取http请求servlet。
	 * @return
	 */
    public static HttpServletRequest getRequest() {
		ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
		if (attributes != null)
			return attributes.getRequest();
		else
			return null;
    }
    
//    public static  boolean isJsonRequest() {
//        Boolean jsonRequest = (Boolean) getRequest().getAttribute(GlobalConstants.REQ_JSON);
//        return jsonRequest != null && jsonRequest;
//    }
    
    public static void main(String[] args) {
    	 Boolean jsonRequest = null;
    	 System.out.println(jsonRequest);
	}
}
