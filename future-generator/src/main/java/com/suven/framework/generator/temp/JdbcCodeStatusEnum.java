package com.suven.framework.generator.temp;

/**
 * @Title: SysDepartFacade.java
 * @author suven
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description:  java jdbc类型不包括缓存实现的模块 实现类
 */


public enum JdbcCodeStatusEnum implements CreateCodeEnum{


    RPC_MODEL(1,"jdbc_cache_model.java.vm","/entity/",".java"),
    RPC_DAO(1,"jdbc_status_dao.java.vm","/dao/","Dao.java"),
//    RPC_CACHE("jdbc_status_cache.java.vm","/cache/","Cache.java"),
    RPC_SERVICE_IMPL(1,"jdbc_status_service_impl.java.vm","/service/","ServiceImpl.java"),

    //api service项目
    API_SERVICE(2,"jdbc_status_service.java.vm","/service/","Service.java"),
    API_DTO_REQUEST(2,"jdbc_cache_request_dto.java.vm","/dto/request/","RequestDto.java"),
    API_DTO_RESPONSE(2,"jdbc_cache_response_dto.java.vm","/dto/response/","ResponseDto.java"),

    //http service项目
    HTTP_VO_REQUEST(3,"jdbc_cache_request_vo.java.vm","/vo/request/","RequestVo.java"),
    HTTP_VO_REQUEST_ADD(3,"jdbc_cache_request_add_vo.java.vm","/vo/request/","AddRequestVo.java"),
    HTTP_VO_REQUEST_QUERY(3,"jdbc_cache_request_query_vo.java.vm","/vo/request/","QueryRequestVo.java"),
    HTTP_VO_RESPONSE(3,"jdbc_cache_response_vo.java.vm","/vo/response/","ResponseVo.java"),
    HTTP_VO_RESPONSE_SHOW(3,"jdbc_cache_response_show_vo.java.vm","/vo/response/","ShowResponseVo.java"),

    HTTP_FACADE(3,"jdbc_cache_facade.java.vm","/facade/","Facade.java"),
    HTTP_CONTROLLER(3,"jdbc_status_controller.java.vm","/controller/","Controller.java"),
    HTTP_CONTROLLER_WEB(3,"jdbc_status_controller_web.java.vm","/controller/","WebController.java"),
    ;

    private int index;
    private String temp;
    private String path;
    private String ext;
    private int isWrite;



    JdbcCodeStatusEnum(int index, String temp, String path, String extName){
        this.index = index;
        this.temp = temp;
        this.path = path;
        this.ext = extName;
    }

    JdbcCodeStatusEnum(int index, String temp, String path, String extName,int isWrite){
        this.index = index;
        this.temp = temp;
        this.path = path;
        this.ext = extName;
        this.isWrite =isWrite;
    }


//    private static Map<String, CreateCodeEnum> typeMap = new HashMap<>();
//    static {
//
//        for(CreateCodeEnum type : values()) {
//            typeMap.put(type.getTemp(), type);
//        }
//    }
//    public static CreateCodeEnum  getEnum(String tem) {
//        return typeMap.get(tem);
//    }



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

}
