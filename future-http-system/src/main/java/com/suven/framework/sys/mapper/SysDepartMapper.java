package com.suven.framework.sys.mapper;

import com.suven.framework.sys.entity.SysDepart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.SelectKey;
import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName: SysDepartMapper.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:13:31
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description: 组织机构表 的数据库sql编写实现类
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

@Mapper
public interface SysDepartMapper extends BaseMapper<SysDepart> {


    final String SQL_INSERT = "INSERT INTO sys_depart ( parent_id, depart_name, depart_name_en, depart_name_abbr, depart_order, description, org_category, org_type, org_code, mobile, fax, address, memo, status, del_flag, qywx_identifier, create_by, create_time, update_by, update_time ) "
				+ " VALUES( #{sysDepart.parentId},  #{sysDepart.departName},  #{sysDepart.departNameEn},  #{sysDepart.departNameAbbr},  #{sysDepart.departOrder},  #{sysDepart.description},  #{sysDepart.orgCategory},  #{sysDepart.orgType},  #{sysDepart.orgCode},  #{sysDepart.mobile},  #{sysDepart.fax},  #{sysDepart.address},  #{sysDepart.memo},  #{sysDepart.status},  #{sysDepart.delFlag},  #{sysDepart.qywxIdentifier},  #{sysDepart.createBy},  #{sysDepart.createTime},  #{sysDepart.updateBy},  #{sysDepart.updateTime} ) ";

	final String SQL_INSERT_ID = "INSERT INTO sys_depart (id, parent_id, depart_name, depart_name_en, depart_name_abbr, depart_order, description, org_category, org_type, org_code, mobile, fax, address, memo, status, del_flag, qywx_identifier, create_by, create_time, update_by, update_time) "
              + " VALUES( #{sysDepart.id},  #{sysDepart.parentId},  #{sysDepart.departName},  #{sysDepart.departNameEn},  #{sysDepart.departNameAbbr},  #{sysDepart.departOrder},  #{sysDepart.description},  #{sysDepart.orgCategory},  #{sysDepart.orgType},  #{sysDepart.orgCode},  #{sysDepart.mobile},  #{sysDepart.fax},  #{sysDepart.address},  #{sysDepart.memo},  #{sysDepart.status},  #{sysDepart.delFlag},  #{sysDepart.qywxIdentifier},  #{sysDepart.createBy},  #{sysDepart.createTime},  #{sysDepart.updateBy},  #{sysDepart.updateTime} ) ";

    /** 插入sql语句实现,返回数据库id主键 **/
    @Insert(SQL_INSERT)
	@Options(keyColumn="id",keyProperty="id",useGeneratedKeys=true)
	Long saveId(@Param("sysDepart")  SysDepart sysDepart);


    /** 插入sql语句实现,返回数据库自定义id主键 **/
    @Insert(SQL_INSERT_ID)
	@SelectKey(statement="SELECT LAST_INSERT_ID()",
			   keyProperty="id",
			   resultType=Long.class,
			   before = false)
	Long saveToId(@Param("sysDepart") SysDepart sysDepart);


    /** 批量插入sql语句实现,返回数据库自定义id主键 **/
	Long saveBatch(List<SysDepart> sysDepart);


	
}
