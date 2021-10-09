package com.suven.framework.http.rpc;

import com.suven.framework.http.message.HttpRequestPostMessage;
import com.suven.framework.http.message.ParamMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.suven.framework.core.jetty.RpcBaseRequest;

/**
 * @ClassName:
 * @Description:
 * @Author aric
 * @Date 2018/5/29 下午3:05
 * @Copyright: (c) 2018 gc by https://www.suven.top
 * @Version : 1.0.0
 * --------------------------------------------------------
 * modifyer    modifyTime                 comment
 * <p>
 * --------------------------------------------------------
 */
@Component
public class RpcBaseFacade {
    public Logger logger = LoggerFactory.getLogger(getClass());

    public void buildParam(RpcBaseRequest param){

        HttpRequestPostMessage requestMessage = ParamMessage.getRequestMessage();
        param.setAppId(requestMessage.getAppId())
                .setChannel(requestMessage.getChannel())
                .setDevice(requestMessage.getDevice())
                .setPlatform(requestMessage.getPlatform())
                .setVersion(requestMessage.getVersion())
                .setIp(requestMessage.getIp());
    }




}
