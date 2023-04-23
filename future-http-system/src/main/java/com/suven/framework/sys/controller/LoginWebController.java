//package com.suven.framework.sys.controller;
//
//import com.suven.framework.common.api.ApiDoc;
//import com.suven.framework.common.api.DocumentConst;
//import com.suven.framework.common.enums.SysResultCodeEnum;
//import com.suven.framework.http.data.vo.RequestParserVo;
//import com.suven.framework.http.exception.SystemRuntimeException;
//import com.suven.framework.http.handler.OutputResponse;
//import com.suven.framework.http.inters.IResultCodeEnum;
//import com.suven.framework.http.processor.url.SysURLCommand;
//import com.suven.framework.sys.dto.response.SysUserResponseDto;
//import com.suven.framework.sys.dto.response.SysUserRoleResponseDto;
//import com.suven.framework.sys.facade.SysUserFacade;
//import com.suven.framework.sys.service.SysUserDepartService;
//import com.suven.framework.sys.service.SysUserService;
//import com.suven.framework.sys.vo.request.SysUserLoginRequestVo;
//import com.suven.framework.sys.vo.request.SysUserRequestVo;
//import com.suven.framework.sys.vo.request.SysUserTokenRequestVo;
//import com.suven.framework.sys.vo.response.LoginCodeResponseVo;
//import com.suven.framework.sys.vo.response.SysLoginResponseVo;
//import com.suven.framework.sys.vo.response.SysUserResponseVo;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.databene.commons.CollectionUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//
///**
// * @Author scott
// * @since 2018-12-17
// */
//@ApiDoc(
//        group = DocumentConst.Sys.SYS_DOC_GROUP,
//        groupDesc= DocumentConst.Sys.SYS_DOC_DES,
//        module = "后台管理用户账号模块"
//)
//@Controller
//public class LoginWebController {
//
//
//    private static Logger logger = LoggerFactory.getLogger(LoggerFactory.class);
//
//    @Autowired
//    private SysUserFacade sysUserFacade;
//
//    @Autowired
//    private SysUserService sysUserService;
//
//    @Autowired
//    private SysUserDepartService sysUserDepartService;
//
//
//    @ApiDoc(
//            value = "登陆接口",
//            request = SysUserLoginRequestVo.class,
//            response = SysLoginResponseVo.class
//    )
//    @RequestMapping(value = SysURLCommand.sys_login, method = RequestMethod.POST)
//    @RequiresPermissions("sys:user:login")
//    public void login(OutputResponse out, SysUserLoginRequestVo sysUserRequestVo) {
//        SysLoginResponseVo responseVo = sysUserFacade.userLogin(sysUserRequestVo);
//        out.write(responseVo);
//    }
//
//
//    /**
//     * 退出登录
//     *
//     * @param request
//     * @param out
//     */
//    @ApiDoc(
//            value = "退出登录",
//            request = RequestParserVo.class,
//            response = boolean.class
//    )
//    @RequestMapping(value = SysURLCommand.sys_logout, method = RequestMethod.POST)
//    @RequiresPermissions("sys:user:logout")
//    public void logout(HttpServletRequest request, OutputResponse out) {
//        IResultCodeEnum msgEnum = sysUserFacade.logout(request);
//        out.write(msgEnum);
//    }
//
//    /**
//     * 获取校验码
//     */
//    @ApiDoc(
//            value = "获取校验码",
//            request = RequestParserVo.class,
//            response = LoginCodeResponseVo.class
//    )
//    @RequestMapping(value = SysURLCommand.sys_get_check_code, method = RequestMethod.GET)
//    @RequiresPermissions("sys:user:getCheckCode")
//    public void getCheckCode(OutputResponse out) {
//        LoginCodeResponseVo vo = sysUserFacade.getCheckCode();
//        out.write(vo);
//    }
//
//
//}