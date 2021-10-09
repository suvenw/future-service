package com.suven.framework.sys.service;

import com.suven.framework.common.data.BasePage;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.sys.dto.request.SysPermissionDataRuleRequestDto;
import com.suven.framework.sys.dto.response.SysPermissionDataRuleResponseDto;
import com.suven.framework.util.PageUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SysPermissionDataRuleService.java
 * @Description: 的数据交互处理类
 * @author xxx.xxx
 * @date   2019-11-25 19:45:26
 * @version V1.0.0
 * <p>
 * ----------------------------------------------------------------------------
 *  modifyer    modifyTime                 comment
 *
 * ----------------------------------------------------------------------------
 * </p>
 */
public interface SysPermissionDataRuleService {



    /**
     * 保存更新数据库和缓存的实现方法
     * @param sysPermissionDataRuleRequestDto  SysPermissionDataRuleRequestDto
     * @return
     */
    SysPermissionDataRuleResponseDto saveSysPermissionDataRule(SysPermissionDataRuleRequestDto sysPermissionDataRuleRequestDto);

    /**
     * 保存更新数据库和缓存的实现方法
     * @return
     */
    boolean saveBatchSysPermissionDataRule(Collection<SysPermissionDataRuleRequestDto> entityList, int batchSize);

    /**
     * 保存更新数据库和缓存的实现方法
     * @return
     */
    boolean saveOrUpdateBatchSysPermissionDataRule(Collection<SysPermissionDataRuleRequestDto> entityList, int batchSize);



    /**
     * 更新同时更新数据库和缓存的实现方法
     * @param sysPermissionDataRuleRequestDto  SysPermissionDataRuleRequestDto
     * @return
     */
    boolean updateSysPermissionDataRule (SysPermissionDataRuleRequestDto sysPermissionDataRuleRequestDto);

    /**
     * 更新同时更新数据库和缓存的实现方法
     * @return
     */
    boolean updateBatchById(Collection<SysPermissionDataRuleRequestDto> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  sysPermissionDataRuleIds
     * @return
     */
    int delSysPermissionDataRuleByIds(List<Long>  sysPermissionDataRuleIds);


    /**
     * 通过主键ID更新对象实现缓存和数据库更新的方法
     * @param  sysPermissionDataRuleId
     * @return
     */
        List<SysPermissionDataRuleResponseDto> getSysPermissionDataRuleById(long sysPermissionDataRuleId);


    /**
     * 通过分页获取SysPermissionDataRuleResponseDto信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     */
    List<SysPermissionDataRuleResponseDto> getSysPermissionDataRuleByPage(BasePage page);


    /**
     * 通过分页获取SysPermissionDataRule 信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     * @author xxx.xxx
     * @date 2019-11-25 19:45:26
     */
    ResponseResultList<SysPermissionDataRuleResponseDto> getSysPermissionDataRuleByNextPage(BasePage page);


    public PageUtils queryPage(Map<String, Object> params);



}