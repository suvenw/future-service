package com.suven.framework.http.exception;

import com.suven.framework.http.inters.IResultCodeEnum;

import java.util.Arrays;

/**
 * Created by Alex on 2014/9/3
 */
public class BusinessLogicException extends Exception {

    private IResultCodeEnum error;

    private int errorCode;

    private String errorMessage;


    public BusinessLogicException() {
        super();
    }

    public BusinessLogicException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;

    }

    public BusinessLogicException(IResultCodeEnum error) {
        super(error.getMsg());
        this.error = error;
        this.errorCode = error.getCode();
        this.errorMessage = error.getMsg();
    }

    public BusinessLogicException(IResultCodeEnum error, String... errParam) {
        super(error.getMsg());
        Object[] param = Arrays.asList(errParam).toArray();
        this.error = error;
        this.errorCode = error.getCode();
        this.errorMessage = String.format(error.getMsg(), param);
    }


    /**
     * @return 返回 error。
     */
    public IResultCodeEnum getError() {
        return error;
    }

    public int getErrorCode() {
        return errorCode;
    }


    public String getErrorMessage() {
        return errorMessage;
    }


}