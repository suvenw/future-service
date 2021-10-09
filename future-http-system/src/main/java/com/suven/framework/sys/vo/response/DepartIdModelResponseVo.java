package com.suven.framework.sys.vo.response;

import com.suven.framework.sys.entity.SysDepart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 部门表 封装树结构的部门的名称的实体类
 * <p>
 * 
 * @Author Steve
 * @Since 2019-01-22 
 *
 */
public class DepartIdModelResponseVo implements Serializable {

    private static final long serialVersionUID = 1L;

    // 主键ID
    private long key;

    // 主键ID
    private long value;

    // 部门名称
    private String title;
    
    List<DepartIdModelResponseVo> children = new ArrayList<>();
    
    /**
     * 将SysDepartTreeModel的部分数据放在该对象当中
     * @param treeModel
     * @return
     */
    public DepartIdModelResponseVo convert(SysDepartTreeModelResponseVo treeModel) {
        this.key = treeModel.getId();
        this.value = treeModel.getId();
        this.title = treeModel.getDepartName();
        return this;
    }
    
    /**
     * 该方法为用户部门的实现类所使用
     * @param sysDepart
     * @return
     */
    public DepartIdModelResponseVo convertByUserDepart(SysDepart sysDepart) {
        this.key = sysDepart.getId();
        this.value = sysDepart.getId();
        this.title = sysDepart.getDepartName();
        return this;
    } 

    public List<DepartIdModelResponseVo> getChildren() {
        return children;
    }

    public void setChildren(List<DepartIdModelResponseVo> children) {
        this.children = children;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
