package com.suven.framework.common.enums;


import com.suven.framework.common.constants.ReflectionsScan;
import com.suven.framework.http.inters.IResultCodeEnum;

import java.util.*;

/**
 * @Title: MsgEnumType.java
 * @author Joven.wang
 * @date 2015年2月10日
 * @version V1.0
 * @Description: TODO(说明) 此类封装了,从第三方平台的返回code,转换成业务逻辑用户错误提示信息; eg:
 *               SYS_CODE_为前缀,后缀编码为第三方反回code信息,括号为提前端编码和提示信息;
 */

public enum SysResultCodeEnum implements IResultCodeEnum {

	/** 编码规范说明: 1,1 01 001 错误编码的意思,第一位(1)代码是系统,第2位是功能码
	 第3，4位( eg: 01用户信息模块, 02礼物模块),第5，6，7号(该模块用到的返回提示用号的编码code)
	 1. 1 00 01 - 100 99(共99个系统级别) 是手端(看7.0 app 2.0) 应用后台系统级别返回来的统一规范编码
	 1.第1位表示软件平台：1
	 2.第2位表示功能类型：1.弹窗提示，2.软接连，3.跳转url
	 3.第3.4位表示模块分类名称：00系统 01 用户 02 验证 03 资源 04 支付 05 资产 06 设备 07 订单 08 活动 09 桌面 10 配置 11 MQ 12 定时 13 第三方 14 应用市场
	 4.第5，6，7位表示错误序列号；
	 */
	SYS_SUCCESS(0, "成功"),




	/** 系统返回错误码 10000~10099*/
	/** 行为类型　０１－２０**/
	SYS_UNKOWNN_FAIL(1100001, "操作失败!"),
	SYS_TOKEN_NULL(1100002, "请重新登录"),
	SYS_PROJECT_MAINTAIN(1100003,"服务维护中！"),
	SYS_LOGIN_CODE_FAIL(1100005, "验证码失效。"),
	SYS_RESPONSE_RESULT_IS_NULL(1100006, "系统规范模板类不能为空。"),
	SYS_RESPONSE_QUERY_IS_NULL(1100007, "查询条件对象不能为空。"),
	SYS_NOT_HAVE_PERMISSION(1100008, "无权限操作！%s"),


	/** 业务类型　２０－４０ **/
	SYS_INVALID_REQUEST(1100021,"无效请求"),
	SYS_PARAM_ERROR(1100022,"参数请求错误，%s！"),
	SYS_PARAM_CHECK(1100023,"参数检验不符要求！"),
	SYS_PARAM_JSON_FAIL(1100024,"json 参数转换异常"),


	SYS_VISITS_WAIT(1100025,"当前访问人数过多，请稍后重试！"),
	SYS_VERSION_NEW_UPDATE(1100026,"新版本更新提示"),
	SYS_VERSION_FORCE_UPDATE(1100027,"强制更新版本提示"),
    SYS_REQUEST_URL_NOT_FOUND(1100028,"该请求不存在,请确认api :[ %s ]"),
	SYS_USER_CODE_NOT(1100029,"账号不存在！"),
	SYS_USER_REGISTER_FAIL(1100030,"账号已注册，请稍后再试！"),
	SYS_AUTH_ACCESS_TOKEN_FAIL(1100031,"ACCESS_TOKEN校验过期,请重新登陆!"),
	SYS_AUTH_REFRES_TOKEN_FAIL(1100032,"REFRES_TOKEN校验过期,请重新登陆!"),
	SYS_AUTH_RESET_PASSWORD_FAIL(1100033,"该账号已被重置密码!"),
	SYS_AUTH_DEVICE_LOGIN_FAIL(1100034,"账号在其他设备登录，请重新登录!"),
	SYS_CUID_FORMAT_ERROR(1100035,"cuid不合法！"),
	SYS_USER_FIND_FAIL(1100036,"未找到角色信息"),
	SYS_USER_ROLE_FIND_FAIL(1100037,"未找到用户相关角色信息"),
	SYS_USER_DEPART_FIND_FAIL(1100038,"未找到用户相关部门信息"),

	SYS_USER_PWD_FAIL(1100040, "用户名或密码错误。"),
	SYS_LOGOUT_FAIL(1100041, "退出登录失败。"),
	SYS_USER_SAVE_FAIL(1100042,"用户添加失败"),
	SYS_USER_FOUND_FAIL(1100043,"未找到角色信息"),
	SYS_USER_ROLE_FOUND_FAIL(1100044,"未找到用户相关角色信息"),
	SYS_USER_OLD_PWD_FAIL(1100045,"旧密码输入错误!"),
	SYS_USER_NEW_PWD_FAIL(1100046,"新密码不允许为空!"),
	SYS_USER_TWO_PWD_FAIL(1100047,"两次输入密码不一致!"),
	SYS_USER_NEW_PWD_LENGTH(1100048,"新密码长度至少6位"),
	SYS_USER_BAND_FAIL(1100049, "用户已被封禁。"),
	SYS_USER_FAIL(1100050, "用户不存在。"),
	SYS_TOKEN_FAIL(1100051, "Token已经失效。"),
	SYS_USER_ROLE_EXISTS(1100052, "角色编码已存在。"),
	SYS_USER_NAME_PHONE_EXISTS(1100053, "用户名或手机号已存在！"),



	MQ_ROCKET_CONFIG_IS_NULL(1100081,"系统引入ROCKET_MQ但未配置相关信息"),
	MQ_ROCKET_CONSUMER_GROUPNAME_IS_NULL(1100082,"ROCKET_MQ的消费者分组名字配置不能为空"),
	MQ_ROCKET_TOPIC_NAME_IS_NULL(1100083,"ROCKET_MQ的TOPIC名字不能为空"),
	MQ_ROCKET_TAG_IS_NULL(1100084,"ROCKET_MQ的名字不能为空"),
	MQ_ROCKET_ADDRESS_IS_NULL(1100085,"ROCKET_MQ的服务器地址不能为空"),
	MQ_ROCKET_PRODUCER_GROUPNAME_IS_NULL(1100086,"ROCKET_MQ的生产者分组名字配置不能为空"),
	MQ_ROCKET_PRODUCER_SEND_ERROR(1100087,"ROCKET_MQ消息发送失败"),
	MQ_ROCKET_PRODUCER_NOT_FOUND(1100088,"ROCKET_MQ的生产者配置未找到"),
	MQ_ROCKET_TOPIC_NOT_FOUND(1100089,"ROCKET_MQ的TOPIC未找到"),
	MQ_ROCKET_TOPIC_SEND_ERROR(1100090,"消息发送失败"),
	SYS_WEB_ID_INFO_NO_EXIST(1100091,"修改表ID小于0,或不存在,请确认"),





	;

	private int code;
	private String msg;


	SysResultCodeEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	@Override
	public int getCode() {
		return code;
	}

	@Override
	public String getMsg() {
		return msg;
	}







}
