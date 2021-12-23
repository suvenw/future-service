package com.suven.framework.sys.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suven.framework.sys.entity.SysUserRole;
import com.suven.framework.sys.mapper.SysUserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.core.mybatis.MyBatisBaseEntityDao;
import com.suven.framework.sys.entity.SysDepart;
import com.suven.framework.sys.mapper.SysDepartMapper;
import com.suven.framework.util.PageUtils;

import java.util.List;
import java.util.Map;


@Service("sysUserRoleDao")
public class SysUserRoleDao extends MyBatisBaseEntityDao<SysUserRoleMapper, SysUserRole> {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysUserRole>  iPage =  new Query<SysUserRole>().getPage(params);

        QueryWrapper<SysUserRole> query =  new QueryWrapper<>();

        IPage<SysUserRole> page = this.page(iPage,query );

        return new PageUtils(page);
    }

    public List<Long> getUserIdByRoleId(long roleId) {
        this.slaveDataSource();
        return this.baseMapper.getUserIdByRoleId(roleId);
    }

    public List<SysUserRole> getByUserId(long userId) {
        return sysUserRoleMapper.getByUserId(userId);
    }

    public List<SysUserRole> getByRoleId(long roleId , long size) {
        return sysUserRoleMapper.getByRoleId(roleId , size);
    }
}