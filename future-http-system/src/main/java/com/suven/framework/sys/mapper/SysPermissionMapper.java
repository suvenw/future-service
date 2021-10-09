package com.suven.framework.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suven.framework.sys.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 菜单权限表
 * 
 * @author xxx.xxx
 * @email xxx@gmail.com
 * @date 2019-10-18 12:35:25
 */
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    @Update("update sys_permission set is_leaf=#{leaf} where id = #{id}")
    int setMenuLeaf(@Param("id") long id, @Param("leaf") int leaf);

    List<SysPermission> queryByUserId(@Param("userId")long userId);
}
