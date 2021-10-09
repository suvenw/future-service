package com.suven.framework.sys.service;

import com.suven.framework.common.data.BasePage;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.sys.dto.request.SysDepartRequestDto;
import com.suven.framework.sys.dto.response.SysDepartResponseDto;
import com.suven.framework.sys.entity.SysDepart;
import com.suven.framework.util.PageUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SysDepartService.java
 * @Description: 部门组织机构表的数据交互处理类
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
public interface SysDepartService {



    /**
     * 保存部门组织机构表更新数据库和缓存的实现方法
     * @param sysDepartRequestDto  SysDepartRequestDto
     * @return
     */
    SysDepartResponseDto saveSysDepart(SysDepartRequestDto sysDepartRequestDto);

    /**
     * 保存部门组织机构表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveBatchSysDepart(Collection<SysDepartRequestDto> entityList, int batchSize);

    /**
     * 保存部门组织机构表更新数据库和缓存的实现方法
     * @return
     */
    boolean saveOrUpdateBatchSysDepart(Collection<SysDepartRequestDto> entityList, int batchSize);



    /**
     * 更新部门组织机构表同时更新数据库和缓存的实现方法
     * @param sysDepartRequestDto  SysDepartRequestDto
     * @return
     */
    boolean updateSysDepart (SysDepartRequestDto sysDepartRequestDto);

    /**
     * 更新部门组织机构表同时更新数据库和缓存的实现方法
     * @return
     */
    boolean updateBatchById(Collection<SysDepartRequestDto> entityList, int batchSize);


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  sysDepartIds
     * @return
     */
    int delSysDepartByIds(List<Long>  sysDepartIds);


    /**
     * 通过主键ID更新对象部门组织机构表实现缓存和数据库更新的方法
     * @param  sysDepartId
     * @return
     */
        SysDepartResponseDto getSysDepartById(long sysDepartId);

    /**
     * 通过主键用户userID查找对象部门组织机构表实现缓存和数据库更新的方法
     * @param  userId
     * @return
     */
    List<SysDepartResponseDto> getSysDepartByUserId(long userId);



    /**
     * 通过分页获取SysDepartResponseDto信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     */
    List<SysDepartResponseDto> getSysDepartByPage(BasePage page);


    /**
     * 通过分页获取SysDepart 部门组织机构表信息实现查找缓存和数据库的方法
     * @param page BasePage
     * @return
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     */
    ResponseResultList<SysDepartResponseDto> getSysDepartByNextPage(BasePage page);

    /**
     * @Title: 启用部门组织机构表信息
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
     * @Title: 禁用部门组织机构表信息
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
     * @Title: 修改排序字段部门组织机构表信息
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
     * @Title: 禁用部门组织机构表信息
     * @Description:
     * @return
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     */
    public boolean updateSortByIds(List<Long> idList,List<Integer> sortList);

    public PageUtils queryPage(Map<String, Object> params);


    List<SysDepartResponseDto> getList();

    List<SysDepartResponseDto> searchBy(String keyWord);
    /**
     * 查询部门列表by 部门ids
     * @return
     */
    List<SysDepart> getList(List<Long> departIds);

    List<SysDepartResponseDto> getSysDepartByIds(List<Long> departIds);
}