package com.suven.framework.sys.controller;


import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.sys.vo.DocumentConst;
import com.suven.framework.sys.vo.response.SysUserResponseVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.databene.commons.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.suven.framework.common.api.ApiDoc;
import com.suven.framework.common.data.BasePage;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.http.handler.OutputResponse;
import com.suven.framework.http.processor.url.SysURLCommand;
import com.suven.framework.sys.dto.request.SysUserDepartRequestDto;
import com.suven.framework.sys.dto.request.SysUserRequestDto;
import com.suven.framework.sys.dto.response.RoleResponseDto;
import com.suven.framework.sys.dto.response.SysUserResponseDto;
import com.suven.framework.sys.entity.SysUserRole;
import com.suven.framework.sys.facade.SysUserFacade;
import com.suven.framework.sys.service.RoleService;
import com.suven.framework.sys.service.SysUserDepartService;
import com.suven.framework.sys.service.SysUserRoleService;
import com.suven.framework.sys.service.SysUserService;
import com.suven.framework.sys.vo.request.*;
import com.suven.framework.sys.vo.response.SysDepartResponseVo;
import com.suven.framework.util.crypt.CryptUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * @author xxx.xxx
 * @version V1.0
 * ----------------------------------------------------------------------------
 * modifier    modifyTime                 comment
 * 用户表
 * ----------------------------------------------------------------------------
 * @Title: SysUserWebController.java
 * @Description: 用户表的控制服务类
 * @date 2019-10-18 12:35:25
 * @RequestMapping("/sys/user")
 */
@Controller
@ApiDoc(
        group = DocumentConst.SysApi.DOC_API_GROUP,
        groupDesc= DocumentConst.SysApi.DOC_API_DES,
        module = "用户表模块", isApp = false
)
public class SysUserWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private SysUserService userService;

    @Autowired
    private SysUserFacade userFacade;

    @Autowired
    private SysUserDepartService sysUserDepartService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RoleService sysRoleService;

    @Autowired
    private SysUserRoleService userRoleService;

    @ApiDoc(
            value = "获取用户表分页信息",
            request = SysUserRequestVo.class,
            response = SysUserResponseVo.class
    )
    @RequestMapping(value = SysURLCommand.sys_user_list, method = RequestMethod.GET)
    @RequiresPermissions("sys:user:list")
    public void list(OutputResponse out, SysUserRequestVo userRequestVo) {
        SysUserRequestDto userRequestDto = SysUserRequestDto.build().clone(userRequestVo);
        BasePage page = BasePage.build().toPageSize(userRequestVo.getPageSize()).toPageNo(userRequestVo.getPageNo());
        page.toParamObject(userRequestDto);
        ResponseResultList result = userFacade.getSysUserList(page);
        out.write(result);
    }

    @ApiDoc(
            value = "按条件查找用户表分页信息",
            request = SysUserRequestVo.class,
            response = SysUserResponseVo.class
    )
    @RequestMapping(value = SysURLCommand.sys_user_queryByUserName, method = RequestMethod.GET)
    @RequiresPermissions("sys:user:list")
    public void queryByUserNameList(OutputResponse out, SysUserRequestVo userRequestVo) {
        SysUserRequestDto userRequestDto = SysUserRequestDto.build().clone(userRequestVo);
        BasePage page = BasePage.build().toPageSize(userRequestVo.getPageSize()).toPageNo(userRequestVo.getPageNo());
        page.toParamObject(userRequestDto);
        ResponseResultList result = userFacade.getSysUserList(page);
        out.write(result);
    }

