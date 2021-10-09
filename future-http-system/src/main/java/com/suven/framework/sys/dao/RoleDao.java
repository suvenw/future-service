package com.suven.framework.sys.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.core.mybatis.MyBatisBaseCacheDao;
import com.suven.framework.sys.entity.Role;
import com.suven.framework.sys.mapper.RoleMapper;
import com.suven.framework.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("roleDao")
public class RoleDao extends MyBatisBaseCacheDao<RoleMapper, Role> {

    @Autowired
    private RoleMapper  roleMapper;

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<Role>  iPage =  new Query<Role>().getPage(params);

        QueryWrapper<Role> query =  new QueryWrapper<>();

        IPage<Role> page = this.page(iPage,query );

        return new PageUtils(page);
    }

}