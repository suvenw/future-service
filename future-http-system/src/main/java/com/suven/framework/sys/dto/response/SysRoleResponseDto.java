package com.suven.framework.sys.dto.response;


import java.io.Serializable;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suven.framework.common.data.BaseByTimeEntity;
import com.suven.framework.common.api.ApiDesc;

/**
 * @ClassName: SysRoleResponseDto.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:43
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description: 角色表 RPC业务接口交互数据返回实现类
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

public class SysRoleResponseDto  extends BaseByTimeEntity implements Serializable {

        private Logger logger = LoggerFactory.getLogger(SysRoleResponseDto.class);


 		/** role_name 角色名称  */
 		@ApiDesc(value = "角色名称", required = 0)
 		private String roleName;

 		/** role_code 角色编码  */
 		@ApiDesc(value = "角色编码", required = 0)
 		private String roleCode;

 		/** description 描述  */
 		@ApiDesc(value = "描述", required = 0)
 		private String description;






        public static SysRoleResponseDto build(){
                return new SysRoleResponseDto();
        }

 		public void setRoleName( String roleName){
 		 		this.roleName = roleName ; 
 		 		}
 		public SysRoleResponseDto toRoleName( String roleName){
 		 		this.roleName = roleName ; 
 		 		 return this ;
 		}

 		public String getRoleName(){
 		 		return this.roleName;
 		}
 		public void setRoleCode( String roleCode){
 		 		this.roleCode = roleCode ; 
 		 		}
 		public SysRoleResponseDto toRoleCode( String roleCode){
 		 		this.roleCode = roleCode ; 
 		 		 return this ;
 		}

 		public String getRoleCode(){
 		 		return this.roleCode;
 		}
 		public void setDescription( String description){
 		 		this.description = description ; 
 		 		}
 		public SysRoleResponseDto toDescription( String description){
 		 		this.description = description ; 
 		 		 return this ;
 		}

 		public String getDescription(){
 		 		return this.description;
 		}







}
