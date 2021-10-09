package com.suven.framework.sys.vo.request;


import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.http.data.vo.RequestParserVo;

/**
* @ClassName: SysRolePermissionRequestVo.java
* @Description: 角色权限表的数据交互处理类
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
public class SysRolePermissionRequestVo extends RequestParserVo {


        private long id;
        private int pageNo;
        private int pageSize;




 		/** roleId 角色id  */
 		@ApiDesc(value = "角色id", required = 0)
 		private long roleId;

 		/** permissionId 权限id  */
 		@ApiDesc(value = "权限id", required = 0)
 		private long permissionId;

 		/** dataRuleIds   */
 		@ApiDesc(value = "", required = 0)
 		private String dataRuleIds;





    public static SysRolePermissionRequestVo build(){
        return new SysRolePermissionRequestVo();
    }






 		public void setRoleId( long roleId){
 		 		this.roleId = roleId ; 
 		 		}
 		public SysRolePermissionRequestVo toRoleId( long roleId){
 		 		this.roleId = roleId ; 
 		 		 return this ;
 		}

 		public long getRoleId(){
 		 		return this.roleId;
 		}

 		public void setPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		}
 		public SysRolePermissionRequestVo toPermissionId( long permissionId){
 		 		this.permissionId = permissionId ; 
 		 		 return this ;
 		}

 		public long getPermissionId(){
 		 		return this.permissionId;
 		}

 		public void setDataRuleIds( String dataRuleIds){
 		 		this.dataRuleIds = dataRuleIds ; 
 		 		}
 		public SysRolePermissionRequestVo toDataRuleIds( String dataRuleIds){
 		 		this.dataRuleIds = dataRuleIds ; 
 		 		 return this ;
 		}

 		public String getDataRuleIds(){
 		 		return this.dataRuleIds;
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

        public  SysRolePermissionRequestVo setPageNo(int pageNo) {
            this.pageNo = pageNo;
            return this;
        }

        public int getPageSize() {
         return pageSize;
        }

        public  SysRolePermissionRequestVo setPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }


}
