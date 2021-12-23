package com.suven.framework.sys.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.inters.IResultCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.suven.framework.common.constants.CommonConstant;
import com.suven.framework.common.data.BasePage;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.common.enums.TbStatusEnum;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.sys.dao.SysPermissionDao;
import com.suven.framework.sys.dao.SysUserDao;
import com.suven.framework.sys.dto.request.SysPermissionRequestDto;
import com.suven.framework.sys.dto.response.SysPermissionResponseDto;
import com.suven.framework.sys.entity.SysPermission;
import com.suven.framework.util.PageUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * @ClassName: SysPermissionDao.java
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
@Component
public class SysPermissionServiceImpl  implements SysPermissionService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysPermissionDao  sysPermissionDao;



    /**
     * 保存菜单权限表同时更新数据库和缓存的实现方法
     * @param sysPermissionRequestDto SysPermissionResponseDto
     * @return
     */
    @Override
    public SysPermissionResponseDto saveSysPermission(SysPermissionRequestDto sysPermissionRequestDto){
        if(sysPermissionRequestDto== null){
            return null;
        }
        if(CommonConstant.MENU_TYPE_0.equals(sysPermissionRequestDto.getMenuType())) {
            sysPermissionRequestDto.setParentId(0);
        }
        //----------------------------------------------------------------------
        long pid = sysPermissionRequestDto.getParentId();
        if(pid > 0) {
            //设置父节点不为叶子节点
            sysPermissionDao.setMenuLeaf(pid, 0);
        }
        sysPermissionRequestDto.setDelFlag(0);
        sysPermissionRequestDto.setIsLeaf(1);//true

        SysPermission sysPermission = SysPermission.build().clone(sysPermissionRequestDto);
        boolean result = sysPermissionDao.save(sysPermission);
        if(!result){
            return null;
        }
        SysPermissionResponseDto sysPermissionResponseDto = SysPermissionResponseDto.build().clone(sysPermission);
        return sysPermissionResponseDto;


    }
    @Override
    public boolean saveBatchSysPermission(Collection<SysPermissionRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() !=  batchSize){
            return false;
        }
        List<SysPermission> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysPermission.build().clone(e)));
        boolean result = sysPermissionDao.saveBatch(list,batchSize);
        return result;
    }

    @Override
    public boolean saveOrUpdateBatchSysPermission(Collection<SysPermissionRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysPermission> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysPermission.build().clone(e)));
        boolean result = sysPermissionDao.saveOrUpdateBatch(list,batchSize);
        return result;
    }

    @Override
    public boolean updateBatchById(Collection<SysPermissionRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysPermission> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysPermission.build().clone(e)));
        boolean result =  sysPermissionDao.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新菜单权限表同时更新数据库和缓存的实现方法
     * @param sysPermissionRequestDto  SysPermissionResponseDto
     * @return
     */
    @Override
    public boolean updateSysPermission(SysPermissionRequestDto sysPermissionRequestDto){

          if(null ==  sysPermissionRequestDto){
              return false;
          }

        SysPermission sysPermission = SysPermission.build().clone(sysPermissionRequestDto);

        return sysPermissionDao.updateById(sysPermission);


    }







    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList
     * @return
     */
    @Override
    public int delSysPermissionByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = sysPermissionDao.removeById(idList.get(0));
        }else {
            result =  sysPermissionDao.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象菜单权限表实现缓存和数据库更新的方法
     * @param  sysPermissionId
     * @return
     */
    @Override
    public SysPermissionResponseDto getSysPermissionById(long sysPermissionId){
        if(sysPermissionId < 0 ){
            return null;
        }
        SysPermission sysPermission =  sysPermissionDao.getById(sysPermissionId);
        if(sysPermission == null){
            return null;
        }
        SysPermissionResponseDto sysPermissionResponseDto = SysPermissionResponseDto.build().clone(sysPermission);

        return sysPermissionResponseDto ;

    }




    /**
     * 通过分页获取SysPermission信息实现查找缓存和数据库的方法
     * @param basePage BasePage
     * @return
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     */
    @Override
    public List<SysPermissionResponseDto> getSysPermissionByPage(BasePage basePage){


        List<SysPermissionResponseDto> resDtoList = new ArrayList<>();
        if(basePage == null){
            return resDtoList;
        }
        //分页对象        PageHelper
        IPage<SysPermission> iPage = new Page<>(basePage.getPageNo(), basePage.getPageSize());
        QueryWrapper<SysPermission> queryWrapper  = (QueryWrapper<SysPermission>) basePage.getParamObject();
        if (queryWrapper == null) {
            queryWrapper = checkQueryCondition(basePage);
        }

        IPage<SysPermission> page = sysPermissionDao.page(iPage, queryWrapper);
        if(null == page){
            return resDtoList;
        }

        List<SysPermission>  list = page.getRecords();;
        if(null == list || list.isEmpty()){
            return resDtoList;
        }
        list.forEach(sysPermission -> {
                SysPermissionResponseDto sysPermissionResponseDto = SysPermissionResponseDto.build().clone(sysPermission);

            resDtoList.add(sysPermissionResponseDto);
        });
        return resDtoList;


    }

    /**
     * 查询条件
     */

    public QueryWrapper<SysPermission> checkQueryCondition(BasePage basePage) {
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("menu_type", CommonConstant.MENU_TYPE_0);
            queryWrapper.eq("del_flag",CommonConstant.DEL_FLAG_0);
            queryWrapper.orderByAsc("sort");
        return queryWrapper;
    }



    /**
     * 通过分页获取SysPermission信息实现查找缓存和数据库的方法
     * @param basePage BasePage
     * @return
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     */
    @Override
    public ResponseResultList<SysPermissionResponseDto> getSysPermissionByNextPage(BasePage basePage){
        List<SysPermissionResponseDto>  list = this.getSysPermissionByPage(basePage);
        if(null == list ){
            list = new ArrayList<>();
        }
        boolean isNext =  basePage.isNextPage(list);
        ResponseResultList<SysPermissionResponseDto> responseResultList = ResponseResultList.build().toIsNextPage(isNext).toList(list)
              ;
        return responseResultList;

    }


    /**
     * @Title: 启用菜单权限表信息
     * @Description:
     * @return
     * @throw
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     */
    @Override
    public boolean turnOn(List<Long> idList){
        if(null == idList || idList.isEmpty()){
            return false;
        }
        SysPermission sysPermission = SysPermission.build().toStatus(TbStatusEnum.ENABLE.index());

        //修改条件s
        UpdateWrapper<SysPermission> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in( "id",idList);

        boolean result = sysPermissionDao.update( sysPermission,updateWrapper );
        return result;
    }
    /**
     * @Title: 禁用菜单权限表信息
     * @Description:
     * @return
     * @throw
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     */
    @Override
    public boolean turnOff(List<Long> idList){
        if(null == idList || idList.isEmpty()){
            return false;
        }
        SysPermission sysPermission = SysPermission.build().toStatus(TbStatusEnum.DISABLE.index());

        //修改条件s
        UpdateWrapper<SysPermission> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in( "id",idList);

        boolean result = sysPermissionDao.update( sysPermission,updateWrapper );
        return result;
    }

    /**
     * @Title: 修改排序字段菜单权限表信息
     * @Description:
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     */
    @Override
    public boolean updateSortById(long id ,int sort){
        if(id < 0 ){
            return false;
        }
        SysPermission sysPermission = SysPermission.build().toSort(sort);
        //修改条件s
        UpdateWrapper<SysPermission> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        boolean result = sysPermissionDao.update( sysPermission,updateWrapper);
        return result;
    }


    /**
     * @Title: 禁用菜单权限表信息
     * @Description:
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     */
    @Override
    public boolean updateSortByIds(List<Long> idList,List<Integer> sortList){
        if(null == idList || null == sortList || idList.isEmpty() ){
            return false;
        }
        List<SysPermission> sysPermissionList = new ArrayList<>();
        for (int i = 0 ,j  = idList.size(); i < j ; i++){
            SysPermission  sysPermission = SysPermission .build().toSort(sortList.get(i));
            sysPermission.toId(idList.get(i));
            sysPermissionList.add(sysPermission);
            //修改条件s
//            UpdateWrapper< SysPermission> updateWrapper = new UpdateWrapper<>();
//           updateWrapper.eq("id",idList.get(i));
//            sysPermissionDao.update(sysPermission,updateWrapper);

        }
            sysPermissionDao.updateBatchById(sysPermissionList);

        return true;
    }

    public PageUtils queryPage(Map<String, Object> params) {
        IPage iPage =  new Query<SysPermission>().getPage(params);
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        IPage<SysPermission> page = sysPermissionDao.page(iPage,queryWrapper);
        return new PageUtils(page);
    }

    @Override
    public List<SysPermissionResponseDto> getSysPermissionList(SysPermissionRequestDto dto) {

        List<SysPermissionResponseDto> resDtoList = new ArrayList<>();
        QueryWrapper<SysPermission> query = new QueryWrapper<>();
        if (dto.getMenuType()>=0) {
            query.eq("menu_type",dto.getMenuType());
        }
        if (dto.getDelFlag()>=0) {
            query.eq("del_flag", dto.getDelFlag());
        }
        if (dto.getParentId()>0) {
            query.eq("parent_id", dto.getParentId());
        }
        query.orderByAsc("sort");
        List<SysPermission> list = sysPermissionDao.list(query);
        list.forEach(sysPermission -> {
            SysPermissionResponseDto sysPermissionResponseDto = SysPermissionResponseDto.build().clone(sysPermission);
            resDtoList.add(sysPermissionResponseDto);
        });
        return resDtoList;
    }

    @Override
    public IResultCodeEnum deleteBatchByIds(List<Long> idList) {
        List<Long> newIds = new ArrayList<>();
        for (long id: idList) {
            setChildrenIds(newIds,id);
        }
        newIds.addAll(idList);
        sysPermissionDao.getBaseMapper().deleteBatchIds(newIds);
        return SysResultCodeEnum.SYS_SUCCESS;
    }

    @Override
    public List<SysPermissionResponseDto> queryByUser(long userId) {
        List<SysPermissionResponseDto> resDtoList = new ArrayList<>();
        List<SysPermission> list = sysPermissionDao.queryByUserId(userId);
        list.forEach(sysPermission -> {
            SysPermissionResponseDto sysDepartResponseDto = SysPermissionResponseDto.build().clone(sysPermission);
            resDtoList.add(sysDepartResponseDto);
        });
        return resDtoList;
    }

    @Override
    public List<SysPermissionResponseDto> getAuthListByType(Integer menuType) {
        List<SysPermissionResponseDto> resDtoList = new ArrayList<>();
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("menu_type",menuType).eq("del_flag",0);
        List<SysPermission> list = sysPermissionDao.selectList(queryWrapper);
        list.forEach(sysPermission -> {
            SysPermissionResponseDto sysDepartResponseDto = SysPermissionResponseDto.build().clone(sysPermission);
            resDtoList.add(sysDepartResponseDto);
        });
        return resDtoList;

    }


    private void setChildrenIds(List<Long> newIds,long parentId) {
        QueryWrapper<SysPermission> query = new QueryWrapper<>();
        // 封装查询条件parentId为主键,
        query.eq("parent_id", parentId);
        // 查出该主键下的所有子级
        List<SysPermission> permissionList = sysPermissionDao.list(query);
        if (permissionList != null && permissionList.size() > 0) {
            long id = 0; // id
            int num = 0; // 查出的子级数量
            // 再遍历刚才查出的集合, 根据每个对象,查找其是否仍有子级
            for (int i = 0, len = permissionList.size(); i < len; i++) {
                id = permissionList.get(i).getId();
                // 如果有, 则递归
                if (id > 0) {
                    newIds.add(id);
                    setChildrenIds(newIds,id);
                }
            }
        }
    }

    /**
     * 保存菜单权限表同时更新数据库和缓存的实现方法
     * @return
     */

    public SysPermission  setSysPermission(){
        SysPermission sysPermission = new SysPermission();
        
 			//sysPermission.setId (long id);
 			//sysPermission.setCreateDate (Date createDate);
 			//sysPermission.setModifyDate (Date modifyDate);
 			//sysPermission.setStatus (int status);
 			//sysPermission.setSort (int sort);
 			//sysPermission.setParentId (long parentId);
 			//sysPermission.setName (String name);
 			//sysPermission.setUrl (String url);
 			//sysPermission.setComponent (String component);
 			//sysPermission.setComponentName (String componentName);
 			//sysPermission.setRedirect (String redirect);
 			//sysPermission.setMenuType (int menuType);
 			//sysPermission.setPerms (String perms);
 			//sysPermission.setPermsType (int permsType);
 			//sysPermission.setAlwaysShow (int alwaysShow);
 			//sysPermission.setIcon (String icon);
 			//sysPermission.setIsRoute (int isRoute);
 			//sysPermission.setIsLeaf (int isLeaf);
 			//sysPermission.setKeepAlive (int keepAlive);
 			//sysPermission.setHidden (int hidden);
 			//sysPermission.setDescription (String description);
 			//sysPermission.setDelFlag (int delFlag);
 			//sysPermission.setRuleFlag (int ruleFlag);

        return sysPermission;
    }




}
