package com.suven.framework.common.data;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.common.api.IBaseApi;
import com.suven.framework.common.api.IBeanClone;

import java.io.Serializable;
import java.util.Date;


/**
 * @author Joven.wang
 * @version V1.0
 * @Title: BaseEnter.java
 * @date 2016年2月20日
 * @Description: TODO(说明)
 * 提供 POJO Bean 对应的对象间,具有相同的方法的属性值,动态拷贝实现,但不支持builder类性的Bean对象属性拷贝;
 * 提供基本的主健id, 创建时间(createDate) ,修改时间(modifyDate) 的基础实体类
 */
public class BaseByTimeEntity extends BaseBeanClone implements IBaseApi, IBeanClone, Serializable {


    private static final long serialVersionUID = -5102197522565173276L;

    @ApiDesc(value =  "对应的业务主键值")
    private long id; //表主键id;
    //	private long globalId; //全局id 用于分表分库使用
    @ApiDesc(value =  "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime; //创建时间;
    @ApiDesc(value =  "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime; //修改时间;

    @ApiDesc(value =  "创建人")
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy; //创建人;
    @ApiDesc(value =  "创建人登录名称")
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy; //修改人;


    public BaseByTimeEntity() {
        super();
        this.createTime = this.updateTime = new Date();
    }


    @Override
    public long getId() {
        return id;
    }

    public String getEsId(){
        return String.valueOf(id);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public  <T extends IBaseApi > T toId(Long id) {
        this.id = id;
        return(T) this;
    }



    public IBaseApi toCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public IBaseApi toUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }



    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public IBaseApi toCreateBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    public IBaseApi toUpdateBy(String updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
