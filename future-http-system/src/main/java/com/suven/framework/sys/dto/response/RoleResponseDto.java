package com.suven.framework.sys.dto.response;


import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.common.data.BaseStatusEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
* @ClassName: RoleResponseDto.java
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
public class RoleResponseDto  extends BaseStatusEntity implements Serializable {

        private Logger logger = LoggerFactory.getLogger(RoleResponseDto.class);






 		/** roleName 角色名称  */
 		@ApiDesc(value = "角色名称", required = 0)
 		private String roleName;

 		/** roleCode 角色编码  */
 		@ApiDesc(value = "角色编码", required = 0)
 		private String roleCode;

 		/** description 描述  */
 		@ApiDesc(value = "描述", required = 0)
 		private String description;


        public static RoleResponseDto build(){
                return new RoleResponseDto();
        }

 		public void setRoleName( String roleName){
 		 		this.roleName = roleName ; 
 		 		}
 		public RoleResponseDto toRoleResponseDtoName( String roleName){
 		 		this.roleName = roleName ; 
 		 		 return this ;
 		}

 		public String getRoleName(){
 		 		return this.roleName;
 		}
 		public void setRoleCode( String roleCode){
 		 		this.roleCode = roleCode ; 
 		 		}
 		public RoleResponseDto toRoleResponseDtoCode( String roleCode){
 		 		this.roleCode = roleCode ; 
 		 		 return this ;
 		}

 		public String getRoleCode(){
 		 		return this.roleCode;
 		}
 		public void setDescription( String description){
 		 		this.description = description ; 
 		 		}
 		public RoleResponseDto toDescription( String description){
 		 		this.description = description ; 
 		 		 return this ;
 		}

 		public String getDescription(){
 		 		return this.description;
 		}







}
