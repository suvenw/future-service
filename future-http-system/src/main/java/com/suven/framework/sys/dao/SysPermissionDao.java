package com.suven.framework.sys.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suven.framework.sys.entity.SysPermission;
import com.suven.framework.sys.mapper.SysPermissionMapper;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.core.mybatis.MyBatisBaseEntityDao;
import com.suven.framework.util.PageUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("sysPermissionDao")
public class SysPermissionDao extends MyBatisBaseEntityDao<SysPermissionMapper, SysPermission> {

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysPermission>  iPage =  new Query<SysPermission>().getPage(params);

        QueryWrapper<SysPermission> query =  new QueryWrapper<>();

        IPage<SysPermission> page = this.page(iPage,query );

        return new PageUtils(page);
    }


    public List<SysPermission> queryByUserId(long userId) {
        this.slaveDataSource();
        return this.baseMapper.queryByUserId(userId);
    }

    public void setMenuLeaf(long pid, int isLeaf) {
        SysPermission sysPermission = this.getById(pid);
        sysPermission.setIsLeaf(isLeaf);
        this.updateById(sysPermission);
    }

    public List<SysPermission> selectList(QueryWrapper<SysPermission> queryWrapper) {
        return this.baseMapper.selectList(queryWrapper);
    }
}