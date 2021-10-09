package com.suven.framework.sys.controller;


import com.suven.framework.common.enums.SysResultCodeEnum;
import com.suven.framework.sys.vo.DocumentConst;
import com.suven.framework.common.api.ApiDoc;
import com.suven.framework.common.data.BasePage;
import com.suven.framework.common.enums.TbStatusEnum;
import com.suven.framework.common.excel.ColumnBox;
import com.suven.framework.http.data.vo.HttpRequestByIdListVo;
import com.suven.framework.http.data.vo.HttpRequestByIdVo;
import com.suven.framework.http.data.vo.HttpRequestSortByIdListVo;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.http.handler.OutputResponse;
import com.suven.framework.http.processor.url.SysURLCommand;
import com.suven.framework.sys.facade.SysDepartFacade;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.suven.framework.sys.dto.request.SysDepartRequestDto;
import com.suven.framework.sys.dto.response.SysDepartResponseDto;
import com.suven.framework.sys.service.SysDepartService;
import com.suven.framework.sys.vo.request.SysDepartRequestVo;
import com.suven.framework.sys.vo.response.DepartIdModelResponseVo;
import com.suven.framework.sys.vo.response.SysDepartResponseVo;
import com.suven.framework.sys.vo.response.SysDepartTreeModelResponseVo;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 * @author xxx.xxx
 * @version V1.0
 * ----------------------------------------------------------------------------
 * modifier    modifyTime                 comment
 * 部门组织机构表
 * ----------------------------------------------------------------------------
 * @Title: SysDepartWebController.java
 * @Description: 部门组织机构表的控制服务类
 * @date 2019-10-18 12:35:25
 * @RequestMapping("/sys/sysDepart")
 */
@Controller
@ApiDoc(
        group = DocumentConst.SysApi.DOC_API_GROUP,
        groupDesc= DocumentConst.SysApi.DOC_API_DES,
        module = "部门组织机构表模块", isApp = false
)
public class SysDepartWebController {


    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private SysDepartService sysDepartService;

    @Autowired
    private SysDepartFacade sysDepartFacade;


    /**
     * 查询数据 查出所有部门,并以树结构数据格式响应给前端
     *
     * @return
     */
    @RequestMapping(value = SysURLCommand.sys_sysDepart_treeList, method = RequestMethod.GET)
    @RequiresPermissions("sys:sysDepart:queryTreeList")
    public void queryTreeList(OutputResponse out) {
        List<SysDepartTreeModelResponseVo> list = sysDepartFacade.queryTreeList();
        out.write(list);
    }


    /**
     * 查询数据 添加或编辑页面对该方法发起请求,以树结构形式加载所有部门的名称,方便用户的操作
     *
     * @return
     */
    @RequestMapping(value = SysURLCommand.sys_sysDepart_treeIdList, method = RequestMethod.GET)
    @RequiresPermissions("sys:sysDepart:queryIdTree")
    public void queryIdTree(OutputResponse out) {
        List<DepartIdModelResponseVo> list = sysDepartFacade.queryDepartIdTreeList();
        out.write(list);
    }


    @RequestMapping(value = SysURLCommand.sys_sysDepart_searchBy, method = RequestMethod.GET)
    @RequiresPermissions("sys:sysDepart:searchBy")
    public void searchBy(OutputResponse out, String keyWord) {
        List<SysDepartTreeModelResponseVo> list = sysDepartFacade.searchBy(keyWord);
        out.write(list);
    }


