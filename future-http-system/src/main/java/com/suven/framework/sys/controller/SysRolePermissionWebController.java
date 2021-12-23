package com.suven.framework.sys.controller;


import com.suven.framework.sys.vo.DocumentConst;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.suven.framework.common.api.ApiDoc;
import com.suven.framework.common.data.BasePage;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.common.enums.TbStatusEnum;
import com.suven.framework.common.excel.ColumnBox;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.http.handler.OutputResponse;
import com.suven.framework.http.processor.url.SysURLCommand;
import com.suven.framework.sys.dto.request.SysRolePermissionRequestDto;
import com.suven.framework.sys.dto.response.SysRolePermissionResponseDto;
import com.suven.framework.sys.service.SysRolePermissionService;
import com.suven.framework.sys.vo.request.SysRolePermissionRequestVo;
import com.suven.framework.sys.vo.response.SysRolePermissionResponseVo;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 * @author xxx.xxx
 * @version V1.0
 * ----------------------------------------------------------------------------
 * modifier    modifyTime                 comment
 * 角色权限表
 * ----------------------------------------------------------------------------
 * @Title: SysRolePermissionWebController.java
 * @Description: 角色权限表的控制服务类
 * @date 2019-10-18 12:35:25
 * @RequestMapping("/sys/sysRolePermission")
 */
