package com.suven.framework.sys.facade;

import com.suven.framework.http.inters.IResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.suven.framework.sys.dto.response.SysRolePermissionResponseDto;
import com.suven.framework.sys.service.SysRolePermissionService;
import com.suven.framework.sys.vo.request.SysRolePermissionSaveRequestVo;

import java.util.List;
import java.util.stream.Collectors;


/**   
 * @Title: SysRolePermissionFacade.java
 * @author xxx.xxx
 * @date   2019-10-18 12:35:25
 * @version V1.0  
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: TODO(说明) 对象缓存统一模板实现类;  
 */
 
@Component
public class SysRolePermissionFacade {
	
	@Autowired
	private SysRolePermissionService sysRolePermissionService;


    public IResultCodeEnum saveRolePermission(SysRolePermissionSaveRequestVo requestVo) {
        IResultCodeEnum sysMsgEnum = sysRolePermissionService.saveRolePermission(requestVo.getRoleId(), requestVo.getPermissionIds(), requestVo.getLastpermissionIds());
        return sysMsgEnum;
    }

    public List<String> queryRolePermission(SysRolePermissionSaveRequestVo requestVo) {

        List<SysRolePermissionResponseDto> responseDtos =  sysRolePermissionService.queryRolePermissionByRoleId(requestVo.getRoleId());
        List<String> list = responseDtos.stream().map(dto ->
                String.valueOf(dto.getPermissionId())).collect(Collectors.toList()
        );
        return list;
    }
}
