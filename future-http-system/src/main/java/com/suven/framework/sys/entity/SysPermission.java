package com.suven.framework.sys.entity;

import com.suven.framework.common.data.BaseByTimeEntity;
import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.core.db.ext.DS;

import java.util.Date;

/**
  * @ClassName: SysPermission.java
  *
  * @Author 作者 : suven
  * @email 邮箱 : suvenw@163.com
  * @CreateDate 创建时间: 2022-02-28 16:10:30
  * @Version 版本: v1.0.0
  * <pre>
  *
  *  @Description: 菜单权限表 数据库表对应的实现类
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

@DS(DataSourceModuleName.module_name_sys)
public class SysPermission extends BaseByTimeEntity{

private static final long serialVersionUID = 1L;



 		/** parent_id 父id  */
 		@ApiDesc(value = "父id", required = 0)
 		private long parentId;

 		/** name 菜单标题  */
 		@ApiDesc(value = "菜单标题", required = 0)
 		private String name;

 		/** url 路径  */
 		@ApiDesc(value = "路径", required = 0)
 		private String url;

 		/** component 组件  */
 		@ApiDesc(value = "组件", required = 0)
 		private String component;

 		/** component_name 组件名字  */
 		@ApiDesc(value = "组件名字", required = 0)
 		private String componentName;

 		/** redirect 一级菜单跳转地址  */
 		@ApiDesc(value = "一级菜单跳转地址", required = 0)
 		private String redirect;

 		/** menu_type 菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)  */
 		@ApiDesc(value = "菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)", required = 0)
 		private int menuType;

 		/** perms 菜单权限编码  */
 		@ApiDesc(value = "菜单权限编码", required = 0)
 		private String perms;

 		/** perms_type 权限策略1显示2禁用  */
 		@ApiDesc(value = "权限策略1显示2禁用", required = 0)
 		private String permsType;

 		/** sort_no 菜单排序  */
 		@ApiDesc(value = "菜单排序", required = 0)
 		private double sortNo;

 		/** always_show 聚合子路由: 1是0否  */
 		@ApiDesc(value = "聚合子路由: 1是0否", required = 0)
 		private int alwaysShow;

 		/** icon 菜单图标  */
 		@ApiDesc(value = "菜单图标", required = 0)
 		private String icon;

 		/** is_route 是否路由菜单: 0:不是  1:是（默认值1）  */
 		@ApiDesc(value = "是否路由菜单: 0:不是  1:是（默认值1）", required = 0)
 		private int isRoute;

 		/** is_leaf 是否叶子节点:    1:是   0:不是  */
 		@ApiDesc(value = "是否叶子节点:    1:是   0:不是", required = 0)
 		private int isLeaf;

 		/** keep_alive 是否缓存该页面:    1:是   0:不是  */
 		@ApiDesc(value = "是否缓存该页面:    1:是   0:不是", required = 0)
 		private int keepAlive;

 		/** hidden 是否隐藏路由: 0否,1是  */
 		@ApiDesc(value = "是否隐藏路由: 0否,1是", required = 0)
 		private int hidden;

 		/** hide_tab 是否隐藏tab: 0否,1是  */
 		@ApiDesc(value = "是否隐藏tab: 0否,1是", required = 0)
 		private int hideTab;

 		/** description 描述  */
 		@ApiDesc(value = "描述", required = 0)
 		private String description;





 		/** del_flag 删除状态 0正常 1已删除  */
 		@ApiDesc(value = "删除状态 0正常 1已删除", required = 0)
 		private int delFlag;

 		/** rule_flag 是否添加数据权限1是0否  */
 		@ApiDesc(value = "是否添加数据权限1是0否", required = 0)
 		private int ruleFlag;

 		/** status 按钮权限状态(0无效1有效)  */
 		@ApiDesc(value = "按钮权限状态(0无效1有效)", required = 0)
 		private int status;

 		/** internal_or_external 外链菜单打开方式 0/内部打开 1/外部打开  */
 		@ApiDesc(value = "外链菜单打开方式 0/内部打开 1/外部打开", required = 0)
 		private int internalOrExternal;


    public static SysPermission build(){
        return new SysPermission();
    }

 		public void setParentId( long parentId){
 		 		this.parentId = parentId ; 
 		 		}
 		public SysPermission toParentId( long parentId){
 		 		this.parentId = parentId ; 
 		 		 return this ;
 		}

 		public long getParentId(){
 		 		return this.parentId;
 		}
 		public void setName( String name){
 		 		this.name = name ; 
 		 		}
 		public SysPermission toName( String name){
 		 		this.name = name ; 
 		 		 return this ;
 		}

 		public String getName(){
 		 		return this.name;
 		}
 		public void setUrl( String url){
 		 		this.url = url ; 
 		 		}
 		public SysPermission toUrl( String url){
 		 		this.url = url ; 
 		 		 return this ;
 		}

 		public String getUrl(){
 		 		return this.url;
 		}
 		public void setComponent( String component){
 		 		this.component = component ; 
 		 		}
 		public SysPermission toComponent( String component){
 		 		this.component = component ; 
 		 		 return this ;
 		}

 		public String getComponent(){
 		 		return this.component;
 		}
 		public void setComponentName( String componentName){
 		 		this.componentName = componentName ; 
 		 		}
 		public SysPermission toComponentName( String componentName){
 		 		this.componentName = componentName ; 
 		 		 return this ;
 		}

 		public String getComponentName(){
 		 		return this.componentName;
 		}
 		public void setRedirect( String redirect){
 		 		this.redirect = redirect ; 
 		 		}
 		public SysPermission toRedirect( String redirect){
 		 		this.redirect = redirect ; 
 		 		 return this ;
 		}

 		public String getRedirect(){
 		 		return this.redirect;
 		}
 		public void setMenuType( int menuType){
 		 		this.menuType = menuType ; 
 		 		}
 		public SysPermission toMenuType( int menuType){
 		 		this.menuType = menuType ; 
 		 		 return this ;
 		}

 		public int getMenuType(){
 		 		return this.menuType;
 		}
 		public void setPerms( String perms){
 		 		this.perms = perms ; 
 		 		}
 		public SysPermission toPerms( String perms){
 		 		this.perms = perms ; 
 		 		 return this ;
 		}

 		public String getPerms(){
 		 		return this.perms;
 		}
 		public void setPermsType( String permsType){
 		 		this.permsType = permsType ; 
 		 		}
 		public SysPermission toPermsType( String permsType){
 		 		this.permsType = permsType ; 
 		 		 return this ;
 		}

 		public String getPermsType(){
 		 		return this.permsType;
 		}
 		public void setSortNo( double sortNo){
 		 		this.sortNo = sortNo ; 
 		 		}
 		public SysPermission toSortNo( double sortNo){
 		 		this.sortNo = sortNo ; 
 		 		 return this ;
 		}

 		public double getSortNo(){
 		 		return this.sortNo;
 		}
 		public void setAlwaysShow( int alwaysShow){
 		 		this.alwaysShow = alwaysShow ; 
 		 		}
 		public SysPermission toAlwaysShow( int alwaysShow){
 		 		this.alwaysShow = alwaysShow ; 
 		 		 return this ;
 		}

 		public int getAlwaysShow(){
 		 		return this.alwaysShow;
 		}
 		public void setIcon( String icon){
 		 		this.icon = icon ; 
 		 		}
 		public SysPermission toIcon( String icon){
 		 		this.icon = icon ; 
 		 		 return this ;
 		}

 		public String getIcon(){
 		 		return this.icon;
 		}
 		public void setIsRoute( int isRoute){
 		 		this.isRoute = isRoute ; 
 		 		}
 		public SysPermission toIsRoute( int isRoute){
 		 		this.isRoute = isRoute ; 
 		 		 return this ;
 		}

 		public int getIsRoute(){
 		 		return this.isRoute;
 		}
 		public void setIsLeaf( int isLeaf){
 		 		this.isLeaf = isLeaf ; 
 		 		}
 		public SysPermission toIsLeaf( int isLeaf){
 		 		this.isLeaf = isLeaf ; 
 		 		 return this ;
 		}

 		public int getIsLeaf(){
 		 		return this.isLeaf;
 		}
 		public void setKeepAlive( int keepAlive){
 		 		this.keepAlive = keepAlive ; 
 		 		}
 		public SysPermission toKeepAlive( int keepAlive){
 		 		this.keepAlive = keepAlive ; 
 		 		 return this ;
 		}

 		public int getKeepAlive(){
 		 		return this.keepAlive;
 		}
 		public void setHidden( int hidden){
 		 		this.hidden = hidden ; 
 		 		}
 		public SysPermission toHidden( int hidden){
 		 		this.hidden = hidden ; 
 		 		 return this ;
 		}

 		public int getHidden(){
 		 		return this.hidden;
 		}
 		public void setHideTab( int hideTab){
 		 		this.hideTab = hideTab ; 
 		 		}
 		public SysPermission toHideTab( int hideTab){
 		 		this.hideTab = hideTab ; 
 		 		 return this ;
 		}

 		public int getHideTab(){
 		 		return this.hideTab;
 		}
 		public void setDescription( String description){
 		 		this.description = description ; 
 		 		}
 		public SysPermission toDescription( String description){
 		 		this.description = description ; 
 		 		 return this ;
 		}

 		public String getDescription(){
 		 		return this.description;
 		}
 		public void setDelFlag( int delFlag){
 		 		this.delFlag = delFlag ; 
 		 		}
 		public SysPermission toDelFlag( int delFlag){
 		 		this.delFlag = delFlag ; 
 		 		 return this ;
 		}

 		public int getDelFlag(){
 		 		return this.delFlag;
 		}
 		public void setRuleFlag( int ruleFlag){
 		 		this.ruleFlag = ruleFlag ; 
 		 		}
 		public SysPermission toRuleFlag( int ruleFlag){
 		 		this.ruleFlag = ruleFlag ; 
 		 		 return this ;
 		}

 		public int getRuleFlag(){
 		 		return this.ruleFlag;
 		}
 		public void setStatus( int status){
 		 		this.status = status ; 
 		 		}
 		public SysPermission toStatus( int status){
 		 		this.status = status ; 
 		 		 return this ;
 		}

 		public int getStatus(){
 		 		return this.status;
 		}
 		public void setInternalOrExternal( int internalOrExternal){
 		 		this.internalOrExternal = internalOrExternal ; 
 		 		}
 		public SysPermission toInternalOrExternal( int internalOrExternal){
 		 		this.internalOrExternal = internalOrExternal ; 
 		 		 return this ;
 		}

 		public int getInternalOrExternal(){
 		 		return this.internalOrExternal;
 		}
}