package com.suven.framework.sys.controller;


import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.sys.vo.DocumentConst;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.suven.framework.common.api.ApiDoc;
import com.suven.framework.common.data.BasePage;
import com.suven.framework.common.enums.TbStatusEnum;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.http.handler.OutputResponse;
import com.suven.framework.http.processor.url.SysURLCommand;
import com.suven.framework.sys.dto.request.SysPermissionRequestDto;
import com.suven.framework.sys.dto.response.SysPermissionDataRuleResponseDto;
import com.suven.framework.sys.dto.response.SysPermissionResponseDto;
import com.suven.framework.sys.entity.SysPermissionDataRule;
import com.suven.framework.sys.facade.SysPermissionFacade;
import com.suven.framework.sys.facade.SysRolePermissionFacade;
import com.suven.framework.sys.service.SysPermissionDataRuleService;
import com.suven.framework.sys.service.SysPermissionService;
import com.suven.framework.sys.service.SysRolePermissionService;
import com.suven.framework.sys.vo.request.SysPermissionRequestVo;
import com.suven.framework.sys.vo.request.SysRolePermissionSaveRequestVo;
import com.suven.framework.sys.vo.request.TokenRequestVo;
import com.suven.framework.sys.vo.response.SysPermissionResponseVo;
import com.suven.framework.sys.vo.response.SysPermissionTreeResponseVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author xxx.xxx
 * @version V1.0
 * ----------------------------------------------------------------------------
 * modifier    modifyTime                 comment
 * 菜单权限表
 * ----------------------------------------------------------------------------
 * @Title: SysPermissionWebController.java
 * @Description: 菜单权限表的控制服务类
 * @date 2019-10-18 12:35:25
 * @RequestMapping("/sys/sysPermission")
 */
