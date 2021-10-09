package com.suven.framework.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suven.framework.sys.entity.SysUserDepart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户部门表
 * 
 * @author xxx.xxx
 * @email 707161293@qq.com
 * @date 2019-11-27 17:49:58
 */
@Mapper
public interface SysUserDepartMapper extends BaseMapper<SysUserDepart> {

    @Select("select id,user_id,dep_id,create_date,modify_date from sys_user_depart where user_id = #{userId}")
    List<SysUserDepart> getListByUserId(@Param("userId") long userId);

    @Select("select dep_id from sys_user_depart where user_id = #{userId}")
    List<Long> queryDepartIdsByUserId(@Param("userId") long userId);

    @Select("select user_id from sys_user_depart where dep_id=#{departId}")
    List<Long> getUserIdByDepId(@Param("departId") long depId);

    @Select("select id,user_id,dep_id,create_date,modify_date from sys_user_depart where dep_id = #{depId} AND user_id = #{userId}")
    SysUserDepart getByDepIdAndUserId(@Param("depId") long depId,@Param("userId") long userId);
}
