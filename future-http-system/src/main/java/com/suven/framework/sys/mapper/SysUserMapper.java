package com.suven.framework.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suven.framework.sys.dto.request.SysUserRequestDto;
import com.suven.framework.sys.dto.response.SysUserResponseDto;
import com.suven.framework.sys.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户表
 *
 * @author xxx.xxx
 * @email xxx@gmail.com
 * @date 2019-10-17 20:54:29
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 通过用户账号查询用户信息
     * @param username
     * @return
     */
    public SysUser getUserByName(@Param("username") String username);

    /**
     *  根据部门Id查询用户信息
     * @param page
     * @param departId
     * @return
     */
    IPage<SysUser> getUserByDepId(Page page, @Param("departId") long departId, @Param("username") String username);

    /**
     * 根据角色Id查询用户信息
     * @param page
     * @param
     * @return
     */
    IPage<SysUser> getUserByRoleId(Page page, @Param("roleId") long roleId, @Param("username") String username);

    /**
     * 根据用户名设置部门ID
     * @param username
     * @param orgCode
     */
    void updateUserDepart(@Param("username") String username,@Param("orgCode") String orgCode);

    /**
     * 根据手机号查询用户信息
     * @param phone
     * @return
     */
    public SysUser getUserByPhone(@Param("phone") String phone);


    /**
     * 根据邮箱查询用户信息
     * @param email
     * @return
     */
    public SysUser getUserByEmail(@Param("email")String email);


    IPage<SysUser> getUserByUserIds(Page<SysUser> page,@Param("userIds") List<Long> userIds, @Param("username") String userName);

    /**
     * 根据条件查询
     * @param page
     * @param userReqDto
     * @return
     */
    IPage<SysUserResponseDto> getPageList(Page<SysUserResponseDto> page, @Param("userReqDto") SysUserRequestDto userReqDto);
}