@Controller
@ApiDoc(
        group = DocumentConst.SysApi.DOC_API_GROUP,
        groupDesc= DocumentConst.SysApi.DOC_API_DES,
        module = "角色权限表模块", isApp = false
)
public class SysRolePermissionWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private SysRolePermissionService sysRolePermissionService;

    /**
     * @return
     * @Title: 跳转到角色权限表主界面
     * @Description:sysRolePermissionRequestVo @{Link SysRolePermissionRequestVo}
     * @throw
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @RequestMapping(value = SysURLCommand.sys_sysRolePermission_index, method = RequestMethod.GET)
    @RequiresPermissions("sys:sysRolePermission:list")
    public String index() {
        return "sys/sysRolePermission_index";
    }


    /**
     * @param
     * @return
     * @Title: 获取角色权限表分页信息
     * @Description:sysRolePermissionRequestVo @{Link SysRolePermissionRequestVo}
     * @throw
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取角色权限表分页信息",
            request = SysRolePermissionRequestVo.class,
            response = SysRolePermissionResponseVo.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysRolePermission_list, method = RequestMethod.GET)
    @RequiresPermissions("sys:sysRolePermission:list")
    public void list(OutputResponse out, SysRolePermissionRequestVo sysRolePermissionRequestVo) {
        SysRolePermissionRequestDto sysRolePermissionRequestDto = SysRolePermissionRequestDto.build().clone(sysRolePermissionRequestVo);

        BasePage page = BasePage.build().toPageSize(sysRolePermissionRequestVo.getPageSize()).toPageNo(sysRolePermissionRequestVo.getPageNo());
        page.toParamObject(sysRolePermissionRequestDto);
        ResponseResultList<SysRolePermissionResponseDto> resultList = sysRolePermissionService.getSysRolePermissionByNextPage(page);
        if (null == resultList || resultList.getList().isEmpty()) {
            out.write(ResponseResultList.build());
            return;
        }
        List<SysRolePermissionResponseVo> listVo = new ArrayList<>();
        resultList.getList().forEach(e -> listVo.add(SysRolePermissionResponseVo.build().clone(e)));
        ResponseResultList result = ResponseResultList.build().toList(listVo)
                .toIsNextPage(resultList.getIsNextPage())
                .toPageIndex(resultList.getPageIndex());
        out.write(result);
    }


    /**
     * @param
     * @return
     * @Title: 新增角色权限表信息
     * @Description:sysRolePermissionRequestVo @{Link SysRolePermissionRequestVo}
     * @throw
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增角色权限表信息",
            request = SysRolePermissionRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysRolePermission_add, method = RequestMethod.POST)
    @RequiresPermissions("sys:sysRolePermission:add")
    public void add(OutputResponse out, SysRolePermissionRequestVo sysRolePermissionRequestVo) {

        SysRolePermissionRequestDto sysRolePermissionRequestDto = SysRolePermissionRequestDto.build().clone(sysRolePermissionRequestVo);

        sysRolePermissionRequestDto.setStatus(TbStatusEnum.ENABLE.index());
        SysRolePermissionResponseDto sysRolePermissionresponseDto = sysRolePermissionService.saveSysRolePermission(sysRolePermissionRequestDto);
        if (sysRolePermissionresponseDto == null) {
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write(sysRolePermissionresponseDto.getId());
    }

    /**
     * @param
     * @return
     * @Title: 修改角色权限表信息
     * @Description:sysRolePermissionRequestVo @{Link SysRolePermissionRequestVo}
     * @throw
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改角色权限表信息",
            request = SysRolePermissionRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysRolePermission_modify, method = RequestMethod.POST)
    @RequiresPermissions("sys:sysRolePermission:modify")
    public void modify(OutputResponse out, SysRolePermissionRequestVo sysRolePermissionRequestVo) {

        SysRolePermissionRequestDto sysRolePermissionRequestDto = SysRolePermissionRequestDto.build().clone(sysRolePermissionRequestVo);

        if (sysRolePermissionRequestDto.getId() == 0) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result = sysRolePermissionService.updateSysRolePermission(sysRolePermissionRequestDto);
        out.write(result);
    }

    /**
     * @param
     * @return
     * @Title: 查看角色权限表信息
     * @Description:sysRolePermissionRequestVo @{Link SysRolePermissionRequestVo}
     * @throw
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看角色权限表信息",
            request = HttpRequestByIdVo.class,
            response = SysRolePermissionResponseVo.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysRolePermission_detail, method = RequestMethod.GET)
    @RequiresPermissions("sys:sysRolePermission:detail")
    public void detail(OutputResponse out, HttpRequestByIdVo idRequestVo) {

        SysRolePermissionResponseDto sysRolePermissionResponseDto = sysRolePermissionService.getSysRolePermissionById(idRequestVo.getId());
        SysRolePermissionResponseVo vo = SysRolePermissionResponseVo.build().clone(sysRolePermissionResponseDto);
        out.write(vo);
    }


    /**
     * @param
     * @return
     * @Title: 跳转角色权限表编辑界面
     * @Description:id @{Link Long}
     * @throw
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @ApiDoc(
            value = "查看角色权限表信息",
            request = HttpRequestByIdVo.class,
            response = SysRolePermissionResponseVo.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysRolePermission_edit, method = RequestMethod.GET)
    @RequiresPermissions("sys:sysRolePermission:modify")
    public void edit(OutputResponse out, HttpRequestByIdVo idRequestVo) {

        SysRolePermissionResponseDto sysRolePermissionResponseDto = sysRolePermissionService.getSysRolePermissionById(idRequestVo.getId());
        SysRolePermissionResponseVo vo = SysRolePermissionResponseVo.build().clone(sysRolePermissionResponseDto);
        out.write(vo);

    }


    /**
     * @param
     * @return
     * @Title: 跳转角色权限表新增编辑界面
     * @Description:id @{Link Long}
     * @throw
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     * --------------------------------------------------------
     * modifyer    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @RequestMapping(value = SysURLCommand.sys_sysRolePermission_newInfo, method = RequestMethod.GET)
    @RequiresPermissions("sys:sysRolePermission:add")
    public String newInfo(ModelMap modelMap) {
        return "sys/sysRolePermission_edit";
    }

    /**
     * @param
     * @return
     * @Title: 删除角色权限表信息
     * @Description:id @{Link Long}
     * @throw
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @ApiDoc(
            value = "删除角色权限表信息",
            request = HttpRequestByIdVo.class,
            response = Integer.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysRolePermission_del, method = RequestMethod.GET)
    @RequiresPermissions("sys:sysRolePermission:del")
    public void del(OutputResponse out, HttpRequestByIdListVo idRequestVo) {
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        int result = sysRolePermissionService.delSysRolePermissionByIds(idRequestVo.getIdList());
        out.write(result);
    }


    /**
     * @param
     * @return
     * @Title: 导出角色权限表信息
     * @Description:id @{Link Long}
     * @throw
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @ApiDoc(
            value = "导出角色权限表信息",
            request = SysRolePermissionRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysRolePermission_export, method = RequestMethod.POST)
    @RequiresPermissions("sys:sysRolePermission:export")
    public void export(HttpServletResponse response, SysRolePermissionRequestVo sysRolePermissionRequestVo) {

        SysRolePermissionRequestDto sysRolePermissionRequestDto = SysRolePermissionRequestDto.build().clone(sysRolePermissionRequestVo);

        BasePage page = BasePage.build().toPageSize(sysRolePermissionRequestVo.getPageSize()).toPageNo(sysRolePermissionRequestVo.getPageNo());
        page.toParamObject(sysRolePermissionRequestDto);

        ResponseResultList<SysRolePermissionResponseDto> resultList = sysRolePermissionService.getSysRolePermissionByNextPage(page);
        List<SysRolePermissionResponseDto> data = resultList.getList();

        ColumnBox columnBox = ColumnBox.create();

//
//        try {
//           // ExportUtils.write(String.format("角色权限表_%s.xls",DateUtil.date("yyyy-MM-dd",new Date().getTime())), response, data, columnBox);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


}