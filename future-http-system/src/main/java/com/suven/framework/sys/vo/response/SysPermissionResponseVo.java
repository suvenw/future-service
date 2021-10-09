package com.suven.framework.sys.vo.response;


import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.common.data.BaseStatusEntity;

import java.io.Serializable;

/**
* @ClassName: SysPermissionResponseVo.java
* @Description: 菜单权限表的数据交互处理类
* @author xxx.xxx
* @date   2019-10-18 12:35:25
* @version V1.0.0
* <p>
* ----------------------------------------------------------------------------
*  modifyer    modifyTime                 comment
*
* ----------------------------------------------------------------------------
* </p>
*/
public class SysPermissionResponseVo  extends BaseStatusEntity implements Serializable {








 		/** parentId 父id  */
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

 		/** componentName 组件名字  */
 		@ApiDesc(value = "组件名字", required = 0)
 		private String componentName;

 		/** redirect 一级菜单跳转地址  */
 		@ApiDesc(value = "一级菜单跳转地址", required = 0)
 		private String redirect;

 		/** menuType 菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)  */
 		@ApiDesc(value = "菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)", required = 0)
 		private int menuType;

 		/** perms 菜单权限编码  */
 		@ApiDesc(value = "菜单权限编码", required = 0)
 		private String perms;

 		/** permsType 权限策略1显示2禁用  */
 		@ApiDesc(value = "权限策略1显示2禁用", required = 0)
 		private int permsType;

 		/** alwaysShow 聚合子路由: 1是0否  */
 		@ApiDesc(value = "聚合子路由: 1是0否", required = 0)
 		private int alwaysShow;

 		/** icon 菜单图标  */
 		@ApiDesc(value = "菜单图标", required = 0)
 		private String icon;

 		/** isRoute 是否路由菜单: 0:不是  1:是（默认值1）  */
 		@ApiDesc(value = "是否路由菜单: 0:不是  1:是（默认值1）", required = 0)
 		private int isRoute;

 		/** isLeaf 是否叶子节点:    1:是   0:不是  */
 		@ApiDesc(value = "是否叶子节点:    1:是   0:不是", required = 0)
 		private int isLeaf;

 		/** keepAlive 是否缓存该页面:    1:是   0:不是  */
 		@ApiDesc(value = "是否缓存该页面:    1:是   0:不是", required = 0)
 		private int keepAlive;

 		/** hidden 是否隐藏路由: 0否,1是  */
 		@ApiDesc(value = "是否隐藏路由: 0否,1是", required = 0)
 		private int hidden;

 		/** description 描述  */
 		@ApiDesc(value = "描述", required = 0)
 		private String description;

 		/** delFlag 删除状态 0正常 1已删除  */
 		@ApiDesc(value = "删除状态 0正常 1已删除", required = 0)
 		private int delFlag;

 		/** ruleFlag 是否添加数据权限1是0否  */
 		@ApiDesc(value = "是否添加数据权限1是0否", required = 0)
 		private int ruleFlag;


    public static SysPermissionResponseVo build(){
        return new SysPermissionResponseVo();
    }

    
    
    
    
    
     		public void setParentId( long parentId){
 		 		this.parentId = parentId ; 
 		 		}
 		public SysPermissionResponseVo toParentId( long parentId){
 		 		this.parentId = parentId ; 
 		 		 return this ;
 		}

 		public long getParentId(){
 		 		return this.parentId;
 		}
    
     		public void setName( String name){
 		 		this.name = name ; 
 		 		}
 		public SysPermissionResponseVo toName( String name){
 		 		this.name = name ; 
 		 		 return this ;
 		}

 		public String getName(){
 		 		return this.name;
 		}
    
     		public void setUrl( String url){
 		 		this.url = url ; 
 		 		}
 		public SysPermissionResponseVo toUrl( String url){
 		 		this.url = url ; 
 		 		 return this ;
 		}

 		public String getUrl(){
 		 		return this.url;
 		}
    
     		public void setComponent( String component){
 		 		this.component = component ; 
 		 		}
 		public SysPermissionResponseVo toComponent( String component){
 		 		this.component = component ; 
 		 		 return this ;
 		}

 		public String getComponent(){
 		 		return this.component;
 		}
    
     		public void setComponentName( String componentName){
 		 		this.componentName = componentName ; 
 		 		}
 		public SysPermissionResponseVo toComponentName( String componentName){
 		 		this.componentName = componentName ; 
 		 		 return this ;
 		}

