package com.suven.framework.http.data.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.suven.framework.http.inters.IResultCodeEnum;
import org.apache.commons.lang3.ClassUtils;

import java.io.Serializable;

/**
 * @Title: ResponseResultVo.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) http 接口返回拉口的统一封装对象类;
 */

public class ResponseResultVo<T> implements IResponseResult<T>, Serializable {

	private int code = 0;     //状态码
	private String msg = "";     //状态描述
	private long times = System.currentTimeMillis();        //时间戳 13位
	private Object data = new JSONObject();    //Json 内容

	public ResponseResultVo(){

	}

	public static ResponseResultVo build(){
		return new ResponseResultVo();
	}


	@Override
	public int code() {
		return code;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String message() {
		return msg;
	}

	@Override
	public boolean success() {
		return true;
	}

	@Override
	public IResponseResult buildResultVo(int code, String message) {
		this.code = code;
		this.msg = message;
		return this;
	}

	@Override
	public IResponseResult buildResultVo(boolean success, int code, String message, Object result) {
		this.code = code;
		this.msg = message;
		this.setData(result);
		return this;
	}


	public ResponseResultVo setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public ResponseResultVo setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = this.initData(data);;
	}

	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}
