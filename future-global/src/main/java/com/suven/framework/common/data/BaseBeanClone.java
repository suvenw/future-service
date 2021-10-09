package com.suven.framework.common.data;

import com.suven.framework.common.api.IBeanClone;

import java.io.Serializable;



/**
 * @author Joven.wang
 * @version V1.0
 * @Title: BaseAipEntity.java
 * @date 2016年2月20日
 * @Description: TODO(说明)
 * 提供 POJO Bean 对应的对象间,具有相同的方法的属性值,动态拷贝实现,但不支持builder类性的Bean对象属性拷贝;
 */
public abstract class BaseBeanClone  implements IBeanClone, Serializable {


    @Override
    public <T extends IBeanClone> T clone(Object source) {
        if(null == source){
            return null;
        }
        BeanCopierUtil.copy(source, this);
        return (T)this;
    }

}
