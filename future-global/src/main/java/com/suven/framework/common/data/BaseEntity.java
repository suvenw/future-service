package com.suven.framework.common.data;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.common.api.IBaseApi;
import com.suven.framework.common.api.IBeanClone;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author Joven.wang
 * @version V1.0
 * @Title: BaseEnter.java
 * @date 2016年2月20日
 * @Description: TODO(说明)
 * 提供 POJO Bean 对应的对象间,具有相同的方法的属性值,动态拷贝实现,但不支持builder类性的Bean对象属性拷贝;
 * 提供基本的主健id, 创建时间(createDate) ,修改时间(modifyDate) 的基础实体类
 */
public class BaseEntity extends BaseBeanClone implements IBaseApi, IBeanClone {


    private static final long serialVersionUID = -5102197522565173276L;

    @ApiDesc(value =  "对应的业务主键值")
    @ExcelIgnore
    private long id; //表主键id;
    //	private long globalId; //全局id 用于分表分库使用
    @ApiDesc(value =  "创建时间")
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    @ExcelProperty("创建时间")
    private Date createDate; //创建时间;
    @ApiDesc(value =  "修改时间")
    @TableField(value = "modify_date", fill = FieldFill.INSERT_UPDATE)
    @ExcelProperty("修改时间")
    private Date modifyDate; //修改时间;


    public BaseEntity() {
        super();
        this.modifyDate = this.createDate = new Date();
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public IBaseApi toCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public Date getModifyDate() {
        if(null == modifyDate ){
            modifyDate = new Date();
        }
        return modifyDate;
    }
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }


    public IBaseApi toModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
        return this;
    }
}
