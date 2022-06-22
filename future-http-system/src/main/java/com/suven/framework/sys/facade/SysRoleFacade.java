package com.suven.framework.sys.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.suven.framework.sys.dto.request.SysPermissionRequestDto;
import com.suven.framework.sys.dto.response.SysPermissionResponseDto;
import com.suven.framework.sys.service.SysRoleService;
import com.suven.framework.sys.service.SysPermissionService;
import com.suven.framework.sys.vo.response.TreeModelResponseVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**   
 * @Title: RoleFacade.java
 * @author xxx.xxx
 * @date   2019-11-21 15:22:59
 * @version V1.0  
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: TODO(说明) 对象缓存统一模板实现类;  
 */
 
@Component
public class SysRoleFacade {


	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysPermissionService sysPermissionService;


	public Map<String, Object> queryTreeList() {

		SysPermissionRequestDto dto = SysPermissionRequestDto.build();
		dto.toDelFlag(0).toMenuType(-1).toParentId(-1);
		List<SysPermissionResponseDto> resultList = sysPermissionService.getSysPermissionList(dto);
		List<String> ids = resultList.stream().map(r ->
				String.valueOf(r.getId())).collect(Collectors.toList()
		);
		List<TreeModelResponseVo> treeList = new ArrayList<>();
		getTreeModelList(treeList, resultList,null);
		Map<String,Object> resMap = new HashMap();
		resMap.put("treeList", treeList); //全部树节点数据
		resMap.put("ids", ids);//全部树ids
		return resMap;
	}




	private void getTreeModelList(List<TreeModelResponseVo> treeList, List<SysPermissionResponseDto> metaList, TreeModelResponseVo temp) {
		metaList.forEach(m ->{
			String  tempPid = String.valueOf(m.getParentId());
			TreeModelResponseVo tree = new TreeModelResponseVo(String.valueOf(m.getId()), String.valueOf(m.getParentId()), m.getName(),
					m.getRuleFlag(), m.getIsLeaf() ==1?true:false);
			if(temp==null && tempPid.equals("0")) {
				treeList.add(tree);
				if(!tree.getIsLeaf()) {
					getTreeModelList(treeList, metaList, tree);
				}
			}else if(temp!=null && tempPid!=null && tempPid.equals(temp.getKey())){
				temp.getChildren().add(tree);
				if(!tree.getIsLeaf()) {
					getTreeModelList(treeList, metaList, tree);
				}
			}
		});
	}







}
