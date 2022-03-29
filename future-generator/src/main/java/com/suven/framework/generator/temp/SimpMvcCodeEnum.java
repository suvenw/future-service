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


public enum SimpMvcCodeEnum implements CreateCodeEnum{


    //rpc
    RPC_MODEL(1,"simp_mvc_model.java.vm","/entity/",".java",1),
    RPC_MAPPER(1,"simp_mvc_mapper.java.vm","/mapper/","Mapper.java"),
    RPC_MAPPER_XML(1,"simp_mvc_mapper.xml.vm","/mapper/","Mapper.xml"),
    RPC_SERVICE_IMPL(1,"simp_mvc_service_impl.java.vm","/service/","ServiceImpl.java"),

    //api service项目
    API_SERVICE(2,"simp_mvc_service.java.vm","/service/","Service.java"),
    API_DTO_QUERY_ENUM(2,"simp_mvc_query_enum.java.vm","/dto/enums/","QueryEnum.java"),

    //接口api
    HTTP_VO_REQUEST(2,"simp_mvc_request_vo.java.vm","/vo/request/","RequestVo.java",1),
    HTTP_VO_RESPONSE(2,"simp_mvc_response_vo.java.vm","/vo/response/","ResponseVo.java",1),
    //后台sys api
    HTTP_VO_REQUEST_ADD(2,"simp_mvc_request_add_vo.java.vm","/vo/request/","AddRequestVo.java",1),



    //http service项目
    HTTP_FACADE(3,"simp_mvc_facade.java.vm","/facade/","Facade.java"),
//    HTTP_CONTROLLER(3,"mvc_controller.java.vm","/controller/","Controller.java"),

    //后台管理系统
    HTTP_CONTROLLER_WEB(3,"simp_mvc_controller_web.java.vm","/controller/","WebController.java"),
    ;

    private int index;
    private String temp;
    private String path;
    private String ext;
    private int isWrite;

    SimpMvcCodeEnum(int index, String temp, String path, String extName){
        this.index = index;
        this.temp = temp;
        this.path = path;
        this.ext = extName;
    }
    SimpMvcCodeEnum(int index, String temp, String path, String extName, int isWrite){
        this.index = index;
        this.temp = temp;
        this.path = path;
        this.ext = extName;
        this.isWrite = isWrite;
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

    public  int isWrite(){ return isWrite;}
}
