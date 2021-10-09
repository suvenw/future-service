package com.suven.framework.generator.utils;

import com.suven.framework.generator.entity.ColumnClassEntity;
import com.suven.framework.generator.entity.PageFieldEntity;

public class GeneratorGetSetBuild {


    static String NTT_DEFINITION = "\n \t\t";
    static String TT_DEFINITION = " \t\t";

    /**
     * 通过数据库字段和类型生成java类对应的属性
     * @param columnEntity
     * @param className
     */
    public static void convertGetSetBuildMethod(ColumnClassEntity columnEntity, String className){


        //属性 注解信息
        String explain = composeExplain(columnEntity.getColumnName(),columnEntity.getComments());
        columnEntity.setExplainAttrName(explain);

        //doc 文档
        String doc =  composeApiDoc(columnEntity.getComments(), 0);
        columnEntity.setDocAttrName(doc);

        //excel 导出支持
        String excel = composeExcelProperty(columnEntity.getComments());
        columnEntity.setExcelAttrName(excel);

        //字段属性 private int id;
        String field =  composeFieldProperty(columnEntity.getAttrType(),columnEntity.getAttrname());
        columnEntity.setFieldAttrName(field);

        //set method eg: public void setId(int id){ this.id  = id;}
        String setMethod = composeSetMethod(columnEntity.getAttrType(),columnEntity.getAttrName(),columnEntity.getAttrname());
        columnEntity.setSetAttrName(setMethod);

        //get method eg: public int getId(){  return this.id ;}
        String getMethod = composeGetMethod(columnEntity.getAttrType(),columnEntity.getAttrName(),columnEntity.getAttrname());
        columnEntity.setGetAttrName(getMethod);

        //build method  eg: public Entity toId(int id){ this.id  = id; return this;}
        String toBuildMethod = composeToBuildMethod(className,columnEntity.getAttrType(),columnEntity.getAttrName(),columnEntity.getAttrname());
        columnEntity.setBuildAttrName(toBuildMethod);
        String clazz = className;

        columnEntity.setBuildRequestVoName(columnEntity.getBuildAttrName().replaceAll(clazz, clazz + "RequestVo"));
        columnEntity.setBuildResponseVoName(columnEntity.getBuildAttrName().replaceAll(clazz, clazz + "ResponseVo"));
        columnEntity.setBuildRequestDtoName(columnEntity.getBuildAttrName().replaceAll(clazz, clazz + "RequestDto"));
        columnEntity.setBuildResponseDtoName(columnEntity.getBuildAttrName().replaceAll(clazz, clazz + "ResponseDto"));

    }

    /**
     * 通过页面选择的字段和类型生成java类对应的属性
     * @param pageFieldEntity
     * @param className
     */
    public static void convertGetSetBuildMethodFromPage(PageFieldEntity pageFieldEntity, String className){

        int require = pageFieldEntity.getNotNull() == null ? 0 : pageFieldEntity.getNotNull();
        //属性 注解信息
        String explain = composeExplain(pageFieldEntity.getColumnName(),pageFieldEntity.getComments());
        pageFieldEntity.setExplainAttrName(explain);
        //doc 文档
        String doc =  composeApiDoc(pageFieldEntity.getComments(), require);
        pageFieldEntity.setDocAttrName(doc);

        //excel 导出支持
        String excel = composeExcelProperty(pageFieldEntity.getComments());
        pageFieldEntity.setExcelAttrName(excel);

        //字段属性 private int id;
        String field =  composeFieldProperty(pageFieldEntity.getAttrType(),pageFieldEntity.getAttrname());
        pageFieldEntity.setFieldAttrName(field);

        //set method eg: public void setId(int id){ this.id  = id;}
        String setMethod = composeSetMethod(pageFieldEntity.getAttrType(),pageFieldEntity.getAttrName(),pageFieldEntity.getAttrname());
        pageFieldEntity.setSetAttrName(setMethod);

        //get method eg: public int getId(){  return this.id ;}
        String getMethod = composeGetMethod(pageFieldEntity.getAttrType(),pageFieldEntity.getAttrName(),pageFieldEntity.getAttrname());
        pageFieldEntity.setGetAttrName(getMethod);

        //build method  eg: public Entity toId(int id){ this.id  = id; return this;}
        String toBuildMethod = composeToBuildMethod(className,pageFieldEntity.getAttrType(),pageFieldEntity.getAttrName(),pageFieldEntity.getAttrname());
        pageFieldEntity.setBuildAttrName(toBuildMethod);

        pageFieldEntity.setBuildRequestAddVoName(pageFieldEntity.getBuildAttrName().replaceAll(className, className + "AddRequestVo"));
        pageFieldEntity.setBuildRequestQueryVoName(pageFieldEntity.getBuildAttrName().replaceAll(className, className + "QueryRequestVo"));

        pageFieldEntity.setBuildResponseShowVoName(pageFieldEntity.getBuildAttrName().replaceAll(className, className + "ShowResponseVo"));

    }
    /**
     * 生成描述,属性 注解信息
     * @param attrname 属性
     * @param comments 注解信息
     * @return
     */
    private static String composeExplain(String attrname,String  comments){
        StringBuilder sb = new StringBuilder();
        sb.append(TT_DEFINITION).append("/** ").append(attrname).append(" ");
        sb.append(comments);//描述
        sb.append(" ").append(" */");
       return sb.toString();
    }

