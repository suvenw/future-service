package com.suven.framework.sys.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.inters.IResultCodeEnum;
import org.databene.commons.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.suven.framework.common.data.BasePage;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.sys.dao.SysRolePermissionDao;
import com.suven.framework.sys.dto.request.SysRolePermissionRequestDto;
import com.suven.framework.sys.dto.response.SysRolePermissionResponseDto;
import com.suven.framework.sys.entity.SysRolePermission;
import com.suven.framework.util.PageUtils;

import java.util.*;


/**
 * @ClassName: SysRolePermissionDao.java
 * @Description: 角色权限表的数据交互处理类
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
public class SysRolePermissionServiceImpl  implements SysRolePermissionService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysRolePermissionDao  sysRolePermissionDao;



    /**
     * 保存角色权限表同时更新数据库和缓存的实现方法
     * @param sysRolePermissionRequestDto SysRolePermissionResponseDto
     * @return
     */
    @Override
    public SysRolePermissionResponseDto saveSysRolePermission(SysRolePermissionRequestDto sysRolePermissionRequestDto){
        if(sysRolePermissionRequestDto== null){
            return null;
        }
        SysRolePermission sysRolePermission = SysRolePermission.build().clone(sysRolePermissionRequestDto);
        boolean result = sysRolePermissionDao.save(sysRolePermission);
        if(!result){
            return null;
        }
        SysRolePermissionResponseDto sysRolePermissionResponseDto = SysRolePermissionResponseDto.build().clone(sysRolePermission);
        return sysRolePermissionResponseDto;


    }
    @Override
    public boolean saveBatchSysRolePermission(Collection<SysRolePermissionRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() !=  batchSize){
            return false;
        }
        List<SysRolePermission> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysRolePermission.build().clone(e)));
        boolean result = sysRolePermissionDao.saveBatch(list,batchSize);
        return result;
    }

    @Override
    public boolean saveOrUpdateBatchSysRolePermission(Collection<SysRolePermissionRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysRolePermission> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysRolePermission.build().clone(e)));
        boolean result = sysRolePermissionDao.saveOrUpdateBatch(list,batchSize);
        return result;
    }

    @Override
    public boolean updateBatchById(Collection<SysRolePermissionRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysRolePermission> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysRolePermission.build().clone(e)));
        boolean result =  sysRolePermissionDao.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新角色权限表同时更新数据库和缓存的实现方法
     * @param sysRolePermissionRequestDto  SysRolePermissionResponseDto
     * @return
     */
    @Override
    public boolean updateSysRolePermission(SysRolePermissionRequestDto sysRolePermissionRequestDto){

          if(null ==  sysRolePermissionRequestDto){
              return false;
          }

        SysRolePermission sysRolePermission = SysRolePermission.build().clone(sysRolePermissionRequestDto);

        return sysRolePermissionDao.updateById(sysRolePermission);


    }







    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList
     * @return
     */
    @Override
    public int delSysRolePermissionByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = sysRolePermissionDao.removeById(idList.get(0));
        }else {
            result =  sysRolePermissionDao.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象角色权限表实现缓存和数据库更新的方法
     * @param  sysRolePermissionId
     * @return
     */
    @Override
    public SysRolePermissionResponseDto getSysRolePermissionById(long sysRolePermissionId){
        if(sysRolePermissionId < 0 ){
            return null;
        }
        SysRolePermission sysRolePermission =  sysRolePermissionDao.getById(sysRolePermissionId);
        if(sysRolePermission == null){
            return null;
        }
        SysRolePermissionResponseDto sysRolePermissionResponseDto = SysRolePermissionResponseDto.build().clone(sysRolePermission);

        return sysRolePermissionResponseDto ;

    }




    /**
     * 通过分页获取SysRolePermission信息实现查找缓存和数据库的方法
     * @param basePage BasePage
     * @return
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     */
    public List<SysRolePermissionResponseDto> getSysRolePermissionByPage(BasePage basePage){


        List<SysRolePermissionResponseDto> resDtoList = new ArrayList<>();
        if(basePage == null){
            return resDtoList;
        }
        //分页对象        PageHelper
        IPage<SysRolePermission> iPage = new Page<>(basePage.getPageNo(), basePage.getPageSize());
        QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();

        IPage<SysRolePermission> page = sysRolePermissionDao.page(iPage, queryWrapper);
        if(null == page){
            return resDtoList;
        }

        List<SysRolePermission>  list = page.getRecords();;
        if(null == list || list.isEmpty()){
            return resDtoList;
        }
        list.forEach(sysRolePermission -> {
                SysRolePermissionResponseDto sysRolePermissionResponseDto = SysRolePermissionResponseDto.build().clone(sysRolePermission);

            resDtoList.add(sysRolePermissionResponseDto);
        });
        return resDtoList;


    }

    /**
     * 通过分页获取SysRolePermission信息实现查找缓存和数据库的方法
     * @param basePage BasePage
     * @return
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     */
    @Override
    public ResponseResultList<SysRolePermissionResponseDto> getSysRolePermissionByNextPage(BasePage basePage){
        List<SysRolePermissionResponseDto>  list = this.getSysRolePermissionByPage(basePage);
        if(null == list ){
            list = new ArrayList<>();
        }
        boolean isNext =  basePage.isNextPage(list);
        ResponseResultList<SysRolePermissionResponseDto> responseResultList = ResponseResultList.build().toIsNextPage(isNext).toList(list);
        return responseResultList;

    }



    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage iPage =  new Query<SysRolePermission>().getPage(params);
        QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();
        IPage<SysRolePermission> page = sysRolePermissionDao.page(iPage,queryWrapper);
        return new PageUtils(page);
    }


    @Override
    public IResultCodeEnum saveRolePermission(long roleId, List<Long> permissionIds, List<Long> lastPermissionIds) {
        List<Long> add = getDiff(lastPermissionIds,permissionIds);
        if(add!=null && add.size()>0) {
            List<SysRolePermission> list = new ArrayList<>();
            for (long p : add) {
                if(p>0) {
                    SysRolePermission rolepms = SysRolePermission.build().toRoleId(roleId).toPermissionId(p);
                    list.add(rolepms);
                }
            }
            sysRolePermissionDao.saveBatch(list,list.size());
        }

        List<Long> delete = getDiff(permissionIds,lastPermissionIds);
        if(delete!=null && delete.size()>0) {
            for (long permissionId : delete) {
                sysRolePermissionDao.remove(new QueryWrapper<SysRolePermission>().eq("role_id", roleId).eq("permission_id", permissionId));
            }
        }
        return SysResultCodeEnum.SYS_SUCCESS;
    }

    @Override
    public List<SysRolePermissionResponseDto> queryRolePermissionByRoleId(long roleId) {
        QueryWrapper<SysRolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        List<SysRolePermission> list = sysRolePermissionDao.list(queryWrapper);
        List<SysRolePermissionResponseDto>  resDtoList = new ArrayList<>();
        if(null == list || list.isEmpty()){
            return resDtoList;
        }
        list.forEach(sysRolePermission -> {
            SysRolePermissionResponseDto sysRolePermissionResponseDto = SysRolePermissionResponseDto.build().clone(sysRolePermission);
            resDtoList.add(sysRolePermissionResponseDto);
        });
        return resDtoList;
    }

    /**
     * 从diff中找出main中没有的元素
     * @param main
     * @param diff
     * @return
     */
    private List<Long> getDiff(List<Long> main,List<Long> diff){

        if(CollectionUtil.isEmpty(diff)) {
            return null;
        }
        Map<Long, Integer> map = new HashMap<>();
        if(!CollectionUtil.isEmpty(main)) {
            for (long string : main) {
                map.put(string, 1);
            }
        }

        List<Long> res = new ArrayList<>();
        for (long key : diff) {
            if(key > 0 && !map.containsKey(key)) {
                res.add(key);
            }
        }
        return res;
    }


    /**
     * 保存角色权限表同时更新数据库和缓存的实现方法
     * @return
     */
    public SysRolePermission  setSysRolePermission(){
        SysRolePermission sysRolePermission = new SysRolePermission();
        
 			//sysRolePermission.setId (long id);
 			//sysRolePermission.setCreateDate (Date createDate);
 			//sysRolePermission.setModifyDate (Date modifyDate);
 			//sysRolePermission.setRoleId (String roleId);
 			//sysRolePermission.setPermissionId (long permissionId);
 			//sysRolePermission.setDataRuleIds (String dataRuleIds);

        return sysRolePermission;
    }




}
