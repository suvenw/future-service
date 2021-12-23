package com.suven.framework.sys.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.databene.commons.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.suven.framework.common.data.BasePage;
import com.suven.framework.sys.dao.SysUserRoleDao;
import com.suven.framework.sys.dto.response.SysUserRoleResponseDto;
import com.suven.framework.sys.entity.SysUserRole;
import com.suven.framework.sys.mapper.SysUserRoleMapper;

import java.util.ArrayList;
import java.util.List;


@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private RoleTokenOutService roleTokenOutService;

    @Override
    public List<SysUserRoleResponseDto> getListByUserId(long userId) {

        List<SysUserRoleResponseDto> resDtoList = new ArrayList<>();
        //分页对象        PageHelper
        BasePage basePage = BasePage.build();
        IPage<SysUserRole> iPage = new Page<>(basePage.getPageNo(), basePage.getPageSize());
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);

        IPage<SysUserRole> page = sysUserRoleDao.page(iPage, queryWrapper);
        List<SysUserRole>  list = page.getRecords();
        if(null == list || list.isEmpty()){
            return resDtoList;
        }
        list.forEach(sysUserRole -> {
            SysUserRoleResponseDto sysRolePermissionResponseDto = SysUserRoleResponseDto.build().clone(sysUserRole);
            resDtoList.add(sysRolePermissionResponseDto);
        });
        return resDtoList;
    }


    @Override
    public void editRole(Long userId, List<Long> roleIds) {
        //先删后加
        sysUserRoleDao.remove(new QueryWrapper<SysUserRole>().lambda().eq(SysUserRole::getUserId, userId));
        if(!CollectionUtil.isEmpty(roleIds)) {
            roleIds.forEach(r ->{
                SysUserRole sysUserRole = SysUserRole.build().toUserId(userId).toRoleId(r);
                sysUserRoleDao.save(sysUserRole);
            });
        }
        roleTokenOutService.delUserTokenInRedisByUserIds(userId);
    }

    /**
     * 删除所有用户角色
     * @param userId
     */
    @Override
    public void deleteAll(Long userId) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        sysUserRoleMapper.delete(queryWrapper);
    }

    @Override
    public List<SysUserRoleResponseDto> getByRoleId(long roleId , int pageSize) {

        List<SysUserRoleResponseDto> resDtoList = new ArrayList<>();

        BasePage basePage = BasePage.build().toPageNo(0).toPageSize(pageSize);
        IPage<SysUserRole> iPage = new Page<>(basePage.getPageNo(), basePage.getRealPageSize());
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        IPage<SysUserRole> page = sysUserRoleDao.page(iPage, queryWrapper);
        if (null == page) {
            return resDtoList;
        }
        List<SysUserRole> list = page.getRecords();
        if(null == list || list.isEmpty()){
            return resDtoList;
        }
        list.forEach(sysUserRole -> {
            SysUserRoleResponseDto sysRolePermissionResponseDto = SysUserRoleResponseDto.build().clone(sysUserRole);
            resDtoList.add(sysRolePermissionResponseDto);
        });
        return resDtoList;
    }
}