@Controller
@ApiDoc(
        group = DocumentConst.SysApi.DOC_API_GROUP,
        groupDesc= DocumentConst.SysApi.DOC_API_DES,
        module = "菜单权限表模块", isApp = false
)
public class SysPermissionWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysPermissionFacade sysPermissionFacade;

    @Autowired
    private SysPermissionDataRuleService sysPermissionDataRuleService;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    @Autowired
    private SysRolePermissionFacade sysRolePermissionFacade;


    /**
     * 系统菜单列表(一级菜单)先查询一级菜单，当用户点击展开菜单时加载子菜单
     *
     * @return
     */
    @RequestMapping(value = SysURLCommand.sys_permission_get_system_menu_list, method = RequestMethod.GET)
    @RequiresPermissions("sys:sysPermission:list")
    public void getSystemMenuList(OutputResponse out, SysPermissionRequestVo sysPermissionRequestVo) {
        List<SysPermissionTreeResponseVo> resultList = sysPermissionFacade.getSysPermissionByList(sysPermissionRequestVo);
        out.write(resultList);
    }


    /**
     * 系统菜单列表(一级菜单)先查询一级菜单，当用户点击展开菜单时加载子菜单
     *
     * @return
     */
    @RequestMapping(value = SysURLCommand.sys_permission_get_tree_list, method = RequestMethod.GET)
    @RequiresPermissions("sys:sysPermission:treeList")
    public void queryTreeList(OutputResponse out) {
        Map<String, Object> map = sysPermissionFacade.queryTreeList();
        out.write(map);
    }


    /**
     * 系统菜单列表(一级菜单)先查询一级菜单，当用户点击展开菜单时加载子菜单
     *
     * @return
     */
    @RequestMapping(value = SysURLCommand.sys_permission_add, method = RequestMethod.POST)
    @RequiresPermissions("sys:sysPermission:add")
    public void addPermission(OutputResponse out, SysPermissionRequestVo requestVo) {
        boolean isFlag = sysPermissionFacade.addPermission(requestVo);
        out.write(isFlag);
    }

    /**
     * 系统菜单列表(一级菜单)先查询一级菜单，当用户点击展开菜单时加载子菜单
     *
     * @return
     */
    @RequestMapping(value = SysURLCommand.sys_permission_edit, method = RequestMethod.POST)
    @RequiresPermissions("sys:sysPermission:modify")
    public void editPermission(OutputResponse out, SysPermissionRequestVo requestVo) {
        boolean isFlag = sysPermissionFacade.editPermission(requestVo);
        out.write(isFlag);
    }


    @RequestMapping(value = SysURLCommand.sys_permission_get_submenu, method = RequestMethod.GET)
    @RequiresPermissions("sys:sysPermission:getSystemSubmenu")
    public void getSystemSubmenu(OutputResponse out, SysPermissionRequestVo requestVo) {
        List<SysPermissionTreeResponseVo> vos = sysPermissionFacade.getSystemSubmenu(requestVo);
        out.write(vos);
    }


    @RequestMapping(value = SysURLCommand.sys_permission_del_batch, method = RequestMethod.POST)
    @RequiresPermissions("sys:sysPermission:deleteBatch")
    public void deleteBatch(OutputResponse out, HttpRequestByIdListVo idListVo) {
        IResultCodeEnum sysMsgEnum = sysPermissionFacade.deleteBatch(idListVo);
        out.write(sysMsgEnum);
    }

    @RequestMapping(value = SysURLCommand.sys_sysPermission_role_save, method = RequestMethod.POST)
    @RequiresPermissions("sys:sysPermission:saveRolePermission")
    public void saveRolePermission(OutputResponse out, SysRolePermissionSaveRequestVo requestVo) {
        IResultCodeEnum sysMsgEnum = sysRolePermissionFacade.saveRolePermission(requestVo);
        out.write(sysMsgEnum);
    }


    /**
     * 查询角色授权
     *
     * @return
     */
    @RequestMapping(value = SysURLCommand.sys_sysPermission_get_role_permission, method = RequestMethod.GET)
    @RequiresPermissions("sys:sysPermission:queryRolePermission")
    public void queryRolePermission(OutputResponse out, SysRolePermissionSaveRequestVo requestVo) {
        List<String> list = sysRolePermissionFacade.queryRolePermission(requestVo);
        out.write(list);
    }


    @ApiDoc(
            value = "获取菜单权限表分页信息",
            request = SysPermissionRequestVo.class,
            response = SysPermissionResponseVo.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysPermission_list, method = RequestMethod.GET)
    @RequiresPermissions("sys:sysPermission:list")
    public void list(OutputResponse out, SysPermissionRequestVo sysPermissionRequestVo) {

        BasePage page = BasePage.build().toPageSize(sysPermissionRequestVo.getPageSize()).toPageNo(sysPermissionRequestVo.getPageNo());
        ResponseResultList<SysPermissionResponseDto> resultList = sysPermissionService.getSysPermissionByNextPage(page);
        if (null == resultList || resultList.getList().isEmpty()) {
            out.write(ResponseResultList.build());
            return;
        }
        List<SysPermissionResponseVo> listVo = new ArrayList<>();
        resultList.getList().forEach(e -> listVo.add(SysPermissionResponseVo.build().clone(e)));
        ResponseResultList result = ResponseResultList.build().toList(listVo)
                .toIsNextPage(resultList.getIsNextPage())
                .toPageIndex(resultList.getPageIndex());
        out.write(result);
    }

    /**
     * 查询用户拥有的菜单权限和按钮权限（根据TOKEN）
     *
     * @return
     */
    @RequestMapping(value = SysURLCommand.sys_sysPermission_get, method = RequestMethod.GET)
    @RequiresPermissions("sys:sysPermission:getByToken")
    public void getUserPermissionByToken(OutputResponse out, TokenRequestVo tokenRequestVo) {
        Object object = sysPermissionFacade.getUserPermissionByToken(tokenRequestVo);
        out.write(object);
    }


    @ApiDoc(
            value = "新增菜单权限表信息",
            request = SysPermissionRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysPermission_add, method = RequestMethod.POST)
    @RequiresPermissions("sys:sysPermission:add")
    public void add(OutputResponse out, SysPermissionRequestVo sysPermissionRequestVo) {

        SysPermissionRequestDto sysPermissionRequestDto = SysPermissionRequestDto.build().clone(sysPermissionRequestVo);

        sysPermissionRequestDto.setStatus(TbStatusEnum.ENABLE.index());
        SysPermissionResponseDto sysPermissionresponseDto = sysPermissionService.saveSysPermission(sysPermissionRequestDto);
        if (sysPermissionresponseDto == null) {
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write(sysPermissionresponseDto.getId());
    }


    @ApiDoc(
            value = "修改菜单权限表信息",
            request = SysPermissionRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysPermission_modify, method = RequestMethod.POST)
    @RequiresPermissions("sys:sysPermission:modify")
    public void modify(OutputResponse out, SysPermissionRequestVo sysPermissionRequestVo) {

        SysPermissionRequestDto sysPermissionRequestDto = SysPermissionRequestDto.build().clone(sysPermissionRequestVo);

        if (sysPermissionRequestDto.getId() == 0) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result = sysPermissionService.updateSysPermission(sysPermissionRequestDto);
        out.write(result);
    }


    @ApiDoc(
            value = "查看菜单权限表信息",
            request = HttpRequestByIdVo.class,
            response = SysPermissionResponseVo.class
    )
    @RequestMapping(value = SysURLCommand.sys_permission_detail, method = RequestMethod.GET)
    @RequiresPermissions("sys:sysPermission:detail")
    public void detail(OutputResponse out, HttpRequestByIdVo idRequestVo) {

        SysPermissionResponseDto sysPermissionResponseDto = sysPermissionService.getSysPermissionById(idRequestVo.getId());
        SysPermissionResponseVo vo = SysPermissionResponseVo.build().clone(sysPermissionResponseDto);
        out.write(vo);
    }


    @ApiDoc(
            value = "删除菜单权限表信息",
            request = HttpRequestByIdVo.class,
            response = Integer.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysPermission_del, method = RequestMethod.GET)
    @RequiresPermissions("sys:sysPermission:del")
    public void del(OutputResponse out, HttpRequestByIdListVo idRequestVo) {
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        int result = sysPermissionService.delSysPermissionByIds(idRequestVo.getIdList());
        out.write(result);
    }


    /**
     * 根据菜单id来获取其对应的权限数据
     *
     * @param sysPermissionDataRule
     * @return
     */
    @RequestMapping(value = SysURLCommand.sys_permission_get_perm_rule_list_byId, method = RequestMethod.GET)
    @RequiresPermissions("sys:sysPermission:getListById")
    public void getPermRuleListByPermId(OutputResponse out, SysPermissionDataRule sysPermissionDataRule) {
        List<SysPermissionDataRuleResponseDto> permRuleList = sysPermissionDataRuleService.getSysPermissionDataRuleById(sysPermissionDataRule.getPermissionId());
        ResponseResultList<SysPermissionDataRuleResponseDto> result = ResponseResultList.build().toList(permRuleList);
        out.write(result);
    }


}