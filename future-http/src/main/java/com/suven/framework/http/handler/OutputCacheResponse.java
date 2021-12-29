/**
 * Copyright(c)  XXX-Inc.All Rights Reserved. 
 */
package com.suven.framework.http.handler;


import com.suven.framework.http.data.vo.ResponseResultVo;
import com.suven.framework.http.message.ParamMessage;
import com.suven.framework.http.processor.url.Cdn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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


	/**
	 * 统一出口,写流和cdn信息
	 */
	@Override
	protected void writeStream(Object responseResultVo) {
		/*** ----------将返回结果进行缓存到redis中---------- ***/
		if(null == responseResultVo || !(responseResultVo instanceof ResponseResultVo)){
			return;
		}
		if(null != request && Cdn.isCdn()){
			ResponseResultVo vo = (ResponseResultVo)responseResultVo;
			cdnCacheResult.set(vo);
		}
		/*** ----------将返回结果进行缓存到redis中---------- ***/
		super.writeStream(responseResultVo);
	}



    public static ResponseResultVo getResponseResultVo(){
        ResponseResultVo vo = cdnCacheResult.get();
        return  vo;

    }



	@Override
	public String toString() {
		long exeTime = System.currentTimeMillis() - ParamMessage.getRequestMessage().getTimes();
		String outLogger =  "OutputCacheResponse{" +
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
