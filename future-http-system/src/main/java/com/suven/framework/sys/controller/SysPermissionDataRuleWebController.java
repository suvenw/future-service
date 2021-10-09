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
import com.suven.framework.sys.service.SysPermissionDataRuleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.suven.framework.sys.dto.request.SysPermissionDataRuleRequestDto;
import com.suven.framework.sys.dto.response.SysPermissionDataRuleResponseDto;
import com.suven.framework.sys.vo.request.SysPermissionDataRuleRequestVo;
import com.suven.framework.sys.vo.response.SysPermissionDataRuleResponseVo;

import java.util.ArrayList;
import java.util.List;


/**
 * @author xxx.xxx
 * @version V1.0
 * ----------------------------------------------------------------------------
 * modifier    modifyTime                 comment
 * <p>
 * ----------------------------------------------------------------------------
 * @Title: SysPermissionDataRuleWebController.java
 * @Description: 的控制服务类
 * @date 2019-11-25 19:45:26
 * @RequestMapping("/sys/sysPermissionDataRule")
 */
@Controller
@ApiDoc(
        group = DocumentConst.SysApi.DOC_API_GROUP,
        groupDesc= DocumentConst.SysApi.DOC_API_DES,
        module = "权限模块", isApp = false
)
public class SysPermissionDataRuleWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private SysPermissionDataRuleService sysPermissionDataRuleService;

    /**
     * @return
     * @Title: 跳转到主界面
     * @Description:sysPermissionDataRuleRequestVo @{Link SysPermissionDataRuleRequestVo}
     * @throw
     * @author xxx.xxx
     * @date 2019-11-25 19:45:26
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @RequestMapping(value = SysURLCommand.sys_sysPermissionDataRule_index, method = RequestMethod.GET)
    @RequiresPermissions("sys:permissionDataRule:list")
    public String index() {
        return "sys/sysPermissionDataRule_index";
    }


    /**
     * @param
     * @return
     * @Title: 获取分页信息
     * @Description:sysPermissionDataRuleRequestVo @{Link SysPermissionDataRuleRequestVo}
     * @throw
     * @author xxx.xxx
     * @date 2019-11-25 19:45:26
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取分页信息",
            request = SysPermissionDataRuleRequestVo.class,
            response = SysPermissionDataRuleResponseVo.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysPermissionDataRule_list, method = RequestMethod.GET)
    @RequiresPermissions("sys:permissionDataRule:list")
    public void list(OutputResponse out, SysPermissionDataRuleRequestVo sysPermissionDataRuleRequestVo) {
        SysPermissionDataRuleRequestDto sysPermissionDataRuleRequestDto = SysPermissionDataRuleRequestDto.build().clone(sysPermissionDataRuleRequestVo);

        BasePage page = BasePage.build().toPageSize(sysPermissionDataRuleRequestVo.getPageSize()).toPageNo(sysPermissionDataRuleRequestVo.getPageNo());
        page.toParamObject(sysPermissionDataRuleRequestDto);
        ResponseResultList<SysPermissionDataRuleResponseDto> resultList = sysPermissionDataRuleService.getSysPermissionDataRuleByNextPage(page);
        if (null == resultList || resultList.getList().isEmpty()) {
            out.write(ResponseResultList.build());
            return;
        }
        List<SysPermissionDataRuleResponseVo> listVo = new ArrayList<>();
        resultList.getList().forEach(e -> listVo.add(SysPermissionDataRuleResponseVo.build().clone(e)));
        ResponseResultList result = ResponseResultList.build().toList(listVo)
                .toIsNextPage(resultList.getIsNextPage())
                .toPageIndex(resultList.getPageIndex());
        out.write(result);
    }


    /**
     * @param
     * @return
     * @Title: 新增信息
     * @Description:sysPermissionDataRuleRequestVo @{Link SysPermissionDataRuleRequestVo}
     * @throw
     * @author xxx.xxx
     * @date 2019-11-25 19:45:26
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增信息",
            request = SysPermissionDataRuleRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysPermissionDataRule_add, method = RequestMethod.POST)
    @RequiresPermissions("sys:permissionDataRule:add")
    public void add(OutputResponse out, SysPermissionDataRuleRequestVo sysPermissionDataRuleRequestVo) {

        SysPermissionDataRuleRequestDto sysPermissionDataRuleRequestDto = SysPermissionDataRuleRequestDto.build().clone(sysPermissionDataRuleRequestVo);

        sysPermissionDataRuleRequestDto.setStatus(TbStatusEnum.ENABLE.index());
        SysPermissionDataRuleResponseDto sysPermissionDataRuleresponseDto = sysPermissionDataRuleService.saveSysPermissionDataRule(sysPermissionDataRuleRequestDto);
        if (sysPermissionDataRuleresponseDto == null) {
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write(sysPermissionDataRuleresponseDto.getId());
    }

    /**
     * @param
     * @return
     * @Title: 修改信息
     * @Description:sysPermissionDataRuleRequestVo @{Link SysPermissionDataRuleRequestVo}
     * @throw
     * @author xxx.xxx
     * @date 2019-11-25 19:45:26
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改信息",
            request = SysPermissionDataRuleRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysPermissionDataRule_modify, method = RequestMethod.POST)
    @RequiresPermissions("sys:permissionDataRule:modify")
    public void modify(OutputResponse out, SysPermissionDataRuleRequestVo sysPermissionDataRuleRequestVo) {

        SysPermissionDataRuleRequestDto sysPermissionDataRuleRequestDto = SysPermissionDataRuleRequestDto.build().clone(sysPermissionDataRuleRequestVo);

        if (sysPermissionDataRuleRequestDto.getId() == 0) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result = sysPermissionDataRuleService.updateSysPermissionDataRule(sysPermissionDataRuleRequestDto);
        out.write(result);
    }

    /**
     * @param
     * @return
     * @Title: 删除信息
     * @Description:id @{Link Long}
     * @throw
     * @author xxx.xxx
     * @date 2019-11-25 19:45:26
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @ApiDoc(
            value = "删除信息",
            request = HttpRequestByIdVo.class,
            response = Integer.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysPermissionDataRule_del, method = RequestMethod.GET)
    @RequiresPermissions("sys:permissionDataRule:del")
    public void del(OutputResponse out, HttpRequestByIdListVo idRequestVo) {
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        int result = sysPermissionDataRuleService.delSysPermissionDataRuleByIds(idRequestVo.getIdList());
        out.write(result);
    }


}