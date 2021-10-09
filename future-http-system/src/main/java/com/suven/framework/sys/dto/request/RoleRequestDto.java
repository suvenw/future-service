package com.suven.framework.sys.dto.request;


import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.common.data.BaseStatusEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
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
 * @ClassName: RoleRequestDto.java
 * @Description: 角色表的数据交互处理类
 * @date 2019-11-21 15:22:59
 */
public class RoleRequestDto extends BaseStatusEntity implements Serializable {


    private Logger logger = LoggerFactory.getLogger(RoleRequestDto.class);

    @ApiDesc(value = "角色名称", required = 0)
    private String roleName;
    @ApiDesc(value = "角色编码", required = 0)
    private String roleCode;
    @ApiDesc(value = "描述", required = 0)
    private String description;

    @ApiDesc(value = "创建开始时间", required = 0)
    private Date createDateBegin;
    @ApiDesc(value = "创建结束时间", required = 0)
    private Date createDateEnd;

    public static RoleRequestDto build() {
        return new RoleRequestDto();
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public RoleRequestDto toRoleRequestDtoName(String roleName) {
        this.roleName = roleName;
        return this;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public RoleRequestDto toRoleRequestDtoCode(String roleCode) {
        this.roleCode = roleCode;
        return this;
    }

    public String getRoleCode() {
        return this.roleCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RoleRequestDto toDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getCreateDateBegin() {
        return createDateBegin;
    }

    public void setCreateDateBegin(Date createDateBegin) {
        this.createDateBegin = createDateBegin;
    }
    public RoleRequestDto toCreateDateBegin(Date createDateBegin) {
        this.createDateBegin = createDateBegin;
        return this;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }
    public RoleRequestDto toCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
        return this;
    }
}
