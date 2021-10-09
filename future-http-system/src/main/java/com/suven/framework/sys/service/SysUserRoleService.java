package com.suven.framework.sys.service;

import com.suven.framework.sys.dto.response.SysUserRoleResponseDto;

import java.util.List;


public interface SysUserRoleService {

    List<SysUserRoleResponseDto> getListByUserId(long userId);

    /**
     * 修改用户和用户角色关系
     * @param userId
     * @param roleIds
     */
    public void editRole(Long userId, List<Long> roleIds);

    public void deleteAll(Long userId);

    public List<SysUserRoleResponseDto> getByRoleId(long roleId , int size);
}