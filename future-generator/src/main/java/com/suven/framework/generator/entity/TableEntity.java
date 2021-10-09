package com.suven.framework.generator.entity;

import java.util.List;

/**
 * 表数据
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月20日 上午12:02:55
 */
public class TableEntity {
	//表的名称
	private String tableName;
	//表的备注
	private String comments;
	//表的主键字段名称
	private String primaryKey;
	//表的主键
	private ColumnClassEntity pk;
	//表的列名(不包含主键)
	private List<ColumnClassEntity> columns;
	
	//类名(第一个字母大写)，如：sys_user => SysUser
	private String className;
	//用于兼容模板,定义两个相同属性
	//类名(第一个字母小写)，如：sys_user => sysUser
	private String classname,paramName;


	//
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getPrimaryKey() {
		return primaryKey;
	}
	public TableEntity setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
		return this;
	}
	public ColumnClassEntity getPk() {
		return pk;
	}
	public void setPk(ColumnClassEntity pk) {
		this.pk = pk;
	}
	public List<ColumnClassEntity> getColumns() {
		return columns;
	}
	public void setColumns(List<ColumnClassEntity> columns) {
		this.columns = columns;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
		this.paramName = classname;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
}
