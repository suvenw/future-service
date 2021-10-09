package com.suven.framework.generator.entity;

import java.util.ArrayList;
import java.util.List;

public class PageListEntity {

    /**
     * 是否显示新增
     */
    private List<PageFieldEntity> addList = new ArrayList<>();

    /**
     * 是否显示编辑
     */
    private List<PageFieldEntity> editList = new ArrayList<>();

    /**
     * 是否查询
     */
    private List<PageFieldEntity> queryList = new ArrayList<>();
    /**
     * 是否显示详情
     */
    private List<PageFieldEntity> detailList = new ArrayList<>();

    /**
     * 是否列表显示
     */
    private List<PageFieldEntity> showList = new ArrayList<>();

    /**
     * 是否Excel导入
     */
    private List<PageFieldEntity> importList = new ArrayList<>();

    /**
     * 是否导出Excel
     */
    private List<PageFieldEntity> exportList = new ArrayList<>();

    public List<PageFieldEntity> getAddList() {
        return addList;
    }

    public void setAddList(List<PageFieldEntity> addList) {
        this.addList = addList;
    }

    public List<PageFieldEntity> getEditList() {
        return editList;
    }

    public void setEditList(List<PageFieldEntity> editList) {
        this.editList = editList;
    }

    public List<PageFieldEntity> getQueryList() {
        return queryList;
    }

    public void setQueryList(List<PageFieldEntity> queryList) {
        this.queryList = queryList;
    }

    public List<PageFieldEntity> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<PageFieldEntity> detailList) {
        this.detailList = detailList;
    }

    public List<PageFieldEntity> getShowList() {
        return showList;
    }

    public void setShowList(List<PageFieldEntity> showList) {
        this.showList = showList;
    }

    public List<PageFieldEntity> getImportList() {
        return importList;
    }

    public void setImportList(List<PageFieldEntity> importList) {
        this.importList = importList;
    }

    public List<PageFieldEntity> getExportList() {
        return exportList;
    }

    public void setExportList(List<PageFieldEntity> exportList) {
        this.exportList = exportList;
    }
}
