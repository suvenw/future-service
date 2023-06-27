package com.suven.framework.http.shiro;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.exception.SystemRuntimeException;
import com.suven.framework.http.handler.OutputSystem;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.suven.framework.common.enums.SysResultCodeEnum.SYS_AUTH_ACCESS_TOKEN_FAIL;

/**
 * @program: st-software-service
 * @description: 异常拦截器
 * @author: xuegao
 * @create: 2019-08-16 11:23
 **/
@ControllerAdvice
public class WebExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(UnauthorizedException.class)
	public void handleShiroException(Exception ex , OutputSystem out , HttpServletRequest request, UnauthorizedException e) {
		logger.info(e.toString());
		out.write(SysResultCodeEnum.SYS_NOT_HAVE_PERMISSION.formatMsg(e.getMessage()));
//		//自定义的记录日志方法
//		SlfUtils.getInst(this).error(0, "访问了无权限目录");
//		//自定义的返回对象
//		return ResultJSON.error("无权限操作");
	}
}
