package com.suven.framework.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.common.data.BaseStatusEntity;
import com.suven.framework.core.db.ext.DS;

/**
 * @Title: Role.java
 * @Description: 角色表的数据模型
 * @author xxx.xxx
 * @date   2019-11-21 15:22:59
 * @version V1.0.0
 * ----------------------------------------------------------------------------
 *  modifyer    modifyTime                 comment
 *
 * ----------------------------------------------------------------------------
 *
 */
@DS("sys")
@TableName(value = "sys_role")
public class SysRole extends BaseStatusEntity {

private static final long serialVersionUID = 1L;






 		/** roleName 角色名称  */
 		@ApiDesc(value = "角色名称", required = 0)
 		private String roleName;

 		/** roleCode 角色编码  */
 		@ApiDesc(value = "角色编码", required = 0)
 		private String roleCode;

 		/** description 描述  */
 		@ApiDesc(value = "描述", required = 0)
 		private String description;


    public static SysRole build(){
        return new SysRole();
    }

 		public void setRoleName( String roleName){
 		 		this.roleName = roleName ; 
 		 		}
 		public SysRole toRoleName(String roleName){
 		 		this.roleName = roleName ; 
 		 		 return this ;
 		}

 		public String getRoleName(){
 		 		return this.roleName;
 		}
 		public void setRoleCode( String roleCode){
 		 		this.roleCode = roleCode ; 
 		 		}
 		public SysRole toRoleCode(String roleCode){
 		 		this.roleCode = roleCode ; 
 		 		 return this ;
 		}

 		public String getRoleCode(){
 		 		return this.roleCode;
 		}
 		public void setDescription( String description){
 		 		this.description = description ; 
 		 		}
 		public SysRole toDescription(String description){
 		 		this.description = description ; 
 		 		 return this ;
 		}

 		public String getDescription(){
 		 		return this.description;
 		}
}