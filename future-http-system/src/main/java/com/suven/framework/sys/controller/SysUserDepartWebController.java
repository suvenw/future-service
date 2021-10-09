package com.suven.framework.sys.controller;


import com.suven.framework.sys.vo.DocumentConst;
import com.suven.framework.common.api.ApiDoc;
import com.suven.framework.common.data.BasePage;
import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.common.enums.TbStatusEnum;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.http.handler.OutputResponse;
import com.suven.framework.http.processor.url.SysURLCommand;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.suven.framework.sys.dto.request.SysUserDepartRequestDto;
import com.suven.framework.sys.dto.response.SysUserDepartResponseDto;
import com.suven.framework.sys.service.SysUserDepartService;
import com.suven.framework.sys.vo.request.SysUserDepartRequestVo;
import com.suven.framework.sys.vo.response.SysUserDepartResponseVo;

import java.util.ArrayList;
import java.util.List;


/**
 * @author xxx.xxx
 * @version V1.0
 * ----------------------------------------------------------------------------
 * modifier    modifyTime                 comment
 * 用户部门表
 * ----------------------------------------------------------------------------
 * @Title: SysUserDepartWebController.java
 * @Description: 用户部门表的控制服务类
 * @date 2019-11-27 17:49:58
 * @RequestMapping("/sys/sysUserDepart")
 */
