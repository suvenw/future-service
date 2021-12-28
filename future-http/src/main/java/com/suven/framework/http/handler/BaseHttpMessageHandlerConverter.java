/**
 * Copyright(c)  XXX-Inc.All Rights Reserved. 
 */
package com.suven.framework.http.handler;


import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.db.IterableConverter;
import com.suven.framework.http.data.vo.IResponseResult;
import com.suven.framework.http.data.vo.ResponseResultVo;
import com.suven.framework.http.message.HttpRequestPostMessage;
import com.suven.framework.http.message.ParamMessage;
import com.suven.framework.http.processor.url.Cdn;
import com.suven.framework.util.constants.Env;
import com.suven.framework.util.crypt.CryptUtil;
import com.suven.framework.util.random.RandomUtils;
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
	 * 写入客户端结果/消息。
	 * @param responseData
	 */
    protected void writeResponseData(ResponseResultVo responseResultVo, Object responseData, String... errParam)  {
        //返回转换后的规范的错误码信息;
		if(responseData != null && (responseData instanceof IResultCodeEnum)){//如果是消息类型
            this.setCodeMsgByEnum(responseData);
            this.returnErrorBeforeConverter(errParam);
			responseResultVo.setCodeMsg(this.getCode(),this.getMsg());
		}else{//为数据,成功结果数据
			this.setCodeMsgByEnum(SysResultCodeEnum.SYS_SUCCESS);
			responseResultVo.setCodeMsg(this.getCode(),this.getMsg());
			responseResultVo.setData(responseData);
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

	/**
	 * 设置data数据结果使用AES加密,解密的密匙key,加密的结果sign 缓存到返回头;
	 * Access-Control-sign:sign sign 为由随机数据加密的结果值;
	 * Access-Control-sign:key  key为随机16位大小写+数字的值;
	 * @param response
	 * @return String 返回参与加密的字符串值;
	 */
	protected String  initAesHeader(HttpServletResponse response) {
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		String key =  RandomUtils.verifyCode(16,-1);
		String value = RandomUtils.verifyCode(16,0);
		String sign = CryptUtil.aesPassEncrypt( value,key);
		response.setHeader("Access-Control-sign",sign);
		response.setHeader("Access-Control-key",key);
		logger.info("initAesHeader Parameter key:[{}], value:[{}],sign:[{}]",key,value,sign);
		return value;
	}

	/**
	 * 设置将返回data数据结果使用AES加密,再返回去;
	 * Access-Control-sign:sign sign 为由随机数据加密的结果值;
	 * Access-Control-sign:key  key为随机16位大小写+数字的值;
	 * @data 为原始返回用户的结果数据,将参与aes加密后,返回给用户 data 值,
	 * @aesEncryptKey 由initAesHeader方法实现的value值,
	 * @return String 返回参与加密的字符串值;
	 */
	protected String  converterAesDate(Object data, String aesEncryptKey) {
		String jsonData = JsonUtils.toJson(data);
		String signDate = CryptUtil.aesPassEncrypt( jsonData,aesEncryptKey);
		return signDate;
	}

	protected void aesDateStream(Object responseResultVo){
		if(null != responseResultVo && responseResultVo instanceof ResponseResultVo){
			ResponseResultVo vo = (ResponseResultVo)responseResultVo;
			if(vo.getCode() == SysResultCodeEnum.SYS_SUCCESS.getCode() && null != vo.getData()){
				String aesEncryptKey = this.initAesHeader(response);
				String aesData = this.converterAesDate(vo.getData(),aesEncryptKey);
				vo.setData(aesData);
			}
		}
	}



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
	 * 统一出口,写流和cdn信息
	 */
	protected void writeAesStream(Object responseResultVo) {

		String smJson = "";
		try {
			ServletOutputStream output = response.getOutputStream();
			if(null == output){
				return ;
			}
			/*** ----------将返回结果进行aes加密处理---------- ***/
			this.aesDateStream(responseResultVo);
			/*** ----------将返回结果进行aes加密处理---------- ***/
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
