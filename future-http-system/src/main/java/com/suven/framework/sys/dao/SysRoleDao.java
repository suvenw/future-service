package com.suven.framework.sys.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.core.mybatis.MyBatisBaseCacheDao;
import com.suven.framework.sys.entity.SysRole;
import com.suven.framework.sys.mapper.SysRoleMapper;
import com.suven.framework.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysRoleDao")
public class SysRoleDao extends MyBatisBaseCacheDao<SysRoleMapper, SysRole> {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysRole>  iPage =  new Query<SysRole>().getPage(params);

        QueryWrapper<SysRole> query =  new QueryWrapper<>();

        IPage<SysRole> page = this.page(iPage,query );

        return new PageUtils(page);
    }

}