package com.suven.framework.core.db.mybatisplus;


import com.baomidou.mybatisplus.core.metadata.IPage;

public interface IPageExt<T> extends IPage<T> {


    /**
     * 是否存在上一页
     *
     * @return true / false
     */
    boolean hasPrevious() ;

    /**
     * 是否存在下一页
     *
     * @return true / false
     */
    boolean hasNext() ;

    /**
     * 当前分页条数大小值+1,用于判断是否有下一页使用,只能在limit后面使用
     *
     * @return 条数大小值+1
     */
    long hasNextSize() ;
}
