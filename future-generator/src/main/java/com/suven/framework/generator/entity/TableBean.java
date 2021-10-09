package com.suven.framework.generator.entity;

/**
 * 列的属性
 *
 * @author suven
 * @email suvenw@gmail.com
 * @date 2016年12月20日 上午12:01:45
 */

public class TableBean {





//    private  StringBuffer codeResponseFileNameList = new StringBuffer();
//
//
//    private  StringBuffer codeGetSetFileNameList = new StringBuffer();
//    private  StringBuffer codeGetSetRequestVo = new StringBuffer();
//    private  StringBuffer codeGetSetResponseVo = new StringBuffer();
//    private  StringBuffer codeGetSetRequestDto = new StringBuffer();
//    private  StringBuffer codeGetSetResponseDto = new StringBuffer();

	private StringBuffer insertSQL  = new StringBuffer();
	private StringBuffer insertVal  = new StringBuffer();
	private StringBuffer insertMybatisVal  = new StringBuffer();
	private StringBuffer insertDbList  = new StringBuffer();

	private StringBuffer updateSQL  = new StringBuffer();
	private StringBuffer updateDbList  = new StringBuffer();
	private StringBuffer updateWhereDbList  = new StringBuffer();

	private StringBuffer selectSQL  = new StringBuffer();
	private StringBuffer mapperBeanVo  = new StringBuffer();

	private StringBuilder deleteSQL = new StringBuilder();

	private StringBuffer serviceVo  = new StringBuffer();


	public static TableBean build(){
		return new TableBean();
	}

	public String getInsertSQL() {
		return insertSQL.toString();
	}


	public TableBean setInsertSQL(String insertSQL) {
		this.insertSQL.append( insertSQL);
		return this;
	}

	public String getInsertVal() {
		return insertVal.toString();
	}


	public TableBean setInsertVal(String insertVal) {
		this.insertVal.append( insertVal);
		return this;
	}

	public TableBean setInsertMybatisVal(String mybatisVal) {
		this.insertMybatisVal.append( mybatisVal);
		return this;
	}

	public String getInsertMybatisVal() {
		return this.insertMybatisVal.toString();
	}
	public String getInsertDbList() {
		return insertDbList.toString();
	}

	public TableBean setInsertDbList(String insertDbList) {
		this.insertDbList.append( insertDbList);
		return this;
	}

	public String getUpdateSQL() {
		return updateSQL.toString();
	}

	public TableBean setUpdateSQL(String updateSQL) {
		this.updateSQL.append( updateSQL);
		return this;
	}

	public String getUpdateDbList() {
		return updateDbList.toString();
	}

	public TableBean setUpdateDbList(String updateDbList) {
		this.updateDbList.append(updateDbList);
		return this;
	}

	public String getUpdateWhereDbList() {
		return updateWhereDbList.toString();
	}

	public TableBean setUpdateWhereDbList(String updateWhereDbList) {
		this.updateWhereDbList.append(  updateWhereDbList);
		return this;
	}

	public String getMapperBeanVo() {
		return mapperBeanVo.toString();
	}

	public TableBean setMapperBeanVo(String mapperBeanVo) {
		this.mapperBeanVo .append(  mapperBeanVo);
		return this;
	}



	public String getSelectSQL() {
		return selectSQL.toString();
	}

	public TableBean setSelectSQL(String selectSQL) {
		this.selectSQL.append(selectSQL);
		return this;
	}

	public String getServiceVo() {
		return serviceVo.toString();
	}

	public TableBean setServiceVo(String serviceVo) {
		this.serviceVo.append(serviceVo);
		return this;
	}

	public String getDeleteSQL() {
		return deleteSQL.toString();
	}

	public TableBean setDeleteSQL(String deleteSQL) {
		this.deleteSQL.append(deleteSQL);
		return this;
	}


//    public String  getCodeResponseFileNameList() {
//        return codeResponseFileNameList.toString();
//    }
//
//    public TableBean setCodeResponseFileNameList(String codeResponseFileName) {
//        this.codeResponseFileNameList.append(codeResponseFileName) ;
//        return this;
//    }
//
//    public String getCodeGetSetFileNameList() {
//        return codeGetSetFileNameList.toString();
//    }
//
//    public String getCodeGetSetRequestVo() {
//        return codeGetSetRequestVo.toString();
//    }
//    public String getCodeGetSetResponseVo() {
//        return this.codeGetSetResponseVo.toString();
//    }
//
//    public String getCodeGetSetRequestDto() {
//        return codeGetSetRequestDto.toString();
//    }
//
//    public String getCodeGetSetResponseDto() {
//        return codeGetSetResponseDto.toString();
//    }

	public void subInsertSQL() {
		if(getInsertSQL().lastIndexOf(",") > 0){
			String sql = insertSQL.substring(0,insertSQL.lastIndexOf(","));
			insertSQL.setLength(0);
			insertSQL.append(sql);
		}
	}

	public void subInsertMybatisVal() {
		if(getInsertMybatisVal().lastIndexOf(",") > 0){
			String sql = insertMybatisVal.substring(0,insertMybatisVal.lastIndexOf(","));
			insertMybatisVal.setLength(0);
			insertMybatisVal.append(sql);
		}
	}
	public void subInsertVal() {
		if(getInsertVal().lastIndexOf(",") > 0){
			String sql = insertVal.substring(0,insertVal.lastIndexOf(","));
			insertVal.setLength(0);
			insertVal.append(sql);
		}
	}
	public void subUpdateSQL() {
		if(getUpdateSQL().lastIndexOf(",") > 0) {
			String sql = updateSQL.substring(0, updateSQL.lastIndexOf(","));
			updateSQL.setLength(0);
			updateSQL.append(sql);
		}
	}
	public void subSelectSQL() {
		if(getSelectSQL().lastIndexOf(",") > 0){
			String sql = selectSQL.substring(0,selectSQL.lastIndexOf(","));
			selectSQL.setLength(0);
			selectSQL.append(sql);
		}
	}



}
