package com.suven.framework.sys.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suven.framework.sys.entity.SysRolePermission;
import com.suven.framework.sys.mapper.SysRolePermissionMapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.core.mybatis.MyBatisBaseEntityDao;
import com.suven.framework.sys.entity.SysDepart;
import com.suven.framework.sys.mapper.SysDepartMapper;
import com.suven.framework.util.PageUtils;

import java.util.Map;


@Service("sysRolePermissionDao")
public class SysRolePermissionDao extends MyBatisBaseEntityDao<SysRolePermissionMapper, SysRolePermission> {


    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysRolePermission>  iPage =  new Query<SysRolePermission>().getPage(params);

        QueryWrapper<SysRolePermission> query =  new QueryWrapper<>();

        IPage<SysRolePermission> page = this.page(iPage,query );

        return new PageUtils(page);
    }

}