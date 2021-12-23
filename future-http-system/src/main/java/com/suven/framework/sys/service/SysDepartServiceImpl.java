package com.suven.framework.sys.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.databene.commons.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.suven.framework.common.data.BasePage;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.common.enums.TbStatusEnum;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.sys.dao.SysDepartDao;
import com.suven.framework.sys.dto.request.SysDepartRequestDto;
import com.suven.framework.sys.dto.response.SysDepartResponseDto;
import com.suven.framework.sys.entity.SysDepart;
import com.suven.framework.util.PageUtils;
import com.suven.framework.util.YouBianCodeUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * @ClassName: SysDepartDao.java
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
@Component
public class SysDepartServiceImpl  implements SysDepartService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysDepartDao  sysDepartDao;



    /**
     * 保存部门组织机构表同时更新数据库和缓存的实现方法
     * @param sysDepartRequestDto SysDepartResponseDto
     * @return
     */
    public SysDepartResponseDto saveSysDepart(SysDepartRequestDto sysDepartRequestDto){
        if(sysDepartRequestDto== null){
            return null;
        }
        SysDepart sysDepart = SysDepart.build().clone(sysDepartRequestDto);

        long parentId = sysDepart.getParentId();
        Object[] codeArray = generateOrgCode(parentId);
        sysDepart.setOrgCode(codeArray[0].toString());
        String orgType = codeArray[1].toString();
        sysDepart.setOrgType(Integer.valueOf(orgType));

        boolean result = sysDepartDao.save(sysDepart);
        if(!result){
            return null;
        }
        SysDepartResponseDto sysDepartResponseDto = SysDepartResponseDto.build().clone(sysDepart);
        return sysDepartResponseDto;


    }

    private Object[] generateOrgCode(long parentId) {
        //update-begin--Author:Steve  Date:20190201 for：组织机构添加数据代码调整
        QueryWrapper<SysDepart> query = new QueryWrapper<SysDepart>();
        QueryWrapper<SysDepart> query1 = new QueryWrapper<SysDepart>();
        Object[] strArray = new String[2];
        // 创建一个List集合,存储查询返回的所有SysDepart对象
        List<SysDepart> departList = new ArrayList<>();
        // 定义新编码字符串
        String newOrgCode = "";
        // 定义旧编码字符串
        String oldOrgCode = "";
        // 定义部门类型
        Integer orgType = 0;
        // 如果是最高级,则查询出同级的org_code, 调用工具类生成编码并返回
        if (parentId <= 0) {
            // 线判断数据库中的表是否为空,空则直接返回初始编码
            query1.eq("parent_id", 0).or().isNull("parent_id").orderByDesc("org_code");
            departList = sysDepartDao.list(query1);
            if(departList == null || departList.size() == 0) {
                strArray[0] = YouBianCodeUtil.getNextYouBianCode(null);
                strArray[1] = 1;
                return strArray;
            }else {
                SysDepart depart = departList.get(0);
                oldOrgCode = depart.getOrgCode();
                orgType = depart.getOrgType();
                newOrgCode = YouBianCodeUtil.getNextYouBianCode(oldOrgCode);
            }
        } else { // 反之则查询出所有同级的部门,获取结果后有两种情况,有同级和没有同级
            // 封装查询同级的条件
            query.eq("parent_id", parentId).orderByDesc("org_code");
            // 查询出同级部门的集合
            List<SysDepart> parentList = sysDepartDao.list(query);
            // 查询出父级部门
            SysDepart depart = sysDepartDao.getById(parentId);
            // 获取父级部门的Code
            String parentCode = depart.getOrgCode();
            // 根据父级部门类型算出当前部门的类型
            orgType = Integer.valueOf(depart.getOrgType()) + 1;
            // 处理同级部门为null的情况
            if (parentList == null || parentList.size() == 0) {
                // 直接生成当前的部门编码并返回
                newOrgCode = YouBianCodeUtil.getSubYouBianCode(parentCode, null);
            } else { //处理有同级部门的情况
                // 获取同级部门的编码,利用工具类
                String subCode = parentList.get(0).getOrgCode();
                // 返回生成的当前部门编码
                newOrgCode = YouBianCodeUtil.getSubYouBianCode(parentCode, subCode);
            }
        }
        // 返回最终封装了部门编码和部门类型的数组
        strArray[0] = newOrgCode;
        strArray[1] = String.valueOf(orgType);
        return strArray;
    }

    public boolean saveBatchSysDepart(Collection<SysDepartRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() !=  batchSize){
            return false;
        }
        List<SysDepart> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDepart.build().clone(e)));
        boolean result = sysDepartDao.saveBatch(list,batchSize);
        return result;
    }


    public boolean saveOrUpdateBatchSysDepart(Collection<SysDepartRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysDepart> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDepart.build().clone(e)));
        boolean result = sysDepartDao.saveOrUpdateBatch(list,batchSize);
        return result;
    }


    public boolean updateBatchById(Collection<SysDepartRequestDto> entityList, int batchSize) {
        if(null == entityList || entityList.size() != batchSize ){
            return false;
        }
        List<SysDepart> list = new ArrayList<>();
        entityList.forEach(e -> list.add(SysDepart.build().clone(e)));
        boolean result =  sysDepartDao.updateBatchById(list,batchSize);
        return result;
    }

    /**
     * 更新部门组织机构表同时更新数据库和缓存的实现方法
     * @param sysDepartRequestDto  SysDepartResponseDto
     * @return
     */
    public boolean updateSysDepart(SysDepartRequestDto sysDepartRequestDto){

          if(null ==  sysDepartRequestDto){
              return false;
          }

        SysDepart sysDepart = SysDepart.build().clone(sysDepartRequestDto);

        return sysDepartDao.updateById(sysDepart);


    }







    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     * @param  idList
     * @return
     */
    public int delSysDepartByIds(List<Long> idList){

        List<Long> newIdList = new ArrayList<>();
        boolean result = false;
        if(null == idList){
            return ResultEnum.FAIL.id();
        }
        for (long id : idList) {
            newIdList.add(id);
            this.checkChildrenExists(id, newIdList);
        }
        result =  sysDepartDao.removeByIds(newIdList);
        if(result){
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }

    /**
     * 找出子部门
     * @param id
     * @param newIdList
     */
    private void checkChildrenExists(long id, List<Long> newIdList) {
        QueryWrapper<SysDepart> query = new QueryWrapper<>();
        query.eq("parent_id",id);
        List<SysDepart> departList = sysDepartDao.list(query);
        if(departList != null && departList.size() > 0) {
            for(SysDepart depart : departList) {
                newIdList.add(depart.getId());
                this.checkChildrenExists(depart.getId(), newIdList);
            }
        }
    }


    /**
     * 通过主键ID更新对象部门组织机构表实现缓存和数据库更新的方法
     * @param  sysDepartId
     * @return
     */
    public SysDepartResponseDto getSysDepartById(long sysDepartId){
        if(sysDepartId < 0 ){
            return null;
        }
        SysDepart sysDepart =  sysDepartDao.getById(sysDepartId);
        if(sysDepart == null){
            return null;
        }
        SysDepartResponseDto sysDepartResponseDto = SysDepartResponseDto.build().clone(sysDepart);

        return sysDepartResponseDto ;

    }

    public List<SysDepartResponseDto> getSysDepartByUserId(long userId){
        List<SysDepartResponseDto> resDtoList = new ArrayList<>();
        if(userId < 0 ){
            return resDtoList;
        }
        List<SysDepart> dbList = sysDepartDao.getUserDepartsByUserId(userId);
        if(dbList == null || dbList.isEmpty()){
            return resDtoList;
        }
        dbList.forEach(sysDepart -> {
            SysDepartResponseDto sysDepartResponseDto = SysDepartResponseDto.build().clone(sysDepart);

            resDtoList.add(sysDepartResponseDto);
        });
        return resDtoList;
    }




    /**
     * 通过分页获取SysDepart信息实现查找缓存和数据库的方法
     * @param basePage BasePage
     * @return
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     */
    public List<SysDepartResponseDto> getSysDepartByPage(BasePage basePage){


        List<SysDepartResponseDto> resDtoList = new ArrayList<>();
        if(basePage == null){
            return resDtoList;
        }
        //分页对象        PageHelper
        IPage<SysDepart> iPage = new Page<>(basePage.getPageNo(), basePage.getPageSize());
        QueryWrapper<SysDepart> queryWrapper = new QueryWrapper<>();

        IPage<SysDepart> page = sysDepartDao.page(iPage, queryWrapper);
        if(null == page){
            return resDtoList;
        }

        List<SysDepart>  list = page.getRecords();;
        if(null == list || list.isEmpty()){
            return resDtoList;
        }
        list.forEach(sysDepart -> {
                SysDepartResponseDto sysDepartResponseDto = SysDepartResponseDto.build().clone(sysDepart);

            resDtoList.add(sysDepartResponseDto);
        });
        return resDtoList;


    }

    /**
     * 通过分页获取SysDepart信息实现查找缓存和数据库的方法
     * @param basePage BasePage
     * @return
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     */
    public ResponseResultList<SysDepartResponseDto> getSysDepartByNextPage(BasePage basePage){
        List<SysDepartResponseDto>  list = this.getSysDepartByPage(basePage);
        if(null == list ){
            list = new ArrayList<>();
        }
        boolean isNext =  basePage.isNextPage(list);
        ResponseResultList<SysDepartResponseDto> responseResultList = ResponseResultList.build().toIsNextPage(isNext).toList(list);
        return responseResultList;

    }


    /**
     * @Title: 启用部门组织机构表信息
     * @Description:
     * @return
     * @throw
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     */
    public boolean turnOn(List<Long> idList){
        if(null == idList || idList.isEmpty()){
            return false;
        }
        SysDepart sysDepart = SysDepart.build().toStatus(TbStatusEnum.ENABLE.index());

        //修改条件s
        UpdateWrapper<SysDepart> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in( "id",idList);

        boolean result = sysDepartDao.update( sysDepart,updateWrapper );
        return result;
    }
    /**
     * @Title: 禁用部门组织机构表信息
     * @Description:
     * @return
     * @throw
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     */
    public boolean turnOff(List<Long> idList){
        if(null == idList || idList.isEmpty()){
            return false;
        }
        SysDepart sysDepart = SysDepart.build().toStatus(TbStatusEnum.DISABLE.index());

        //修改条件s
        UpdateWrapper<SysDepart> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in( "id",idList);

        boolean result = sysDepartDao.update( sysDepart,updateWrapper );
        return result;
    }

    /**
     * @Title: 修改排序字段部门组织机构表信息
     * @Description:
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     */
    public boolean updateSortById(long id ,int sort){
        if(id < 0 ){
            return false;
        }
        SysDepart sysDepart = SysDepart.build().toSort(sort);
        //修改条件s
        UpdateWrapper<SysDepart> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        boolean result = sysDepartDao.update( sysDepart,updateWrapper);
        return result;
    }


    /**
     * @Title: 禁用部门组织机构表信息
     * @Description:
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     */
    public boolean updateSortByIds(List<Long> idList,List<Integer> sortList){
        if(null == idList || null == sortList || idList.isEmpty() ){
            return false;
        }
        List<SysDepart> sysDepartList = new ArrayList<>();
        for (int i = 0 ,j  = idList.size(); i < j ; i++){
            SysDepart  sysDepart = SysDepart .build().toSort(sortList.get(i));
            sysDepart.toId(idList.get(i));
            sysDepartList.add(sysDepart);
            //修改条件s
//            UpdateWrapper< SysDepart> updateWrapper = new UpdateWrapper<>();
//           updateWrapper.eq("id",idList.get(i));
//            sysDepartDao.update(sysDepart,updateWrapper);

        }
            sysDepartDao.updateBatchById(sysDepartList);

        return true;
    }

    public PageUtils queryPage(Map<String, Object> params) {
        IPage iPage =  new Query<SysDepart>().getPage(params);
        QueryWrapper<SysDepart> queryWrapper = new QueryWrapper<>();


        IPage<SysDepart> page = sysDepartDao.page(iPage,queryWrapper);
        return new PageUtils(page);
    }

    @Override
    public List<SysDepartResponseDto> getList() {
        List<SysDepartResponseDto> resDtoList = new ArrayList<>();

        QueryWrapper<SysDepart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",1).orderByAsc("sort");

        List<SysDepart> dbList =  sysDepartDao.list(queryWrapper);
        if(dbList == null || dbList.isEmpty()){
            return resDtoList;
        }
        dbList.forEach(sysDepart -> {
            SysDepartResponseDto sysDepartResponseDto = SysDepartResponseDto.build().clone(sysDepart);
            resDtoList.add(sysDepartResponseDto);
        });
        return resDtoList;
    }

    @Override
    public List<SysDepartResponseDto> searchBy(String keyWord) {
        List<SysDepartResponseDto> resDtoList = new ArrayList<>();
        QueryWrapper<SysDepart> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("depart_name", keyWord);
        List<SysDepart> dbList = sysDepartDao.list(queryWrapper);
        if (dbList == null || dbList.isEmpty()) {
            return resDtoList;
        }
        dbList.forEach(sysDepart -> {
            SysDepartResponseDto sysDepartResponseDto = SysDepartResponseDto.build().clone(sysDepart);
            resDtoList.add(sysDepartResponseDto);
        });
        return resDtoList;
    }

    public List<SysDepart> getList(List<Long> departIds) {
        if (CollectionUtil.isEmpty(departIds)){
            return new ArrayList<>();
        }
        return (List<SysDepart>) sysDepartDao.listByIds(departIds);
    }

    @Override
    public List<SysDepartResponseDto> getSysDepartByIds(List<Long> departIds) {
        List<SysDepartResponseDto> resDtoList = new ArrayList<>();
        if(CollectionUtil.isEmpty(departIds) ){
            return resDtoList;
        }
        List<SysDepart> dbList = sysDepartDao.getUserDepartsByIds(departIds);
        if(dbList == null || dbList.isEmpty()){
            return resDtoList;
        }
        dbList.forEach(sysDepart -> {
            SysDepartResponseDto sysDepartResponseDto = SysDepartResponseDto.build().clone(sysDepart);

            resDtoList.add(sysDepartResponseDto);
        });
        return resDtoList;
    }


    /**
     * 保存部门组织机构表同时更新数据库和缓存的实现方法
     * @return
     */
    public SysDepart  setSysDepart(){
        SysDepart sysDepart = new SysDepart();
        
 			//sysDepart.setId (long id);
 			//sysDepart.setCreateDate (Date createDate);
 			//sysDepart.setModifyDate (Date modifyDate);
 			//sysDepart.setStatus (int status);
 			//sysDepart.setSort (int sort);
 			//sysDepart.setParentId (long parentId);
 			//sysDepart.setDepartName (String departName);
 			//sysDepart.setDepartNameEn (String departNameEn);
 			//sysDepart.setDepartNameAbbr (String departNameAbbr);
 			//sysDepart.setDescription (String description);
 			//sysDepart.setOrgType (int orgType);
 			//sysDepart.setOrgCode (String orgCode);
 			//sysDepart.setMobile (String mobile);
 			//sysDepart.setFax (String fax);
 			//sysDepart.setAddress (String address);
 			//sysDepart.setRemarks (String remarks);

        return sysDepart;
    }




}
