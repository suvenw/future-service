package com.suven.framework.sys.service;

import com.suven.framework.common.data.BasePage;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.sys.dto.request.SysUserRequestDto;
import com.suven.framework.sys.dto.response.SysUserResponseDto;
import com.suven.framework.sys.entity.SysUserRole;
import com.suven.framework.sys.vo.request.AllStatusRequestVo;
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
 * @ClassName: SysUserService.java
 * @Description: 用户表的数据交互处理类
 * @date 2019-10-18 12:35:25
 */
public interface SysUserService {


    /**
     * 保存用户表更新数据库和缓存的实现方法
     *
     * @param sysUserRequestDto SysUserRequestDto
     * @return
     */
    SysUserResponseDto saveSysUser(SysUserRequestDto sysUserRequestDto);

    /**
     * 保存用户表更新数据库和缓存的实现方法
     *
     * @return
     */
    boolean saveBatchSysUser(Collection<SysUserRequestDto> entityList, int batchSize);

    /**
     * 保存用户表更新数据库和缓存的实现方法
     *
     * @return
     */
    boolean saveOrUpdateBatchSysUser(Collection<SysUserRequestDto> entityList, int batchSize);


    /**
     * 更新用户表同时更新数据库和缓存的实现方法
     *
     * @param sysUserRequestDto SysUserRequestDto
     * @return
     */
    boolean updateSysUser(SysUserRequestDto sysUserRequestDto);

//    /**
//     * 更新用户表同时更新数据库和缓存的实现方法
//     * @return
//     */
//    boolean updateBatchById(Collection<SysUserRequestDto> entityList, int batchSize);

    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     *
     * @param sysUserIds
     * @return
     */
    int delSysUserByIds(List<Long> sysUserIds);


    /**
     * 通过主键ID更新对象用户表实现缓存和数据库更新的方法
     *
     * @param sysUserId
     * @return
     */
    SysUserResponseDto getSysUserById(long sysUserId);


    List<SysUserResponseDto> getSysUserByIds(List<Long> userIds);


    /**
     * 通过分页获取SysUser 用户表信息实现查找缓存和数据库的方法
     *
     * @param page BasePage
     * @return
     * @author suven
     * @date 2019-10-18 12:35:25
     */
    ResponseResultList<SysUserResponseDto> getSysUserByNextPage(BasePage page);

    /**
     * @return
     * @Title: 启用用户表信息
     * @Description:
     * @throw
     * @author suven
     * @date 2019-10-18 12:35:25
     * --------------------------------------------------------
     * modifyer    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    public boolean turnOn(List<Long> idList);

    /**
     * @return
     * @Title: 禁用用户表信息
     * @Description:
     * @throw
     * @author suven
     * @date 2019-10-18 12:35:25
     * --------------------------------------------------------
     * modifyer    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    public boolean turnOff(List<Long> idList);

    /**
     * 冻结用户
     *
     * @param idList
     * @return
     */
    public boolean frozenBatch(List<Long> idList);

    /**
     * 处理禁言
     *
     * @param actionReq
     * @return
     */
    public boolean handleMuted(AllStatusRequestVo actionReq);

    /**
     * 处理封号
     *
     * @param actionReq
     * @return
     */
    public boolean handleBan(AllStatusRequestVo actionReq);

    /**
     * @return
     * @Title: 修改排序字段用户表信息
     * @Description:
     * @author suven
     * @date 2019-10-18 12:35:25
     * --------------------------------------------------------
     * modifyer    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    public boolean updateSortById(long id, int sort);


    /**
     * @return
     * @Title: 禁用用户表信息
     * @Description:
     * @author suven
     * @date 2019-10-18 12:35:25
     */
    public boolean updateSortByIds(List<Long> idList, List<Integer> sortList);

    public PageUtils queryPage(Map<String, Object> params);


    SysUserResponseDto getUserByName(String username);

    void updateUserDepart(String username, String orgCode);

    /**
     * 查询用户角色列表by用户id
     *
     * @param userId
     * @return
     */
    List<SysUserRole> queryUserRole(long userId);

    ResponseResultList<SysUserResponseDto> getUserByDepIdPage(BasePage page, long depId, String userName);

    ResponseResultList<SysUserResponseDto> getSysUserRoleId(BasePage page, long roleId, String userName);

    boolean addSysUserRole(long roleId, List<Long> idList);

    boolean deleteUserRole(long roleId, List<Long> idList);

    SysUserResponseDto getUserByPhone(String phone);

    SysUserResponseDto saveSysUserInfo(SysUserRequestDto sysUserRequestDto);

    Map<Long,SysUserResponseDto> getSysUserByIds(Collection<Long> userIds);
}