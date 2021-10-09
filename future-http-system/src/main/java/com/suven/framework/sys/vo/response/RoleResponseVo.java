package com.suven.framework.sys.vo.response;


import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.common.data.BaseStatusEntity;

import java.io.Serializable;

/**
* @ClassName: RoleResponseVo.java
* @Description: 角色表的数据交互处理类
* @author xxx.xxx
* @date   2019-11-21 15:22:59
* @version V1.0.0
* <p>
* ----------------------------------------------------------------------------
*  modifyer    modifyTime                 comment
*
* ----------------------------------------------------------------------------
* </p>
*/
public class RoleResponseVo  extends BaseStatusEntity implements Serializable {








 		/** roleName 角色名称  */
 		@ApiDesc(value = "角色名称", required = 0)
 		private String roleName;

 		/** roleCode 角色编码  */
 		@ApiDesc(value = "角色编码", required = 0)
 		private String roleCode;

 		/** description 描述  */
 		@ApiDesc(value = "描述", required = 0)
 		private String description;


    public static RoleResponseVo build(){
        return new RoleResponseVo();
    }

    
    
    
    
    
     		public void setRoleName( String roleName){
 		 		this.roleName = roleName ; 
 		 		}
 		public RoleResponseVo toRoleResponseVoName( String roleName){
 		 		this.roleName = roleName ; 
 		 		 return this ;
 		}

 		public String getRoleName(){
 		 		return this.roleName;
 		}
    
     		public void setRoleCode( String roleCode){
 		 		this.roleCode = roleCode ; 
 		 		}
 		public RoleResponseVo toRoleResponseVoCode( String roleCode){
 		 		this.roleCode = roleCode ; 
 		 		 return this ;
 		}

 		public String getRoleCode(){
 		 		return this.roleCode;
 		}
    
     		public void setDescription( String description){
 		 		this.description = description ; 
 		 		}
 		public RoleResponseVo toDescription( String description){
 		 		this.description = description ; 
 		 		 return this ;
 		}

 		public String getDescription(){
 		 		return this.description;
 		}








}
