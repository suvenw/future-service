package com.suven.framework.http.processor.url.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author vincentdeng
 * 此注解加在UrlCommand类里面
 * 用途：
 * 1.是否需要现网测试，请求方式
 * 2.是否需要加入白名单
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface UrlRemote {

	/** true 代表加入白名单 默认加入白名单,在 Command类的url上打上标签,默认是白名单 **/
	boolean isWhite() default true;
	/** true 一定需要登录验证 false 不需要验证, 金钱类,或敏感信息接口时,mustCheck 设置为true,强制验证登陆 token 信息**/
	boolean mustCheck() default false; //true 一定需要登录验证 false 不需要验证
	/** 接口参数防篡改,系统级别有配置开关,默认是开启参数加密验证的,特殊接口不需要验证参数加密,在url设置false **/
	boolean isParamSign() default true;//true 接口需要做参数加密
//	boolean isPrint() default true;//true 是否打印请求参数日志,默认不打印
	/** 在接口中,有些字段参数较大,排队参与参数加密逻辑, 不参与加密,多个字段属性于,用","隔开; **/
	String  excludeParam() default ""; //排除请求参数字段,不参与加密,多个字段属性于,用","隔开;
}