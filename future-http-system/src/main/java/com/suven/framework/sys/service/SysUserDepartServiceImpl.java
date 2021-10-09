package com.suven.framework.sys.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suven.framework.common.data.BasePage;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.sys.dao.SysUserDepartDao;
import com.suven.framework.sys.entity.SysUserDepart;
import com.suven.framework.sys.mapper.SysUserDepartMapper;
import com.suven.framework.util.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.suven.framework.sys.dto.request.SysUserDepartRequestDto;
import com.suven.framework.sys.dto.response.SysUserDepartResponseDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * @ClassName: SysUserDepartDao.java
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
@Service
public class SysUserDepartServiceImpl  implements SysUserDepartService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserDepartDao sysUserDepartDao;

    @Autowired
    private SysUserDepartMapper sysUserDepartMapper;


    /**
     * 保存用户部门表同时更新数据库和缓存的实现方法
     * @param sysUserDepartRequestDto SysUserDepartResponseDto
     * @return
     */
    public SysUserDepartResponseDto saveSysUserDepart(SysUserDepartRequestDto sysUserDepartRequestDto){
        if(sysUserDepartRequestDto== null){
            return null;
        }
        SysUserDepart sysUserDepart = SysUserDepart.build().clone(sysUserDepartRequestDto);
        boolean result = sysUserDepartDao.save(sysUserDepart);
        if(!result){
            return null;
        }
        SysUserDepartResponseDto sysUserDepartResponseDto = SysUserDepartResponseDto.build().clone(sysUserDepart);
        return sysUserDepartResponseDto;


    }

    public boolean saveBatchSysUserDepart(Collection<SysUserDepartRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() !=  batchSize){
            return false;
        }
        List<SysUserDepart> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysUserDepart.build().clone(e)));
        boolean result = sysUserDepartDao.saveBatch(list,batchSize);
        return result;
    }


    public boolean saveOrUpdateBatchSysUserDepart(Collection<SysUserDepartRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysUserDepart> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysUserDepart.build().clone(e)));
        boolean result = sysUserDepartDao.saveOrUpdateBatch(list,batchSize);
        return result;
    }


    public boolean updateBatchById(Collection<SysUserDepartRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysUserDepart> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysUserDepart.build().clone(e)));
        boolean result =  sysUserDepartDao.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新用户部门表同时更新数据库和缓存的实现方法
     * @param sysUserDepartRequestDto  SysUserDepartResponseDto
     * @return
     */
    public boolean updateSysUserDepart(SysUserDepartRequestDto sysUserDepartRequestDto){

          if(null ==  sysUserDepartRequestDto){
              return false;
          }

        SysUserDepart sysUserDepart = SysUserDepart.build().clone(sysUserDepartRequestDto);

        return sysUserDepartDao.updateById(sysUserDepart);


    }







    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList
     * @return
     */
    public int delSysUserDepartByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = sysUserDepartDao.removeById(idList.get(0));
        }else {
            result =  sysUserDepartDao.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象用户部门表实现缓存和数据库更新的方法
     * @param  sysUserDepartId
     * @return
     */
    public SysUserDepartResponseDto getSysUserDepartById(long sysUserDepartId){
        if(sysUserDepartId < 0 ){
            return null;
        }
        SysUserDepart sysUserDepart =  sysUserDepartDao.getById(sysUserDepartId);
        if(sysUserDepart == null){
            return null;
        }
        SysUserDepartResponseDto sysUserDepartResponseDto = SysUserDepartResponseDto.build().clone(sysUserDepart);

        return sysUserDepartResponseDto ;

    }




    /**
     * 通过分页获取SysUserDepart信息实现查找缓存和数据库的方法
     * @param basePage BasePage
     * @return
     * @author xxx.xxx
     * @date 2019-11-27 17:49:58
     */
    public List<SysUserDepartResponseDto> getSysUserDepartByPage(BasePage basePage){


        List<SysUserDepartResponseDto> resDtoList = new ArrayList<>();
        if(basePage == null){
            return resDtoList;
        }
        //分页对象        PageHelper
        IPage<SysUserDepart> iPage = new Page<>(basePage.getPageNo(), basePage.getPageSize());
        QueryWrapper<SysUserDepart> queryWrapper = new QueryWrapper<>();

        IPage<SysUserDepart> page = sysUserDepartDao.page(iPage, queryWrapper);
        if(null == page){
            return resDtoList;
        }

        List<SysUserDepart>  list = page.getRecords();;
        if(null == list || list.isEmpty()){
            return resDtoList;
        }
        list.forEach(sysUserDepart -> {
                SysUserDepartResponseDto sysUserDepartResponseDto = SysUserDepartResponseDto.build().clone(sysUserDepart);

            resDtoList.add(sysUserDepartResponseDto);
        });
        return resDtoList;


    }

    /**
     * 通过分页获取SysUserDepart信息实现查找缓存和数据库的方法
     * @param basePage BasePage
     * @return
     * @author xxx.xxx
     * @date 2019-11-27 17:49:58
     */
    public ResponseResultList<SysUserDepartResponseDto> getSysUserDepartByNextPage(BasePage basePage){
        List<SysUserDepartResponseDto>  list = this.getSysUserDepartByPage(basePage);
        if(null == list ){
            list = new ArrayList<>();
        }
        boolean isNext =  basePage.isNextPage(list);
        ResponseResultList<SysUserDepartResponseDto> responseResultList = ResponseResultList.build().toIsNextPage(isNext).toList(list);
        return responseResultList;

    }

    public PageUtils queryPage(Map<String, Object> params) {
        IPage iPage =  new Query<SysUserDepart>().getPage(params);
        QueryWrapper<SysUserDepart> queryWrapper = new QueryWrapper<>();
        IPage<SysUserDepart> page = sysUserDepartDao.page(iPage,queryWrapper);
        return new PageUtils(page);
    }

    @Override
    public Boolean deleteUserInDepart(long depId,List<Long> idList) {
        QueryWrapper<SysUserDepart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dep_id", depId).in("user_id",idList);
        return sysUserDepartDao.remove(queryWrapper);
    }

    @Override
    public Boolean editSysDepartWithUser(SysUserDepartRequestDto dto) {

        long sysDepId = dto.getDepId();
        for (long sysUserId : dto.getUserIdList()) {
            SysUserDepart sysUserDepart = SysUserDepart.build().setUserId(sysUserId).setDepId(sysDepId);
            SysUserDepart one = sysUserDepartDao.getByDepIdAndUserId(sysDepId,sysUserId);
            if (one == null) {
                sysUserDepartDao.save(sysUserDepart);
            }
        }
        return true;
    }
    /**
     * 查询用户角色信息by用户id
     * @param userId
     * @return
     */
    @Override
    public List<SysUserDepart> getListByUserId(long userId) {
        return sysUserDepartDao.getListByUserId(userId);
    }

    @Override
    public List<Long> getDepartIdsByUserId(long userId) {
        return sysUserDepartDao.getDepartIdsByUserId(userId);
    }


    /**
     * 保存用户部门表同时更新数据库和缓存的实现方法
     * @return
     */
    public SysUserDepart  setSysUserDepart(){
        SysUserDepart sysUserDepart = new SysUserDepart();
        /**
 			//sysUserDepart.setUserId (long userId);
 			//sysUserDepart.setDepId (long depId);
 			//sysUserDepart.setCreateDate (Date createDate);
 			//sysUserDepart.setModifyDate (Date modifyDate);
		**/

        return sysUserDepart;
    }




}
