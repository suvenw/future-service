package com.suven.framework.sys.vo.request;


import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.http.data.vo.RequestParserVo;

/**
* @ClassName: SysUserRoleRequestVo.java
* @Description: 用户角色表的数据交互处理类
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
public class SysUserRoleRequestVo extends RequestParserVo {


        private long id;
        private int pageNo;
        private int pageSize;




 		/** userId 用户id  */
 		@ApiDesc(value = "用户id", required = 0)
 		private long userId;

 		/** roleId 角色id  */
 		@ApiDesc(value = "角色id", required = 0)
 		private long roleId;

        /** username 用户名称username  */
        @ApiDesc(value = "用户名称username", required = 0)
        private String username;




    public static SysUserRoleRequestVo build(){
        return new SysUserRoleRequestVo();
    }






 		public void setUserId( long userId){
 		 		this.userId = userId ; 
 		 		}
 		public SysUserRoleRequestVo toUserId( long userId){
 		 		this.userId = userId ; 
 		 		 return this ;
 		}

 		public long getUserId(){
 		 		return this.userId;
 		}

 		public void setRoleId( long roleId){
 		 		this.roleId = roleId ; 
 		 		}
 		public SysUserRoleRequestVo toRoleId( long roleId){
 		 		this.roleId = roleId ; 
 		 		 return this ;
 		}

 		public long getRoleId(){
 		 		return this.roleId;
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

        public  SysUserRoleRequestVo setPageNo(int pageNo) {
            this.pageNo = pageNo;
            return this;
        }

        public int getPageSize() {
         return pageSize;
        }

        public  SysUserRoleRequestVo setPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
}
