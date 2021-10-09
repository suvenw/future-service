/**
 * Copyright(c)  XXX-Inc.All Rights Reserved. 
 */
package com.suven.framework.http.handler;


import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.data.vo.IResponseResult;
import com.suven.framework.http.message.HttpRequestPostMessage;
import com.suven.framework.http.message.ParamMessage;
import com.suven.framework.http.processor.url.Cdn;
import com.suven.framework.util.constants.Env;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.util.json.JsonFormatTool;
import com.suven.framework.util.json.JsonUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

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
public abstract class BaseHttpMessageHandlerConverter {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 返回码。
	 */
	public int code;
	/**
	 * 返回信息。
	 */
	public String msg = "";


    protected HttpServletResponse response;


	/**
	 * 返回前端数据前处理。
	 * @param errParam
	 */
    protected   void returnErrorBeforeConverter(String... errParam){
		if(errParam == null || errParam.length <=0){
			return;
		}
		Object[] param = Arrays.asList(errParam).toArray();
		String msg = String.format(this.getMsg(), param);
		this.msg = msg;
	}


    /**
     * 写入客户端结果/消息。以错误返回结果实现
     * @param enumType
     */
    protected void writeResponseData(IResponseResult iResponseResult,IResultCodeEnum enumType, Object errorToData)  {
        setCodeMsgByEnum(enumType);
		iResponseResult.buildResponseResultVo(getCode(),getMsg(),errorToData);
    }

	/**
	 * 写入客户端结果/消息。
	 * @param responseData
	 */
    protected void writeResponseData(IResponseResult iResponseResult,Object responseData, String... errParam)  {
        //返回转换后的规范的错误码信息;
		if(responseData != null && (responseData instanceof IResultCodeEnum)){//如果是消息类型
            setCodeMsgByEnum(responseData);
            returnErrorBeforeConverter(errParam);
			iResponseResult.buildResponseResultVo(getCode(),getMsg(),responseData);
		}else{//为数据,成功结果数据
            setCodeMsgByEnum(SysResultCodeEnum.SYS_SUCCESS);
			iResponseResult.buildResponseResultVo(getCode(),getMsg(),responseData);
		}
	}


	/**
	 * 解析出返回代码及返回信息。
	 * @param errorType
	 */
    protected void setCodeMsgByEnum(Object errorType) {
        code = SysResultCodeEnum.SYS_UNKOWNN_FAIL.getCode();
        msg = SysResultCodeEnum.SYS_UNKOWNN_FAIL.getMsg();
		try {
			if(errorType instanceof IResultCodeEnum){
				IResultCodeEnum warnType = (IResultCodeEnum)errorType;
				code = warnType.getCode();
				msg =  warnType.getMsg();
			}
		} catch (Exception e) {
			logger.warn("type=Exception, IMsgEnumTypeException =[{}] ", e);
		}
	}

    /**
     * 设置CDN缓存实现
     * @param response
     * @return
     */
    protected boolean setCdnCache(HttpServletResponse response) {
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.setHeader("Access-Control-Allow-Origin","*");
		int cdnTime = Cdn.get(ParamMessage.getRequestRemote().getUrl());
		if (cdnTime == 0) {
			response.addHeader("Cache-Control", "no-cache");
		} else {
			response.setHeader("Cache-Control", "max-age=" + cdnTime);
			response.addDateHeader("Last-Modified", System.currentTimeMillis());
			response.addDateHeader("Date", System.currentTimeMillis());
			response.addDateHeader("Expires", System.currentTimeMillis() + (cdnTime*1000));
			return true;
		}
		return false;
	}

//    /**
//     * 统一出口
//     * @param responseData json对象
//     * @param code 状态码
//     * @param msg 状态码的描述
//     */
//    protected void buildResponseBody(IResponseResult iResponseResult,Object responseData, int code, String msg) {
//        iResponseResult.buildResponseResultVo(code,msg,responseData);
//    }



    /**
     * 统一出口,写流和cdn信息
     */
    protected void writeStream(Object responseResultVo) {

        String smJson = "";
        try {
            ServletOutputStream output = response.getOutputStream();
            if(null == output){
                return ;
            }
            smJson = JsonUtils.toJson(responseResultVo);
            this.setCdnCache(response);
            byte[] jsonBytes = smJson.getBytes();
            output.write(jsonBytes);
            output.flush();
            output.close();

        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e1) {
            logger.error("type=Exception,  ResponseError=[{}]", e1);
        }finally {
            if(!Env.isProd()){
                logger.warn("type=Success, ResponseMessage=[{}]", JsonFormatTool.formatJson(smJson));
            }

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


	/**
	 * @return 返回 code。
	 */
    private int getCode() {
		return code;
	}


	/**
	 * @return 返回 msg。
	 */
	private String getMsg() {
		return msg;
	}


	public void printErrorLogForRequestMessage(Logger logger,int code ,String msg) {
		HttpRequestPostMessage message = ParamMessage.getRequestMessage();
		String  json = ParamMessage.getJsonParseString();
		long netTime = ParamMessage.getRequestRemote().getNetTime();
		long exeTime = System.currentTimeMillis() - message.getTimes();
		logger.warn("type=Exception, request-url=[{}], code=[{}], msg=[{}],ip=[{}]; FROM-Request-BaseParam = [{}],Request-AttributeParam=[{}], responseEndTime =[{}],netEndTime =[{}]",
				message.getUri(), code, msg, message.getIp(),
				message.toString(),json,exeTime,netTime);
	}
	public void printSuccessLog(Logger logger) {
		HttpRequestPostMessage message = ParamMessage.getRequestMessage();
		long netTime = ParamMessage.getRequestRemote().getNetTime();
		long exeTime = System.currentTimeMillis() - message.getTimes();
		logger.warn("type=Success ,ip= [{}], RequestMessage= [{}],responseEndTime =[{}],netEndTime =[{}]",
				message.getIp(),
				message.toString(),exeTime,netTime);
	}



}
