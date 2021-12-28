package com.suven.framework.sys.controller;

import com.suven.framework.http.handler.OutputAesResponse;
import com.suven.framework.http.handler.OutputResponse;
import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.http.processor.url.SysURLCommand;
import com.suven.framework.sys.dto.response.SysUserResponseDto;
import com.suven.framework.sys.facade.SysUserFacade;
import com.suven.framework.sys.service.SysUserDepartService;
import com.suven.framework.sys.service.SysUserService;
import com.suven.framework.sys.vo.request.SysUserRequestVo;
import com.suven.framework.sys.vo.response.LoginCodeResponseVo;
import com.suven.framework.sys.vo.response.SysLoginResponseVo;
import com.suven.framework.sys.vo.response.SysUserResponseVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author scott
 * @since 2018-12-17
 */
@Controller
public class LoginWebController {


    private static Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    @Autowired
    private SysUserFacade sysUserFacade;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserDepartService sysUserDepartService;


    @RequestMapping(value = SysURLCommand.sys_login, method = RequestMethod.POST)
    @RequiresPermissions("sys:user:login")
    public void login(OutputResponse out, SysUserRequestVo sysUserRequestVo) {
        SysUserResponseDto dto = sysUserService.getUserByPhone(sysUserRequestVo.getUsername());
        IResultCodeEnum msgEnum = sysUserFacade.checkUserIsEffective(dto, sysUserRequestVo);
        if (msgEnum != null) {
            out.write(msgEnum);
            return;
        }
        SysUserResponseVo vo = SysUserResponseVo.build().clone(dto);
        SysLoginResponseVo responseVo = sysUserFacade.userInfo(vo);
        out.write(responseVo);
    }


    /**
     * 退出登录
     *
     * @param request
     * @param out
     */
    @RequestMapping(value = SysURLCommand.sys_logout, method = RequestMethod.POST)
    @RequiresPermissions("sys:user:logout")
    public void logout(HttpServletRequest request, OutputResponse out) {
        IResultCodeEnum msgEnum = sysUserFacade.logout(request);
        out.write(msgEnum);
    }

    /**
     * 获取校验码
     */
    @RequestMapping(value = SysURLCommand.sys_get_check_code, method = RequestMethod.GET)
    @RequiresPermissions("sys:user:getCheckCode")
    public void getCheckCode(OutputResponse out) {
        LoginCodeResponseVo vo = sysUserFacade.getCheckCode();
        out.write(vo);
    }


}