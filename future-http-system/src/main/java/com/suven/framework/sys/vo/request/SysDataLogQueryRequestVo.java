package com.suven.framework.sys.vo.request;


import java.io.Serializable;
import java.util.Date;
import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.http.data.vo.HttpRequestByIdPageVo;


/**
 * @ClassName: SysDataLogQueryRequestVo.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:10:02
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description:  http业务接口交互数据请求参数实现类
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

public class SysDataLogQueryRequestVo extends HttpRequestByIdPageVo{




 		/** create_by 创建人登录名称  */
 		@ApiDesc(value = "创建人登录名称", required = 0)
 		private String createBy;

 		/** create_time 创建日期  */
 		@ApiDesc(value = "创建日期", required = 0)
 		private Date createTime;

 		/** update_by 更新人登录名称  */
 		@ApiDesc(value = "更新人登录名称", required = 0)
 		private String updateBy;

 		/** update_time 更新日期  */
 		@ApiDesc(value = "更新日期", required = 0)
 		private Date updateTime;

 		/** data_table 表名  */
 		@ApiDesc(value = "表名", required = 0)
 		private String dataTable;

 		/** data_id 数据ID  */
 		@ApiDesc(value = "数据ID", required = 0)
 		private String dataId;

 		/** data_content 数据内容  */
 		@ApiDesc(value = "数据内容", required = 0)
 		private String dataContent;

 		/** data_version 版本号  */
 		@ApiDesc(value = "版本号", required = 0)
 		private int dataVersion;




    public static SysDataLogQueryRequestVo build(){
        return new SysDataLogQueryRequestVo();
    }



 		public void setCreateBy( String createBy){
 		 		this.createBy = createBy ; 
 		 		}

 		public String getCreateBy(){
 		 		return this.createBy;
 		}
 		public SysDataLogQueryRequestVo toCreateBy( String createBy){
 		 		this.createBy = createBy ; 
 		 		 return this ;
 		}

 		public void setCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		}

 		public Date getCreateTime(){
 		 		return this.createTime;
 		}
 		public SysDataLogQueryRequestVo toCreateTime( Date createTime){
 		 		this.createTime = createTime ; 
 		 		 return this ;
 		}

 		public void setUpdateBy( String updateBy){
 		 		this.updateBy = updateBy ; 
 		 		}

 		public String getUpdateBy(){
 		 		return this.updateBy;
 		}
 		public SysDataLogQueryRequestVo toUpdateBy( String updateBy){
 		 		this.updateBy = updateBy ; 
 		 		 return this ;
 		}

 		public void setUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		}

 		public Date getUpdateTime(){
 		 		return this.updateTime;
 		}
 		public SysDataLogQueryRequestVo toUpdateTime( Date updateTime){
 		 		this.updateTime = updateTime ; 
 		 		 return this ;
 		}

 		public void setDataTable( String dataTable){
 		 		this.dataTable = dataTable ; 
 		 		}

 		public String getDataTable(){
 		 		return this.dataTable;
 		}
 		public SysDataLogQueryRequestVo toDataTable( String dataTable){
 		 		this.dataTable = dataTable ; 
 		 		 return this ;
 		}

 		public void setDataId( String dataId){
 		 		this.dataId = dataId ; 
 		 		}

 		public String getDataId(){
 		 		return this.dataId;
 		}
 		public SysDataLogQueryRequestVo toDataId( String dataId){
 		 		this.dataId = dataId ; 
 		 		 return this ;
 		}

 		public void setDataContent( String dataContent){
 		 		this.dataContent = dataContent ; 
 		 		}

 		public String getDataContent(){
 		 		return this.dataContent;
 		}
 		public SysDataLogQueryRequestVo toDataContent( String dataContent){
 		 		this.dataContent = dataContent ; 
 		 		 return this ;
 		}

 		public void setDataVersion( int dataVersion){
 		 		this.dataVersion = dataVersion ; 
 		 		}

 		public int getDataVersion(){
 		 		return this.dataVersion;
 		}
 		public SysDataLogQueryRequestVo toDataVersion( int dataVersion){
 		 		this.dataVersion = dataVersion ; 
 		 		 return this ;
 		}





}
