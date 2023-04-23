package com.suven.framework.sys.dto.response;


import java.io.Serializable;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suven.framework.common.data.BaseByTimeEntity;
import com.suven.framework.common.api.ApiDesc;

/**
 * @ClassName: SysThirdAccountResponseDto.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:09:47
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description: 第三方登陆表 RPC业务接口交互数据返回实现类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * </pre>
 * @Copyright: (c) 2021 gc by https://www.suven.top
 **/

public class SysThirdAccountResponseDto  extends BaseByTimeEntity implements Serializable {

        private Logger logger = LoggerFactory.getLogger(SysThirdAccountResponseDto.class);


 		/** sys_user_id 第三方登录id  */
 		@ApiDesc(value = "第三方登录id", required = 0)
 		private String sysUserId;

 		/** avatar 头像  */
 		@ApiDesc(value = "头像", required = 0)
 		private String avatar;

 		/** status 状态（1启用，0不启用）  */
 		@ApiDesc(value = "状态（1启用，0不启用）", required = 0)
 		private int status;

 		/** del_flag 删除状态(0-正常,1-已删除)  */
 		@ApiDesc(value = "删除状态(0-正常,1-已删除)", required = 0)
 		private int delFlag;

 		/** realname 真实姓名  */
 		@ApiDesc(value = "真实姓名", required = 0)
 		private String realname;

 		/** third_user_uuid 第三方账号  */
 		@ApiDesc(value = "第三方账号", required = 0)
 		private String thirdUserUuid;

 		/** third_user_id 第三方app用户账号  */
 		@ApiDesc(value = "第三方app用户账号", required = 0)
 		private String thirdUserId;

 		/** third_type 登录来源  */
 		@ApiDesc(value = "登录来源", required = 0)
 		private String thirdType;


        public static SysThirdAccountResponseDto build(){
                return new SysThirdAccountResponseDto();
        }

 		public void setSysUserId( String sysUserId){
 		 		this.sysUserId = sysUserId ; 
 		 		}
 		public SysThirdAccountResponseDto toSysUserId( String sysUserId){
 		 		this.sysUserId = sysUserId ; 
 		 		 return this ;
 		}

 		public String getSysUserId(){
 		 		return this.sysUserId;
 		}
 		public void setAvatar( String avatar){
 		 		this.avatar = avatar ; 
 		 		}
 		public SysThirdAccountResponseDto toAvatar( String avatar){
 		 		this.avatar = avatar ; 
 		 		 return this ;
 		}

 		public String getAvatar(){
 		 		return this.avatar;
 		}
 		public void setStatus( int status){
 		 		this.status = status ; 
 		 		}
 		public SysThirdAccountResponseDto toStatus( int status){
 		 		this.status = status ; 
 		 		 return this ;
 		}

 		public int getStatus(){
 		 		return this.status;
 		}
 		public void setDelFlag( int delFlag){
 		 		this.delFlag = delFlag ; 
 		 		}
 		public SysThirdAccountResponseDto toDelFlag( int delFlag){
 		 		this.delFlag = delFlag ; 
 		 		 return this ;
 		}

 		public int getDelFlag(){
 		 		return this.delFlag;
 		}
 		public void setRealname( String realname){
 		 		this.realname = realname ; 
 		 		}
 		public SysThirdAccountResponseDto toRealname( String realname){
 		 		this.realname = realname ; 
 		 		 return this ;
 		}

 		public String getRealname(){
 		 		return this.realname;
 		}
 		public void setThirdUserUuid( String thirdUserUuid){
 		 		this.thirdUserUuid = thirdUserUuid ; 
 		 		}
 		public SysThirdAccountResponseDto toThirdUserUuid( String thirdUserUuid){
 		 		this.thirdUserUuid = thirdUserUuid ; 
 		 		 return this ;
 		}

 		public String getThirdUserUuid(){
 		 		return this.thirdUserUuid;
 		}
 		public void setThirdUserId( String thirdUserId){
 		 		this.thirdUserId = thirdUserId ; 
 		 		}
 		public SysThirdAccountResponseDto toThirdUserId( String thirdUserId){
 		 		this.thirdUserId = thirdUserId ; 
 		 		 return this ;
 		}

 		public String getThirdUserId(){
 		 		return this.thirdUserId;
 		}
 		public void setThirdType( String thirdType){
 		 		this.thirdType = thirdType ; 
 		 		}
 		public SysThirdAccountResponseDto toThirdType( String thirdType){
 		 		this.thirdType = thirdType ; 
 		 		 return this ;
 		}

 		public String getThirdType(){
 		 		return this.thirdType;
 		}







}
