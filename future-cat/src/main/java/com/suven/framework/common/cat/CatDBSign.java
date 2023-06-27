package com.suven.framework.common.cat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * <pre>
 * 
 * </pre>
 * @author suven.wang
 * @version 1.00.000
 * <pre>
 * @desc:    cat 监控获取 DB数据库对应 api 执行方法上的 监控注解标签,用于统计 db 方法执行时长统计
 * </pre>
 */
@Target(value={ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CatDBSign {
	
}