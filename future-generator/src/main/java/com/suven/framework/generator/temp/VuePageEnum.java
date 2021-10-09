package com.suven.framework.generator.temp;



/**
 * @Title: JdbcCodeCacheEnum.java
 * @author suven
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description:  java jdbc类型包括缓存实现的的模块 实现类
 */


public enum VuePageEnum implements CreateCodeEnum{

    MENU_SQL_VM(13,"code_menu.sql.vm","db/menu/","_menu.sql"),

    //JEECG AntDesign vue项目
    PAGE_LIST_VUE(11,"code_page_list.vue.vm","/","List.vue"),
    PAGE_MODAL_VUE(11,"code_page_modal.vue.vm","/modules/","Modal.vue"),

    //renren Element vue项目
    RR_ADD_VUE(12,"code_add-or-update.vue.vm","/","-add-or-update.vue"),
    RR_INDEX_VUE(12,"code_index.vue.vm","/",".vue"),

    ;

    private int index;
    private String temp;
    private String path;
    private String ext;




    VuePageEnum(int index,String temp, String path, String ext){
        this.index = index;
        this.temp = temp;
        this.path = path;
        this.ext = ext;
    }

    public String getTemp() {
        return temp;
    }

    public String getPath() {
        return path;
    }

    public String getExt() {
        return ext;
    }

    public int getIndex(){
        return index;
//        switch (this){
//
//            case PAGE_MODAL_VUE:
//            case PAGE_LIST_VUE:
//
//                return 11;
//            case RR_INDEX_VUE:
//            case RR_ADD_VUE:
//                return 12;
//
//            case MENU_SQL_VM:
//                return 13;
//             default:
//                 return 0;
//
//        }
    }

}
