package com.suven.framework.http.message;



/**
 * @Title: HttpRequestGetMessage.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) http post 接口公共基础参数实现类;
 */

public class HttpRequestPostMessage extends HttpRequestGetMessage {

	private int appId;     //客户端 XXX登录申请的appid
	private long userId;       //用户Id，pid
	private String accessToken;     //令牌
	private String device;   //设备标识
	private String sysVersion;//客户端手机系统版本号
	private long times;      //时间戳13位 1387614995111
	private int channel;      //app第三方渠道号
	private String ip;
	private String uri;
	private String brand;


	public HttpRequestPostMessage(){}
	
    public static HttpRequestPostMessage valueOf(HttpRequestGetMessage cmessage){
		if(cmessage ==null){
			return null;
		}		
		HttpRequestPostMessage message = new HttpRequestPostMessage();
		message.setPlatform(cmessage.getPlatform());
		message.setCliSign(cmessage.getCliSign());
		message.setVersion(cmessage.getVersion());
		return message;
	}


	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}


	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getSysVersion() {
		return sysVersion;
	}

	public void setSysVersion(String sysVersion) {
		this.sysVersion = sysVersion;
	}

	public long getTimes() {
		return times;
	}

	public void setTimes(long times) {
		this.times = times;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	@Override
	public String toString() {
		return "RequestMessage{" +
				"userId=" + userId +
				", appId=" + appId +
				", accessToken='" + accessToken + '\'' +
				", device='" + device + '\'' +
				", sysVersion='" + sysVersion + '\'' +
				", times=" + times +
				", channel=" + channel +
				", ip='" + ip + '\'' +
				", uri='" + uri + '\'' +
				'}';
	}




}