    /**
     * 生成文档信息
     * @param comments
     * @param required
     * @return
     */
    private static String composeApiDoc(String  comments, int required ){
        StringBuilder sb = new StringBuilder();
        sb.append(TT_DEFINITION);
        sb.append("@ApiDesc(value = \"").append(comments).append("\"");
        sb.append(", ").append("required = ").append(required);
        sb.append(")");
        return sb.toString();
    }

    /**
     * excel 导出支持
     * 生成导出信息
     * @param comments
     * @return
     */
    private static String composeExcelProperty(String  comments ) {
        StringBuilder sb = new StringBuilder();
        sb.append(TT_DEFINITION);
        sb.append("@ExcelProperty(value = \"").append(comments).append("\"");
        sb.append(")");
        return sb.toString();

    }

    /**
     * 生成类 字段field 属性
     * 字段属性 private int id;
     * @param attrType 字段数据类型
     * @param attrname 字段名称
     * @return
     */
    private static String composeFieldProperty(String  attrType,String  attrname ) {
        //字段属性 private int id;
        StringBuilder sb = new StringBuilder();
        sb.append(TT_DEFINITION);
        sb.append("private ").append(attrType).append(" ").append(attrname).append(";");
        return sb.toString();

    }

    /**
     * 生成类 setter 方法
     * set method eg: public void setId(int id){ this.id  = id;}
     * @return
     */
    private static String composeSetMethod(String attrType,String attrName, String attrname ){
        StringBuilder sb = new StringBuilder();
        sb.append(TT_DEFINITION)
                .append("public").append(" void ").append("set").append(attrName)
                .append("( ") .append(attrType) .append(" ").append(attrname).append("){")
                .append(NTT_DEFINITION).append(TT_DEFINITION)
                .append("this.").append(attrname).append(" = ").append(attrname).append(" ; ")
                .append(NTT_DEFINITION).append(TT_DEFINITION)
                .append("}");
        return sb.toString();
    }
    /**
     * 生成类 getter 方法
     * get method eg: public int getId(){  return this.id ;}
     * @param attrType  字段的数据类型
     * @param attrName  字段首字段大写
     * @param attrname  字段首字段小写
     * @return
     */
    private static String composeGetMethod(String attrType,String attrName, String attrname ){
        StringBuilder sb = new StringBuilder();
        sb.append(NTT_DEFINITION)
                .append("public ").append(attrType).append(" get").append(attrName).append("(){")
                .append(NTT_DEFINITION).append(TT_DEFINITION)
                .append("return this.").append(attrname).append(";")
                .append(NTT_DEFINITION)
                .append("}");
        return sb.toString();
    }

    /**
     * 生成类 toBuild 方法
     * build method  eg: public Entity toId(int id){ this.id  = id; return this;}
     * @param className 生成的java 类名称
     * @param attrType  字段的数据类型
     * @param attrName  字段首字段大写
     * @param attrname  字段首字段小写
     * @return
     */
    private static String composeToBuildMethod(String className, String attrType,String attrName, String attrname ){
        //build method  eg: public Entity toId(int id){ this.id  = id; return this;}
        StringBuilder sb = new StringBuilder();
        sb.append(TT_DEFINITION)
                .append("public ").append(className).append(" to").append(attrName)
                .append("( ") .append(attrType) .append(" ").append(attrname).append("){")
                .append(NTT_DEFINITION).append(TT_DEFINITION)
                .append("this.").append(attrname).append(" = ").append(attrname).append(" ; ")
                .append(NTT_DEFINITION).append(TT_DEFINITION)
                .append(" return this ;").append(NTT_DEFINITION)
                .append("}");
        return sb.toString();
    }

}
