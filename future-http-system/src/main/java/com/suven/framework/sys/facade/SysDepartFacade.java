package com.suven.framework.sys.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.suven.framework.sys.dto.response.SysDepartResponseDto;
import com.suven.framework.sys.service.SysDepartService;
import com.suven.framework.sys.vo.response.DepartIdModelResponseVo;
import com.suven.framework.sys.vo.response.SysDepartTreeModelResponseVo;

import java.util.ArrayList;
import java.util.List;


/**   
 * @Title: SysDepartFacade.java
 * @author xxx.xxx
 * @date   2019-10-18 12:35:25
 * @version V1.0  
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: TODO(说明) 对象缓存统一模板实现类;  
 */
 
@Service
public class SysDepartFacade {
	
	@Autowired
	private SysDepartService sysDepartService;


    public List<SysDepartTreeModelResponseVo> queryTreeList() {
        List<SysDepartResponseDto> list = sysDepartService.getList();
        List<SysDepartTreeModelResponseVo> listResult = wrapTreeDataToTreeList(list);
        return listResult;
    }

    public List<DepartIdModelResponseVo> queryDepartIdTreeList() {
        List<SysDepartResponseDto> list = sysDepartService.getList();
        List<DepartIdModelResponseVo> listResult = wrapTreeDataToDepartIdTreeList(list);
        return listResult;
    }

    public List<SysDepartTreeModelResponseVo> searchBy(String keyWord) {
        List<SysDepartResponseDto> list = sysDepartService.searchBy(keyWord);
        SysDepartTreeModelResponseVo model = new SysDepartTreeModelResponseVo();
        List<SysDepartTreeModelResponseVo> newList = new ArrayList<>();
        for(SysDepartResponseDto depart : list) {
            model = new SysDepartTreeModelResponseVo(depart);
            model.setChildren(null);
            newList.add(model);
        }
        return newList;
    }


    /**
     * queryTreeList的子方法 ====1=====
     * 该方法是s将SysDepart类型的list集合转换成SysDepartTreeModel类型的集合
     */
    public static List<SysDepartTreeModelResponseVo> wrapTreeDataToTreeList(List<SysDepartResponseDto> recordList) {
        // 在该方法每请求一次,都要对全局list集合进行一次清理
        //idList.clear();
        List<DepartIdModelResponseVo> idList = new ArrayList();
        List<SysDepartTreeModelResponseVo> records = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            SysDepartResponseDto depart = recordList.get(i);
            records.add(new SysDepartTreeModelResponseVo(depart));
        }
        List<SysDepartTreeModelResponseVo> tree = findChildren(records, idList);
        setEmptyChildrenAsNull(tree);
        return tree;
    }

    /**
     * 获取 DepartIdModel
     * @param recordList
     * @return
     */
    public static List<DepartIdModelResponseVo> wrapTreeDataToDepartIdTreeList(List<SysDepartResponseDto> recordList) {
        // 在该方法每请求一次,都要对全局list集合进行一次清理
        //idList.clear();
        List<DepartIdModelResponseVo> idList = new ArrayList();
        List<SysDepartTreeModelResponseVo> records = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            SysDepartResponseDto depart = recordList.get(i);
            records.add(new SysDepartTreeModelResponseVo(depart));
        }
        findChildren(records, idList);
        return idList;
    }

    /**
     * queryTreeList的子方法 ====2=====
     * 该方法是找到并封装顶级父类的节点到TreeList集合
     */
    private static List<SysDepartTreeModelResponseVo> findChildren(List<SysDepartTreeModelResponseVo> recordList,
                                                                   List<DepartIdModelResponseVo> departIdList) {

        List<SysDepartTreeModelResponseVo> treeList = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            SysDepartTreeModelResponseVo branch = recordList.get(i);
            if (branch.getParentId()<=0) {
                treeList.add(branch);
                DepartIdModelResponseVo departIdModel = new DepartIdModelResponseVo().convert(branch);
                departIdList.add(departIdModel);
            }
        }
        getGrandChildren(treeList,recordList,departIdList);

        //idList = departIdList;
        return treeList;
    }

    /**
     * queryTreeList的子方法====3====
     *该方法是找到顶级父类下的所有子节点集合并封装到TreeList集合
     */
    private static void getGrandChildren(List<SysDepartTreeModelResponseVo> treeList,List<SysDepartTreeModelResponseVo> recordList,List<DepartIdModelResponseVo> idList) {

        for (int i = 0; i < treeList.size(); i++) {
            SysDepartTreeModelResponseVo model = treeList.get(i);
            DepartIdModelResponseVo idModel = idList.get(i);
            for (int i1 = 0; i1 < recordList.size(); i1++) {
                SysDepartTreeModelResponseVo m = recordList.get(i1);
                if (m.getParentId()> 0 && m.getParentId() == model.getId()) {
                    model.getChildren().add(m);
                    DepartIdModelResponseVo dim = new DepartIdModelResponseVo().convert(m);
                    idModel.getChildren().add(dim);
                }
            }
            getGrandChildren(treeList.get(i).getChildren(), recordList, idList.get(i).getChildren());
        }

    }


    /**
     * queryTreeList的子方法 ====4====
     * 该方法是将子节点为空的List集合设置为Null值
     */
    private static void setEmptyChildrenAsNull(List<SysDepartTreeModelResponseVo> treeList) {

        for (int i = 0; i < treeList.size(); i++) {
            SysDepartTreeModelResponseVo model = treeList.get(i);
            if (model.getChildren().size() == 0) {
                model.setChildren(null);
                model.setIsLeaf(true);
            }else{
                setEmptyChildrenAsNull(model.getChildren());
                model.setIsLeaf(false);
            }
        }
    }


}
