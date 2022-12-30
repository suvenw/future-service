package com.suven.framework.common.data;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.common.api.IBaseApi;
import com.suven.framework.common.api.IBeanClone;

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
public class BaseTimeEntity extends BaseBeanClone implements IBaseApi, IBeanClone {


    private static final long serialVersionUID = -5102197522565173276L;

    @ApiDesc(value =  "对应的业务主键值")
    @ExcelIgnore
    private long id; //表主键id;
    //	private long globalId; //全局id 用于分表分库使用
    @ApiDesc(value =  "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ExcelProperty("创建时间")
    private Date createTime; //创建时间;
    @ApiDesc(value =  "修改时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @ExcelProperty("修改时间")
    private Date updateTime; //修改时间;


    public BaseTimeEntity() {
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
}
