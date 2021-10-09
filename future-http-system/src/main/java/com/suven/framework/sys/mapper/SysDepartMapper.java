package com.suven.framework.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suven.framework.sys.entity.SysDepart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门组织机构表
 * 
 * @author xxx.xxx
 * @email xxx@gmail.com
 * @date 2019-10-18 12:35:25
 */
@Mapper
public interface SysDepartMapper extends BaseMapper<SysDepart> {
    /**
     * 根据用户ID查询部门集合
     */
    List<SysDepart> queryUserDepartsByUserId(@Param("userId") long userId);

    List<SysDepart> queryUserDepartsByIds(@Param("departIds") List<Long> departIds);
}
