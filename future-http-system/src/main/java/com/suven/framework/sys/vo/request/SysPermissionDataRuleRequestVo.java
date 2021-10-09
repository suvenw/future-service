package com.suven.framework.sys.vo.request;


import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.http.data.vo.RequestParserVo;

/**
* @ClassName: SysPermissionDataRuleRequestVo.java
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
public class SysPermissionDataRuleRequestVo extends RequestParserVo {


        private long id;
        private int pageNo;
        private int pageSize;






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





    public static SysPermissionDataRuleRequestVo build(){
        return new SysPermissionDataRuleRequestVo();
    }








 		public void setPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		}
 		public SysPermissionDataRuleRequestVo toPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		 return this ;
 		}

 		public long getPermissionId(){
 		 		return this.permissionId;
 		}

 		public void setRuleName( String ruleName){
 		 		this.ruleName = ruleName ; 
 		 		}
 		public SysPermissionDataRuleRequestVo toRuleName( String ruleName){
 		 		this.ruleName = ruleName ; 
 		 		 return this ;
 		}

 		public String getRuleName(){
 		 		return this.ruleName;
 		}

 		public void setRuleColumn( String ruleColumn){
 		 		this.ruleColumn = ruleColumn ; 
 		 		}
 		public SysPermissionDataRuleRequestVo toRuleColumn( String ruleColumn){
 		 		this.ruleColumn = ruleColumn ; 
 		 		 return this ;
 		}

 		public String getRuleColumn(){
 		 		return this.ruleColumn;
 		}

 		public void setRuleConditions( String ruleConditions){
 		 		this.ruleConditions = ruleConditions ; 
 		 		}
 		public SysPermissionDataRuleRequestVo toRuleConditions( String ruleConditions){
 		 		this.ruleConditions = ruleConditions ; 
 		 		 return this ;
 		}

 		public String getRuleConditions(){
 		 		return this.ruleConditions;
 		}

 		public void setRuleValue( String ruleValue){
 		 		this.ruleValue = ruleValue ; 
 		 		}
 		public SysPermissionDataRuleRequestVo toRuleValue( String ruleValue){
 		 		this.ruleValue = ruleValue ; 
 		 		 return this ;
 		}

 		public String getRuleValue(){
 		 		return this.ruleValue;
 		}



        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getPageNo() {
             return pageNo;
        }

        public  SysPermissionDataRuleRequestVo setPageNo(int pageNo) {
            this.pageNo = pageNo;
            return this;
        }

        public int getPageSize() {
         return pageSize;
        }

        public  SysPermissionDataRuleRequestVo setPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }


}
