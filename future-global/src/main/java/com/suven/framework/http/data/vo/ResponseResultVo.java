package com.suven.framework.http.data.vo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.suven.framework.http.inters.IResultCodeEnum;
import org.apache.commons.lang3.ClassUtils;

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

public class ResponseResultVo implements IResponseResult{

	private int code = 0;     //状态码
	private String msg = "";     //状态描述
	private long times = System.currentTimeMillis();        //时间戳 13位
	private Object data = new JSONObject();    //Json 内容

	public ResponseResultVo(){

	}

	public static ResponseResultVo build(){
		return new ResponseResultVo();
	}


	public ResponseResultVo buildResponseResultVo() {
		return  this;
	}
	public static ResponseResultVo error(IResultCodeEnum enumType){
        return  build().setCodeMsg(enumType.getCode(),enumType.getMsg());
    }


	public ResponseResultVo setCodeMsg(int code, String msg){
		return this.setCode(code).setMsg(msg);
	}

	public int getCode() {
		return code;
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

	public ResponseResultVo setData(Object data) {
		if(data == null){
			return this;
		}
		if(!ClassUtils.isPrimitiveOrWrapper(data.getClass())){
			this.data = data;
			return this;
		}
		if(data instanceof  Boolean){
			int value = (Boolean)data ? 1 : 0;
			put("result", value);
			return this;
		}
		put("pkId",data);
		return this;
	}

	private void put(String result, Object value){
		JSONObject object = new JSONObject();
		object.put(result,value);
		data = object;
	}




	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}
