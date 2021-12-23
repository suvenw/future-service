package com.suven.framework.sys.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.databene.commons.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.suven.framework.common.data.BasePage;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.common.enums.TbStatusEnum;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.core.mybatis.MyBatisBaseRedisClient;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.sys.dao.SysPermissionDao;
import com.suven.framework.sys.dao.SysUserDao;
import com.suven.framework.sys.dao.SysUserDepartDao;
import com.suven.framework.sys.dao.SysUserRoleDao;
import com.suven.framework.sys.dto.request.SysUserRequestDto;
import com.suven.framework.sys.dto.response.SysUserResponseDto;
import com.suven.framework.sys.entity.SysUser;
import com.suven.framework.sys.entity.SysUserDepart;
import com.suven.framework.sys.entity.SysUserRole;
import com.suven.framework.sys.vo.request.AllStatusRequestVo;
import com.suven.framework.util.PageUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author xxx.xxx
 * @version V1.0.0
 * <p>
 * ----------------------------------------------------------------------------
 * modifyer    modifyTime                 comment
 * <p>
 * ----------------------------------------------------------------------------
 * </p>
 * @ClassName: SysUserDao.java
 * @Description: 用户表的数据交互处理类
 * @date 2019-10-18 12:35:25
 */
@Component
public class SysUserServiceImpl implements SysUserService {


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysPermissionDao sysPermissionDao;

    @Autowired
    private SysUserDepartDao sysUserDepartDao;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;



    @Autowired
    private MyBatisBaseRedisClient myBatisRedisClient;


    /**
     * 保存用户表同时更新数据库和缓存的实现方法
     *
     * @param sysUserRequestDto SysUserResponseDto
     * @return
     */
    public SysUserResponseDto saveSysUser(SysUserRequestDto sysUserRequestDto) {
        if (sysUserRequestDto == null) {
            return null;
        }
        SysUser sysUser = SysUser.build().clone(sysUserRequestDto);
        boolean result = false;
        result = sysUserDao.save(sysUser);
        //保存用户部门关系
        List<Long> departIds = sysUserRequestDto.getDepartIds();
        Collection<SysUserDepart> userDeparts = new ArrayList<>();
        if (!CollectionUtil.isEmpty(departIds)) {
            departIds.forEach(d -> {
                userDeparts.add(SysUserDepart.build().toDepId(d).toUserId(sysUser.getId()));
            });
            sysUserDepartDao.saveBatch(userDeparts, userDeparts.size());
        }
        //保存用户角色关系
        List<Long> roleIds = sysUserRequestDto.getRoleIds();
        Collection<SysUserRole> userRoles = new ArrayList<>();
        if (!CollectionUtil.isEmpty(roleIds)) {
            roleIds.forEach(r -> {
                userRoles.add(SysUserRole.build().toRoleId(r).toUserId(sysUser.getId()));
            });
            sysUserRoleDao.saveBatch(userRoles);
        }
        if (!result) {
            return null;
        }
        SysUserResponseDto sysUserResponseDto = SysUserResponseDto.build().clone(sysUser);

        return sysUserResponseDto;
    }