@Controller
@ApiDoc(
        group = DocumentConst.SysApi.DOC_API_GROUP,
        groupDesc= DocumentConst.SysApi.DOC_API_DES,
        module = "用户部门表模块", isApp = false
)
public class SysUserDepartWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private SysUserDepartService sysUserDepartService;

    /**
     * @return
     * @Title: 跳转到用户部门表主界面
     * @Description:sysUserDepartRequestVo @{Link SysUserDepartRequestVo}
     * @throw
     * @author xxx.xxx
     * @date 2019-11-27 17:49:58
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @RequestMapping(value = SysURLCommand.sys_sysUserDepart_index, method = RequestMethod.GET)
    @RequiresPermissions("sys:userDepart:list")
    public String index() {
        return "sys/sysUserDepart_index";
    }


    /**
     * @param
     * @return
     * @Title: 获取用户部门表分页信息
     * @Description:sysUserDepartRequestVo @{Link SysUserDepartRequestVo}
     * @throw
     * @author xxx.xxx
     * @date 2019-11-27 17:49:58
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取用户部门表分页信息",
            request = SysUserDepartRequestVo.class,
            response = SysUserDepartResponseVo.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysUserDepart_list, method = RequestMethod.GET)
    @RequiresPermissions("sys:userDepart:list")
    public void list(OutputResponse out, SysUserDepartRequestVo sysUserDepartRequestVo) {
        SysUserDepartRequestDto sysUserDepartRequestDto = SysUserDepartRequestDto.build().clone(sysUserDepartRequestVo);

        BasePage page = BasePage.build().toPageSize(sysUserDepartRequestVo.getPageSize()).toPageNo(sysUserDepartRequestVo.getPageNo());
        page.toParamObject(sysUserDepartRequestDto);
        ResponseResultList<SysUserDepartResponseDto> resultList = sysUserDepartService.getSysUserDepartByNextPage(page);
        if (null == resultList || resultList.getList().isEmpty()) {
            out.write(ResponseResultList.build());
            return;
        }
        List<SysUserDepartResponseVo> listVo = new ArrayList<>();
        resultList.getList().forEach(e -> listVo.add(SysUserDepartResponseVo.build().clone(e)));
        ResponseResultList result = ResponseResultList.build().toList(listVo)
                .toIsNextPage(resultList.getIsNextPage())
                .toPageIndex(resultList.getPageIndex());
        out.write(result);
    }


    /**
     * @param
     * @return
     * @Title: 新增用户部门表信息
     * @Description:sysUserDepartRequestVo @{Link SysUserDepartRequestVo}
     * @throw
     * @author xxx.xxx
     * @date 2019-11-27 17:49:58
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增用户部门表信息",
            request = SysUserDepartRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysUserDepart_add, method = RequestMethod.POST)
    @RequiresPermissions("sys:userDepart:add")
    public void add(OutputResponse out, SysUserDepartRequestVo sysUserDepartRequestVo) {

        SysUserDepartRequestDto sysUserDepartRequestDto = SysUserDepartRequestDto.build().clone(sysUserDepartRequestVo);

        sysUserDepartRequestDto.setStatus(TbStatusEnum.ENABLE.index());
        SysUserDepartResponseDto sysUserDepartresponseDto = sysUserDepartService.saveSysUserDepart(sysUserDepartRequestDto);
        if (sysUserDepartresponseDto == null) {
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write(sysUserDepartresponseDto.getId());
    }

    /**
     * @param
     * @return
     * @Title: 修改用户部门表信息
     * @Description:sysUserDepartRequestVo @{Link SysUserDepartRequestVo}
     * @throw
     * @author xxx.xxx
     * @date 2019-11-27 17:49:58
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改用户部门表信息",
            request = SysUserDepartRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysUserDepart_modify, method = RequestMethod.POST)
    @RequiresPermissions("sys:userDepart:modify")
    public void modify(OutputResponse out, SysUserDepartRequestVo sysUserDepartRequestVo) {

        SysUserDepartRequestDto sysUserDepartRequestDto = SysUserDepartRequestDto.build().clone(sysUserDepartRequestVo);

        if (sysUserDepartRequestDto.getId() == 0) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result = sysUserDepartService.updateSysUserDepart(sysUserDepartRequestDto);
        out.write(result);
    }

    /**
     * @param
     * @return
     * @Title: 查看用户部门表信息
     * @Description:sysUserDepartRequestVo @{Link SysUserDepartRequestVo}
     * @throw
     * @author xxx.xxx
     * @date 2019-11-27 17:49:58
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看用户部门表信息",
            request = HttpRequestByIdVo.class,
            response = SysUserDepartResponseVo.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysUserDepart_detail, method = RequestMethod.GET)
    @RequiresPermissions("sys:userDepart:detail")
    public void detail(OutputResponse out, HttpRequestByIdVo idRequestVo) {

        SysUserDepartResponseDto sysUserDepartResponseDto = sysUserDepartService.getSysUserDepartById(idRequestVo.getId());
        SysUserDepartResponseVo vo = SysUserDepartResponseVo.build().clone(sysUserDepartResponseDto);
        out.write(vo);
    }


    /**
     * @param
     * @return
     * @Title: 跳转用户部门表编辑界面
     * @Description:id @{Link Long}
     * @throw
     * @author xxx.xxx
     * @date 2019-11-27 17:49:58
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @ApiDoc(
            value = "查看用户部门表信息",
            request = HttpRequestByIdVo.class,
            response = SysUserDepartResponseVo.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysUserDepart_edit, method = RequestMethod.GET)
    @RequiresPermissions("sys:userDepart:modify")
    public void edit(OutputResponse out, HttpRequestByIdVo idRequestVo) {

        SysUserDepartResponseDto sysUserDepartResponseDto = sysUserDepartService.getSysUserDepartById(idRequestVo.getId());
        SysUserDepartResponseVo vo = SysUserDepartResponseVo.build().clone(sysUserDepartResponseDto);
        out.write(vo);

    }

    @ApiDoc(
            value = "删除用户部门表信息",
            request = HttpRequestByIdVo.class,
            response = Integer.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysUserDepart_del, method = RequestMethod.GET)
    @RequiresPermissions("sys:userDepart:del")
    public void del(OutputResponse out, HttpRequestByIdListVo idRequestVo) {
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        int result = sysUserDepartService.delSysUserDepartByIds(idRequestVo.getIdList());
        out.write(result);
    }


}