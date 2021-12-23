package com.suven.framework.sys.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.databene.commons.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.suven.framework.common.data.BasePage;
import com.suven.framework.common.enums.ResultEnum;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.sys.dao.RoleDao;
import com.suven.framework.sys.dto.request.RoleRequestDto;
import com.suven.framework.sys.dto.response.RoleResponseDto;
import com.suven.framework.sys.entity.Role;
import com.suven.framework.util.PageUtils;

import java.util.ArrayList;
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
 * @ClassName: RoleDao.java
 * @Description: 角色表的数据交互处理类
 * @date 2019-11-21 15:22:59
 */
@Service
public class RoleServiceImpl implements RoleService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RoleDao roleDao;





    /**
     * 保存角色表同时更新数据库和缓存的实现方法
     *
     * @param roleRequestDto RoleResponseDto
     * @return
     */
    public RoleResponseDto saveRole(RoleRequestDto roleRequestDto) {
        if (roleRequestDto == null) {
            return null;
        }
        Role role = Role.build().clone(roleRequestDto);
        boolean result = roleDao.save(role);
        if (!result) {
            return null;
        }
        RoleResponseDto roleResponseDto = RoleResponseDto.build().clone(role);
        return roleResponseDto;


    }

    public boolean saveBatchRole(Collection<RoleRequestDto> entityList, int batchSize) {
        if (null == entityList || entityList.size() != batchSize) {
            return false;
        }
        List<Role> list = new ArrayList<>();
        entityList.forEach(e -> list.add(Role.build().clone(e)));
        boolean result = roleDao.saveBatch(list, batchSize);
        return result;
    }


    public boolean saveOrUpdateBatchRole(Collection<RoleRequestDto> entityList, int batchSize) {
        if (null == entityList || entityList.size() != batchSize) {
            return false;
        }
        List<Role> list = new ArrayList<>();
        entityList.forEach(e -> list.add(Role.build().clone(e)));
        boolean result = roleDao.saveOrUpdateBatch(list, batchSize);
        return result;
    }


    public boolean updateBatchById(Collection<RoleRequestDto> entityList, int batchSize) {
        if (null == entityList || entityList.size() != batchSize) {
            return false;
        }
        List<Role> list = new ArrayList<>();
        entityList.forEach(e -> list.add(Role.build().clone(e)));
        boolean result = roleDao.updateBatchById(list, batchSize);
        return result;
    }

    /**
     * 更新角色表同时更新数据库和缓存的实现方法
     *
     * @param roleRequestDto RoleResponseDto
     * @return
     */
    public boolean updateRole(RoleRequestDto roleRequestDto) {

        if (null == roleRequestDto) {
            return false;
        }

        Role role = Role.build().clone(roleRequestDto);

        return roleDao.updateById(role);


    }


    /**
     * 通过主键ID删除对象信息实现缓存和数据库,同时删除的方法
     *
     * @param idList
     * @return
     */
    public int delRoleByIds(List<Long> idList) {
        boolean result = false;
        if (null == idList) {
            return ResultEnum.FAIL.id();
        }
        if (idList.size() == 1) {
            result = roleDao.removeById(idList.get(0));
        } else {
            result = roleDao.removeByIds(idList);
        }
        if (result) {
            return ResultEnum.SUCCESS.id();
        }
        return ResultEnum.FAIL.id();

    }


    /**
     * 通过主键ID更新对象角色表实现缓存和数据库更新的方法
     *
     * @param roleId
     * @return
     */
    public RoleResponseDto getRoleById(long roleId) {
        if (roleId < 0) {
            return null;
        }
        Role role = roleDao.getById(roleId);
        if (role == null) {
            return null;
        }
        RoleResponseDto roleResponseDto = RoleResponseDto.build().clone(role);

        return roleResponseDto;

    }


    /**
     * 通过分页获取Role信息实现查找缓存和数据库的方法
     *
     * @param basePage BasePage
     * @return
     * @author xinye
     * @date 2019-11-21 15:22:59
     */
    public List<RoleResponseDto> getRoleByPage( ResponseResultList<RoleResponseDto> responseResultList,BasePage basePage) {


        List<RoleResponseDto> resDtoList = new ArrayList<>();
        if (basePage == null) {
            return resDtoList;
        }
        //分页对象        PageHelper
        IPage<Role> iPage = new Page<>(basePage.getPageNo(), basePage.getRealPageSize());
        QueryWrapper<Role> queryWrapper = checkQueryCondition(basePage);
        IPage<Role> page = roleDao.page(iPage, queryWrapper);
        if (null == page) {
            return resDtoList;
        }

        List<Role> list = page.getRecords();
        ;
        if (null == list || list.isEmpty()) {
            return resDtoList;
        }
        list.forEach(role -> {
            RoleResponseDto roleResponseDto = RoleResponseDto.build().clone(role);

            resDtoList.add(roleResponseDto);
        });
        responseResultList.setTotal((int)iPage.getTotal());
        return resDtoList;
    }

    /**
     * 查询条件
     */
    public QueryWrapper<Role> checkQueryCondition(BasePage basePage) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        RoleRequestDto param = (RoleRequestDto) basePage.getParamObject();
        if (!StringUtils.isEmpty(param.getRoleName())) {
            queryWrapper.like("role_name", param.getRoleName());
        }
        if (param.getCreateDateBegin()!=null) {
            queryWrapper.ge("create_date",param.getCreateDateBegin());
        }
        if (param.getCreateDateEnd()!=null) {
            queryWrapper.le("create_date",param.getCreateDateEnd());
        }
        return queryWrapper;
    }

    /**
     * 通过分页获取Role信息实现查找缓存和数据库的方法
     *
     * @param basePage BasePage
     * @return
     * @author xinye
     * @date 2019-11-21 15:22:59
     */
    public ResponseResultList<RoleResponseDto> getRoleByNextPage(BasePage basePage) {
        ResponseResultList<RoleResponseDto> responseResultList = ResponseResultList.build();
        List<RoleResponseDto> list = this.getRoleByPage(responseResultList,basePage);
        if (null == list) {
            list = new ArrayList<>();
        }
        responseResultList.toList(list);
        return responseResultList;

    }

    public PageUtils queryPage(Map<String, Object> params) {
        IPage iPage = new Query<Role>().getPage(params);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        IPage<Role> page = roleDao.page(iPage, queryWrapper);
        return new PageUtils(page);
    }

    /**
     * 查询角色列表
     * @return
     */
    @Override
    public List<Role> list() {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        return roleDao.list(queryWrapper);
    }


    /**
     * 保存角色表同时更新数据库和缓存的实现方法
     *
     * @return
     */
    public Role setRole() {
        Role role = new Role();
        /**
         //role.setCreateDate (Date createDate);
         //role.setModifyDate (Date modifyDate);
         //role.setStatus (int status);
         //role.setSort (int sort);
         //role.setRoleName (String roleName);
         //role.setRoleCode (String roleCode);
         //role.setDescription (String description);
         **/

        return role;
    }

    public RoleResponseDto getRoleByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }

        BasePage basePage = BasePage.build().toPageNo(0).toPageSize(1);
        IPage<Role> iPage = new Page<>(basePage.getPageNo(), basePage.getRealPageSize());
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Role::getRoleCode, code);
        IPage<Role> page = roleDao.page(iPage, queryWrapper);
        if (null == page) {
            return null;
        }

        Role role = page.getRecords().get(0);
        if (role == null) {
            return null;
        }

        RoleResponseDto roleResponseDto = RoleResponseDto.build().clone(role);

        return roleResponseDto;

    }

    /**
     * 通过主键ID集合更新对象角色表实现缓存和数据库更新的方法
     * @param  sysRoleIds
     * @return
     */
    public  List<RoleResponseDto>  getSysRoleByIds(List sysRoleIds){
        if(CollectionUtil.isEmpty(sysRoleIds)){
            return null;
        }
        List<Role> sysRoleList = roleDao.getListByIds(sysRoleIds);
        if(sysRoleList == null){
            return null;
        }
        List<RoleResponseDto> roleDtos = new ArrayList<>();
        sysRoleList.forEach(s ->{
            RoleResponseDto sysRoleResponseDto = RoleResponseDto.build().clone(s);
            roleDtos.add(sysRoleResponseDto);
        });
        return roleDtos ;

    }
}
