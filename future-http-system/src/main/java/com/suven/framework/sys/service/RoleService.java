package com.suven.framework.sys.service;

import com.suven.framework.common.data.BasePage;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.sys.dto.request.RoleRequestDto;
import com.suven.framework.sys.dto.response.RoleResponseDto;
import com.suven.framework.sys.entity.Role;
import com.suven.framework.util.PageUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: RoleService.java
 * @Description: 角色表的数据交互处理类
 * @author xxx.xxx
 * @date   2019-11-21 15:22:59
 * @version V1.0.0
 * <p>
 * ----------------------------------------------------------------------------
 *  modifyer    modifyTime                 comment
 *
 * ----------------------------------------------------------------------------
 * </p>
 */
public interface RoleService {



    /**
     * 保存角色表更新数据库和缓存的实现方法
     * @param roleRequestDto  RoleRequestDto
     * @return
     */
    RoleResponseDto saveRole(RoleRequestDto roleRequestDto);

    /**
     * 保存角色表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveBatchRole(Collection<RoleRequestDto> entityList, int batchSize);

    /**
     * 保存角色表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveOrUpdateBatchRole(Collection<RoleRequestDto> entityList, int batchSize);



    /**
     * 更新角色表同时更新数据库和缓存的实现方法
     * @param roleRequestDto  RoleRequestDto
     * @return
     */
    boolean updateRole (RoleRequestDto roleRequestDto);

    /**
     * 更新角色表同时更新数据库和缓存的实现方法
     * @return
     */
    boolean updateBatchById(Collection<RoleRequestDto> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  roleIds
     * @return
     */
    int delRoleByIds(List<Long>  roleIds);


    /**
     * 通过主键ID更新对象角色表实现缓存和数据库更新的方法
     * @param  roleId
     * @return
     */
        RoleResponseDto getRoleById(long roleId);



    /**
     * 通过分页获取RoleResponseDto信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     */
//    List<RoleResponseDto> getRoleByPage(BasePage page);


    /**
     * 通过分页获取Role 角色表信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     * @author xxx.xxx
     * @date 2019-11-21 15:22:59
     */
    ResponseResultList<RoleResponseDto> getRoleByNextPage(BasePage page);


    public PageUtils queryPage(Map<String, Object> params);


    /**
     * 查询角色列表
     * @return
     */
    List<Role> list();


    RoleResponseDto getRoleByCode(String code);

    /**
     * 通过主键ID集合更新对象角色表实现缓存和数据库更新的方法
     *
     * @param sysRoleIds
     * @return
     */
    List<RoleResponseDto> getSysRoleByIds(List sysRoleIds);
}