package com.suven.framework.sys.dao;


import java.util.Map;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.suven.framework.util.PageUtils;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.core.mybatis.MyBatisBaseEntityDao;
import com.suven.framework.common.api.IBaseExcelData;

import com.suven.framework.sys.mapper.SysDepartPermissionMapper;
import com.suven.framework.sys.entity.SysDepartPermission;
import com.suven.framework.sys.dao.SysDepartPermissionDao;
import com.suven.framework.sys.dto.enums.SysDepartPermissionQueryEnum;
import java.util.ArrayList;
import java.util.List;



/**
 * @ClassName: SysDepartPermissionDao.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:14:27
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description: 部门权限表 的数据库查询逻辑实现类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * </pre>
 * @Copyright: (c) 2021 gc by https://www.suven.top
 **/



@Service("sysDepartPermissionDao")
public class SysDepartPermissionDao extends MyBatisBaseEntityDao<SysDepartPermissionMapper, SysDepartPermission> implements IBaseExcelData{

    @Autowired
    private SysDepartPermissionMapper  sysDepartPermissionMapper;

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysDepartPermission>  iPage =  new Query<SysDepartPermission>().getPage(params);

        QueryWrapper<SysDepartPermission> query =  new QueryWrapper<>();

        IPage<SysDepartPermission> page = this.page(iPage,query );

        return new PageUtils(page);
    }

    /**
     * 保存创建SysDepartPermission,并且保存到缓存中
     * @param sysDepartPermission
     * @author suven
     * @date 2022-02-28 16:14:27
     */

    public SysDepartPermission saveId(SysDepartPermission sysDepartPermission){
            if(null == sysDepartPermission){
                return  null;
           }
          long id = sysDepartPermissionMapper.saveId(sysDepartPermission);
          if (returnBool(id)){
                return sysDepartPermission;
          }
           return null;


    }

     /**
         * 保存创建SysDepartPermission,并且保存到缓存中
         * @param sysDepartPermission
         * @author suven
         * @date 2022-02-28 16:14:27
         */

        public SysDepartPermission saveToId(SysDepartPermission sysDepartPermission){
                if(null == sysDepartPermission){
                    return  null;
               }
              long id = sysDepartPermissionMapper.saveToId(sysDepartPermission);
              if (returnBool(id)){
                    return sysDepartPermission;
              }
               return null;


        }

    /**
     * 批量保存创建SysDepartPermission,并且保存到缓存中
     * @param sysDepartPermissionList
     * @author suven
     * @date 2022-02-28 16:14:27
     */
    public boolean saveBatchId(List<SysDepartPermission> sysDepartPermissionList){
            if(null == sysDepartPermissionList)
                return  false;
           long id =  sysDepartPermissionMapper.saveBatch(sysDepartPermissionList);
           return returnBool(id);

    }


    @Override
    public void saveData(List<Object> list) {
        List<SysDepartPermission> datas = new ArrayList<>();
        list.forEach(e -> datas.add(SysDepartPermission.build().clone(e)));
        this.saveBatch(datas, BATCH_SIZE);
        }

    /**
     * 通过分页获取SysDepartPermission信息实现查找缓存和数据库的方法
     * @param queryWrapper BasePage
     * @return
     * @author suven
     * @date 2022-02-28 16:14:27
     */
    public List<SysDepartPermission> getListByPage(IPage<SysDepartPermission> iPage, QueryWrapper<SysDepartPermission> queryWrapper ){


        List<SysDepartPermission> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper();
        }

        IPage<SysDepartPermission> page = super.page(iPage, queryWrapper);
        if(null == page){
            return resDtoList;
        }

        List<SysDepartPermission>  list = page.getRecords();
        if(null == list || list.isEmpty()){
            return resDtoList;
        }


        return list;

        }
    /**
     * 通过分页获取SysDepartPermission信息实现查找缓存和数据库的方法
     * @param queryWrapper QueryWrapper
     * @return
     * @author suven
     * @date 2022-02-28 16:14:27
     */
    public List<SysDepartPermission> getListByQuery( QueryWrapper<SysDepartPermission> queryWrapper ){


        List<SysDepartPermission> resDtoList = new ArrayList<>();
        if(queryWrapper == null){
            queryWrapper = new QueryWrapper();
        }

        List<SysDepartPermission> list = super.list(queryWrapper);
        if(null == list){
            return resDtoList;
        }

        return list;

        }

    /**
     * 通过枚举实现SysDepartPermission不同数据库的条件查询的逻辑实现的方法
     * @param queryEnum RedGroupDeviceQueryShipEnum
     * @param queryObject 参数对象实现
     * @return
     * @author suven
     * @date 2022-02-28 16:14:27
     */
    public QueryWrapper<SysDepartPermission> builderQueryEnum(SysDepartPermissionQueryEnum queryEnum,  Object queryObject){
           QueryWrapper<SysDepartPermission> queryWrapper = new QueryWrapper<>();
           if(queryEnum == null){
               return queryWrapper;
           }
           if(queryObject == null){
               return queryWrapper;
           }
            SysDepartPermission  sysDepartPermission = SysDepartPermission.build().clone(queryObject);
           switch (queryEnum){
               case DESC_ID :{
                   queryWrapper.orderByDesc("id");
                   break;
               }
               default:
                   break;
           }
          return queryWrapper;
       }
}