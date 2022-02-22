/**
 * Copyright(c)  XXX-Inc.All Rights Reserved. 
 */
package com.suven.framework.http.handler;


import com.suven.framework.http.data.vo.IResponseResult;
import com.suven.framework.http.data.vo.ResponseResultVo;
import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.http.message.ParamMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

/**
 * @Title: OutputResponse.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) http 接口统一请求返回结果,返回结果实现写到redis 缓存中,逻辑实现业务类;
 */
public class BaseHttpResponseWrite extends BaseHttpResponseWriteHandlerConverter implements IResponseHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static BaseHttpResponseWrite build(HttpServletResponse response) {
        BaseHttpResponseWrite OutputResponse = new BaseHttpResponseWrite();
        OutputResponse.response = response;
        return OutputResponse ;
    }


    @Override
    public IResponseHandler initResponse(HttpServletResponse httpResponse) {
        this.response = httpResponse;
        return this;
    }

    @Override
    public IResponseResult getResultVo() {
        return ResponseResultVo.build();
    }




}
