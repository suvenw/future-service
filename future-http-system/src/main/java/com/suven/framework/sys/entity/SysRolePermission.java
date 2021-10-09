package com.suven.framework.sys.entity;

import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.common.data.BaseEntity;
import com.suven.framework.core.db.ext.DS;

/**
 * @Title: SysRolePermission.java
 * @Description: 角色权限表的数据模型
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
public class SysRolePermission extends BaseEntity {

private static final long serialVersionUID = 1L;




 		/** roleId 角色id  */
 		@ApiDesc(value = "角色id", required = 0)
 		private long roleId;

 		/** permissionId 权限id  */
 		@ApiDesc(value = "权限id", required = 0)
 		private long permissionId;

 		/** dataRuleIds   */
 		@ApiDesc(value = "", required = 0)
 		private String dataRuleIds;


    public static SysRolePermission build(){
        return new SysRolePermission();
    }

 		public void setRoleId( long roleId){
 		 		this.roleId = roleId ; 
 		 		}
 		public SysRolePermission toRoleId( long roleId){
 		 		this.roleId = roleId ; 
 		 		 return this ;
 		}

 		public long getRoleId(){
 		 		return this.roleId;
 		}
 		public void setPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		}
 		public SysRolePermission toPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		 return this ;
 		}

 		public long getPermissionId(){
 		 		return this.permissionId;
 		}
 		public void setDataRuleIds( String dataRuleIds){
 		 		this.dataRuleIds = dataRuleIds ; 
 		 		}
 		public SysRolePermission toDataRuleIds( String dataRuleIds){
 		 		this.dataRuleIds = dataRuleIds ; 
 		 		 return this ;
 		}

 		public String getDataRuleIds(){
 		 		return this.dataRuleIds;
 		}
}