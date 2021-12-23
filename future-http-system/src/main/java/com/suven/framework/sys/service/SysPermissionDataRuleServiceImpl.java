package com.suven.framework.sys.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.databene.commons.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.suven.framework.common.data.BasePage;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.sys.dao.SysPermissionDataRuleDao;
import com.suven.framework.sys.dto.request.SysPermissionDataRuleRequestDto;
import com.suven.framework.sys.dto.response.SysPermissionDataRuleResponseDto;
import com.suven.framework.sys.entity.SysPermissionDataRule;
import com.suven.framework.util.PageUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * @ClassName: SysPermissionDataRuleDao.java
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
@Service
public class SysPermissionDataRuleServiceImpl  implements SysPermissionDataRuleService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysPermissionDataRuleDao  sysPermissionDataRuleDao;



    /**
     * 保存同时更新数据库和缓存的实现方法
     * @param sysPermissionDataRuleRequestDto SysPermissionDataRuleResponseDto
     * @return
     */
    public SysPermissionDataRuleResponseDto saveSysPermissionDataRule(SysPermissionDataRuleRequestDto sysPermissionDataRuleRequestDto){
        if(sysPermissionDataRuleRequestDto== null){
            return null;
        }
        SysPermissionDataRule sysPermissionDataRule = SysPermissionDataRule.build().clone(sysPermissionDataRuleRequestDto);
        boolean result = sysPermissionDataRuleDao.save(sysPermissionDataRule);
        if(!result){
            return null;
        }
        SysPermissionDataRuleResponseDto sysPermissionDataRuleResponseDto = SysPermissionDataRuleResponseDto.build().clone(sysPermissionDataRule);
        return sysPermissionDataRuleResponseDto;


    }

    public boolean saveBatchSysPermissionDataRule(Collection<SysPermissionDataRuleRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() !=  batchSize){
            return false;
        }
        List<SysPermissionDataRule> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysPermissionDataRule.build().clone(e)));
        boolean result = sysPermissionDataRuleDao.saveBatch(list,batchSize);
        return result;
    }


    public boolean saveOrUpdateBatchSysPermissionDataRule(Collection<SysPermissionDataRuleRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysPermissionDataRule> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysPermissionDataRule.build().clone(e)));
        boolean result = sysPermissionDataRuleDao.saveOrUpdateBatch(list,batchSize);
        return result;
    }


    public boolean updateBatchById(Collection<SysPermissionDataRuleRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysPermissionDataRule> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysPermissionDataRule.build().clone(e)));
        boolean result =  sysPermissionDataRuleDao.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新同时更新数据库和缓存的实现方法
     * @param sysPermissionDataRuleRequestDto  SysPermissionDataRuleResponseDto
     * @return
     */
    public boolean updateSysPermissionDataRule(SysPermissionDataRuleRequestDto sysPermissionDataRuleRequestDto){

          if(null ==  sysPermissionDataRuleRequestDto){
              return false;
          }

        SysPermissionDataRule sysPermissionDataRule = SysPermissionDataRule.build().clone(sysPermissionDataRuleRequestDto);

        return sysPermissionDataRuleDao.updateById(sysPermissionDataRule);


    }







    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList
     * @return
     */
    public int delSysPermissionDataRuleByIds(List<Long> idList){
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        if( idList.size() == 1) {
            result = sysPermissionDataRuleDao.removeById(idList.get(0));
        }else {
            result =  sysPermissionDataRuleDao.removeByIds(idList);
        }
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象实现缓存和数据库更新的方法
     * @param  sysPermissionDataRuleId
     * @return
     */
    public List<SysPermissionDataRuleResponseDto> getSysPermissionDataRuleById(long sysPermissionDataRuleId){
        if(sysPermissionDataRuleId < 0 ){
            return null;
        }
        List<Long> ids = new ArrayList<>();
        ids.add(sysPermissionDataRuleId);
        List<SysPermissionDataRule> sysPermissionDataRules =  sysPermissionDataRuleDao.getListByIds(ids);
        if(CollectionUtil.isEmpty(sysPermissionDataRules)){
            return new ArrayList<>();
        }
        List<SysPermissionDataRuleResponseDto> resps = new ArrayList<>();
        sysPermissionDataRules.forEach(s -> {
            resps.add(SysPermissionDataRuleResponseDto.build().clone(s));
        });
        return resps;

    }




    /**
     * 通过分页获取SysPermissionDataRule信息实现查找缓存和数据库的方法
     * @param basePage BasePage
     * @return
     * @author xxx.xxx
     * @date 2019-11-25 19:45:26
     */
    public List<SysPermissionDataRuleResponseDto> getSysPermissionDataRuleByPage(BasePage basePage){


        List<SysPermissionDataRuleResponseDto> resDtoList = new ArrayList<>();
        if(basePage == null){
            return resDtoList;
        }
        //分页对象        PageHelper
        IPage<SysPermissionDataRule> iPage = new Page<>(basePage.getPageNo(), basePage.getPageSize());
        QueryWrapper<SysPermissionDataRule> queryWrapper = new QueryWrapper<>();

        IPage<SysPermissionDataRule> page = sysPermissionDataRuleDao.page(iPage, queryWrapper);
        if(null == page){
            return resDtoList;
        }

        List<SysPermissionDataRule>  list = page.getRecords();;
        if(null == list || list.isEmpty()){
            return resDtoList;
        }
        list.forEach(sysPermissionDataRule -> {
                SysPermissionDataRuleResponseDto sysPermissionDataRuleResponseDto = SysPermissionDataRuleResponseDto.build().clone(sysPermissionDataRule);

            resDtoList.add(sysPermissionDataRuleResponseDto);
        });
        return resDtoList;


    }

    /**
     * 通过分页获取SysPermissionDataRule信息实现查找缓存和数据库的方法
     * @param basePage BasePage
     * @return
     * @author xxx.xxx
     * @date 2019-11-25 19:45:26
     */
    public ResponseResultList<SysPermissionDataRuleResponseDto> getSysPermissionDataRuleByNextPage(BasePage basePage){
        List<SysPermissionDataRuleResponseDto>  list = this.getSysPermissionDataRuleByPage(basePage);
        if(null == list ){
            list = new ArrayList<>();
        }
        boolean isNext =  basePage.isNextPage(list);
        ResponseResultList<SysPermissionDataRuleResponseDto> responseResultList = ResponseResultList.build().toIsNextPage(isNext).toList(list);
        return responseResultList;

    }

    public PageUtils queryPage(Map<String, Object> params) {
        IPage iPage =  new Query<SysPermissionDataRule>().getPage(params);
        QueryWrapper<SysPermissionDataRule> queryWrapper = new QueryWrapper<>();
        IPage<SysPermissionDataRule> page = sysPermissionDataRuleDao.page(iPage,queryWrapper);
        return new PageUtils(page);
    }


    /**
     * 保存同时更新数据库和缓存的实现方法
     * @return
     */
    public SysPermissionDataRule  setSysPermissionDataRule(){
        SysPermissionDataRule sysPermissionDataRule = new SysPermissionDataRule();
        /**
 			//sysPermissionDataRule.setCreateDate (Date createDate);
 			//sysPermissionDataRule.setModifyDate (Date modifyDate);
 			//sysPermissionDataRule.setStatus (int status);
 			//sysPermissionDataRule.setSort (int sort);
 			//sysPermissionDataRule.setPermissionId (long permissionId);
 			//sysPermissionDataRule.setRuleName (String ruleName);
 			//sysPermissionDataRule.setRuleColumn (String ruleColumn);
 			//sysPermissionDataRule.setRuleConditions (String ruleConditions);
 			//sysPermissionDataRule.setRuleValue (String ruleValue);
		**/

        return sysPermissionDataRule;
    }




}
