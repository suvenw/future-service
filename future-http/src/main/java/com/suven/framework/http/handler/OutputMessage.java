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
 * @Title: OutputMessage.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) http 接口统一请求返回结果,返回结果实现写到redis 缓存中,逻辑实现业务类;
 */
public class OutputMessage  extends BaseHttpMessageHandlerConverter {

    private Logger logger = LoggerFactory.getLogger(getClass());



    public static OutputMessage getInstance( HttpServletResponse response) {
        OutputMessage outputMessage = new OutputMessage();
        outputMessage.response = response;
        return outputMessage ;
    }


    /**
     * 写入客户端结果/消息。以错误返回结果实现
     * @param enumType
     */
    @Deprecated
    public void writeAuth(IResultCodeEnum enumType, Object errParam)  {
        ResponseResultVo vo = ResponseResultVo.build();
        writeResponseData(vo,enumType,errParam);
    }


    /**
     * 将默认返回对象转换成指定对象实现逻辑方法
     * @param responseData
     */
    public void writeResult(IResponseResult iResponseResult, Object responseData, String... errParam)  {
        ResponseResultVo vo = ResponseResultVo.build();
        this.writeResponseData(vo,responseData,errParam);
        IResponseResult result = iResponseResult.buildResponseResultVo(vo);
        this.writeStream(result);
        if(vo.getCode() == SysResultCodeEnum.SYS_SUCCESS.getCode()){
            this.printSuccessLog(logger);
        }else {
            this.printErrorLogForRequestMessage(logger,vo.getCode(),vo.getMsg());
        }
    }
    /**
     * 写入客户端结果/消息。
     * @param responseData
     */
    public void write(Object responseData, String... errParam)  {
        ResponseResultVo vo = ResponseResultVo.build();
        this.writeResponseData(vo,responseData,errParam);
        this.writeStream(vo);
        if(vo.getCode() == SysResultCodeEnum.SYS_SUCCESS.getCode()){
            this.printSuccessLog(logger);
        }else {
            this.printErrorLogForRequestMessage(logger,vo.getCode(),vo.getMsg());
        }
    }

    public void writeSuccess(){
        write(null);
    }

    /**
     * 将List列表数据,返回封装返回,和是否有下一页
     * @param responseDataList
     * @param isNextPage
     */
    public void writeList(List<?> responseDataList, boolean isNextPage){
        IResponseResultList list = ResponseResultList.build()
                .toList(responseDataList).toIsNextPage(isNextPage);
        this.write(list);
    }

    /**
     * 将List列表数据,返回封装返回,和是否有下一页
     * @param responseDataList
     * @param isNextPage
     */
    public void writeList(List responseDataList, boolean isNextPage, long pageIndex, int total){
        ResponseResultList list = ResponseResultList.build();
        list.toList(responseDataList)
                .toIsNextPage(isNextPage)
                .toPageIndex(pageIndex)
                .toTotal(total);
        this.write(list);
    }

    /**
     * 统一出口,写流和cdn信息
     */
    public void writeApi(Object responseResultVo) {

       this.writeStream(responseResultVo);
    }



    @Override
    public String toString() {
        long exeTime = System.currentTimeMillis() - ParamMessage.getRequestMessage().getTimes();
        String outLogger =  "OutputMessage{" +
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
