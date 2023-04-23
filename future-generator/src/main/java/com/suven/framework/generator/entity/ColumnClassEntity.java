package com.suven.framework.generator.entity;

/**
 * 列的属性
 * 
 * @author suven
 * @email suvenw@gmail.com
 * @date 2016年12月20日 上午12:01:45
 */
public class ColumnClassEntity extends JavaClassFieldEntity{
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

    private String index = "2";
    


	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}

	public boolean isColumnKey() {
		return "PRI".equalsIgnoreCase(columnKey);
	}
	public String getColumnKey() {
		return columnKey;
	}
	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public long getNumericPrecision() {
		return numericPrecision;
	}

	public void setNumericPrecision(Object numericPrecision) {
		this.numericPrecision = toLong(numericPrecision);
	}

	public long getNumericScale() {
		return numericScale;
	}

	public void setNumericScale(Object numericScale) {
		this.numericScale = toLong(numericScale);
	}

	public long getCharmaxLength() {
		return charmaxLength;
	}

	public void setCharmaxLength(Object charmaxLength) {
		this.charmaxLength = toLong(charmaxLength);
	}

	public String getNullable() {
		return nullable;
	}

	public void setNullable(String nullable) {
		this.nullable = nullable;
	}

	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}



	public String getIndex() {
		return index;
	}

	public ColumnClassEntity setIndex(String index) {
		this.index = index;
		return this;
	}

	private static long toLong(Object object) {
		try{
			long result = object == null ? 0 : Long.parseLong(String.valueOf(object));
			return result;
		}catch (Exception e){
		}
		return 0;
	}
}
