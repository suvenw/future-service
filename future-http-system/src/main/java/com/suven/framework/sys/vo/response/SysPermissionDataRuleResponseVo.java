package com.suven.framework.sys.vo.response;


import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.common.data.BaseStatusEntity;

import java.io.Serializable;

/**
* @ClassName: SysPermissionDataRuleResponseVo.java
* @Description: 的数据交互处理类
* @author xxx.xxx
* @date   2019-11-25 19:45:26
* @version V1.0.0
* <p>
* ----------------------------------------------------------------------------
*  modifyer    modifyTime                 comment
*
* ----------------------------------------------------------------------------
* </p>
*/
public class SysPermissionDataRuleResponseVo  extends BaseStatusEntity implements Serializable {








 		/** permissionId 菜单ID  */
 		@ApiDesc(value = "菜单ID", required = 0)
 		private long permissionId;

 		/** ruleName 规则名称  */
 		@ApiDesc(value = "规则名称", required = 0)
 		private String ruleName;

 		/** ruleColumn 字段  */
 		@ApiDesc(value = "字段", required = 0)
 		private String ruleColumn;

 		/** ruleConditions 条件  */
 		@ApiDesc(value = "条件", required = 0)
 		private String ruleConditions;

 		/** ruleValue 规则值  */
 		@ApiDesc(value = "规则值", required = 0)
 		private String ruleValue;


    public static SysPermissionDataRuleResponseVo build(){
        return new SysPermissionDataRuleResponseVo();
    }

    
    
    
    
    
     		public void setPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		}
 		public SysPermissionDataRuleResponseVo toPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		 return this ;
 		}

 		public long getPermissionId(){
 		 		return this.permissionId;
 		}
    
     		public void setRuleName( String ruleName){
 		 		this.ruleName = ruleName ; 
 		 		}
 		public SysPermissionDataRuleResponseVo toRuleName( String ruleName){
 		 		this.ruleName = ruleName ; 
 		 		 return this ;
 		}

 		public String getRuleName(){
 		 		return this.ruleName;
 		}
    
     		public void setRuleColumn( String ruleColumn){
 		 		this.ruleColumn = ruleColumn ; 
 		 		}
 		public SysPermissionDataRuleResponseVo toRuleColumn( String ruleColumn){
 		 		this.ruleColumn = ruleColumn ; 
 		 		 return this ;
 		}

 		public String getRuleColumn(){
 		 		return this.ruleColumn;
 		}
    
     		public void setRuleConditions( String ruleConditions){
 		 		this.ruleConditions = ruleConditions ; 
 		 		}
 		public SysPermissionDataRuleResponseVo toRuleConditions( String ruleConditions){
 		 		this.ruleConditions = ruleConditions ; 
 		 		 return this ;
 		}

 		public String getRuleConditions(){
 		 		return this.ruleConditions;
 		}
    
     		public void setRuleValue( String ruleValue){
 		 		this.ruleValue = ruleValue ; 
 		 		}
 		public SysPermissionDataRuleResponseVo toRuleValue( String ruleValue){
 		 		this.ruleValue = ruleValue ; 
 		 		 return this ;
 		}

 		public String getRuleValue(){
 		 		return this.ruleValue;
 		}
    






}