//    @ApiDoc(
//            value = "获取用户表分页信息",
//            request = SysUserRequestVo.class,
//            response = SysUserResponseVo.class
//    )
//    @RequestMapping(value = SysURLCommand.sys_user_list, method = RequestMethod.GET)
//    @RequiresPermissions("sys:user:list")
//    public void list(OutputResponse out, SysUserRequestVo userRequestVo) {
//        SysUserRequestDto userRequestDto = SysUserRequestDto.build().clone(userRequestVo);
//        BasePage page = BasePage.build().toPageSize(userRequestVo.getPageSize()).toPageNo(userRequestVo.getPageNo());
//        page.toParamObject(userRequestDto);
//        ResponseResultList result = userFacade.getSysUserList(page);
//        out.write(result);
//    }


    @ApiDoc(
            value = "修改用户表信息",
            request = SysUserRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = SysURLCommand.sys_user_modify, method = RequestMethod.POST)
    @RequiresPermissions("sys:user:modify")
    public void modify(OutputResponse out, SysUserRequestVo userRequestVo) {

        /*SysUserRequestDto userRequestDto = SysUserRequestDto.build().clone(userRequestVo);

        if (userRequestVo.getBirthdayDate() != null) {
            userRequestDto.setBirthday(userRequestVo.getBirthdayDate().getTime());
        }

        if (StringUtils.isNotBlank(userRequestDto.getPassword())) {
            userRequestDto.setPassword(CryptUtil.md5(userRequestDto.getPassword()));
        }
        if (userRequestDto.getId() == 0) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }

        if (StringUtils.isNotBlank(userRequestDto.getPhone())) {
            SysUserResponseDto sysUserResponseDto = sysUserService.getUserByPhone(userRequestDto.getPhone());
            if (sysUserResponseDto != null && sysUserResponseDto.getId() != userRequestDto.getId()) {
                out.write(UserMsgCodeEnum.USER_PHONE_EXIST);
                return;
            }
        }

        if (!CollectionUtil.isEmpty(userRequestVo.getRoleIds())) {
            userRoleService.editRole(userRequestDto.getId(), userRequestVo.getRoleIds());
        } else {
            userRoleService.deleteAll(userRequestDto.getId());
        }

        SysRoleResponseDto sysRoleResponseDto = sysRoleService.getSysRoleById(userRequestVo.getRoleIds().get(0));
        if (sysRoleResponseDto != null && "00002".equals(sysRoleResponseDto.getRoleCode())) {
            userRequestDto.setIsModerator(1);
        }

        boolean result = userService.updateSysUser(userRequestDto);
        out.write(result);*/

        SysUserRequestDto userRequestDto = SysUserRequestDto.build().clone(userRequestVo);
        userRequestDto.setBirthday(userRequestVo.getBirthdayDate());
        if (userRequestDto.getId() == 0) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        if (!CollectionUtil.isEmpty(userRequestVo.getRoleIds())) {
            userRoleService.editRole(userRequestDto.getId(), userRequestVo.getRoleIds());
        } else {
            userRoleService.deleteAll(userRequestDto.getId());
        }
        boolean result = userService.updateSysUser(userRequestDto);
        out.write(result);
    }


    @ApiDoc(
            value = "启用,上架用户表信息",
            request = HttpRequestByIdVo.class,
            response = boolean.class
    )
    @RequestMapping(value = SysURLCommand.sys_user_turnOn, method = RequestMethod.GET)
    @RequiresPermissions("sys:user:turnOn")
    public void turnOn(OutputResponse out, HttpRequestByIdListVo idRequestVo) {
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result = userService.turnOn(idRequestVo.getIdList());
        out.write(result);
    }


    @ApiDoc(
            value = "下架用户表信息",
            request = HttpRequestByIdVo.class,
            response = boolean.class
    )
    @RequestMapping(value = SysURLCommand.sys_user_turnOff, method = RequestMethod.GET)
    @RequiresPermissions("sys:user:turnOff")
    public void turnOff(OutputResponse out, HttpRequestByIdListVo idRequestVo) {
        if (idRequestVo == null || idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result = userService.turnOff(idRequestVo.getIdList());
        out.write(result);
    }


    /**
     * 查询用户角色信息
     */
    @RequestMapping(value = SysURLCommand.sys_user_role, method = RequestMethod.GET)
    @RequiresPermissions("sys:user:queryUserRole")
    public void queryUserRole(OutputResponse out, @RequestParam(name = "userid", required = true) String userIdStr) {
        /*if (StringUtils.isEmpty(userIdStr)) {
            out.write(SysResultCodeEnum.SYS_PARAM_CHECK);
            return;
        }
        long userId = Long.valueOf(userIdStr);
        List<SysUserRole> userRoles = userService.queryUserRole(userId);
        if (CollectionUtil.isEmpty(userRoles)) {
            out.write(SysResultCodeEnum.SYS_USER_ROLE_FIND_FAIL);
            return;
        }
        List<Long> roleIds = new ArrayList<>();
        userRoles.forEach(u -> {
            roleIds.add(u.getRoleId());
        });
        //查询用户角色信息
        List<SysRoleResponseDto> roleList = sysRoleService.getSysRoleByIds(roleIds);
        List<String> roleNames = new ArrayList<>();
        roleList.forEach(r -> {
            roleNames.add(r.getRoleName());
        });
        ResponseResultList resultList = ResponseResultList.build();
        resultList.toList(roleNames);
        out.write(resultList);*/
        if (StringUtils.isEmpty(userIdStr)) {
            out.write(SysResultCodeEnum.SYS_PARAM_CHECK);
            return;
        }
        long userId = Long.valueOf(userIdStr);
        List<SysUserRole> userRoles = userService.queryUserRole(userId);
        if (CollectionUtil.isEmpty(userRoles)) {
            out.write(SysResultCodeEnum.SYS_USER_ROLE_FIND_FAIL);
            return;
        }
        List<Long> roleIds = new ArrayList<>();
        userRoles.forEach(u -> {
            roleIds.add(u.getRoleId());
        });
        //查询用户角色信息
        List<RoleResponseDto> roleList = sysRoleService.getSysRoleByIds(roleIds);
        List<String> roleNames = new ArrayList<>();
        roleList.forEach(r -> {
            roleNames.add(r.getRoleName());
        });
        ResponseResultList resultList = ResponseResultList.build();
        resultList.toList(roleNames);
        out.write(resultList);

    }

    /**
     * 查询用户部门信息
     *
     * @param out
     * @param userDepartRequestVo
     */
    @RequestMapping(value = SysURLCommand.sys_user_depart, method = RequestMethod.GET)
    @RequiresPermissions("sys:user:getUserDepartList")
    public void getUserDepartList(OutputResponse out, SysUserDepartRequestVo userDepartRequestVo) {
        BasePage page = BasePage.build().toPageSize(userDepartRequestVo.getPageSize()).toPageNo(userDepartRequestVo.getPageNo());
        ResponseResultList<SysUserResponseDto> list = sysUserService.getUserByDepIdPage(page, userDepartRequestVo.getDepId(), userDepartRequestVo.getUsername());
        out.write(list);
    }

    /**
     * 查询角色用户信息
     *
     * @param out
     * @param sysUserRoleRequestVo
     */
    @RequestMapping(value = SysURLCommand.sys_user_userRoleList, method = RequestMethod.GET)
    @RequiresPermissions("sys:user:userRoleList")
    public void userRoleList(OutputResponse out, SysUserRoleRequestVo sysUserRoleRequestVo) {
        BasePage page = BasePage.build().toPageSize(sysUserRoleRequestVo.getPageSize()).toPageNo(sysUserRoleRequestVo.getPageNo());
        ResponseResultList<SysUserResponseDto> dtos = sysUserService.getSysUserRoleId(page, sysUserRoleRequestVo.getRoleId(), sysUserRoleRequestVo.getUsername());
        out.write(dtos);
    }


    /**
     * 查询角色用户信息
     *
     * @param out
     * @param userDepartRequestVo
     */
    @RequestMapping(value = SysURLCommand.sys_user_addSysUserRole, method = RequestMethod.POST)
    @RequiresPermissions("sys:user:addSysUserRole")
    public void addSysUserRole(OutputResponse out, SysUserRoleRequestVo userDepartRequestVo, HttpRequestByIdListVo idListVo) {
        Boolean isFlag = userFacade.addSysUserRole(userDepartRequestVo, idListVo);
        out.write(isFlag);
    }

    /**
     * 删除角色用户
     *
     * @param out
     * @param userDepartRequestVo
     */
    @RequestMapping(value = SysURLCommand.sys_user_deleteUserRoleBatch, method = RequestMethod.POST)
    @RequiresPermissions("sys:user:deleteUserRole")
    public void deleteUserRole(OutputResponse out, SysUserRoleRequestVo userDepartRequestVo, HttpRequestByIdListVo idListVo) {
        Boolean isFlag = userFacade.deleteUserRole(userDepartRequestVo, idListVo);
        out.write(isFlag);
    }


    /**
     * 删除用户部门
     */
    @RequestMapping(value = SysURLCommand.sys_user_del_depart, method = RequestMethod.POST)
    @RequiresPermissions("sys:user:deleteUserInDepart")
    public void deleteUserInDepart(OutputResponse out, SysUserDepartRequestVo userDepartRequestVo, HttpRequestByIdListVo idListVo) {
        Boolean isFlag = userFacade.deleteUserInDepart(userDepartRequestVo, idListVo);
        out.write(isFlag);
    }

    /**
     * 添加部门人员
     */
    @RequestMapping(value = SysURLCommand.sys_user_editSysDepart, method = RequestMethod.POST)
    @RequiresPermissions("sys:user:editSysDepartWithUser")
    public void editSysDepartWithUser(OutputResponse out, SysUserDepartRequestVo userDepartRequestVo, HttpRequestByIdListVo idListVo) {
        SysUserDepartRequestDto dto = SysUserDepartRequestDto.build().setUserIdList(idListVo.getIdList()).clone(userDepartRequestVo);
        Boolean isFlag = sysUserDepartService.editSysDepartWithUser(dto);
        out.write(isFlag);
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = SysURLCommand.sys_user_updatePassword, method = RequestMethod.POST)
    @RequiresPermissions("sys:user:updatePassword")
    public void updatePassword(OutputResponse out, SysUserUpdatePwdRequestVo userUpdatePwdRequestVo) {
        IResultCodeEnum msgEnum = userFacade.updatePassword(userUpdatePwdRequestVo);
        out.write(msgEnum);
    }


    /**
     * 用户部门列表by用户id
     *
     * @param out
     * @param userIdStr
     */
    @RequestMapping(value = "/sys/user/userDepartList", method = RequestMethod.GET)
    @RequiresPermissions("sys:user:getUserDepartList")
    public void getUserDepartList(OutputResponse out, @RequestParam(name = "userId", required = true) String userIdStr) {
        if (StringUtils.isEmpty(userIdStr)) {
            out.write(SysResultCodeEnum.SYS_PARAM_CHECK);
            return;
        }
        long userId = Long.valueOf(userIdStr);
        ResponseResultList result = ResponseResultList.build();
        List<SysDepartResponseVo.DepartTreeRespVo> departList = userFacade.getUserDepartList(userId);
        if (CollectionUtil.isEmpty(departList)) {
            out.write(SysResultCodeEnum.SYS_USER_DEPART_FIND_FAIL);
            return;
        }
        result.toList(departList);
        out.write(result);
    }


    @ApiDoc(
            value = "冻结用户",
            request = HttpRequestByIdVo.class,
            response = boolean.class
    )
    @RequestMapping(value = SysURLCommand.sys_user_frozen_batch, method = RequestMethod.GET)
    @RequiresPermissions("sys:user:frozenBatch")
    public void frozenBatch(OutputResponse out, HttpRequestByIdListVo idRequestVo) {
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result = userService.frozenBatch(idRequestVo.getIdList());
        out.write(result);
    }

    @ApiDoc(
            value = "禁言(解禁)用户",
            request = AllStatusRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = SysURLCommand.sys_user_handle_muted, method = RequestMethod.POST)
    @RequiresPermissions("sys:user:handleMuted")
    public void handleMuted(OutputResponse out, AllStatusRequestVo statusReqVo) {
        if (statusReqVo.getId() <= 0) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result = userService.handleMuted(statusReqVo);
        out.write(result);
    }


    private static String coverPassword(String sourcePassword) {
        String decryptPassword = CryptUtil.decryptPassword(sourcePassword);
        String password = CryptUtil.md5(decryptPassword);
        return password;
    }


}