 		public String getComponentName(){
 		 		return this.componentName;
 		}
    
     		public void setRedirect( String redirect){
 		 		this.redirect = redirect ; 
 		 		}
 		public SysPermissionResponseVo toRedirect( String redirect){
 		 		this.redirect = redirect ; 
 		 		 return this ;
 		}

 		public String getRedirect(){
 		 		return this.redirect;
 		}
    
     		public void setMenuType( int menuType){
 		 		this.menuType = menuType ; 
 		 		}
 		public SysPermissionResponseVo toMenuType( int menuType){
 		 		this.menuType = menuType ; 
 		 		 return this ;
 		}

 		public int getMenuType(){
 		 		return this.menuType;
 		}
    
     		public void setPerms( String perms){
 		 		this.perms = perms ; 
 		 		}
 		public SysPermissionResponseVo toPerms( String perms){
 		 		this.perms = perms ; 
 		 		 return this ;
 		}

 		public String getPerms(){
 		 		return this.perms;
 		}
    
     		public void setPermsType( int permsType){
 		 		this.permsType = permsType ; 
 		 		}
 		public SysPermissionResponseVo toPermsType( int permsType){
 		 		this.permsType = permsType ; 
 		 		 return this ;
 		}

 		public int getPermsType(){
 		 		return this.permsType;
 		}
    
     		public void setAlwaysShow( int alwaysShow){
 		 		this.alwaysShow = alwaysShow ; 
 		 		}
 		public SysPermissionResponseVo toAlwaysShow( int alwaysShow){
 		 		this.alwaysShow = alwaysShow ; 
 		 		 return this ;
 		}

 		public int getAlwaysShow(){
 		 		return this.alwaysShow;
 		}
    
     		public void setIcon( String icon){
 		 		this.icon = icon ; 
 		 		}
 		public SysPermissionResponseVo toIcon( String icon){
 		 		this.icon = icon ; 
 		 		 return this ;
 		}

 		public String getIcon(){
 		 		return this.icon;
 		}
    
     		public void setIsRoute( int isRoute){
 		 		this.isRoute = isRoute ; 
 		 		}
 		public SysPermissionResponseVo toIsRoute( int isRoute){
 		 		this.isRoute = isRoute ; 
 		 		 return this ;
 		}

 		public int getIsRoute(){
 		 		return this.isRoute;
 		}
    
     		public void setIsLeaf( int isLeaf){
 		 		this.isLeaf = isLeaf ; 
 		 		}
 		public SysPermissionResponseVo toIsLeaf( int isLeaf){
 		 		this.isLeaf = isLeaf ; 
 		 		 return this ;
 		}

 		public int getIsLeaf(){
 		 		return this.isLeaf;
 		}
    
     		public void setKeepAlive( int keepAlive){
 		 		this.keepAlive = keepAlive ; 
 		 		}
 		public SysPermissionResponseVo toKeepAlive( int keepAlive){
 		 		this.keepAlive = keepAlive ; 
 		 		 return this ;
 		}

 		public int getKeepAlive(){
 		 		return this.keepAlive;
 		}
    
     		public void setHidden( int hidden){
 		 		this.hidden = hidden ; 
 		 		}
 		public SysPermissionResponseVo toHidden( int hidden){
 		 		this.hidden = hidden ; 
 		 		 return this ;
 		}

 		public int getHidden(){
 		 		return this.hidden;
 		}
    
     		public void setDescription( String description){
 		 		this.description = description ; 
 		 		}
 		public SysPermissionResponseVo toDescription( String description){
 		 		this.description = description ; 
 		 		 return this ;
 		}

 		public String getDescription(){
 		 		return this.description;
 		}
    
     		public void setDelFlag( int delFlag){
 		 		this.delFlag = delFlag ; 
 		 		}
 		public SysPermissionResponseVo toDelFlag( int delFlag){
 		 		this.delFlag = delFlag ; 
 		 		 return this ;
 		}

 		public int getDelFlag(){
 		 		return this.delFlag;
 		}
    
     		public void setRuleFlag( int ruleFlag){
 		 		this.ruleFlag = ruleFlag ; 
 		 		}
 		public SysPermissionResponseVo toRuleFlag( int ruleFlag){
 		 		this.ruleFlag = ruleFlag ; 
 		 		 return this ;
 		}

 		public int getRuleFlag(){
 		 		return this.ruleFlag;
 		}
    






}
