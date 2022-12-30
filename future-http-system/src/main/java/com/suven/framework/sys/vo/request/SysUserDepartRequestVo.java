package com.suven.framework.sys.vo.request;


import java.io.Serializable;
import java.util.Date;
import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByIdPageVo;


/**
 * @ClassName: SysUserDepartRequestVo.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:14:14
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description: 用户部门关系表 http业务接口交互数据请求参数实现类
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

public class SysUserDepartRequestVo extends HttpRequestByIdPageVo{





 		/** user_id 用户id  */
 		@ApiDesc(value = "用户id", required = 0)
 		private long userId;

 		/** dep_id 部门id  */
 		@ApiDesc(value = "部门id", required = 0)
 		private long depId;







    public static SysUserDepartRequestVo build(){
        return new SysUserDepartRequestVo();
    }




 		public void setUserId( long userId){
 		 		this.userId = userId ; 
 		 		}
 		public SysUserDepartRequestVo toUserId( long userId){
 		 		this.userId = userId ; 
 		 		 return this ;
 		}

 		public long getUserId(){
 		 		return this.userId;
 		}

 		public void setDepId( long depId){
 		 		this.depId = depId ; 
 		 		}
 		public SysUserDepartRequestVo toDepId( long depId){
 		 		this.depId = depId ; 
 		 		 return this ;
 		}

 		public long getDepId(){
 		 		return this.depId;
 		}






}
