/**
 * Copyright(c)  XXX-Inc.All Rights Reserved. 
 */
package com.suven.framework.http.handler;


import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.data.vo.IResponseResult;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.http.data.vo.ResponseResultVo;
import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.http.message.ParamMessage;
import com.suven.framework.http.processor.url.Cdn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Title: OutputResponse.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) http 接口统一请求返回结果,返回结果实现写到redis 缓存中,逻辑实现业务类;
 */
public class OutputCacheResponse extends BaseHttpResponseWriteHandlerConverter {

	private Logger logger = LoggerFactory.getLogger(getClass());
    private  HttpServletRequest request;
    private static ThreadLocal<ResponseResultVo> cdnCacheResult = new ThreadLocal<>();



	public static OutputCacheResponse getInstance(HttpServletRequest request, HttpServletResponse response) {
		OutputCacheResponse outputResponse = new OutputCacheResponse();
        outputResponse.request = request;
        outputResponse.response = response;
		return outputResponse ;
	}






    public static ResponseResultVo getResponseResultVo(){
        ResponseResultVo vo = cdnCacheResult.get();
        return  vo;

    }



	@Override
	public String toString() {
		long exeTime = System.currentTimeMillis() - ParamMessage.getRequestMessage().getTimes();
		String outLogger =  "OutputResponse{" +
				"" + ParamMessage.getRequestMessage().toString() +
				" [ code = "+ code +
				", msg = " + msg +
				"] "+
				"responseEndTime = " + exeTime
				+"}";
		logger.warn(outLogger);
		return outLogger;
	}



}
