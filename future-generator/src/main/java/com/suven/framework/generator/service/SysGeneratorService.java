
package com.suven.framework.generator.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.suven.framework.generator.entity.*;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import com.suven.framework.generator.config.SysDataConfig;
import com.suven.framework.generator.entity.*;
import com.suven.framework.generator.utils.GeneratorUtils;
import com.suven.framework.generator.utils.PageUtils;
import com.suven.framework.generator.utils.Query;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器
 * 
 * @author Mark sunlightcs@gmail.com
 */
@Service
public class SysGeneratorService {
	@Autowired
	private SysDataConfig sysDataConfig;

	public PageUtils queryList(Query query) {
		Page<?> page = PageHelper.startPage(query.getPage(), query.getLimit());
		List<Map<String, Object>> list = sysDataConfig.getGeneratorDao().queryList(query);

		return new PageUtils(list, (int)page.getTotal(), query.getLimit(), query.getPage());
	}

	public TableEntity queryTable(String tableName) {
		Map<String, String> table =  sysDataConfig.getGeneratorDao().queryTable(tableName);

		//表信息
		TableEntity tableEntity = new TableEntity();
		tableEntity.setTableName(table.get("tableName" ));
		tableEntity.setComments(table.get("tableComment" ));
		tableEntity.setPrimaryKey(table.get("columnKey" ));

		return tableEntity;
		//表名转换成Java类名

	}

	public List<ColumnClassEntity> queryColumns(String tableName) {
		List<ColumnClassEntity> list = new ArrayList<>();
		Configuration config = GeneratorUtils.getConfig();
		List<Map<String, String>> maps =  sysDataConfig.getGeneratorDao().queryColumns(tableName);
		maps.forEach( column -> {
			ColumnClassEntity columnEntity = new ColumnClassEntity();
			columnEntity.setColumnName(column.get("columnName" ));
			columnEntity.setDataType(column.get("dataType" ));
			columnEntity.setComments(column.get("columnComment" ));
			columnEntity.setExtra(column.get("extra" ));
			//数据库副加信息//配置信息
			columnEntity.setColumnKey(column.get("columnKey"));
			columnEntity.setNumericPrecision(column.get("numericPrecision"));
			columnEntity.setNumericScale(column.get("numericPrecision"));
			columnEntity.setNullable(column.get("nullable"));
			columnEntity.setCharmaxLength(column.get("charmaxLength"));



			//列名转换成Java属性名
			String attrName = GeneratorUtils.columnToJava(columnEntity.getColumnName());
			columnEntity.setAttrName(attrName);
			columnEntity.setAttrname(StringUtils.uncapitalize(attrName));
			//列的数据类型，转换成Java类型
			String attrType = config.getString(columnEntity.getDataType(), "unknowType" );
			columnEntity.setAttrType(attrType);

			if(columnEntity.isColumnKey()){//主键设置集合第1位;
				list.add(0,columnEntity);
			}else {
				list.add(columnEntity);
			}

		});

		return list;
	}


	/** 生成文件并添加到压缩文件中,通过文件流提供下载 **/
	public byte[] generatorCodeToFileByZip(ClassConfigEntity entity, List<ColumnBean> entityList) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		GeneratorInfo generatorInfo = getGeneratorInfoByTableName(entity, entity.getTables());
		generatorInfo.setPageColumnBeanList(entityList);
		//生成代码
		GeneratorUtils.generatorCode(generatorInfo,sysDataConfig,zip);
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

	/** 生成文件写到配置的指定项目中 **/
	public boolean generatorCodeToFile(ClassConfigEntity entity) {
		String[] tableNames = entity.getTables().split(",");
		for(String tableName : tableNames){
			GeneratorInfo generatorInfo = getGeneratorInfoByTableName(entity, tableName);
			//生成代码
			GeneratorUtils.generatorCode(generatorInfo,sysDataConfig,null);
		}
		return true;
	}
	/** 生成文件写到配置的指定项目中 **/
	public boolean generatorCodeToFileByPage(ClassConfigEntity entity, List<ColumnBean> entityList) {
//		PageListEntity pageEntity = converterPageListToVoEntity(entityList);
		GeneratorInfo generatorInfo = getGeneratorInfoByTableName(entity, entity.getTables());
		generatorInfo.setPageColumnBeanList(entityList);
		//生成代码
		GeneratorUtils.generatorCode(generatorInfo,sysDataConfig,null);
		return true;
	}

	/**
	 * 通过表名,查询数据库表的所有字段信息,和生成类相关信息,组装成生成对应所需要信息对象;
	 * @param entity
	 * @param tableName
	 * @return
	 */
	private GeneratorInfo getGeneratorInfoByTableName(ClassConfigEntity entity, String tableName) {
		//查询列信息,转换成java类型
		List<ColumnClassEntity> columns = queryColumns(tableName);
		//查询表信息 表名称,和表介绍
		TableEntity table = queryTable(tableName);
		table.setColumns(columns);

		String className = GeneratorUtils.tableToJava(table.getTableName(), entity.getTablePrefix());
		table.setClassName(className);
		table.setClassname(StringUtils.uncapitalize(className));
		table.setParamName(StringUtils.uncapitalize(className));

		//主键对象
		if(null!= columns && !columns.isEmpty() && columns.get(0) != null){
			ColumnClassEntity column = columns.get(0);
			if(column.isColumnKey()){
				table.setPk(column);
				table.setPrimaryKey(column.getColumnName());
			}
		}

		return GeneratorInfo.build(entity, table);
	}
//	public byte[] generatorCode(String[] tableNames) {
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//		ZipOutputStream zip = new ZipOutputStream(outputStream);
//
//		for(String tableName : tableNames){
//			//查询表信息
//			Map<String, String> table = queryTable(tableName);
//			//查询列信息
//			List<ColumnEntity> columns = queryColumns(tableName);
//			//生成代码
//			GeneratorUtils.generatorCode(table, columns, zip,sysDataConfig);
//		}
//		IOUtils.closeQuietly(zip);
//		return outputStream.toByteArray();
//	}
}
