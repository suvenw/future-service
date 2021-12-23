package com.suven.framework.sys.controller;


import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.core.db.IterableConverter;
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
import com.suven.framework.sys.dto.request.RoleRequestDto;
import com.suven.framework.sys.dto.response.RoleResponseDto;
import com.suven.framework.sys.entity.Role;
import com.suven.framework.sys.facade.RoleFacade;
import com.suven.framework.sys.service.RoleService;
import com.suven.framework.sys.service.SysPermissionService;
import com.suven.framework.sys.vo.request.RoleRequestVo;
import com.suven.framework.sys.vo.response.RoleResponseVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author xinye
 * @version V1.0
 * ----------------------------------------------------------------------------
 * modifier    modifyTime                 comment
 * 角色表
 * ----------------------------------------------------------------------------
 * @Title: RoleWebController.java
 * @Description: 角色表的控制服务类
 * @date 2019-11-21 15:22:59
 * @RequestMapping("/sys/role")
 */
@Controller
@ApiDoc(
        group = DocumentConst.SysApi.DOC_API_GROUP,
        groupDesc= DocumentConst.SysApi.DOC_API_DES,
        module = "角色表模块", isApp = false
)
public class RoleWebController {


    private final Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    private RoleService roleService;
    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private RoleFacade sysRoleFacade;

    @ApiDoc(
            value = "获取角色表分页信息",
            request = RoleRequestVo.RolePageListReqVo.class,
            response = RoleResponseVo.class
    )
    @RequestMapping(value = SysURLCommand.sys_role_list, method = RequestMethod.GET)
    @RequiresPermissions("sys:role:list")
    public void list(OutputResponse out, RoleRequestVo.RolePageListReqVo roleRequestVo) {
        RoleRequestDto roleRequestDto = RoleRequestDto.build().clone(roleRequestVo);
        roleRequestDto.setCreateDateBegin(roleRequestVo.getCreateDateBegin());
        roleRequestDto.setCreateDateEnd(roleRequestVo.getCreateDateEnd());
        BasePage page = BasePage.build().toPageSize(roleRequestVo.getPageSize()).toPageNo(roleRequestVo.getPageNo());
        page.toParamObject(roleRequestDto);
        ResponseResultList<RoleResponseDto> resultList = roleService.getRoleByNextPage(page);
        if (null == resultList || resultList.getList().isEmpty()) {
            out.write(ResponseResultList.build());
            return;
        }
        List<RoleResponseVo> listVo = IterableConverter.convertList(resultList.getList(),RoleResponseVo.class);

        ResponseResultList result = ResponseResultList.build().setResult(listVo,page.getSize(),resultList.getTotal())
                .toPageIndex(resultList.getPageIndex())
                ;
        out.write(result);
    }


    @ApiDoc(
            value = "新增角色表信息",
            request = RoleRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = SysURLCommand.sys_role_add, method = RequestMethod.POST)
    @RequiresPermissions("sys:role:add")
    public void add(OutputResponse out, RoleRequestVo roleRequestVo) {

        RoleRequestDto roleRequestDto = RoleRequestDto.build().clone(roleRequestVo);

        roleRequestDto.setStatus(TbStatusEnum.ENABLE.index());
        RoleResponseDto roleresponseDto = roleService.saveRole(roleRequestDto);
        if (roleresponseDto == null) {
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write(roleresponseDto.getId());
    }


    @ApiDoc(
            value = "修改角色表信息",
            request = RoleRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = SysURLCommand.sys_role_modify, method = RequestMethod.POST)
    @RequiresPermissions("sys:role:modify")
    public void modify(OutputResponse out, RoleRequestVo roleRequestVo) {
        RoleRequestDto roleRequestDto = RoleRequestDto.build().clone(roleRequestVo);
        if (roleRequestDto.getId() == 0) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result = roleService.updateRole(roleRequestDto);
        out.write(result);
    }


    @ApiDoc(
            value = "查看角色表信息",
            request = HttpRequestByIdVo.class,
            response = RoleResponseVo.class
    )
    @RequestMapping(value = SysURLCommand.sys_role_detail, method = RequestMethod.GET)
    @RequiresPermissions("sys:role:detail")
    public void detail(OutputResponse out, HttpRequestByIdVo idRequestVo) {

        RoleResponseDto roleResponseDto = roleService.getRoleById(idRequestVo.getId());
        RoleResponseVo vo = RoleResponseVo.build().clone(roleResponseDto);
        out.write(vo);
    }


    /**
     * @param
     * @return
     * @Title: 删除角色表信息
     * @Description:id @{Link Long}
     * @throw
     * @author xinye
     * @date 2019-11-21 15:22:59
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @ApiDoc(
            value = "删除角色表信息",
            request = HttpRequestByIdVo.class,
            response = Integer.class
    )
    @RequestMapping(value = SysURLCommand.sys_role_del, method = RequestMethod.POST)
    @RequiresPermissions("sys:role:del")
    public void del(OutputResponse out, HttpRequestByIdListVo idRequestVo) {
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        int result = roleService.delRoleByIds(idRequestVo.getIdList());
        out.write(result);
    }

    /**
     * 用户角色授权功能，查询菜单权限树
     *
     * @param
     * @return
     */

    @RequestMapping(value = SysURLCommand.sys_role_query_tree_list, method = RequestMethod.GET)
    @RequiresPermissions("sys:role:queryTreeList")
    public void queryTreeList(OutputResponse out) {
        Map<String, Object> map = sysRoleFacade.queryTreeList();
        out.write(map);
    }


    /**
     * 查询角色列表
     *
     * @param out
     */
    @RequestMapping(value = "/sys/role/queryall", method = RequestMethod.GET)
    @RequiresPermissions("sys:role:queryall")
    public void queryall(OutputResponse out) {
        ResponseResultList result = ResponseResultList.build();
        List<Role> roleList = roleService.list();
        if (roleList == null || roleList.size() <= 0) {
            out.write(SysResultCodeEnum.SYS_USER_FIND_FAIL);
            return;
        }
        result.toList(roleList);
        out.write(result);
    }


}