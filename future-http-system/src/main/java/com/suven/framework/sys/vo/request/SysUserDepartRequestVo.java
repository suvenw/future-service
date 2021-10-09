package com.suven.framework.sys.vo.request;


import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.http.data.vo.RequestParserVo;

/**
* @ClassName: SysUserDepartRequestVo.java
* @Description: 用户部门表的数据交互处理类
* @author xxx.xxx
* @date   2019-11-27 17:49:58
* @version V1.0.0
* <p>
    * ----------------------------------------------------------------------------
    *  modifyer    modifyTime                 comment
    *
    * ----------------------------------------------------------------------------
    * </p>
*/
public class SysUserDepartRequestVo extends RequestParserVo {


        private long id;
        private int pageNo;
        private int pageSize;


 		/** userId 用户id  */
 		@ApiDesc(value = "用户id", required = 0)
 		private long userId;

 		/** depId 部门id  */
 		@ApiDesc(value = "部门id", required = 0)
 		private long depId;


        /** userName 用户名称  */
        @ApiDesc(value = "用户名称", required = 0)
        private String username;

        /** roleId 角色id  */
        @ApiDesc(value = "角色id", required = 0)
        private long roleId;






    public static SysUserDepartRequestVo build(){
        return new SysUserDepartRequestVo();
    }




 		public void setUserId( long userId){
 		 		this.userId = userId ; 
 		 		}
 		public SysUserDepartRequestVo toUserId( long userId){
 		 		this.userId = userId ; 
 		 		 return this ;
 		}

 		public long getUserId(){
 		 		return this.userId;
 		}

 		public void setDepId( long depId){
 		 		this.depId = depId ; 
 		 		}
 		public SysUserDepartRequestVo toDepId( long depId){
 		 		this.depId = depId ; 
 		 		 return this ;
 		}

 		public long getDepId(){
 		 		return this.depId;
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

        public  SysUserDepartRequestVo setPageNo(int pageNo) {
            this.pageNo = pageNo;
            return this;
        }

        public int getPageSize() {
         return pageSize;
        }

        public  SysUserDepartRequestVo setPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public String getUsername() {
            return username;
        }

        public SysUserDepartRequestVo setUsername(String username) {
            this.username = username;
            return this;
        }

        public long getRoleId() {
            return roleId;
        }

        public void setRoleId(long roleId) {
            this.roleId = roleId;
        }
}
