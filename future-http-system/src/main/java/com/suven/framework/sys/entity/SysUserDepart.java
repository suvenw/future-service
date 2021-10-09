package com.suven.framework.sys.entity;

import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.common.data.BaseStatusEntity;
import com.suven.framework.core.db.ext.DS;

/**
 * @Title: SysUserDepart.java
 * @Description: 用户部门表的数据模型
 * @author xxx.xxx
 * @date   2019-11-27 17:49:58
 * @version V1.0.0
 * ----------------------------------------------------------------------------
 *  modifyer    modifyTime                 comment
 *
 * ----------------------------------------------------------------------------
 *
 */
@DS("sys")
public class SysUserDepart extends BaseStatusEntity {

private static final long serialVersionUID = 1L;


 		/** userId 用户id  */
 		@ApiDesc(value = "用户id", required = 0)
 		private long userId;

 		/** depId 部门id  */
 		@ApiDesc(value = "部门id", required = 0)
 		private long depId;




    public static SysUserDepart build(){
        return new SysUserDepart();
    }

 		public SysUserDepart setUserId( long userId){
 		 		this.userId = userId ;
 		 		return this;
 		 		}
 		public SysUserDepart toUserId( long userId){
 		 		this.userId = userId ; 
 		 		 return this ;
 		}

 		public long getUserId(){
 		 		return this.userId;
 		}
 		public SysUserDepart setDepId( long depId){
 		 		this.depId = depId ;
 		 		return this;
 		 		}
 		public SysUserDepart toDepId( long depId){
 		 		this.depId = depId ; 
 		 		 return this ;
 		}

 		public long getDepId(){
 		 		return this.depId;
 		}
}