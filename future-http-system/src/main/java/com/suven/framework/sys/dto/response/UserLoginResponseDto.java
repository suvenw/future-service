package com.suven.framework.sys.dto.response;


import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.common.data.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
* @ClassName: UserLoginResponseDto.java
* @Description: 用户登陆的数据交互处理类
* @author xxx.xxx
* @date   2020-01-09 14:53:51
* @version V1.0.0
* <p>
* ----------------------------------------------------------------------------
*  modifyer    modifyTime                 comment
*
* ----------------------------------------------------------------------------
* </p>
*/
public class UserLoginResponseDto  extends BaseEntity implements Serializable {

        private Logger logger = LoggerFactory.getLogger(UserLoginResponseDto.class);




 		/** userType 登录类型,1:邮箱，2：手机号码，3：openid（qq），4：unionid(微信)  */
 		@ApiDesc(value = "登录类型,1:邮箱，2：手机号码，3：openid（qq），4：unionid(微信)", required = 0)
 		private String userType;

 		/** ip 登录ip  */
 		@ApiDesc(value = "登录ip", required = 0)
 		private String ip;

 		/** platform 客户端手机分类平台  */
 		@ApiDesc(value = "客户端手机分类平台", required = 0)
 		private int platform;

 		/** channel 登录渠道  */
 		@ApiDesc(value = "登录渠道", required = 0)
 		private int channel;

 		/** device 登录设备  */
 		@ApiDesc(value = "登录设备", required = 0)
 		private String device;

 		/** imei 登录imei  */
 		@ApiDesc(value = "登录imei", required = 0)
 		private String imei;

 		/** version 登录版本号  */
 		@ApiDesc(value = "登录版本号", required = 0)
 		private int version;

 		/** sysVersion 客户端手机系统版本号  */
 		@ApiDesc(value = "客户端手机系统版本号", required = 0)
 		private String sysVersion;


        public static UserLoginResponseDto build(){
                return new UserLoginResponseDto();
        }

 		public void setUserType( String userType){
 		 		this.userType = userType ; 
 		 		}
 		public UserLoginResponseDto toUserType( String userType){
 		 		this.userType = userType ; 
 		 		 return this ;
 		}

 		public String getUserType(){
 		 		return this.userType;
 		}
 		public void setIp( String ip){
 		 		this.ip = ip ; 
 		 		}
 		public UserLoginResponseDto toIp( String ip){
 		 		this.ip = ip ; 
 		 		 return this ;
 		}

 		public String getIp(){
 		 		return this.ip;
 		}
 		public void setPlatform( int platform){
 		 		this.platform = platform ; 
 		 		}
 		public UserLoginResponseDto toPlatform( int platform){
 		 		this.platform = platform ; 
 		 		 return this ;
 		}

 		public int getPlatform(){
 		 		return this.platform;
 		}
 		public void setChannel( int channel){
 		 		this.channel = channel ; 
 		 		}
 		public UserLoginResponseDto toChannel( int channel){
 		 		this.channel = channel ; 
 		 		 return this ;
 		}

 		public int getChannel(){
 		 		return this.channel;
 		}
 		public void setDevice( String device){
 		 		this.device = device ; 
 		 		}
 		public UserLoginResponseDto toDevice( String device){
 		 		this.device = device ; 
 		 		 return this ;
 		}

 		public String getDevice(){
 		 		return this.device;
 		}
 		public void setImei( String imei){
 		 		this.imei = imei ; 
 		 		}
 		public UserLoginResponseDto toImei( String imei){
 		 		this.imei = imei ; 
 		 		 return this ;
 		}

 		public String getImei(){
 		 		return this.imei;
 		}
 		public void setVersion( int version){
 		 		this.version = version ; 
 		 		}
 		public UserLoginResponseDto toVersion( int version){
 		 		this.version = version ; 
 		 		 return this ;
 		}

 		public int getVersion(){
 		 		return this.version;
 		}
 		public void setSysVersion( String sysVersion){
 		 		this.sysVersion = sysVersion ; 
 		 		}
 		public UserLoginResponseDto toSysVersion( String sysVersion){
 		 		this.sysVersion = sysVersion ; 
 		 		 return this ;
 		}

 		public String getSysVersion(){
 		 		return this.sysVersion;
 		}







}
