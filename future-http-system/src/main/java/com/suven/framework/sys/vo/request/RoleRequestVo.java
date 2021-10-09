package com.suven.framework.sys.vo.request;


import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.common.api.ApiDoc;
import com.suven.framework.http.data.vo.RequestParserVo;

import java.util.Date;

/**
 * @author xxx.xxx
 * @version V1.0.0
 * <p>
 * ----------------------------------------------------------------------------
 * modifyer    modifyTime                 comment
 * <p>
 * ----------------------------------------------------------------------------
 * </p>
 * @ClassName: RoleRequestVo.java
 * @Description: 角色表的数据交互处理类
 * @date 2019-11-21 15:22:59
 */
public class RoleRequestVo extends RequestParserVo {


    private long id;
    private int pageNo;
    private int pageSize;


    /**
     * roleName 角色名称
     */
    @ApiDesc(value = "角色名称", required = 0)
    private String roleName;

    /**
     * roleCode 角色编码
     */
    @ApiDesc(value = "角色编码", required = 0)
    private String roleCode;

    /**
     * description 描述
     */
    @ApiDesc(value = "描述", required = 0)
    private String description;

    @ApiDesc(value = "创建开始时间", required = 0)
    private Date createDateBegin;

    @ApiDesc(value = "创建结束时间", required = 0)
    private Date createDateEnd;


    public static RoleRequestVo build() {
        return new RoleRequestVo();
    }


    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public RoleRequestVo toRoleRequestVoName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public RoleRequestVo toRoleRequestVoCode(String roleCode) {
        this.roleCode = roleCode;
        return this;
    }

    public String getRoleCode() {
        return this.roleCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RoleRequestVo toDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDescription() {
        return this.description;
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

    public RoleRequestVo setPageNo(int pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public RoleRequestVo setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public Date getCreateDateBegin() {
        return createDateBegin;
    }

    public RoleRequestVo setCreateDateBegin(Date createDateBegin) {
        this.createDateBegin = createDateBegin;
        return this;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public RoleRequestVo setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
        return this;
    }

    @ApiDoc(value = "角色分页列表请求类")
    public static class RolePageListReqVo extends RequestParserVo {
		@ApiDesc(value = "页码  默认：第1页",required = 0)
		private int pageNo;
		@ApiDesc(value = "条数 默认：100条",required = 0)
		private int pageSize;
		@ApiDesc(value = "角色名称", required = 0)
		private String roleName;
		@ApiDesc(value = "创建开始时间", required = 0)
		private Date createDateBegin;
		@ApiDesc(value = "创建结束时间", required = 0)
		private Date createDateEnd;

		public static RolePageListReqVo build() {
			return new RolePageListReqVo();
		}

		public int getPageNo() {
			return pageNo;
		}

		public RolePageListReqVo setPageNo(int pageNo) {
			this.pageNo = pageNo;
			return this;
		}

		public int getPageSize() {
			return pageSize;
		}

		public RolePageListReqVo setPageSize(int pageSize) {
			this.pageSize = pageSize;
			return this;
		}

		public String getRoleName() {
			return roleName;
		}

		public RolePageListReqVo setRoleName(String roleName) {
			this.roleName = roleName;
			return this;
		}

		public Date getCreateDateBegin() {
			return createDateBegin;
		}

		public RolePageListReqVo setCreateDateBegin(Date createDateBegin) {
			this.createDateBegin = createDateBegin;
			return this;
		}

		public Date getCreateDateEnd() {
			return createDateEnd;
		}

		public RolePageListReqVo setCreateDateEnd(Date createDateEnd) {
			this.createDateEnd = createDateEnd;
			return this;
		}
	}
}
