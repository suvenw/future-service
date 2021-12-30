package com.suven.framework.http.message;


import com.suven.framework.common.api.ApiDesc;
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

    @ApiDesc(value= "客户端版本（前2位产品,中间两位大版本，最后三位补丁例如1.1.1就是101001）",required=1 )
    private int version;        //版本
    @ApiDesc(value= "平台(0.缺省平台 1.苹果手机  2.安卓手机 3.window 手机 4.网站 5.H5小程序6.官方网站)",required=1 )
    private int platform ;    //平台
    @ApiDesc(value= "签名机制16位，全部小写(登录接口必传),具体实现规则与技术对接",required=1 )
    private String cliSign;        //会话密码

    @ApiDesc(value= "返回数据类型:0.json,1.json (如果get请求,且接口设置了cds缓存,数据来源 redis cache),2.aes data结果数据加密, 3.aes且cache",required=0 )
    private int dataType;

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

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
}
