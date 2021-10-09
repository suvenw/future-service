package com.suven.framework.http.message;


import com.suven.framework.http.data.vo.RequestParserVo;

/**
 * @Title: HttpRequestGetMessage.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) http get 接口公共基础参数实现类;
 */
public class HttpRequestGetMessage extends RequestParserVo {

    private int version;        //版本
    private int platform ;    //平台
    private String cliSign;        //会话密码
    private int aesData;

    public int getVersion() {
        return version;
    }
    public void setVersion(int version) {
        this.version = version;
    }
    public int getPlatform() {
        return platform;
    }
    public void setPlatform(int platform) {
        this.platform = platform;
    }
    public String getCliSign() {
        return cliSign;
    }
    public void setCliSign(String cliSign) {
        this.cliSign = cliSign;
    }

    public int getAesData() {
        return aesData;
    }

    public void setAesData(int aesData) {
        this.aesData = aesData;
    }
}
