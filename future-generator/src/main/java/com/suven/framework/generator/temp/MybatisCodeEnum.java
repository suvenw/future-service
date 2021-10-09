package com.suven.framework.generator.temp;


/**
 * @Title: MybatisCodeEnum.java
 * @author suven
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description:  java mybatis类型的模块 实现类
 */


public enum MybatisCodeEnum implements CreateCodeEnum{


    RPC_MODEL(1,"jdbc_cache_model.java.vm","/entity/",".java"),
    RPC_MAPPER(1,"mybatis_mapper.java.vm","/mapper/","Mapper.java"),
    RPC_MAPPER_XML(1,"mybatis_mapper.xml.vm","/mapper/","Mapper.xml"),
    RPC_DAO(1,"mybatis_dao.java.vm","/dao/","Dao.java"),
    RPC_SERVICE_IMPL(1,"mybatis_service_impl.java.vm","/service/","ServiceImpl.java"),

    //api service项目
    API_SERVICE(2,"mybatis_service.java.vm","/service/","Service.java"),
    API_DTO_QUERY_ENUM(2,"mybatis_query_enum.java.vm","/dto/enums/","QueryEnum.java"),
    API_DTO_REQUEST(2,"jdbc_cache_request_dto.java.vm","/dto/request/","RequestDto.java"),
    API_DTO_RESPONSE(2,"jdbc_cache_response_dto.java.vm","/dto/response/","ResponseDto.java"),

    //http service项目
    HTTP_VO_REQUEST(3,"jdbc_cache_request_vo.java.vm","/vo/request/","RequestVo.java"),
    HTTP_VO_REQUEST_ADD(3,"jdbc_cache_request_add_vo.java.vm","/vo/request/","AddRequestVo.java"),
    HTTP_VO_REQUEST_QUERY(3,"jdbc_cache_request_query_vo.java.vm","/vo/request/","QueryRequestVo.java"),
    HTTP_VO_RESPONSE(3,"jdbc_cache_response_vo.java.vm","/vo/response/","ResponseVo.java"),
    HTTP_VO_RESPONSE_SHOW(3,"jdbc_cache_response_show_vo.java.vm","/vo/response/","ShowResponseVo.java"),

    HTTP_FACADE(3,"jdbc_cache_facade.java.vm","/facade/","Facade.java"),
    HTTP_CONTROLLER(3,"mybatis_controller.java.vm","/controller/","Controller.java"),
    HTTP_CONTROLLER_WEB(3,"mybatis_controller_web.java.vm","/controller/","WebController.java"),
    ;

    private int index;
    private String temp;
    private String path;
    private String ext;

    MybatisCodeEnum(int index,String temp, String path, String extName){
        this.index = index;
        this.temp = temp;
        this.path = path;
        this.ext = extName;
    }
//    private static Map<String, MybatisCodeEnum> typeMap = new HashMap<>();
//    static {
//
//        for(MybatisCodeEnum type : values()) {
//            typeMap.put(type.getTemp(), type);
//        }
//    }
//    public static MybatisCodeEnum  getEnum(String tem) {
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
