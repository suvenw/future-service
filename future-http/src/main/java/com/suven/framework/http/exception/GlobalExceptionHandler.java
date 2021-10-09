package com.suven.framework.http.exception;


import com.suven.framework.common.enums.SysResultCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;
import com.suven.framework.http.data.vo.ResponseResultVo;
import org.springframework.web.util.NestedServletException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title: GlobalExceptionHandler.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) http 接口异常全局统一处理返回错误信息的统一封装对象类;
 *
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler extends GlobalExceptionErrorResponse {

	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     */
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//
//	}



	@ResponseBody
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({NoHandlerFoundException.class })
	protected ResponseResultVo handleNoHandlerFoundException(NoHandlerFoundException e, HttpServletResponse response) {

		this.printExceptionErrorLogger(logger,e);
		return this.write(SysResultCodeEnum.SYS_INVALID_REQUEST, response);
	}

	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler({ SystemRuntimeException.class })
	@ResponseBody
	protected ResponseEntity handleRuntimeException(SystemRuntimeException e, HttpServletResponse response) {
		this.printExceptionErrorLogger(logger,e);
		return new ResponseEntity(this.write(e, response), HttpStatus.OK);

	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ NestedServletException.class})
	@ResponseBody
	protected ResponseEntity handleBusinessException(NestedServletException e, HttpServletResponse response) {

		this.printExceptionErrorLogger(logger,e);
		return  new ResponseEntity(this.write(e, response),HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler({ BusinessLogicException.class})
	@ResponseBody
	protected ResponseResultVo handleBusinessException(BusinessLogicException e, HttpServletResponse response) {

		this.printExceptionErrorLogger(logger,e);
		if(e.getError() == null) {
			return this.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL, response);
		}else{
			return this.write(e.getError(), response);
		}
	}


	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ IllegalArgumentException.class })
	protected ResponseResultVo handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) {

		this.printExceptionErrorLogger(logger,e);
		return this.write(SysResultCodeEnum.SYS_INVALID_REQUEST, response);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ Exception.class })
	protected ResponseEntity handleException(Exception e, HttpServletResponse response) {
		if(e.getCause() instanceof  SystemRuntimeException){
			return handleRuntimeException((SystemRuntimeException)e.getCause(),response);
		}
		this.printExceptionErrorLogger(logger,e);
		return new ResponseEntity(this.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL, response),HttpStatus.INTERNAL_SERVER_ERROR);
	}

//	//RPC异常处理
//	@ResponseStatus(HttpStatus.OK)
//	@ExceptionHandler({RpcException.class})
//	@ResponseBody
//	protected ResponseEntity handleException(RpcException e, HttpServletResponse response) {
//
//		if (StringUtils.isBlank(e.getMessage()) || e.getMessage().indexOf("RpcException") == -1) {
//			return handleException((Exception) e, response);
//		}
////		SystemRuntimeException systemRuntimeException = new SystemRuntimeException(SysMsgEnumType.SYS_VISITS_WAIT);
//		return new ResponseEntity(this.write(SysMsgEnum.SYS_VISITS_WAIT, response), HttpStatus.OK);
//	}

	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler({ BindException.class })
	@ResponseBody
	protected ResponseResultVo handleException(BindException e, HttpServletResponse response) {
		String message = converterParamError(e);
		SystemRuntimeException systemRuntimeException = new SystemRuntimeException(SysResultCodeEnum.SYS_PARAM_ERROR, new String[]{message});
		return this.write(systemRuntimeException, response);
	}








	private String converterParamError(BindException e ){
		List<ObjectError> objectErrorList = e.getAllErrors();
		List errorList = objectErrorList.stream().map(a->a.getDefaultMessage()).collect(Collectors.toList());
		String message = String.join("，",errorList);
		return message;
	}

}
