/**
 * Copyright(c)  XXX-Inc.All Rights Reserved. 
 */
package com.suven.framework.http.handler;


import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.http.message.ParamMessage;
import com.suven.framework.http.processor.url.Cdn;
import com.suven.framework.http.data.vo.ResponseResultVo;
import com.suven.framework.http.inters.IResultCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
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
public class OutputResponse extends BaseHttpMessageHandlerConverter {

	private Logger logger = LoggerFactory.getLogger(getClass());
    private  HttpServletRequest request;
    private static ThreadLocal<ResponseResultVo> cdnCacheResult = new ThreadLocal<>();



	public static OutputResponse getInstance(HttpServletRequest request, HttpServletResponse response) {
		OutputResponse outputMessage = new OutputResponse();
        outputMessage.request = request;
		outputMessage.response = response;
		return outputMessage ;
	}


    /**
     * 写入客户端结果/消息。以错误返回结果实现
     * @param enumType
     */
    public void write(IResultCodeEnum enumType, Object errParam)  {
        ResponseResultVo vo = ResponseResultVo.build();
        this.writeResponseData(vo,enumType,errParam);
        this.writeStream(vo);
        this.printErrorLogForRequestMessage(logger,vo.getCode(),vo.getMsg());
    }



    /**
     * 写入客户端结果/消息。
     * @param responseData
     */
    public void write(Object responseData, String... errParam)  {
        ResponseResultVo vo = ResponseResultVo.build();
        this.writeResponseData(vo,responseData,errParam);
        this.writeStream(vo);
        if(vo.getCode() != SysResultCodeEnum.SYS_SUCCESS.getCode()){
            //写错误日志
            this.printErrorLogForRequestMessage(logger,vo.getCode(),vo.getMsg());
        }
        this.printSuccessLog(logger);
        if(null != request && Cdn.isCdn()){
            cdnCacheResult.set(vo);
        }
    }

    /**
     * 成功,默认返回缺省值的方法
     */
    public void writeSuccess(){
        write(null);
    }


    /**
     * 将List列表数据,返回封装返回,和是否有下一页
     * @param responseDataList
     * @param isNextPage
     */
    public void write(List<?> responseDataList, boolean isNextPage){
        ResponseResultList list = ResponseResultList.build()
                .toList(responseDataList).toIsNextPage(isNextPage);
        this.write(list);
    }

    /**
     * 将List列表数据,返回封装返回,和是否有下一页
     * @param responseDataList
     * @param isNextPage
     */
    public void write(List<?> responseDataList, boolean isNextPage, long pageIndex, int total){
        ResponseResultList list = ResponseResultList.build();
        list.toList(responseDataList)
                .toIsNextPage(isNextPage)
                .toPageIndex(pageIndex)
                .toTotal(total);
        this.write(list);
    }

    public static ResponseResultVo getResponseResultVo(){
        ResponseResultVo vo = cdnCacheResult.get();
        return  vo;

    }


	@Override
	public String toString() {
		long exeTime = System.currentTimeMillis() - ParamMessage.getRequestMessage().getTimes();
		String outLogger =  "OutputResponse{" +
				"" + ParamMessage.getRequestMessage().toString() +
				" [ code = "+ code +
				", msg = " + msg +
				"] "+
				"responseEndTime = " + exeTime
				+"}";
		logger.warn(outLogger);
		return outLogger;
	}



}
