package com.suven.framework.sys.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.core.mybatis.MyBatisBaseEntityDao;
import com.suven.framework.sys.entity.SysDepart;
import com.suven.framework.sys.mapper.SysDepartMapper;
import com.suven.framework.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("sysDepartDao")
public class SysDepartDao extends MyBatisBaseEntityDao<SysDepartMapper, SysDepart> {


    @Autowired
    private SysDepartMapper sysDepartMapper;

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysDepart>  iPage =  new Query<SysDepart>().getPage(params);

        QueryWrapper<SysDepart> query =  new QueryWrapper<>();

        IPage<SysDepart> page = this.page(iPage,query );

        return new PageUtils(page);
    }


    /**
     * 根据用户ID查询部门集合
     */
    public List<SysDepart> getUserDepartsByUserId( long userId){
       return sysDepartMapper.queryUserDepartsByUserId(userId);
    }

    public List<SysDepart> getUserDepartsByIds(List<Long> departIds) {
        QueryWrapper<SysDepart> query =  new QueryWrapper<>();
        query.in("id",departIds);
        return this.list(query);
    }
}