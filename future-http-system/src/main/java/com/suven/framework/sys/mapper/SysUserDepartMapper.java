package com.suven.framework.sys.mapper;

import com.suven.framework.sys.entity.SysUserDepart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName: SysUserDepartMapper.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:14:14
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description: 用户部门关系表 的数据库sql编写实现类
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
public interface SysUserDepartMapper extends BaseMapper<SysUserDepart> {


    final String SQL_INSERT = "INSERT INTO sys_user_depart ( user_id, dep_id, create_time, update_time ) "
				+ " VALUES( #{sysUserDepart.userId},  #{sysUserDepart.depId},  #{sysUserDepart.createTime},  #{sysUserDepart.updateTime} ) ";

	final String SQL_INSERT_ID = "INSERT INTO sys_user_depart (id, user_id, dep_id, create_time, update_time) "
              + " VALUES( #{sysUserDepart.id},  #{sysUserDepart.userId},  #{sysUserDepart.depId},  #{sysUserDepart.createTime},  #{sysUserDepart.updateTime} ) ";

    /** 插入sql语句实现,返回数据库id主键 **/
    @Insert(SQL_INSERT)
	@Options(keyColumn="id",keyProperty="id",useGeneratedKeys=true)
	Long saveId(@Param("sysUserDepart")  SysUserDepart sysUserDepart);


    /** 插入sql语句实现,返回数据库自定义id主键 **/
    @Insert(SQL_INSERT_ID)
	@SelectKey(statement="SELECT LAST_INSERT_ID()",
			   keyProperty="id",
			   resultType=Long.class,
			   before = false)
	Long saveToId(@Param("sysUserDepart") SysUserDepart sysUserDepart);


    /** 批量插入sql语句实现,返回数据库自定义id主键 **/
	Long saveBatch(List<SysUserDepart> sysUserDepart);

	@Select("select user_id from sys_user_depart where dep_id=#{departId}")
	List<Long> getUserIdByDepId(@Param("departId") long depId);
	
}
