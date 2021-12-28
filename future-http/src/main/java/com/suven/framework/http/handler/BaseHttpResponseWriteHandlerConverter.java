/**
 * Copyright(c)  XXX-Inc.All Rights Reserved. 
 */
package com.suven.framework.http.handler;


import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.data.vo.IResponseResult;
import com.suven.framework.http.data.vo.IResponseResultList;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.http.data.vo.ResponseResultVo;
import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.http.message.HttpRequestPostMessage;
import com.suven.framework.http.message.ParamMessage;
import com.suven.framework.http.processor.url.Cdn;
import com.suven.framework.util.constants.Env;
import com.suven.framework.util.crypt.CryptUtil;
import com.suven.framework.util.json.JsonFormatTool;
import com.suven.framework.util.json.JsonUtils;
import com.suven.framework.util.random.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author suven  pf_qq@163.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:  v1.0.0    修改人： suven  修改日期:  20160110   修改内容: 添加异常信息参数化 
 * </pre>
 */
public abstract class BaseHttpResponseWriteHandlerConverter extends BaseHttpMessageHandlerConverter {


	private	Logger logger = LoggerFactory.getLogger(BaseHttpResponseWriteHandlerConverter.class);
	/**
	 * 按默认格式返回data数据;返回客户端结果/消息。
	 * ResponseMessage=[{
	 *    "code":0,
	 *    "data":"{}",
	 *    "msg":"成功"
	 * }]
	 * @param responseData
	 */
	public void write(Object responseData, String... errParam)  {
		ResponseResultVo vo = ResponseResultVo.build();
		this.writeResponseData(vo,responseData,errParam);
		this.writeStream(vo);
		if(vo.getCode() == SysResultCodeEnum.SYS_SUCCESS.getCode()){
			this.printSuccessLog(logger);
		}else {
			this.printErrorLogForRequestMessage(logger,vo.getCode(),vo.getMsg());
		}
	}

	public void writeSuccess(){
		write(null);
	}

	/**
	 * 将List列表数据,返回封装返回,和是否有下一页
	 * @param responseDataList
	 * @param isNextPage
	 */
	public void writeList(List<?> responseDataList, boolean isNextPage){
		IResponseResultList list = ResponseResultList.build()
				.toList(responseDataList).toIsNextPage(isNextPage);
		this.write(list);
	}

	/**
	 * 将List列表数据,返回封装返回,和是否有下一页
	 * @param responseDataList
	 * @param isNextPage
	 */
	public void writeList(List responseDataList, boolean isNextPage, long pageIndex, int total){
		ResponseResultList list = ResponseResultList.build();
		list.toList(responseDataList)
				.toIsNextPage(isNextPage)
				.toPageIndex(pageIndex)
				.toTotal(total);
		this.write(list);
	}

	/**
	 * 统一出口,写流和cdn信息,
	 */
	public void writeApi(Object responseResultVo) {
		this.printSuccessLog(logger);
		this.writeStream(responseResultVo);
	}
	/**
	 * 走错误code提示逻辑,但业务处理逻辑写到data对象,返回到客户端结果/消息。以错误返回结果实现
	 * @param enumType
	 */
	public void writeErrorData(IResultCodeEnum enumType, Object errorToData)  {
		ResponseResultVo vo = ResponseResultVo.build();
		this.writeResponseData(vo,enumType);
		vo.setData(errorToData);
	}

	/**
	 * 自定义返回结果格式对象,取代或重写ResponseResultVo,按自定义格式返回对象转换成指定对象实现逻辑方法
	 * @param responseData
	 */
	public void writeResult(IResponseResult iResponseResult, Object responseData, String... errParam)  {
		//组合错误信息
		ResponseResultVo vo = iResponseResult.buildResponseResultVo();
		this.writeResponseData(vo,responseData,errParam);
		this.writeStream(iResponseResult);
		if(vo.getCode() == SysResultCodeEnum.SYS_SUCCESS.getCode()){
			this.printSuccessLog(logger);
		}else {
			this.printErrorLogForRequestMessage(logger,vo.getCode(),vo.getMsg());
		}
	}

//	/**
//	 * 自定义返回结果格式对象,取代或重写ResponseResultVo,按自定义格式返回对象转换成指定对象实现逻辑方法
//	 * @param responseData
//	 */
//	public void writeAesResult(IResponseResult iResponseResult, Object responseData, String... errParam)  {
//		//组合错误信息
//		ResponseResultVo vo = iResponseResult.buildResponseResultVo();
//		this.writeResponseData(vo,responseData,errParam);
//		this.writeStream(iResponseResult);
//		if(vo.getCode() == SysResultCodeEnum.SYS_SUCCESS.getCode()){
//			this.printSuccessLog(logger);
//		}else {
//			this.printErrorLogForRequestMessage(logger,vo.getCode(),vo.getMsg());
//		}
//	}



}
