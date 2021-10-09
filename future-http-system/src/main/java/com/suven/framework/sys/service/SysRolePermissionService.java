package com.suven.framework.sys.service;

import com.suven.framework.common.data.BasePage;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.sys.dto.request.SysRolePermissionRequestDto;
import com.suven.framework.sys.dto.response.SysRolePermissionResponseDto;
import com.suven.framework.util.PageUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author xxx.xxx
 * @version V1.0.0
 * <p>
 * ----------------------------------------------------------------------------
 * modifyer    modifyTime                 comment
 * <p>
 * ----------------------------------------------------------------------------
 * </p>
 * @ClassName: SysRolePermissionService.java
 * @Description: 角色权限表的数据交互处理类
 * @date 2019-10-18 12:35:25
 */
public interface SysRolePermissionService {


    /**
     * 保存角色权限表更新数据库和缓存的实现方法
     *
     * @param sysRolePermissionRequestDto SysRolePermissionRequestDto
     * @return
     */
    SysRolePermissionResponseDto saveSysRolePermission(SysRolePermissionRequestDto sysRolePermissionRequestDto);

    /**
     * 保存角色权限表更新数据库和缓存的实现方法
     *
     * @return
     */
    boolean saveBatchSysRolePermission(Collection<SysRolePermissionRequestDto> entityList, int batchSize);

    /**
     * 保存角色权限表更新数据库和缓存的实现方法
     *
     * @return
     */
    boolean saveOrUpdateBatchSysRolePermission(Collection<SysRolePermissionRequestDto> entityList, int batchSize);


    /**
     * 更新角色权限表同时更新数据库和缓存的实现方法
     *
     * @param sysRolePermissionRequestDto SysRolePermissionRequestDto
     * @return
     */
    boolean updateSysRolePermission(SysRolePermissionRequestDto sysRolePermissionRequestDto);

    /**
     * 更新角色权限表同时更新数据库和缓存的实现方法
     *
     * @return
     */
    boolean updateBatchById(Collection<SysRolePermissionRequestDto> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     *
     * @param sysRolePermissionIds
     * @return
     */
    int delSysRolePermissionByIds(List<Long> sysRolePermissionIds);


    /**
     * 通过主键ID更新对象角色权限表实现缓存和数据库更新的方法
     *
     * @param sysRolePermissionId
     * @return
     */
    SysRolePermissionResponseDto getSysRolePermissionById(long sysRolePermissionId);


    /**
     * 通过分页获取SysRolePermissionResponseDto信息实现查找缓存和数据库的方法
     *
     * @param page BasePage
     * @return
     */
    List<SysRolePermissionResponseDto> getSysRolePermissionByPage(BasePage page);


    /**
     * 通过分页获取SysRolePermission 角色权限表信息实现查找缓存和数据库的方法
     *
     * @param page BasePage
     * @return
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     */
    ResponseResultList<SysRolePermissionResponseDto> getSysRolePermissionByNextPage(BasePage page);


    public PageUtils queryPage(Map<String, Object> params);


    IResultCodeEnum saveRolePermission(long roleId, List<Long> permissionIds, List<Long> lastpermissionIds);

    List<SysRolePermissionResponseDto> queryRolePermissionByRoleId(long roleId);
}