package com.suven.framework.sys.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suven.framework.core.mybatis.MyBatisBaseCacheDao;
import com.suven.framework.sys.entity.SysUserDepart;
import com.suven.framework.sys.mapper.SysUserDepartMapper;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("sysUserDepartDao")
public class SysUserDepartDao extends MyBatisBaseCacheDao<SysUserDepartMapper, SysUserDepart> {

    @Autowired
    private SysUserDepartMapper  sysUserDepartMapper;

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysUserDepart>  iPage =  new Query<SysUserDepart>().getPage(params);

        QueryWrapper<SysUserDepart> query =  new QueryWrapper<>();

        IPage<SysUserDepart> page = this.page(iPage,query );

        return new PageUtils(page);
    }


    public List<SysUserDepart> getListByUserId(long userId) {
        return sysUserDepartMapper.getListByUserId(userId);
    }


    public List<Long> getDepartIdsByUserId(long userId) {
        List<Long> ids = new ArrayList<>();
        QueryWrapper<SysUserDepart> query =  new QueryWrapper<>();
        query.eq("user_id",userId);
        List<SysUserDepart> list = this.list(query);
        list.forEach(obj -> {
            ids.add(obj.getDepId());
        });
        return ids;
    }

    public List<Long> getUserIdByDepId(long depId) {
        this.slaveDataSource();
        return this.baseMapper.getUserIdByDepId(depId);
    }

    public SysUserDepart getByDepIdAndUserId(long depId, long userId) {
        this.slaveDataSource();
        return this.baseMapper.getByDepIdAndUserId(depId,userId);
    }
}