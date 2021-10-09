package com.suven.framework.sys.service;

import com.suven.framework.common.data.BasePage;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.sys.dto.request.SysUserDepartRequestDto;
import com.suven.framework.sys.dto.response.SysUserDepartResponseDto;
import com.suven.framework.sys.entity.SysUserDepart;
import com.suven.framework.util.PageUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * @ClassName: SysUserDepartService.java
 * @Description: 用户部门表的数据交互处理类
 * @author xxx.xxx
 * @date   2019-11-27 17:49:58
 * @version V1.0.0
 * <p>
 * ----------------------------------------------------------------------------
 *  modifyer    modifyTime                 comment
 *
 * ----------------------------------------------------------------------------
 * </p>
 */
public interface SysUserDepartService {



    /**
     * 保存用户部门表更新数据库和缓存的实现方法
     * @param sysUserDepartRequestDto  SysUserDepartRequestDto
     * @return
     */
    SysUserDepartResponseDto saveSysUserDepart(SysUserDepartRequestDto sysUserDepartRequestDto);

    /**
     * 保存用户部门表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveBatchSysUserDepart(Collection<SysUserDepartRequestDto> entityList, int batchSize);

    /**
     * 保存用户部门表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveOrUpdateBatchSysUserDepart(Collection<SysUserDepartRequestDto> entityList, int batchSize);



    /**
     * 更新用户部门表同时更新数据库和缓存的实现方法
     * @param sysUserDepartRequestDto  SysUserDepartRequestDto
     * @return
     */
    boolean updateSysUserDepart (SysUserDepartRequestDto sysUserDepartRequestDto);

    /**
     * 更新用户部门表同时更新数据库和缓存的实现方法
     * @return
     */
    boolean updateBatchById(Collection<SysUserDepartRequestDto> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  sysUserDepartIds
     * @return
     */
    int delSysUserDepartByIds(List<Long>  sysUserDepartIds);


    /**
     * 通过主键ID更新对象用户部门表实现缓存和数据库更新的方法
     * @param  sysUserDepartId
     * @return
     */
        SysUserDepartResponseDto getSysUserDepartById(long sysUserDepartId);



    /**
     * 通过分页获取SysUserDepartResponseDto信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     */
    List<SysUserDepartResponseDto> getSysUserDepartByPage(BasePage page);


    /**
     * 通过分页获取SysUserDepart 用户部门表信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     * @author xxx.xxx
     * @date 2019-11-27 17:49:58
     */
    ResponseResultList<SysUserDepartResponseDto> getSysUserDepartByNextPage(BasePage page);


    public PageUtils queryPage(Map<String, Object> params);


    Boolean deleteUserInDepart(long depId,List<Long> idList);

    Boolean editSysDepartWithUser(SysUserDepartRequestDto dto);
    /**
     * 查询用户角色信息by用户id
     * @param userId
     * @return
     */
    List<SysUserDepart> getListByUserId(long userId);

    List<Long> getDepartIdsByUserId(long userId);
}