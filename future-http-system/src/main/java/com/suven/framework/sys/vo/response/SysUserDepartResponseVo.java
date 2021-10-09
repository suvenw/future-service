package com.suven.framework.sys.vo.response;


import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.common.data.BaseStatusEntity;

import java.io.Serializable;

/**
* @ClassName: SysUserDepartResponseVo.java
* @Description: 用户部门表的数据交互处理类
* @author xxx.xxx
* @date   2019-11-27 17:49:58
* @version V1.0.0
* <p>
* ----------------------------------------------------------------------------
*  modifyer    modifyTime                 comment
*
* ----------------------------------------------------------------------------
* </p>
*/
public class SysUserDepartResponseVo  extends BaseStatusEntity implements Serializable {




 		/** userId 用户id  */
 		@ApiDesc(value = "用户id", required = 0)
 		private long userId;

 		/** depId 部门id  */
 		@ApiDesc(value = "部门id", required = 0)
 		private long depId;




    public static SysUserDepartResponseVo build(){
        return new SysUserDepartResponseVo();
    }

    
     		public void setUserId( long userId){
 		 		this.userId = userId ; 
 		 		}
 		public SysUserDepartResponseVo toUserId( long userId){
 		 		this.userId = userId ; 
 		 		 return this ;
 		}

 		public long getUserId(){
 		 		return this.userId;
 		}
    
     		public void setDepId( long depId){
 		 		this.depId = depId ; 
 		 		}
 		public SysUserDepartResponseVo toDepId( long depId){
 		 		this.depId = depId ; 
 		 		 return this ;
 		}

 		public long getDepId(){
 		 		return this.depId;
 		}
    
    
    






}
