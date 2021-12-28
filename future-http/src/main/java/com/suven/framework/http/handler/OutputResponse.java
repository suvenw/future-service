/**
 * Copyright(c)  XXX-Inc.All Rights Reserved. 
 */
package com.suven.framework.http.handler;


import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.data.vo.IResponseResult;
import com.suven.framework.http.data.vo.IResponseResultList;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.http.message.ParamMessage;
import com.suven.framework.http.data.vo.ResponseResultVo;
import com.suven.framework.http.inters.IResultCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
public class OutputResponse extends BaseHttpResponseWriteHandlerConverter {

    private Logger logger = LoggerFactory.getLogger(getClass());



    public static OutputResponse getInstance(HttpServletResponse response) {
        OutputResponse OutputResponse = new OutputResponse();
        OutputResponse.response = response;
        return OutputResponse ;
    }


    /**
     * 走错误code提示逻辑,但业务处理逻辑写到data对象,返回到客户端结果/消息。以错误返回结果实现
     * 兼容历史版本,不建议使用
     * @param enumType
     */
    @Deprecated
    public void writeAuth(IResultCodeEnum enumType, Object errorToData)  {
        this.writeErrorData(enumType,errorToData);
    }





    @Override
    public String toString() {
        long exeTime = System.currentTimeMillis() - ParamMessage.getRequestMessage().getTimes();
        String outLogger =  "OutputResponse{" +
                "" + ParamMessage.getRequestMessage().toString() +
                " [ code = "+code +
                ", msg = " +msg +
                "] "+
                "responseEndTime = " + exeTime
                +"}";
        logger.warn(outLogger);
        return outLogger;
    }



}
