package com.suven.framework.sys.entity;

import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.common.data.BaseEntity;
import com.suven.framework.core.db.ext.DS;

/**
 * @Title: SysUserRole.java
 * @Description: 用户角色表的数据模型
 * @author xxx.xxx
 * @date   2019-10-18 12:35:25
 * @version V1.0.0
 * ----------------------------------------------------------------------------
 *  modifyer    modifyTime                 comment
 *
 * ----------------------------------------------------------------------------
 *
 */
@DS("sys")
public class SysUserRole extends BaseEntity {

private static final long serialVersionUID = 1L;




 		/** userId 用户id  */
 		@ApiDesc(value = "用户id", required = 0)
 		private long userId;

 		/** roleId 角色id  */
 		@ApiDesc(value = "角色id", required = 0)
 		private long roleId;


    public static SysUserRole build(){
        return new SysUserRole();
    }

 		public void setUserId( long userId){
 		 		this.userId = userId ; 
 		 		}
 		public SysUserRole toUserId( long userId){
 		 		this.userId = userId ; 
 		 		 return this ;
 		}

 		public long getUserId(){
 		 		return this.userId;
 		}
 		public void setRoleId( long roleId){
 		 		this.roleId = roleId ; 
 		 		}
 		public SysUserRole toRoleId( long roleId){
 		 		this.roleId = roleId ; 
 		 		 return this ;
 		}

 		public long getRoleId(){
 		 		return this.roleId;
 		}
}