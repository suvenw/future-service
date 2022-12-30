package com.suven.framework.sys.service;

import com.suven.framework.common.data.BasePage;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.sys.dto.enums.SysUserQueryEnum;
import com.suven.framework.sys.dto.request.SysUserLoginRequestDto;
import com.suven.framework.sys.dto.request.SysUserRequestDto;
import com.suven.framework.sys.dto.response.SysLoginResponseDto;
import com.suven.framework.sys.dto.response.SysUserResponseDto;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;


/**
 * @ClassName: SysUserService.java
 *
 * @Author 作者 : suven
 * @CreateDate 创建时间: 2022-02-28 16:09:37
 * @Version 版本: v1.0.0
 * <pre>
 *
 *  @Description: 用户表 RPC业务接口逻辑实现类
 *
 * </pre>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * ----------------------------------------------------------------------------
 *
 * ----------------------------------------------------------------------------
 * </pre>
 * @Copyright: (c) 2021 gc by https://www.suven.top
 **/


public interface SysUserLoginService {


    /**
     * 保存用户登陆实现方法
     * @param sysUserLoginRequestDto SysUserLoginRequestDto
     * @return
     */
    SysLoginResponseDto loginSysUser(SysUserLoginRequestDto sysUserLoginRequestDto);



}