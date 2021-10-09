package com.suven.framework.sys.entity;

import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.common.data.BaseStatusEntity;
import com.suven.framework.core.db.ext.DS;

/**
 * @Title: SysDepart.java
 * @Description: 部门组织机构表的数据模型
 * @author xxx.xxx
 * @date   2019-10-18 12:35:25
 * @version V1.0.0
 * ----------------------------------------------------------------------------
 *  modifyer    modifyTime                 comment
 *
 * ----------------------------------------------------------------------------
 *
 */
@DS("sys")
public class SysDepart extends BaseStatusEntity {

private static final long serialVersionUID = 1L;






 		/** parentId 父机构ID  */
 		@ApiDesc(value = "父机构ID", required = 0)
 		private long parentId;

 		/** departName 机构/部门名称  */
 		@ApiDesc(value = "机构/部门名称", required = 0)
 		private String departName;

 		/** departNameEn 英文名  */
 		@ApiDesc(value = "英文名", required = 0)
 		private String departNameEn;

 		/** departNameAbbr 缩写  */
 		@ApiDesc(value = "缩写", required = 0)
 		private String departNameAbbr;

 		/** description 描述  */
 		@ApiDesc(value = "描述", required = 0)
 		private String description;

 		/** orgType 机构类型 1一级部门 2子部门  */
 		@ApiDesc(value = "机构类型 1一级部门 2子部门", required = 0)
 		private int orgType;

 		/** orgCode 机构编码  */
 		@ApiDesc(value = "机构编码", required = 0)
 		private String orgCode;

 		/** mobile 手机号  */
 		@ApiDesc(value = "手机号", required = 0)
 		private String mobile;

 		/** fax 传真  */
 		@ApiDesc(value = "传真", required = 0)
 		private String fax;

 		/** address 地址  */
 		@ApiDesc(value = "地址", required = 0)
 		private String address;

 		/** remarks 备注  */
 		@ApiDesc(value = "备注", required = 0)
 		private String remarks;
	@ApiDesc(value = "排序", required = 0)
	private int sort;

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
	public SysDepart toSort(int sort) {
		this.sort = sort;
		return this ;
	}

    public static SysDepart build(){
        return new SysDepart();
    }

 		public void setParentId( long parentId){
 		 		this.parentId = parentId ; 
 		 		}
 		public SysDepart toParentId( long parentId){
 		 		this.parentId = parentId ; 
 		 		 return this ;
 		}

 		public long getParentId(){
 		 		return this.parentId;
 		}
 		public void setDepartName( String departName){
 		 		this.departName = departName ; 
 		 		}
 		public SysDepart toDepartName( String departName){
 		 		this.departName = departName ; 
 		 		 return this ;
 		}

 		public String getDepartName(){
 		 		return this.departName;
 		}
 		public void setDepartNameEn( String departNameEn){
 		 		this.departNameEn = departNameEn ; 
 		 		}
 		public SysDepart toDepartNameEn( String departNameEn){
 		 		this.departNameEn = departNameEn ; 
 		 		 return this ;
 		}

 		public String getDepartNameEn(){
 		 		return this.departNameEn;
 		}
 		public void setDepartNameAbbr( String departNameAbbr){
 		 		this.departNameAbbr = departNameAbbr ; 
 		 		}
 		public SysDepart toDepartNameAbbr( String departNameAbbr){
 		 		this.departNameAbbr = departNameAbbr ; 
 		 		 return this ;
 		}

 		public String getDepartNameAbbr(){
 		 		return this.departNameAbbr;
 		}
 		public void setDescription( String description){
 		 		this.description = description ; 
 		 		}
 		public SysDepart toDescription( String description){
 		 		this.description = description ; 
 		 		 return this ;
 		}

 		public String getDescription(){
 		 		return this.description;
 		}
 		public void setOrgType( int orgType){
 		 		this.orgType = orgType ; 
 		 		}
 		public SysDepart toOrgType( int orgType){
 		 		this.orgType = orgType ; 
 		 		 return this ;
 		}

 		public int getOrgType(){
 		 		return this.orgType;
 		}
 		public void setOrgCode( String orgCode){
 		 		this.orgCode = orgCode ; 
 		 		}
 		public SysDepart toOrgCode( String orgCode){
 		 		this.orgCode = orgCode ; 
 		 		 return this ;
 		}

 		public String getOrgCode(){
 		 		return this.orgCode;
 		}
 		public void setMobile( String mobile){
 		 		this.mobile = mobile ; 
 		 		}
 		public SysDepart toMobile( String mobile){
 		 		this.mobile = mobile ; 
 		 		 return this ;
 		}

 		public String getMobile(){
 		 		return this.mobile;
 		}
 		public void setFax( String fax){
 		 		this.fax = fax ; 
 		 		}
 		public SysDepart toFax( String fax){
 		 		this.fax = fax ; 
 		 		 return this ;
 		}

 		public String getFax(){
 		 		return this.fax;
 		}
 		public void setAddress( String address){
 		 		this.address = address ; 
 		 		}
 		public SysDepart toAddress( String address){
 		 		this.address = address ; 
 		 		 return this ;
 		}

 		public String getAddress(){
 		 		return this.address;
 		}
 		public void setRemarks( String remarks){
 		 		this.remarks = remarks ; 
 		 		}
 		public SysDepart toRemarks( String remarks){
 		 		this.remarks = remarks ; 
 		 		 return this ;
 		}

 		public String getRemarks(){
 		 		return this.remarks;
 		}
}