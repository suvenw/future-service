package com.suven.framework.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suven.framework.sys.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户角色表
 *
 * @author xxx.xxx
 * @email xxx@gmail.com
 * @date 2019-10-17 20:54:29
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    @Select("select role_code from sys_role where id in (select role_id from sys_user_role where user_id = (select id from sys_user where username=#{username}))")
    List<String> getRoleByUserName(@Param("username") String username);


    /**
     * 查询用户角色信息by用户id
     * @param userId
     * @return
     */
    @Select("select id ,create_date,modify_date,user_id,role_id from sys_user_role where user_id = #{userId}")
    List<SysUserRole> getByUserId(@Param("userId") long userId);



    @Select("select user_id from sys_user_role where role_id=#{roleId}")
    List<Long> getUserIdByRoleId(@Param("roleId") long roleId);

    /**
     * 查询用户角色信息by角色id
     * @param roleId
     * @return
     */
    @Select("select id ,create_date,modify_date,user_id,role_id from sys_user_role where role_id = #{roleId} limit #{pageSize} ")
    List<SysUserRole> getByRoleId(@Param("roleId") long roleId , @Param("pageSize") long pageSize );
}
