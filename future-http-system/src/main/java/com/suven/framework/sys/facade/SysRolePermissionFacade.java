package com.suven.framework.sys.facade;

import com.suven.framework.core.db.IterableConverter;
import com.suven.framework.http.data.vo.ResponseResultList;
import com.suven.framework.http.inters.IResultCodeEnum;
import com.suven.framework.sys.dto.response.SysPermissionResponseDto;
import com.suven.framework.sys.service.SysPermissionService;
import com.suven.framework.sys.vo.request.SysRolePermissionSaveRequestVo;
import com.suven.framework.sys.vo.response.SysPermissionResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.suven.framework.sys.service.SysRolePermissionService;
import com.suven.framework.sys.dto.request.SysRolePermissionRequestDto;
import com.suven.framework.sys.dto.response.SysRolePermissionResponseDto;
import com.suven.framework.sys.vo.request.SysRolePermissionRequestVo;
import com.suven.framework.sys.vo.response.SysRolePermissionResponseVo;

import java.util.List;
import java.util.stream.Collectors;


/**
  * @ClassName: SysRolePermissionFacade.java
  *
  * @Author 作者 : suven
  * @email 邮箱 : suvenw@163.com
  * @CreateDate 创建时间: 2022-02-28 16:10:49
  * @Version 版本: v1.0.0
  * <pre>
  *
  *  @Description: 角色权限表 的业务综合处理门面实现逻辑类
  *
  * </pre>
  * <pre>
  * 修改记录
  *    修改后版本:     修改人：  修改日期:     修改内容:
  * ----------------------------------------------------------------------------
  *
  * ----------------------------------------------------------------------------
  * @RequestMapping("/sys/sysRolePermission")
  * </pre>
  * @Copyright: (c) 2021 gc by https://www.suven.top
  **/

@Component
public class SysRolePermissionFacade {

	@Autowired
	private SysRolePermissionService  sysRolePermissionService;

	

	@Autowired
	private SysPermissionService sysPermissionService;

    public IResultCodeEnum saveRolePermission(SysRolePermissionSaveRequestVo requestVo) {
        IResultCodeEnum sysMsgEnum = sysRolePermissionService.saveRolePermission(requestVo.getRoleId(), requestVo.getPermissionIds(), requestVo.getLastpermissionIds());
        return sysMsgEnum;
    }

	public ResponseResultList<SysPermissionResponseVo> queryUserPermission(long userId) {
		List<SysPermissionResponseDto> responseDtoList = sysPermissionService.queryByUser(userId);
		List<SysPermissionResponseVo> list = IterableConverter.convertList(responseDtoList,SysPermissionResponseVo.class);
		ResponseResultList<SysPermissionResponseVo> resultList = ResponseResultList.build().toList(list);
		return resultList;
	}

	public List<String> queryRolePermission(SysRolePermissionSaveRequestVo requestVo) {
		List<SysRolePermissionResponseDto> responseDtos = this.sysRolePermissionService.queryRolePermissionByRoleId(requestVo.getRoleId());
		List<String> list = (List)responseDtos.stream().map((dto) -> {
			return String.valueOf(dto.getPermissionId());
		}).collect(Collectors.toList());
		return list;
	}

}
