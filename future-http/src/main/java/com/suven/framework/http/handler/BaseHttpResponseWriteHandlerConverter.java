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
import com.suven.framework.util.constants.Env;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
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
public abstract class BaseHttpResponseWriteHandlerConverter extends BaseHttpResponseHandlerConverter {


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

	/**
	 * 按默认格式返回data数据;返回客户端结果/消息。
	 * ResponseMessage=[{
	 *    "code":0,
	 *    "data":"{}",
	 *    "msg":"成功"
	 * }]
	 */
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
	 * 走错误code提示逻辑,但业务处理逻辑写到data对象,返回到客户端结果/消息。以错误返回结果实现
	 * @param enumType
	 */
	public void writeErrorData(IResultCodeEnum enumType, Object errorToData)  {
		ResponseResultVo vo = ResponseResultVo.build();
		this.writeResponseData(vo,enumType);
		vo.setData(errorToData);
	}

	/**
	 * 按默认格式返回data数据;返回客户端结果/消息。
	 * Object 为返回的完整的结构responseResultVo对象
	 * 直接将参数对象转换成json字符串再按字节流返回用户端
	 * ResponseMessage=[{
	 *    "code":0,
	 *    "data":"{}",
	 *    "msg":"成功"
	 * }]
	 */
	public void writeResult(Object responseResultVo) {
		this.printSuccessLog(logger);
		this.writeStream(responseResultVo);
	}
	/**
	 *
	 * 自定义返回结果格式对象,取代或重写ResponseResultVo,按自定义格式返回对象转换成指定对象实现逻辑方法
	 * 兼容错误协议封装处理逻辑实现,再将对象转换成json字符串再按字节流返回用户端
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

	/**
	 * 将被下载文件流,通过文件名下载
	 * @param fileName
	 * @param data
	 */
	public void writeStream(String fileName , byte[] data){
		try {
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename='" + fileName + " '");
			response.addHeader("Content-Length", "" + data.length);
			response.setContentType("application/octet-stream; charset=UTF-8");
			ServletOutputStream output = response.getOutputStream();
			if(null == output){
				return ;
			}
			output.write(data);
			output.flush();
			output.close();
		} catch (Exception e1) {
			logger.error("type=Exception,  ResponseError=[{}]", e1);
		}finally {
			if(!Env.isProd()){
				logger.warn("type=Success, fileName=[{}]", fileName);
			}
			this.printErrorLogForRequestMessage(logger,0,"");

		}
	}



}