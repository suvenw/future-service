package com.suven.framework.generator.entity;

/**
 * 列的属性
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月20日 上午12:01:45
 */
public class ColumnBean {



	//列名
    private String columnName;
    //列名类型
    private String dataType;
    //列名备注
    private String comments;

	//列名主键,如果为主键值为"PRI", 其它为空
	private String columnKey;
	//列名主键,索引类型,
	//auto_increment
	private String extra;

	//列名是最大有效位,int(11), long(19)
	private long numericPrecision;
	//列名scale是小数点右边小数部分 小数的位数;
	private long numericScale;
	//列名是对应varch长度,其它为[NULL]
	private long charmaxLength;
	//列名是否允许为空: 不允许为空NO, 允许为空;YES
	private String nullable;
	//表单显示
	private String isShowAdd="是";
	//列表显示
	private String isShowList="是";
	//显示类型
	private String showType="文本";
	//控件长度
	private String inputlength="50";
	//是否查询
	private String isQuery="是";
	//查询类型
	private String queryMode="为空";
	private String regex="为空";

	public ColumnBean setIsShowAdd(String isShowAdd) {
		this.isShowAdd = isShowAdd;
		return this;
	}

	public ColumnBean setIsShowList(String isShowList) {
		this.isShowList = isShowList;
		return this;
	}

	public ColumnBean setShowType(String showType) {
		this.showType = showType;
		return this;
	}

	public ColumnBean setInputlength(String inputlength) {
		this.inputlength = inputlength;
		return this;
	}

	public ColumnBean setIsQuery(String isQuery) {
		this.isQuery = isQuery;
		return this;
	}

	public ColumnBean setQueryMode(String queryMode) {
		this.queryMode = queryMode;
		return this;
	}

	public String getIsShowAdd() {
		return isShowAdd;
	}

	public String getIsShowList() {
		return isShowList;
	}

	public String getShowType() {
		return showType;
	}

	public String getInputlength() {
		return inputlength;
	}

	public String getIsQuery() {
		return isQuery;
	}

	public String getQueryMode() {
		return queryMode;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public static ColumnBean build(ColumnClassEntity entity){
		ColumnBean bean =  new ColumnBean();
		bean.setColumnName(entity.getColumnName())
				.setComments(entity.getComments())
				.setDataType(entity.getDataType())

				.setColumnKey(entity.getColumnKey())
				.setExtra(entity.getExtra())
				.setNumericPrecision(entity.getNumericPrecision())
				.setNumericScale(entity.getNumericScale())
				.setCharmaxLength(entity.getCharmaxLength())
				.setNullable(entity.getNullable())


		.converColumnBean(entity,bean);
		return bean ;
	}

	private ColumnBean  converColumnBean(ColumnClassEntity entity, ColumnBean bean){
		if(entity.isColumnKey()){
			entity.setNullable("NO");
			bean.isShowAdd="否";
			bean.isShowList="否";
			bean.showType="ID";
			bean.isQuery="否";
			bean.queryMode="主键";

		}
		return bean;
	}

	public boolean isColumnKey() {
		return "PRI".equalsIgnoreCase(columnKey);
	}
	public boolean isTure(String  isStr){
		return "是".equals(isStr);
	}

	public String getColumnName() {
		return columnName;
	}

	public ColumnBean setColumnName(String columnName) {
		this.columnName = columnName;
		return this;
	}

	public String getDataType() {
		return dataType;
	}

	public ColumnBean setDataType(String dataType) {
		this.dataType = dataType;
		return this;
	}

	public String getComments() {
		return comments;
	}

	public ColumnBean setComments(String comments) {
		this.comments = comments;
		return this;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public ColumnBean setColumnKey(String columnKey) {
		this.columnKey = columnKey;
		return this;
	}

	public String getExtra() {
		return extra;
	}

	public ColumnBean setExtra(String extra) {
		this.extra = extra;
		return this;
	}

	public long getNumericPrecision() {
		return numericPrecision;
	}

	public ColumnBean setNumericPrecision(long numericPrecision) {
		this.numericPrecision = numericPrecision;
		return this;
	}

	public long getNumericScale() {
		return numericScale;
	}

	public ColumnBean setNumericScale(long numericScale) {
		this.numericScale = numericScale;
		return this;
	}

	public long getCharmaxLength() {
		return charmaxLength;
	}

	public ColumnBean setCharmaxLength(long charmaxLength) {
		this.charmaxLength = charmaxLength;
		return this;
	}

	public String getNullable() {
		return nullable;
	}

	public ColumnBean setNullable(String nullable) {
		this.nullable = nullable;
		return this;
	}
}
