/**
 * Copyright(c)  XXX-Inc.All Rights Reserved. 
 */
package com.suven.framework.http.handler;


import com.suven.framework.http.message.ParamMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class OutputAllResponse extends BaseHttpResponseWriteHandlerConverter implements IResponseVo {

	private Logger logger = LoggerFactory.getLogger(getClass());



	public static OutputAllResponse getInstance(HttpServletResponse response) {
		OutputAllResponse outputResponse = new OutputAllResponse();
        outputResponse.response = response;
		return outputResponse ;
	}


	@Override
	public IResponseVo initResponse(HttpServletResponse httpResponse) {
		this.response = httpResponse;
		return this;
	}



	/**
	 * 统一出口,写流和cdn信息
	 */
	@Override
	protected void writeStream(Object responseResultVo) {

		if(null == responseResultVo){
			super.writeSuccess();
			return;
		}
		int dataType = ParamMessage.getRequestMessage().getDataType();
		switch (dataType){
			case 1:
				/*** ----------将返回结果进行缓存到redis中---------- ***/
				this.cacheDataResultVo(responseResultVo);
				break;
			case 2:
				/*** ----------将返回结果进行aes加密处理---------- ***/
				this.aesDateResultVo(responseResultVo);
				break;
			case 3:
				/*** ----------将返回结果进行aes加密处理---------- ***/
				this.aesDateResultVo(responseResultVo);
				/*** ----------将返回结果进行缓存到redis中---------- ***/
				this.cacheDataResultVo(responseResultVo);
				break;
		}
		super.writeStream(responseResultVo);
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
