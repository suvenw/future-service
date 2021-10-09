package com.suven.framework.sys.vo.response;


import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.common.data.BaseStatusEntity;

import java.io.Serializable;

/**
* @ClassName: SysUserRoleResponseVo.java
* @Description: 用户角色表的数据交互处理类
* @author xxx.xxx
* @date   2019-10-18 12:35:25
* @version V1.0.0
* <p>
* ----------------------------------------------------------------------------
*  modifyer    modifyTime                 comment
*
* ----------------------------------------------------------------------------
* </p>
*/
public class SysUserRoleResponseVo  extends BaseStatusEntity implements Serializable {






 		/** userId 用户id  */
 		@ApiDesc(value = "用户id", required = 0)
 		private long userId;

 		/** roleId 角色id  */
 		@ApiDesc(value = "角色id", required = 0)
 		private long roleId;


    public static SysUserRoleResponseVo build(){
        return new SysUserRoleResponseVo();
    }

    
    
    
     		public void setUserId( long userId){
 		 		this.userId = userId ; 
 		 		}
 		public SysUserRoleResponseVo toUserId( long userId){
 		 		this.userId = userId ; 
 		 		 return this ;
 		}

 		public long getUserId(){
 		 		return this.userId;
 		}
    
     		public void setRoleId( long roleId){
 		 		this.roleId = roleId ; 
 		 		}
 		public SysUserRoleResponseVo toRoleId( long roleId){
 		 		this.roleId = roleId ; 
 		 		 return this ;
 		}

 		public long getRoleId(){
 		 		return this.roleId;
 		}
    






}
