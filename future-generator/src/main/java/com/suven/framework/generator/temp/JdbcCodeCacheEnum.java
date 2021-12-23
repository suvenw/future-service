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


public enum JdbcCodeCacheEnum implements CreateCodeEnum{


    //rpc model 项目

    RPC_MODEL(1,"jdbc_cache_model.java.vm","/entity/",".java"),
    RPC_DAO(1,"jdbc_cache_dao.java.vm","/dao/","Dao.java"),
    RPC_CACHE(1,"jdbc_cache_cache.java.vm","/cache/","Cache.java"),
    RPC_SERVICE_IMPL(1,"jdbc_cache_service_impl.java.vm","/service/","ServiceImpl.java"),

    //api service项目
    API_SERVICE(2,"jdbc_cache_service.java.vm","/service/","Service.java"),
    API_DTO_REQUEST(2,"jdbc_cache_request_dto.java.vm","/dto/request/","RequestDto.java"),
    API_DTO_RESPONSE(2,"jdbc_cache_response_dto.java.vm","/dto/response/","ResponseDto.java"),

    //http service项目
    HTTP_VO_REQUEST(3,"jdbc_cache_request_vo.java.vm","/vo/request/","RequestVo.java"),
    HTTP_VO_REQUEST_ADD(3,"jdbc_cache_request_add_vo.java.vm","/vo/request/","AddRequestVo.java"),
    HTTP_VO_REQUEST_QUERY(3,"jdbc_cache_request_query_vo.java.vm","/vo/request/","QueryRequestVo.java"),
    HTTP_VO_RESPONSE(3,"jdbc_cache_response_vo.java.vm","/vo/response/","ResponseVo.java"),
    HTTP_VO_RESPONSE_SHOW(3,"jdbc_cache_response_show_vo.java.vm","/vo/response/","ShowResponseVo.java"),

    HTTP_FACADE(3,"jdbc_cache_facade.java.vm","/facade/","Facade.java"),
    HTTP_CONTROLLER(3,"jdbc_cache_controller.java.vm","/controller/","Controller.java"),
    HTTP_CONTROLLER_WEB(3,"jdbc_cache_controller_web.java.vm","/controller/","WebController.java"),
    ;

    private int index;
    private String temp;
    private String path;
    private String ext;
    private int isWrite;

//    private static Map<String, JdbcCodeCacheEnum> typeMap = new HashMap<>();
//    static {
//
//        for(JdbcCodeCacheEnum type : values()) {
//            typeMap.put(type.getTemp(), type);
//        }
//    }
//    public static JdbcCodeCacheEnum  getEnum(String tem) {
//        return typeMap.get(tem);
//    }
//


    JdbcCodeCacheEnum(int index,String temp, String path, String ext){
        this.index = index;
        this.temp = temp;
        this.path = path;
        this.ext = ext;
    }

    JdbcCodeCacheEnum(int index,String temp, String path, String ext,int isWrite){
        this.index = index;
        this.temp = temp;
        this.path = path;
        this.ext = ext;
        this.isWrite = isWrite;
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
    }

    public  int isWrite(){ return isWrite;}

}
