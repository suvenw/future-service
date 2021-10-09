package com.suven.framework.sys.vo.request;

import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.common.api.ApiDoc;

import java.io.Serializable;

/**
 * @Title: DuplicateCheckVo
 * @Description: 重复校验VO
 * @author xxx.xxx
 * @Date 2019-11-21
 * @Version V1.0
 */
@ApiDoc(value="重复校验数据模型")
public class DuplicateCheckVo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 表名
	 */
	@ApiDesc(value="表名")
	private String tableName;
	
	/**
	 * 字段名
	 */
	@ApiDesc(value="字段名")
	private String fieldName;
	
	/**
	 * 字段值
	 */
	@ApiDesc(value="字段值")
	private String fieldVal;
	
	/**
	 * 数据ID
	*/
	@ApiDesc(value="数据ID")
	private String dataId;

	public static DuplicateCheckVo build() {
		return new DuplicateCheckVo();
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getTableName() {
		return tableName;
	}

	public DuplicateCheckVo setTableName(String tableName) {
		this.tableName = tableName;
		return this;
	}

	public String getFieldName() {
		return fieldName;
	}

	public DuplicateCheckVo setFieldName(String fieldName) {
		this.fieldName = fieldName;
		return this;
	}

	public String getFieldVal() {
		return fieldVal;
	}

	public DuplicateCheckVo setFieldVal(String fieldVal) {
		this.fieldVal = fieldVal;
		return this;
	}

	public String getDataId() {
		return dataId;
	}

	public DuplicateCheckVo setDataId(String dataId) {
		this.dataId = dataId;
		return this;
	}
}