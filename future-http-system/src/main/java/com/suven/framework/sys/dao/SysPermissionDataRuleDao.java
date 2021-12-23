package com.suven.framework.sys.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suven.framework.core.mybatis.MyBatisBaseCacheDao;
import com.suven.framework.sys.entity.SysPermissionDataRule;
import com.suven.framework.sys.mapper.SysPermissionDataRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.core.mybatis.MyBatisBaseEntityDao;
import com.suven.framework.sys.entity.SysDepart;
import com.suven.framework.sys.mapper.SysDepartMapper;
import com.suven.framework.util.PageUtils;

import java.util.Map;


@Service("sysPermissionDataRuleDao")
public class SysPermissionDataRuleDao extends MyBatisBaseCacheDao<SysPermissionDataRuleMapper, SysPermissionDataRule> {

    @Autowired
    private SysPermissionDataRuleMapper  sysPermissionDataRuleMapper;

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysPermissionDataRule>  iPage =  new Query<SysPermissionDataRule>().getPage(params);

        QueryWrapper<SysPermissionDataRule> query =  new QueryWrapper<>();

        IPage<SysPermissionDataRule> page = this.page(iPage,query );

        return new PageUtils(page);
    }

}