    /**
     * @return
     * @Title: 跳转到部门组织机构表主界面
     * @Description:sysDepartRequestVo @{Link SysDepartRequestVo}
     * @throw
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @RequestMapping(value = SysURLCommand.sys_sysDepart_index, method = RequestMethod.GET)
    @RequiresPermissions("sys:sysDepart:list")
    public String index() {
        return "sys/sysDepart_index";
    }


    /**
     * @param
     * @return
     * @Title: 获取部门组织机构表分页信息
     * @Description:sysDepartRequestVo @{Link SysDepartRequestVo}
     * @throw
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @ApiDoc(
            value = "获取部门组织机构表分页信息",
            request = SysDepartRequestVo.class,
            response = SysDepartResponseVo.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysDepart_list, method = RequestMethod.GET)
    @RequiresPermissions("sys:sysDepart:list")
    public void list(OutputResponse out, SysDepartRequestVo sysDepartRequestVo) {
        SysDepartRequestDto sysDepartRequestDto = SysDepartRequestDto.build().clone(sysDepartRequestVo);

        BasePage page = BasePage.build().toPageSize(sysDepartRequestVo.getPageSize()).toPageNo(sysDepartRequestVo.getPageNo());
        page.toParamObject(sysDepartRequestDto);
        ResponseResultList<SysDepartResponseDto> resultList = sysDepartService.getSysDepartByNextPage(page);
        if (null == resultList || resultList.getList().isEmpty()) {
            out.write(ResponseResultList.build());
            return;
        }
        List<SysDepartResponseVo> listVo = new ArrayList<>();
        resultList.getList().forEach(e -> listVo.add(SysDepartResponseVo.build().clone(e)));
        ResponseResultList result = ResponseResultList.build().toList(listVo)
                .toIsNextPage(resultList.getIsNextPage())
                .toPageIndex(resultList.getPageIndex());
        out.write(result);
    }


    /**
     * @param
     * @return
     * @Title: 新增部门组织机构表信息
     * @Description:sysDepartRequestVo @{Link SysDepartRequestVo}
     * @throw
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @ApiDoc(
            value = "新增部门组织机构表信息",
            request = SysDepartRequestVo.class,
            response = Long.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysDepart_add, method = RequestMethod.POST)
    @RequiresPermissions("sys:sysDepart:add")
    public void add(OutputResponse out, SysDepartRequestVo sysDepartRequestVo) {
        SysDepartRequestDto sysDepartRequestDto = SysDepartRequestDto.build().clone(sysDepartRequestVo);
        sysDepartRequestDto.setStatus(TbStatusEnum.ENABLE.index());
        SysDepartResponseDto sysDepartresponseDto = sysDepartService.saveSysDepart(sysDepartRequestDto);
        if (sysDepartresponseDto == null) {
            out.write(SysResultCodeEnum.SYS_UNKOWNN_FAIL);
            return;
        }
        out.write(sysDepartresponseDto.getId());
    }

    /**
     * @param
     * @return
     * @Title: 修改部门组织机构表信息
     * @Description:sysDepartRequestVo @{Link SysDepartRequestVo}
     * @throw
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @ApiDoc(
            value = "修改部门组织机构表信息",
            request = SysDepartRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysDepart_edit, method = RequestMethod.POST)
    @RequiresPermissions("sys:sysDepart:modify")
    public void modify(OutputResponse out, SysDepartRequestVo sysDepartRequestVo) {
        SysDepartRequestDto sysDepartRequestDto = SysDepartRequestDto.build().clone(sysDepartRequestVo);
        if (sysDepartRequestDto.getId() == 0) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result = sysDepartService.updateSysDepart(sysDepartRequestDto);
        out.write(result);
    }

    /**
     * @param
     * @return
     * @Title: 查看部门组织机构表信息
     * @Description:sysDepartRequestVo @{Link SysDepartRequestVo}
     * @throw
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     * --------------------------------------------------------
     * modifier    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */

    @ApiDoc(
            value = "查看部门组织机构表信息",
            request = HttpRequestByIdVo.class,
            response = SysDepartResponseVo.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysDepart_detail, method = RequestMethod.GET)
    @RequiresPermissions("sys:sysDepart:detail")
    public void detail(OutputResponse out, HttpRequestByIdVo idRequestVo) {

        SysDepartResponseDto sysDepartResponseDto = sysDepartService.getSysDepartById(idRequestVo.getId());
        SysDepartResponseVo vo = SysDepartResponseVo.build().clone(sysDepartResponseDto);
        out.write(vo);
    }


