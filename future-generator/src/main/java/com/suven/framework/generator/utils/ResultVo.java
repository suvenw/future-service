package com.suven.framework.generator.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午9:59:27
 */
public class ResultVo extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public ResultVo() {
		put("code", 0);
	}
	
	public static ResultVo error() {
		return error(500, "未知异常，请联系管理员");
	}
	
	public static ResultVo error(String msg) {
		return error(500, msg);
	}
	
	public static ResultVo error(int code, String msg) {
		ResultVo r = new ResultVo();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static ResultVo ok(String msg) {
		ResultVo r = new ResultVo();
		r.put("msg", msg);
		return r;
	}
	
	public static ResultVo ok(Map<String, Object> map) {
		ResultVo r = new ResultVo();
		r.putAll(map);
		return r;
	}
	
	public static ResultVo ok() {
		return new ResultVo();
	}

	public ResultVo put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
