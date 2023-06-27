package com.suven.framework.sys.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.suven.framework.sys.service.SysDepartPermissionService;
import com.suven.framework.sys.dto.request.SysDepartPermissionRequestDto;
import com.suven.framework.sys.dto.response.SysDepartPermissionResponseDto;
import com.suven.framework.sys.vo.request.SysDepartPermissionRequestVo;
import com.suven.framework.sys.vo.response.SysDepartPermissionResponseVo;




/**
  * @ClassName: SysDepartPermissionFacade.java
  *
  * @Author 作者 : suven
  * @email 邮箱 : suvenw@163.com
  * @CreateDate 创建时间: 2022-02-28 16:14:27
  * @Version 版本: v1.0.0
  * <pre>
  *
  *  @Description: 部门权限表 的业务综合处理门面实现逻辑类
  *
  * </pre>
  * <pre>
  * 修改记录
  *    修改后版本:     修改人：  修改日期:     修改内容:
  * ----------------------------------------------------------------------------
  *
  * ----------------------------------------------------------------------------
  * @RequestMapping("/sys/sysDepartPermission")
  * </pre>
  * @Copyright: (c) 2021 gc by https://www.suven.top
  **/

@Component
public class SysDepartPermissionFacade {

	@Autowired
	private SysDepartPermissionService  sysDepartPermissionService;

	



}
