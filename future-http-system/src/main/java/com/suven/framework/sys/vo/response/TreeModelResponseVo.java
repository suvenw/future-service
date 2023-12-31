package com.suven.framework.sys.vo.response;


import com.suven.framework.sys.dto.response.SysPermissionResponseDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
  * 树形列表用到
 */
public class TreeModelResponseVo implements Serializable {

	private static final long serialVersionUID = 4013193970046502756L;

	private String key;

	private String title;

	private String slotTitle;

	private boolean isLeaf;

	private String icon;

	private Integer ruleFlag;

	private Map<String,String> scopedSlots;

	public Map<String, String> getScopedSlots() {
		return scopedSlots;
	}

	public void setScopedSlots(Map<String, String> scopedSlots) {
		this.scopedSlots = scopedSlots;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	private List<TreeModelResponseVo> children;

	public List<TreeModelResponseVo> getChildren() {
		return children;
	}

	public void setChildren(List<TreeModelResponseVo> children) {
		this.children = children;
	}

	public TreeModelResponseVo() {

	}

	public TreeModelResponseVo(SysPermissionResponseDto permission) {
		this.key = String.valueOf(permission.getId());
		this.icon = permission.getIcon();
		this.parentId = String.valueOf(permission.getParentId());
		this.title = permission.getName();
		this.slotTitle =  permission.getName();
		this.value = String.valueOf(permission.getId());
		this.isLeaf = permission.getIsLeaf()== 1?true:false;
		Map<String,String> map = new HashMap<String,String>();
		map.put("title", "hasDatarule");
		this.scopedSlots = map;
		this.label = permission.getName();
		if(permission.getIsLeaf() != 1) {
			this.children = new ArrayList<TreeModelResponseVo>();
		}

	}


	public TreeModelResponseVo(SysPermissionResponseDto permission,boolean flag) {
		this.key = String.valueOf(permission.getId());
		this.icon = permission.getIcon();
		this.parentId = String.valueOf(permission.getParentId());
		this.title = permission.getName();
		this.slotTitle =  permission.getName();
		this.value = String.valueOf(permission.getId());
		this.isLeaf = permission.getIsLeaf()== 1?true:false;
		this.label = permission.getName();
		if(permission.getIsLeaf() != 1) {
			this.children = new ArrayList<TreeModelResponseVo>();
		}
	}

	 public TreeModelResponseVo(String key, String parentId, String slotTitle, Integer ruleFlag, boolean isLeaf) {
    	this.key = key;
    	this.parentId = parentId;
    	this.ruleFlag=ruleFlag;
    	this.slotTitle =  slotTitle;
    	Map<String,String> map = new HashMap<String,String>();
    	map.put("title", "hasDatarule");
    	this.scopedSlots = map;
    	this.isLeaf = isLeaf;
    	this.value = key;
    	if(!isLeaf) {
    		this.children = new ArrayList<TreeModelResponseVo>();
    	}
    }

	 private String parentId;

	private String label;

	private String value;


	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	public String getSlotTitle() {
		return slotTitle;
	}

	public void setSlotTitle(String slotTitle) {
		this.slotTitle = slotTitle;
	}

	public Integer getRuleFlag() {
		return ruleFlag;
	}

	public void setRuleFlag(Integer ruleFlag) {
		this.ruleFlag = ruleFlag;
	}

}
