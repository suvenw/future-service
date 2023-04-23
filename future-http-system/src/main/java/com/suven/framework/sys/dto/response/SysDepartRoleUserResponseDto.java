package com.suven.framework.sys.dto.response;


import java.io.Serializable;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.suven.framework.common.data.BaseTimeEntity;
import com.suven.framework.common.api.ApiDesc;

/**
 * @ClassName: SysDepartRoleUserResponseDto.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:53:58
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description: 部门角色用户表 RPC业务接口交互数据返回实现类
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

public class SysDepartRoleUserResponseDto  extends BaseTimeEntity implements Serializable {

        private Logger logger = LoggerFactory.getLogger(SysDepartRoleUserResponseDto.class);


 		/** user_id 用户id  */
 		@ApiDesc(value = "用户id", required = 0)
 		private long userId;

 		/** depart_role_id 角色id  */
 		@ApiDesc(value = "角色id", required = 0)
 		private long departRoleId;

 		/** depart_id 部门id  */
 		@ApiDesc(value = "部门id", required = 0)
 		private long departId;




        public static SysDepartRoleUserResponseDto build(){
                return new SysDepartRoleUserResponseDto();
        }

 		public void setUserId( long userId){
 		 		this.userId = userId ; 
 		 		}
 		public SysDepartRoleUserResponseDto toUserId( long userId){
 		 		this.userId = userId ; 
 		 		 return this ;
 		}

 		public long getUserId(){
 		 		return this.userId;
 		}
 		public void setDepartRoleId( long departRoleId){
 		 		this.departRoleId = departRoleId ; 
 		 		}
 		public SysDepartRoleUserResponseDto toDepartRoleId( long departRoleId){
 		 		this.departRoleId = departRoleId ; 
 		 		 return this ;
 		}

 		public long getDepartRoleId(){
 		 		return this.departRoleId;
 		}
 		public void setDepartId( long departId){
 		 		this.departId = departId ; 
 		 		}
 		public SysDepartRoleUserResponseDto toDepartId( long departId){
 		 		this.departId = departId ; 
 		 		 return this ;
 		}

 		public long getDepartId(){
 		 		return this.departId;
 		}







}