    public boolean saveBatchSysUser(Collection<SysUserRequestDto> entityList, int batchSize) {
        if (null == entityList || entityList.size() != batchSize) {
            return false;
        }
        List<SysUser> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysUser.build().clone(e)));
        boolean result = sysUserDao.saveBatch(list, batchSize);
        return result;
    }


    public boolean saveOrUpdateBatchSysUser(Collection<SysUserRequestDto> entityList, int batchSize) {
        if (null == entityList || entityList.size() != batchSize) {
            return false;
        }
        List<SysUser> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysUser.build().clone(e)));
        boolean result = sysUserDao.saveOrUpdateBatch(list, batchSize);
        return result;
    }


    public boolean updateBatchById(Collection<SysUserRequestDto> entityList, int batchSize) {
        if (null == entityList || entityList.size() != batchSize) {
            return false;
        }
        List<SysUser> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysUser.build().clone(e)));
        boolean result = sysUserDao.updateBatchById(list, batchSize);
        return result;
    }

    /**
     * 更新用户表同时更新数据库和缓存的实现方法
     *
     * @param sysUserRequestDto SysUserResponseDto
     * @return
     */
    public boolean updateSysUser(SysUserRequestDto sysUserRequestDto) {

        if (null == sysUserRequestDto) {
            return false;
        }

        SysUser sysUser = SysUser.build().clone(sysUserRequestDto);
        sysUser.setModifyDate(new Date());
        boolean result = sysUserDao.updateById(sysUser);
        //添加查询缓存 和app缓存key 一致
        SysUser userInfo = SysUser.build().clone(sysUser);
        String cacheKey = "user_info:" + sysUser.getId();
        logger.info("redisKey{}", cacheKey);
        if (result) {
            myBatisRedisClient.addCacheByKey(cacheKey, userInfo);
        }
        return result;


    }


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     *
     * @param idList
     * @return
     */
    public int delSysUserByIds(List<Long> idList) {
        boolean result = false;
        if (null == idList) {
            return ResultEnum.FAIL.id();
        }
        if (idList.size() == 1) {
            result = sysUserDao.removeById(idList.get(0));
        } else {
            result = sysUserDao.removeByIds(idList);
        }
        //删除用户部门关联关系
        LambdaQueryWrapper<SysUserDepart> departQuery = new LambdaQueryWrapper<>();
        departQuery.eq(SysUserDepart::getUserId, idList.get(0));
        sysUserDepartDao.remove(departQuery);
        //删除 用户角色关联关系
        LambdaQueryWrapper<SysUserRole> roleQuery = new LambdaQueryWrapper<>();
        roleQuery.eq(SysUserRole::getUserId, idList.get(0));
        sysUserRoleDao.remove(roleQuery);
        if (result) {
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();
    }


    /**
     * 通过主键ID更新对象用户表实现缓存和数据库更新的方法
     *
     * @param sysUserId
     * @return
     */
    public SysUserResponseDto getSysUserById(long sysUserId) {
        if (sysUserId < 0) {
            return null;
        }
        SysUser sysUser = sysUserDao.getById(sysUserId);
        if (sysUser == null) {
            return null;
        }
        SysUserResponseDto sysUserResponseDto = SysUserResponseDto.build().clone(sysUser);

        return sysUserResponseDto;

    }

    /**
     * 根据用户id 集合获取用户信息
     *
     * @param userIds
     * @return
     */
    @Override
    public List<SysUserResponseDto> getSysUserByIds(List<Long> userIds) {
        List<SysUser> sysUsers = (List<SysUser>) sysUserDao.listByIds(userIds);
        List<SysUserResponseDto> userResponseDtos = new ArrayList<>();
        sysUsers.forEach(u -> {
            userResponseDtos.add(SysUserResponseDto.build().clone(u));
        });
        return userResponseDtos;
    }


    /**
     * 通过分页获取SysUser信息实现查找缓存和数据库的方法
     *
     * @param basePage BasePage
     * @return
     * @author suven
     * @date 2019-10-18 12:35:25
     */
    public List<SysUserResponseDto> getSysUserByPage(ResponseResultList<SysUserResponseDto> responseResultList, BasePage basePage) {


        List<SysUserResponseDto> resDtoList = new ArrayList<>();
        if (basePage == null) {
            return resDtoList;
        }
        Page<SysUserResponseDto> page = new Page<>(basePage.getPageNo(), basePage.getRealPageSize());
        SysUserRequestDto userReqDto = (SysUserRequestDto) basePage.getParamObject();
        IPage<SysUserResponseDto> respPage = sysUserDao.getPageList(page, userReqDto);
        if (respPage == null) {
            return resDtoList;
        }
        resDtoList = respPage.getRecords();
        if (null == resDtoList || resDtoList.isEmpty()) {
            return resDtoList;
        }
        responseResultList.setTotal((int) page.getTotal());
        return resDtoList;


    }


    /**
     * 查询条件
     */
    public QueryWrapper<SysUser> checkQueryCondition(BasePage basePage) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        SysUserRequestDto param = (SysUserRequestDto) basePage.getParamObject();
        if (param.getId() > 0) {
            queryWrapper.eq("id", param.getId());
        }
        if (!StringUtils.isEmpty(param.getNickName())) {
            queryWrapper.like("nickname", param.getNickName());
        }
        if (!StringUtils.isEmpty(param.getPhone())) {
            queryWrapper.eq("phone", param.getPhone());
        }

        if (param.getSex() > 0) {
            queryWrapper.eq("sex", param.getSex());
        }
        /*if (param.getBanned() > 0) {
            queryWrapper.eq("banned", param.getBanned());
        }*/
        if (param.getStatus() > 0) {
            queryWrapper.eq("status", param.getStatus());
        }

        if (param.getStartDate() != null && param.getEndDate() != null) {
            queryWrapper.between("create_date", param.getStartDate(), param.getEndDate());
        }

        return queryWrapper;
    }


    /**
     * 通过分页获取SysUser信息实现查找缓存和数据库的方法
     *
     * @param basePage BasePage
     * @return
     * @author suven
     * @date 2019-10-18 12:35:25
     */
    public ResponseResultList<SysUserResponseDto> getSysUserByNextPage(BasePage basePage) {
        ResponseResultList<SysUserResponseDto> responseResultList = ResponseResultList.build();
        List<SysUserResponseDto> userList = this.getSysUserByPage(responseResultList, basePage);
        if (CollectionUtil.isEmpty(userList)) {
            userList = new ArrayList<>();
            return responseResultList.toIsNextPage(basePage.isNextPage(userList)).toList(userList);
        }
        //查询 用户等级
        List<Long> userIds = new ArrayList<>();
        userList.forEach(u -> {
            userIds.add(u.getId());
        });
//        List<UserGradeShipResponseDto> gradeList = userGradeShipDao.getGradeListByUserIds(userIds);
//        Map<Long, Integer> gradeMap = new HashMap<>();
//        if (CollectionUtil.isEmpty(gradeList)) {
//            return responseResultList.toIsNextPage(basePage.isNextPage(userList)).toList(userList);
//        }
//        gradeList.forEach(g -> {
//            gradeMap.put(g.getId(), g.getGrade());
//        });

        //查询 用户最后登陆时间
//        List<UserLoginResponseDto> userLoginList = userLoginDao.getListByUserIds(userIds);
//        Map<Long, Date> loginMap = new HashMap<>();
//        if (CollectionUtil.isEmpty(userLoginList)) {
//            return responseResultList.toIsNextPage(basePage.isNextPage(userList)).toList(userList);
//        }
//        userLoginList.forEach(l -> {
//            loginMap.put(l.getId(), l.getModifyDate());
//        });

//        userList.forEach(u -> {
//            if (gradeMap.containsKey(u.getId())) {
//                u.setGrade(gradeMap.get(u.getId()));
//            }
//            if (loginMap.containsKey(u.getId())) {
//                u.setLastLoginDate(loginMap.get(u.getId()));
//            }
//        });
        boolean isNext = basePage.isNextPage(userList);
        responseResultList.toIsNextPage(isNext).toList(userList);
        return responseResultList;
    }


    /**
     * @return
     * @Title: 启用用户表信息
     * @Description:
     * @throw
     * @author suven
     * @date 2019-10-18 12:35:25
     */
    public boolean turnOn(List<Long> idList) {
        if (null == idList || idList.isEmpty()) {
            return false;
        }
        SysUser sysUser = sysUserDao.getById(idList.get(0));
        sysUser.toStatus(TbStatusEnum.ENABLE.index());
        sysUser.setModifyDate(new Date());
        boolean result = sysUserDao.updateById(sysUser);
        //添加查询缓存 和app缓存key 一致
        SysUser userInfo = SysUser.build().clone(sysUser);
        String cacheKey = "user_info:" + sysUser.getId();
        logger.info("redisKey{}", cacheKey);
        if (result) {
            myBatisRedisClient.addCacheByKey(cacheKey, userInfo);
        }
        return result;
    }

    /**
     * @return
     * @Title: 禁用用户表信息
     * @Description:
     * @throw
     * @author suven
     * @date 2019-10-18 12:35:25
     */
    public boolean turnOff(List<Long> idList) {
        if (null == idList || idList.isEmpty()) {
            return false;
        }
        SysUser sysUser = sysUserDao.getById(idList.get(0));
        sysUser.toStatus(TbStatusEnum.DISABLE.index());
        sysUser.setModifyDate(new Date());
        boolean result = sysUserDao.updateById(sysUser);
        //添加查询缓存 和app缓存key 一致
        SysUser userInfo = SysUser.build().clone(sysUser);
        String cacheKey = "user_info:" + sysUser.getId();
        logger.info("redisKey{}", cacheKey);
        if (result) {
            myBatisRedisClient.addCacheByKey(cacheKey, userInfo);
        }
        return result;
    }

    /**
     * 冻结用户
     *
     * @param idList
     * @return
     */
    @Override
    public boolean frozenBatch(List<Long> idList) {
        if (null == idList || idList.isEmpty()) {
            return false;
        }
        SysUser sysUser = SysUser.build().toStatus(TbStatusEnum.FROZEN.index());
        //修改条件s
        UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id", idList);

        boolean result = sysUserDao.update(sysUser, updateWrapper);
        return result;
    }

    /**
     * 处理禁言
     *
     * @param statusReqVo
     * @return
     */
    @Override
    public boolean handleMuted(AllStatusRequestVo statusReqVo) {
        if (statusReqVo.getId() <= 0) {
            return false;
        }
        SysUser sysUser = sysUserDao.getById(statusReqVo.getId());
        //sysUser.toBanned(statusReqVo.getBanned());
        sysUser.setModifyDate(new Date());
        boolean result = sysUserDao.updateById(sysUser);

        //添加查询缓存 和app缓存key 一致 和app 类保持一致
        String cacheKey = "user_info:" + sysUser.getId();
        logger.info("redisKey{}", cacheKey);
        if (result) {
            myBatisRedisClient.addCacheByKey(cacheKey, sysUser);
        }
        return result;
    }

    /**
     * 处理封号
     *
     * @param statusReqVo
     * @return
     */
    @Override
    public boolean handleBan(AllStatusRequestVo statusReqVo) {
        if (statusReqVo.getId() <= 0) {
            return false;
        }
        SysUser sysUser = sysUserDao.getById(statusReqVo.getId());
        sysUser.toStatus(statusReqVo.getBan());
        sysUser.setModifyDate(new Date());

        boolean result = sysUserDao.updateById(sysUser);
        //添加查询缓存 和app缓存key 一致
        SysUser userInfo = SysUser.build().clone(sysUser);
        String cacheKey = "user_info:" + sysUser.getId();
        logger.info("redisKey{}", cacheKey);
        if (result) {
            myBatisRedisClient.addCacheByKey(cacheKey, userInfo);
        }

        //封禁，下架该用户所有视频 解禁 上架因封禁而下架的视频
//        videoInfoService.BanVideo(statusReqVo.getBan(), statusReqVo.getId());
        return result;
    }


    /**
     * @Title: 修改排序字段用户表信息
     * @Description:
     * @author suven
     * @date 2019-10-18 12:35:25
     */
    public boolean updateSortById(long id, int sort) {
        if (id < 0) {
            return false;
        }
        SysUser sysUser = SysUser.build();
        //修改条件s
        UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        boolean result = sysUserDao.update(sysUser, updateWrapper);

        return result;
    }


    /**
     * @Title: 禁用用户表信息
     * @Description:
     * @author suven
     * @date 2019-10-18 12:35:25
     */
    public boolean updateSortByIds(List<Long> idList, List<Integer> sortList) {
        if (null == idList || null == sortList || idList.isEmpty()) {
            return false;
        }
        List<SysUser> sysUserList = new ArrayList<>();
        for (int i = 0, j = idList.size(); i < j; i++) {
            SysUser sysUser = SysUser.build();
            sysUser.toId(idList.get(i));
            sysUserList.add(sysUser);
            //修改条件s
//            UpdateWrapper< SysUser> updateWrapper = new UpdateWrapper<>();
//           updateWrapper.eq("id",idList.get(i));
//            sysUserDao.update(sysUser,updateWrapper);

        }
        sysUserDao.updateBatchById(sysUserList);

        return true;
    }

    public PageUtils queryPage(Map<String, Object> params) {
        IPage iPage = new Query<SysUser>().getPage(params);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        IPage<SysUser> page = sysUserDao.page(iPage, queryWrapper);
        return new PageUtils(page);
    }

    @Override
    public SysUserResponseDto getUserByName(String username) {
        SysUser user = sysUserDao.getUserByName(username);
        SysUserResponseDto sysUserResponseDto = SysUserResponseDto.build().clone(user);
        return sysUserResponseDto;
    }

    @Override
    public void updateUserDepart(String username, String orgCode) {
        sysUserDao.updateUserDepart(username, orgCode);
    }


    /**
     * 保存用户表同时更新数据库和缓存的实现方法
     *
     * @return
     */
    public SysUser setSysUser() {
        SysUser sysUser = new SysUser();

        //sysUser.setId (long id);
        //sysUser.setCreateDate (Date createDate);
        //sysUser.setModifyDate (Date modifyDate);
        //sysUser.setStatus (int status);
        //sysUser.setSort (int sort);
        //sysUser.setUsername (String username);
        //sysUser.setRealname (String realname);
        //sysUser.setPassword (String password);
        //sysUser.setSalt (String salt);
        //sysUser.setHead (String head);
        //sysUser.setBirthday (Date birthday);
        //sysUser.setSex (int sex);
        //sysUser.setEmail (String email);
        //sysUser.setPhone (String phone);
        //sysUser.setOrgCode (String orgCode);
        //sysUser.setActivitiSync (int activitiSync);

        return sysUser;
    }

    public List<SysUserRole> queryUserRole(long userId) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userId);
        return sysUserRoleDao.list(queryWrapper);
    }

    @Override
    public ResponseResultList<SysUserResponseDto> getUserByDepIdPage(BasePage basePage, long depId, String userName) {
        ResponseResultList<SysUserResponseDto> responseResultList = ResponseResultList.build();
        List<SysUserResponseDto> resDtoList = new ArrayList<>();
        if (basePage == null) {
            return responseResultList.toTotal(0).toList(resDtoList);
        }


        Page<SysUser> page = new Page(basePage.getPageNo(), basePage.getRealPageSize());
        List<Long> userIds = sysUserDepartDao.getUserIdByDepId(depId);
        if (CollectionUtil.isEmpty(userIds)) {
            return responseResultList.toTotal(0).toList(resDtoList);
        }
        IPage<SysUser> pageList = sysUserDao.getUserByUserIds(page, userIds, userName);
        List<SysUser> list = pageList.getRecords();
        list.forEach(sysUser -> {
            SysUserResponseDto roleResponseDto = SysUserResponseDto.build().clone(sysUser);
            resDtoList.add(roleResponseDto);
        });
        return responseResultList.toTotal((int) pageList.getTotal()).toList(resDtoList);
    }

    @Override
    public ResponseResultList<SysUserResponseDto> getSysUserRoleId(BasePage basePage, long roleId, String userName) {
        ResponseResultList<SysUserResponseDto> resDtoList = ResponseResultList.build();
        if (basePage == null) {
            return resDtoList;
        }

        List<SysUserResponseDto> responseDto = new ArrayList<>();
        Page<SysUser> page = new Page(basePage.getPageNo(), basePage.getRealPageSize());
        //分页对象        PageHelper


        List<Long> userIds = sysUserRoleDao.getUserIdByRoleId(roleId);
        if (CollectionUtil.isEmpty(userIds)) {
            return resDtoList;
        }

        IPage<SysUser> iPage = sysUserDao.getUserByUserIds(page, userIds, userName);


        List<SysUser> list = page.getRecords();
        if (null == list || list.isEmpty()) {
            return resDtoList;
        }
        list.forEach(sysUser -> {
            SysUserResponseDto sysUserResponseDto = SysUserResponseDto.build().clone(sysUser);
            responseDto.add(sysUserResponseDto);
        });
        resDtoList.toTotal((int) iPage.getTotal()).toList(responseDto);
        return resDtoList;
    }

    @Override
    public boolean addSysUserRole(long roleId, List<Long> idList) {
        for (long sysUserId : idList) {
            SysUserRole sysUserRole = SysUserRole.build().toUserId(sysUserId).toRoleId(roleId);
            QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper();
            queryWrapper.eq("role_id", roleId).eq("user_id", sysUserId);
            SysUserRole one = sysUserRoleDao.getOne(queryWrapper);
            if (one == null) {
                sysUserRoleDao.save(sysUserRole);
            }
        }
        return true;
    }

    @Override
    public boolean deleteUserRole(long roleId, List<Long> idList) {
        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper();
        queryWrapper.eq("role_id", roleId).in("user_id", idList);
        return sysUserRoleDao.remove(queryWrapper);
    }

    @Override
    public SysUserResponseDto getUserByPhone(String phone) {
        SysUser user = sysUserDao.getUserByPhone(phone);
        SysUserResponseDto sysUserResponseDto = SysUserResponseDto.build().clone(user);
        return sysUserResponseDto;
    }

    /**
     * 保存用户岗位表同时更新数据库和缓存的实现方法
     * @param sysUserRequestDto SysUserResponseDto
     * @return
     */
    @Override
    public SysUserResponseDto saveSysUserInfo(SysUserRequestDto sysUserRequestDto){
        if(sysUserRequestDto== null){
            return null;
        }

        SysUser sysUser = SysUser.build().clone(sysUserRequestDto);
        boolean result = sysUserDao.save(sysUser);
        if(!result){
            return null;
        }
        SysUserResponseDto sysUserResponseDto = SysUserResponseDto.build().clone(sysUser);
        return sysUserResponseDto;
    }

    /**
     * 根据用户id 集合获取用户信息
     *
     * @param userIds
     * @return
     */
    @Override
    public Map<Long,SysUserResponseDto> getSysUserByIds(Collection<Long> userIds) {
        List<SysUser> sysUsers = (List<SysUser>) sysUserDao.listByIds(userIds);
        if(CollectionUtils.isEmpty(sysUsers)){
            return null;
        }
        List<SysUserResponseDto> userResponseDtos = new ArrayList<>();
        sysUsers.forEach(u -> {
            userResponseDtos.add(SysUserResponseDto.build().clone(u));
        });
        //return userResponseDtos.stream().collect(Collectors.groupingBy(SysUserResponseDto::getId));
        return userResponseDtos.stream().collect(Collectors.toMap(SysUserResponseDto::getId, a->a));
    }
}
