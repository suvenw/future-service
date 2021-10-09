package com.suven.framework.sys.service;

import com.suven.framework.common.data.BasePage;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.sys.dto.request.SysPermissionRequestDto;
import com.suven.framework.sys.dto.response.SysPermissionResponseDto;
import com.suven.framework.util.PageUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SysPermissionService.java
 * @Description: 菜单权限表的数据交互处理类
 * @author xxx.xxx
 * @date   2019-10-18 12:35:25
 * @version V1.0.0
 * <p>
 * ----------------------------------------------------------------------------
 *  modifyer    modifyTime                 comment
 *
 * ----------------------------------------------------------------------------
 * </p>
 */
public interface SysPermissionService {



    /**
     * 保存菜单权限表更新数据库和缓存的实现方法
     * @param sysPermissionRequestDto  SysPermissionRequestDto
     * @return
     */
    SysPermissionResponseDto saveSysPermission(SysPermissionRequestDto sysPermissionRequestDto);

    /**
     * 保存菜单权限表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveBatchSysPermission(Collection<SysPermissionRequestDto> entityList, int batchSize);

    /**
     * 保存菜单权限表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveOrUpdateBatchSysPermission(Collection<SysPermissionRequestDto> entityList, int batchSize);



    /**
     * 更新菜单权限表同时更新数据库和缓存的实现方法
     * @param sysPermissionRequestDto  SysPermissionRequestDto
     * @return
     */
    boolean updateSysPermission (SysPermissionRequestDto sysPermissionRequestDto);

    /**
     * 更新菜单权限表同时更新数据库和缓存的实现方法
     * @return
     */
    boolean updateBatchById(Collection<SysPermissionRequestDto> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  sysPermissionIds
     * @return
     */
    int delSysPermissionByIds(List<Long>  sysPermissionIds);


    /**
     * 通过主键ID更新对象菜单权限表实现缓存和数据库更新的方法
     * @param  sysPermissionId
     * @return
     */
        SysPermissionResponseDto getSysPermissionById(long sysPermissionId);



    /**
     * 通过分页获取SysPermissionResponseDto信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     */
    List<SysPermissionResponseDto> getSysPermissionByPage(BasePage page);


    /**
     * 通过分页获取SysPermission 菜单权限表信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     */
    ResponseResultList<SysPermissionResponseDto> getSysPermissionByNextPage(BasePage page);

    /**
     * @Title: 启用菜单权限表信息
     * @Description:
     * @return
     * @throw
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    public boolean turnOn(List<Long> idList);

    /**
     * @Title: 禁用菜单权限表信息
     * @Description:
     * @return
     * @throw
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    public boolean turnOff(List<Long> idList);


    /**
     * @Title: 修改排序字段菜单权限表信息
     * @Description:
     * @return
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     *  --------------------------------------------------------
     *  modifyer    modifyTime                 comment
     *
     *  --------------------------------------------------------
     */
    public boolean updateSortById(long id ,int sort);


    /**
     * @Title: 禁用菜单权限表信息
     * @Description:
     * @return
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     */
    public boolean updateSortByIds(List<Long> idList,List<Integer> sortList);

    public PageUtils queryPage(Map<String, Object> params);


    List<SysPermissionResponseDto> getSysPermissionList(SysPermissionRequestDto dto);

    IResultCodeEnum deleteBatchByIds(List<Long> idList);

    List<SysPermissionResponseDto> queryByUser(long userId);

    List<SysPermissionResponseDto> getAuthListByType(Integer menuType2);
}