    /**
     * @param
     * @return
     * @Title: 跳转部门组织机构表新增编辑界面
     * @Description:id @{Link Long}
     * @throw
     * @author xxx.xxx
     * @date 2019-10-18 12:35:25
     * --------------------------------------------------------
     * modifyer    modifyTime                 comment
     * <p>
     * --------------------------------------------------------
     */
    @RequestMapping(value = SysURLCommand.sys_sysDepart_newInfo, method = RequestMethod.GET)
    @RequiresPermissions("sys:sysDepart:newInfo")
    public String newInfo(ModelMap modelMap) {
        return "sys/sysDepart_edit";
    }

    /**
     * @param
     * @return
     * @Title: 删除部门组织机构表信息
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
            value = "删除部门组织机构表信息",
            request = HttpRequestByIdVo.class,
            response = Integer.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysDepart_del, method = RequestMethod.POST)
    @RequiresPermissions("sys:sysDepart:del")
    public void del(OutputResponse out, HttpRequestByIdListVo idRequestVo) {
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        int result = sysDepartService.delSysDepartByIds(idRequestVo.getIdList());
        out.write(result);
    }


    /**
     * @param
     * @return
     * @Title: 排序部门组织机构表信息
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
            value = "排序部门组织机构表信息",
            request = HttpRequestByIdVo.class,
            response = boolean.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysDepart_sort, method = RequestMethod.POST)
    @RequiresPermissions("sys:sysDepart:sort")
    public void sort(OutputResponse out, HttpRequestSortByIdListVo idRequestVo) {
        if (idRequestVo == null || idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result = false;
        if (idRequestVo.getIdList().size() == 1) {
            result = sysDepartService.updateSortById(idRequestVo.getIdList().get(0), idRequestVo.getSortList().get(0));
        } else {
            result = sysDepartService.updateSortByIds(idRequestVo.getIdList(), idRequestVo.getSortList());
        }
        out.write(result);
    }

    /**
     * @param
     * @return
     * @Title: 启用部门组织机构表信息
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
            value = "启用,上架部门组织机构表信息",
            request = HttpRequestByIdVo.class,
            response = boolean.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysDepart_turnOn, method = RequestMethod.GET)
    @RequiresPermissions("sys:sysDepart:turnOn")
    public void turnOn(OutputResponse out, HttpRequestByIdListVo idRequestVo) {
        if (idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result = sysDepartService.turnOn(idRequestVo.getIdList());
        out.write(result);
    }


    /**
     * @param
     * @return
     * @Title: 禁用部门组织机构表信息
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
            value = "下架部门组织机构表信息",
            request = HttpRequestByIdVo.class,
            response = boolean.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysDepart_turnOff, method = RequestMethod.GET)
    @RequiresPermissions("sys:sysDepart:turnOff")
    public void turnOff(OutputResponse out, HttpRequestByIdListVo idRequestVo) {
        if (idRequestVo == null || idRequestVo.getIdList() == null || idRequestVo.getIdList().isEmpty()) {
            out.write(SysResultCodeEnum.SYS_WEB_ID_INFO_NO_EXIST);
            return;
        }
        boolean result = sysDepartService.turnOff(idRequestVo.getIdList());
        out.write(result);
    }


    /**
     * @param
     * @return
     * @Title: 导出部门组织机构表信息
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
            value = "导出部门组织机构表信息",
            request = SysDepartRequestVo.class,
            response = boolean.class
    )
    @RequestMapping(value = SysURLCommand.sys_sysDepart_export, method = RequestMethod.POST)
    @RequiresPermissions("sys:sysDepart:export")
    public void export(HttpServletResponse response, SysDepartRequestVo sysDepartRequestVo) {

        SysDepartRequestDto sysDepartRequestDto = SysDepartRequestDto.build().clone(sysDepartRequestVo);

        BasePage page = BasePage.build().toPageSize(sysDepartRequestVo.getPageSize()).toPageNo(sysDepartRequestVo.getPageNo());
        page.toParamObject(sysDepartRequestDto);

        ResponseResultList<SysDepartResponseDto> resultList = sysDepartService.getSysDepartByNextPage(page);
        List<SysDepartResponseDto> data = resultList.getList();

        ColumnBox columnBox = ColumnBox.create();


//        try {
//            //ExportUtils.write(String.format("部门组织机构表_%s.xls",DateUtil.date("yyyy-MM-dd",new Date().getTime())), response, data, columnBox);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


}