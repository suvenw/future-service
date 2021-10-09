package com.suven.framework.sys.vo.response;


import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.common.api.ApiDoc;
import com.suven.framework.common.data.BaseStatusEntity;
import com.suven.framework.sys.entity.SysDepart;

import java.io.Serializable;

/**
 * @author xxx.xxx
 * @version V1.0.0
 * <p>
 * ----------------------------------------------------------------------------
 * modifyer    modifyTime                 comment
 * <p>
 * ----------------------------------------------------------------------------
 * </p>
 * @ClassName: SysDepartResponseVo.java
 * @Description: 部门组织机构表的数据交互处理类
 * @date 2019-10-18 12:35:25
 */
public class SysDepartResponseVo extends BaseStatusEntity implements Serializable {


    /**
     * parentId 父机构ID
     */
    @ApiDesc(value = "父机构ID", required = 0)
    private long parentId;

    /**
     * departName 机构/部门名称
     */
    @ApiDesc(value = "机构/部门名称", required = 0)
    private String departName;

    /**
     * departNameEn 英文名
     */
    @ApiDesc(value = "英文名", required = 0)
    private String departNameEn;

    /**
     * departNameAbbr 缩写
     */
    @ApiDesc(value = "缩写", required = 0)
    private String departNameAbbr;

    /**
     * description 描述
     */
    @ApiDesc(value = "描述", required = 0)
    private String description;

    /**
     * orgType 机构类型 1一级部门 2子部门
     */
    @ApiDesc(value = "机构类型 1一级部门 2子部门", required = 0)
    private int orgType;

    /**
     * orgCode 机构编码
     */
    @ApiDesc(value = "机构编码", required = 0)
    private String orgCode;

    /**
     * mobile 手机号
     */
    @ApiDesc(value = "手机号", required = 0)
    private String mobile;

    /**
     * fax 传真
     */
    @ApiDesc(value = "传真", required = 0)
    private String fax;

    /**
     * address 地址
     */
    @ApiDesc(value = "地址", required = 0)
    private String address;

    /**
     * remarks 备注
     */
    @ApiDesc(value = "备注", required = 0)
    private String remarks;


    public static SysDepartResponseVo build() {
        return new SysDepartResponseVo();
    }


    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public SysDepartResponseVo toParentId(long parentId) {
        this.parentId = parentId;
        return this;
    }

    public long getParentId() {
        return this.parentId;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public SysDepartResponseVo toDepartName(String departName) {
        this.departName = departName;
        return this;
    }

    public String getDepartName() {
        return this.departName;
    }

    public void setDepartNameEn(String departNameEn) {
        this.departNameEn = departNameEn;
    }

    public SysDepartResponseVo toDepartNameEn(String departNameEn) {
        this.departNameEn = departNameEn;
        return this;
    }

    public String getDepartNameEn() {
        return this.departNameEn;
    }

    public void setDepartNameAbbr(String departNameAbbr) {
        this.departNameAbbr = departNameAbbr;
    }

    public SysDepartResponseVo toDepartNameAbbr(String departNameAbbr) {
        this.departNameAbbr = departNameAbbr;
        return this;
    }

    public String getDepartNameAbbr() {
        return this.departNameAbbr;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SysDepartResponseVo toDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public void setOrgType(int orgType) {
        this.orgType = orgType;
    }

    public SysDepartResponseVo toOrgType(int orgType) {
        this.orgType = orgType;
        return this;
    }

    public int getOrgType() {
        return this.orgType;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public SysDepartResponseVo toOrgCode(String orgCode) {
        this.orgCode = orgCode;
        return this;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public SysDepartResponseVo toMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public SysDepartResponseVo toFax(String fax) {
        this.fax = fax;
        return this;
    }

    public String getFax() {
        return this.fax;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public SysDepartResponseVo toAddress(String address) {
        this.address = address;
        return this;
    }

    public String getAddress() {
        return this.address;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public SysDepartResponseVo toRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public String getRemarks() {
        return this.remarks;
    }


    /**
     * * 部门表 封装树结构的部门的名称的实体类
     */
    @ApiDoc(module = "部门名返回类")
    public static class DepartTreeRespVo extends BaseStatusEntity implements Serializable {
        private static final long serialVersionUID = 1L;

        // 主键ID
        @ApiDesc("部门表ID")
        private long key;

        // 主键ID
        @ApiDesc("部门表ID")
        private long value;

        // 部门名称
        @ApiDesc("部门名称")
        private String title;

        public static DepartTreeRespVo build() {
            return new DepartTreeRespVo();
        }

        /**
         * 该方法为用户部门的实现类所使用
         *
         * @param sysDepart
         * @return
         */
        public DepartTreeRespVo convertByUserDepart(SysDepart sysDepart) {
            this.key = sysDepart.getId();
            this.value = sysDepart.getId();
            this.title = sysDepart.getDepartName();
            return this;
        }


        public long getKey() {
            return key;
        }

        public DepartTreeRespVo setKey(long key) {
            this.key = key;
            return this;
        }

        public long getValue() {
            return value;
        }

        public DepartTreeRespVo setValue(long value) {
            this.value = value;
            return this;
        }

        public String getTitle() {
            return title;
        }

        public DepartTreeRespVo setTitle(String title) {
            this.title = title;
            return this;
        }
    }


}
