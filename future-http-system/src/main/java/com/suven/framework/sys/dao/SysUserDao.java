package com.suven.framework.sys.dao;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suven.framework.core.db.ext.Query;
import com.suven.framework.core.mybatis.MyBatisBaseEntityDao;
import com.suven.framework.sys.dto.request.SysUserRequestDto;
import com.suven.framework.sys.dto.response.SysUserResponseDto;
import com.suven.framework.sys.entity.SysUser;
import com.suven.framework.sys.entity.SysUserRole;
import com.suven.framework.sys.mapper.SysPermissionMapper;
import com.suven.framework.sys.mapper.SysUserMapper;
import com.suven.framework.sys.mapper.SysUserRoleMapper;
import com.suven.framework.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service("sysUserDao")
public class SysUserDao extends MyBatisBaseEntityDao<SysUserMapper, SysUser> {


    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;


    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysUser> iPage = new Query<SysUser>().getPage(params);

        QueryWrapper<SysUser> query = new QueryWrapper<>();

        IPage<SysUser> page = this.page(iPage, query);

        return new PageUtils(page);
    }

    public SysUser getUserByName(String username) {
        return sysUserMapper.getUserByName(username);
    }


    public void addUserWithRole(SysUser user, String roles) {
        this.save(user);
        if (null != roles && !"".equals(roles)) {
            String[] arr = roles.split(",");
            for (String roleId : arr) {
                int roleid = Integer.parseInt(roleId);
                SysUserRole userRole = SysUserRole.build().toRoleId(roleid).toUserId(user.getId());
                sysUserRoleMapper.insert(userRole);
            }
        }
    }

    public void editUserWithRole(SysUser user, String roles) {
        this.updateById(user);
        //先删后加
        sysUserRoleMapper.delete(new QueryWrapper<SysUserRole>().lambda().eq(SysUserRole::getUserId, user.getId()));
        if (null != roles && !"".equals(roles)) {
            String[] arr = roles.split(",");
            for (String roleId : arr) {
                int roleid = Integer.parseInt(roleId);
                SysUserRole userRole = SysUserRole.build().toRoleId(roleid).toUserId(user.getId());
                sysUserRoleMapper.insert(userRole);
            }
        }
    }


    public List<String> getRole(String username) {
        return sysUserRoleMapper.getRoleByUserName(username);
    }

    /**
     * 通过用户名获取用户角色集合
     *
     * @param username 用户名
     * @return 角色集合
     */
    public Set<String> getUserRolesSet(String username) {
        // 查询用户拥有的角色集合
        List<String> roles = sysUserRoleMapper.getRoleByUserName(username);
//        log.info("-------通过数据库读取用户拥有的角色Rules------username： " + username + ",Roles size: " + (roles == null ? 0 : roles.size()));
        return new HashSet<>(roles);
    }


    // 根据部门Id查询
    public IPage<SysUser> getUserByDepId(Page<SysUser> page, long departId, String username) {
        return this.baseMapper.getUserByDepId(page, departId, username);
    }


    // 根据角色Id查询
    public IPage<SysUser> getUserByRoleId(Page<SysUser> page, long roleId, String username) {
        this.slaveDataSource();
        return this.baseMapper.getUserByRoleId(page, roleId, username);
    }


    public void updateUserDepart(String username, String orgCode) {
        baseMapper.updateUserDepart(username, orgCode);
    }


    public SysUser getUserByPhone(String phone) {
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.eq("phone",phone);
        return this.getOne(query,false);
    }


    public SysUser getUserByEmail(String email) {
        return this.baseMapper.getUserByEmail(email);
    }





    public IPage<SysUser> getUserByUserIds(Page<SysUser> page, List<Long> userIds, String userName) {
        this.slaveDataSource();
        return this.baseMapper.getUserByUserIds(page, userIds, userName);
    }

    public IPage<SysUserResponseDto> getPageList(Page<SysUserResponseDto> page, SysUserRequestDto userReqDto) {
        this.slaveDataSource();
        return this.baseMapper.getPageList(page,userReqDto);
    }
}