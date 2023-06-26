package com.sixeco.framework.dubbo;


/**
  * @ClassName: SysDataLog.java
  *
  * @Author 作者 : suven
  * @email 邮箱 : suvenw@163.com
  * @CreateDate 创建时间: 2022-02-28 16:10:02
  * @Version 版本: v1.0.0
  * <pre>
  *
  *  @Description:  数据库表对应的实现类
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

public class SysDataLog implements java.io.Serializable{

private static final long serialVersionUID = 1L;


 		/** data_table 表名  */
 		private String dataTable;

 		/** data_id 数据ID  */
 		private String dataId;

 		/** data_content 数据内容  */
 		private String dataContent;

 		/** data_version 版本号  */
 		private int dataVersion;


    public static SysDataLog build(){
        return new SysDataLog();
    }

 		public void setDataTable( String dataTable){
 		 		this.dataTable = dataTable ; 
 		 		}
 		public SysDataLog toDataTable( String dataTable){
 		 		this.dataTable = dataTable ; 
 		 		 return this ;
 		}

 		public String getDataTable(){
 		 		return this.dataTable;
 		}
 		public void setDataId( String dataId){
 		 		this.dataId = dataId ; 
 		 		}
 		public SysDataLog toDataId( String dataId){
 		 		this.dataId = dataId ; 
 		 		 return this ;
 		}

 		public String getDataId(){
 		 		return this.dataId;
 		}
 		public void setDataContent( String dataContent){
 		 		this.dataContent = dataContent ; 
 		 		}
 		public SysDataLog toDataContent( String dataContent){
 		 		this.dataContent = dataContent ; 
 		 		 return this ;
 		}

 		public String getDataContent(){
 		 		return this.dataContent;
 		}
 		public void setDataVersion( int dataVersion){
 		 		this.dataVersion = dataVersion ; 
 		 		}
 		public SysDataLog toDataVersion( int dataVersion){
 		 		this.dataVersion = dataVersion ; 
 		 		 return this ;
 		}

 		public int getDataVersion(){
 		 		return this.dataVersion;
 		}
}