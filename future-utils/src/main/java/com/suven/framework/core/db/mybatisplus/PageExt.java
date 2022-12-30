package com.suven.framework.core.db.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public class PageExt<T> extends Page<T> implements IPageExt<T>{
    public PageExt() {
        super();
    }

    public PageExt(long current, long size) {
        super(current, size);
    }

    public PageExt(long current, long size, long total) {
        super(current, size, total);
    }

    public PageExt(long current, long size, boolean isSearchCount) {
        super(current, size, isSearchCount);
    }

    public PageExt(long current, long size, long total, boolean isSearchCount) {
        super(current, size, total, isSearchCount);
    }

    @Override
    public boolean hasPrevious() {
        return super.hasPrevious();
    }

    @Override
    public boolean hasNext() {
        if(!isSearchCount()){
            long recordsSize =  super.getRecords().size();
            return recordsSize == hasNextSize();
        }
        return super.hasNext();

    }

    @Override
    public long getSize() {
        if(!isSearchCount()){
            return super.getSize();
        }else {
          return hasNextSize();
        }

    }

    @Override
    public List<T> getRecords() {
        if(!isSearchCount()){
            if(hasNext()){
               int index =  super.getRecords().size();
                List<T> recordList =  super.getRecords();
                recordList.remove(index);
                return recordList;
            }
        }
        return super.getRecords();

    }

    /**
     * 当前分页条数大小值+1,用于判断是否有下一页使用,只能在limit后面使用
     *
     * @return 条数大小值+1
     */
    @Override
    public long hasNextSize() {
        return this.getSize()+1;
    }
}
