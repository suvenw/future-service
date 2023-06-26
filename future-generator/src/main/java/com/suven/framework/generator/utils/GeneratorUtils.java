package com.suven.framework.generator.utils;

import com.alibaba.fastjson.JSON;
import com.suven.framework.generator.config.ProjectPathConfig;
import com.suven.framework.generator.config.SysDataConfig;
import com.suven.framework.generator.entity.*;
import com.suven.framework.generator.temp.MybatisCodeEnum;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.app.Velocity;
import com.suven.framework.generator.enums.BaseEntityEnum;
import com.suven.framework.generator.enums.PageShowTypeEnum;
import com.suven.framework.generator.enums.QueryTypeEnum;
import com.suven.framework.generator.enums.RegexEnum;
import com.suven.framework.generator.temp.CreateCodeEnum;
import com.suven.framework.generator.temp.VuePageEnum;

import java.util.*;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 *
 * @author suven
 * @email suvenw@gmail.com
 * @date 2016年12月19日 下午11:40:24
 */
public class GeneratorUtils {

//    public static List<String> getTemplates(){
//        List<String> templates = new ArrayList<String>();
////        templates.add("template/Entity.java.vm");
////        templates.add("template/Dao.java.vm");
////        templates.add("template/Dao.xml.vm");
////        templates.add("template/Service.java.vm");
////        templates.add("template/ServiceImpl.java.vm");
////        templates.add("template/Controller.java.vm");
//        templates.add("template/menu.sql.vm");
//
//        templates.add("template/index.vue.vm");
//        templates.add("template/add-or-update.vue.vm");
//
//        templates.add("template/code_page_modal.vue.vm");
//        templates.add("template/code_page_list.vue.vm");
//
//        return templates;
//    }
    private final static String UTF_8 = "UTF-8";
    private final static String js_messageWarning = "this.$message.warning";
    private final static String js_successMsg = "that.$message.success(res.message);";
    private final static String js_warningMsg = "that.$message.warning(res.message);";
    private final static String js_fileSuccessMsg = "this.$message.success(info.file.name+'文件上传成功');";
    private final static String js_fileWarningMsg = "this.$message.error(info.file.name+'文件上传失败');";

    private final static String handleSubmit = "this.$refs.form.validate(valid=>{\n" +
            "          if(valid){\n" +
            "          this.confirmLoading = true;\n" +
            "          let obj;\n" +
            "          if(!this.model.id){\n" +
            "          obj=postAction(this.url.add,this.model);\n" +
            "          }else{\n" +
            "          obj=postAction(this.url.edit,this.model);\n" +
            "          }\n" +
            "          obj.then((res)=>{\n" +
            "          if(res.success){\n" +
            "          this.$message.success(res.message);\n" +
            "          this.$emit('ok');\n" +
            "          }else{\n" +
            "          this.$message.warning(res.message);\n" +
            "          }\n" +
            "          }).finally(() => {\n" +
            "          this.confirmLoading = false;\n" +
            "          this.close();\n" +
            "          })\n" +
            "          }else{\n" +
            "          return false;\n" +
            "          }\n" +
            "          })";

    private static List<String> excludeFieldNameList = new ArrayList<>(Arrays.asList("id","createDate","modifyDate"));



    /**
     * 生成代码
     */
    public static void generatorCode(GeneratorInfo generatorInfo, SysDataConfig sysDataConfig, ZipOutputStream zip) {

        boolean hasBigDecimal = false;


        TableBean tableBean = TableBean.build();
        tableBean .setInsertDbList("").setUpdateDbList("");
        //过滤列信息
        Set<String>excludeFieldList  = new HashSet<>(excludeFieldNameList);
        BaseEntityEnum entityEnum = sysDataConfig.getBaseEntityNo();
        excludeFieldList.addAll( Arrays.asList(entityEnum.getExcValue().split(",")));
//        if(BaseEntityEnum.BASE_STATUS_ENTITY.equals(sysDataConfig.getBaseEntityNo())){
//            excludeFieldList.add("sort");
//            excludeFieldList.add("status");
//        }
//        if(BaseEntityEnum.BASE_TIME_ENTITY.equals(sysDataConfig.getBaseEntityNo())){
//            excludeFieldList.add("createTime");
//            excludeFieldList.add("updateTime");
//        }

        TableEntity tableEntity =  generatorInfo.getTableEntity();
        ClassConfigEntity classEntity = generatorInfo.getClassEntity();

        for(ColumnClassEntity columnEntity : tableEntity.getColumns()){//java filed

            if(!excludeFieldList.contains(columnEntity.getAttrname())){
                //get set build 实现方法;
                GeneratorGetSetBuild.convertGetSetBuildMethod(columnEntity,tableEntity.getClassName());
            }
            convertSQLMethod(tableBean,columnEntity,tableEntity);
        }
        //删除sql
        tableBean.setDeleteSQL(tableEntity.getTableName())
                .setDeleteSQL(" WHERE ").setDeleteSQL(tableEntity.getPrimaryKey());//.setDeleteSQL(" = ? ");
        //删除拼接式的豆号
        convertSubSQLMethod(tableBean);


        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader" );
        Velocity.init(prop);
//        //封装模板数据
        Map<String, Object> map = new HashMap<>();
//        map.put("tableName", tableEntity.getTableName());
//        map.put("comments", tableEntity.getComments());
//        map.put("pk", tableEntity.getPk());
//        map.put("className", tableEntity.getClassName());
//        map.put("classname", tableEntity.getClassname());
//        map.put("paramName",  tableEntity.getClassname());
//        map.put("columns", tableEntity.getColumns());

//        map.put("importServicePackage","import org.springframework.stereotype.Service;");
//        map.put("importAutowiredPackage","import org.springframework.beans.factory.annotation.Autowired;");
//        map.put("importAutowired","@Autowired");
//        if(sysDataConfig.getSysConfig().getDubbo() == 1){
//            map.put("importServicePackage","import org.apache.dubbo.config.annotation.Service;");
//            map.put("importAutowiredPackage","import org.apache.dubbo.config.annotation.Reference;");
//            map.put("importAutowired","@Reference");
//        }
        if(sysDataConfig.getSysConfig().getDubbo() == 1){
            classEntity.setDubboInfo();
        }
        /** ======= 生成界面vo 实现方法  start =======  **/
        List<ColumnBean>  pageColumnBeanList = generatorInfo.getPageColumnBeanList();
        if(null != pageColumnBeanList && !pageColumnBeanList.isEmpty()){
            PageListEntity pageListEntity = converterPageListToVoEntity(pageColumnBeanList,tableEntity.getClassName());
            map.putAll(JSON.parseObject(JSON.toJSONString(pageListEntity),Map.class));
        }
        /** ======= 生成界面vo 实现方法 end ======= **/

        map.putAll(JSON.parseObject(JSON.toJSONString(tableEntity),Map.class));
        map.putAll(JSON.parseObject(JSON.toJSONString(classEntity),Map.class));
        map.putAll(JSON.parseObject(JSON.toJSONString(tableBean),Map.class));

        String pathUrl  = tableEntity.getClassname().toLowerCase();
        pathUrl = pathUrl.startsWith(classEntity.getModuleName()) ? pathUrl.substring(classEntity.getModuleName().length()) : pathUrl;
        map.put("pathName", pathUrl);
        map.put("hasBigDecimal", hasBigDecimal);
        map.put("pk", tableEntity.getPk());
        map.put("columnId",tableEntity.getPrimaryKey());
        map.put("description",tableEntity.getComments());

        map.put("listObject","List<Object> dbList = new ArrayList<>();");
        map.put("dbList", "dbList");//传参数组;
        map.put("dbListToArray", "dbList.toArray()");//传参数组;
        map.put("serviceVo", "/**"+tableBean.getServiceVo()+"\n\t\t**/");
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));

        map.put("baseEntityNo",sysDataConfig.getBaseEntityNo().getId());
        map.put("baseEntity",sysDataConfig.getEntityClass());
        map.put("baseEntityDao",sysDataConfig.getEntityDao());
        map.put("isLombok",sysDataConfig.getSysConfig().getIsLombok());
        map.put("$message","$message");



        //js 实现

        map.put("importExcelUrl","${window._CONFIG['domianURL']}/${this.url.importExcelUrl}");
        map.put("js_successMsg",js_successMsg);
        map.put("js_warningMsg",js_warningMsg);
        map.put("js_messageWarning",js_messageWarning);

        map.put("js_fileSuccessMsg",js_fileSuccessMsg);
        map.put("js_fileWarningMsg",js_fileWarningMsg);

        map.put("modules", ProjectPathConfig.modules);
        map.put("handleSubmit", handleSubmit);

        System.out.println(JSON.toJSONString(map));


        //获取模板列表
        List<CreateCodeEnum> enumList =  new ArrayList<>();
        Collection<CreateCodeEnum>  pageVueList = sysDataConfig.getEnumMap(VuePageEnum.class).values();
        //获取配置模板类
        Class tempCodeEnum = sysDataConfig.getTempCodeEnumClass();
        Collection<CreateCodeEnum>  enumJavaList = sysDataConfig.getEnumMap(tempCodeEnum).values();
//        3.MyBatisBaseEntityDao,4.MyBatisBaseCacheDao
        if(tempCodeEnum.getSimpleName().equals(MybatisCodeEnum.class.getSimpleName()) ){
            if("MyBatisBaseEntityDao".equals(sysDataConfig.getEntityDao())){
                enumJavaList.remove(MybatisCodeEnum.RPC_CACHE_DAO);
            }
            if("MyBatisBaseCacheDao".equals(sysDataConfig.getEntityDao())){
                enumJavaList.remove(MybatisCodeEnum.RPC_ENTITY_DAO);
            }
        }

        enumList.addAll(pageVueList);
        enumList.addAll(enumJavaList);

        if(null != zip){//压缩类型返回流
            GeneratorFile.zipGeneratorClassFile( zip,  map,classEntity,tableEntity, enumList,sysDataConfig);
            return;
        }else {//写文件
            GeneratorFile.writeGeneratorClassFile(map,enumList,classEntity,tableEntity,sysDataConfig);
        }


    }

    /**
     *循环解释界面传过来的属性
     * @param entityList
     * @return
     */
    private static PageListEntity converterPageListToVoEntity(List<ColumnBean> entityList,String className ) {
        PageListEntity pageListEntity = new PageListEntity();

        for (ColumnBean entity : entityList){
            if(entity.isTure(entity.getIsShowAdd())){
                PageFieldEntity page = buildPageFieldToVoEntity(entity,className);
                pageListEntity.getAddList().add(page);
            }
            if(entity.isTure(entity.getIsShowList())){
                PageFieldEntity page = buildPageFieldToVoEntity(entity,className);
                pageListEntity.getShowList().add(page);
            }
            if(entity.isTure(entity.getIsQuery())){
                PageFieldEntity page = 	buildPageFieldToVoEntity(entity,className);
                pageListEntity.getQueryList().add(page);
            }
        }
        return pageListEntity;
    }

    /**
     * 组成生成界面vo实现类的相关字段属性和get set build方法实现
     * @param entity
     * @return
     */
    private static PageFieldEntity buildPageFieldToVoEntity(ColumnBean entity,String className){
        PageFieldEntity pageFieldEntity = PageFieldEntity.build();
        Configuration config = GeneratorUtils.getConfig();

        //db 数据库类型
        pageFieldEntity.setColumnName(entity.getColumnName());
        pageFieldEntity.setDataType(entity.getDataType());
        pageFieldEntity.setComments(entity.getComments());

        //列名转换成Java属性名
        String attrName = GeneratorUtils.columnToJava(entity.getColumnName());
        pageFieldEntity.setAttrName(attrName);
        pageFieldEntity.setAttrname(StringUtils.uncapitalize(attrName));
        String attrType = config.getString(entity.getDataType(), "unknowType" );
        pageFieldEntity.setAttrType(attrType);
        pageFieldEntity.setComments(entity.getComments())
                .setShowType(PageShowTypeEnum.getId(entity.getShowType()))
                .setIsKey(entity.isColumnKey() ? 1: 0)
                .setQueryMode(QueryTypeEnum.getId(entity.getQueryMode()))
                .setRegex(RegexEnum.getKey(entity.getRegex()));
        ;
        GeneratorGetSetBuild.convertGetSetBuildMethodFromPage(pageFieldEntity,className);
        return pageFieldEntity;
    }


    /**
     * 将生成的sql 字符串 处理正常的sql 语句;
     * @param tableBean
     */
    public static void convertSubSQLMethod(TableBean  tableBean){
        tableBean.subInsertSQL();
        tableBean.subInsertVal();
        tableBean.subUpdateSQL();
        tableBean.subSelectSQL();
        tableBean.subInsertMybatisVal();
    }


    /**
     * 将生成列字段对应sql 字符串 语句 实现方法 ;
     * @param tableBean
     */
    public static void convertSQLMethod(TableBean  tableBean, ColumnClassEntity columnEntity, TableEntity tableEntity){
        String split_comma = ", ";
        if(null == columnEntity || tableEntity == null){
            return;
        }
        //查询
        tableBean.setSelectSQL(columnEntity.getColumnName()).setSelectSQL(split_comma);
        tableBean.setMapperBeanVo(getColumnToFileName(columnEntity));

        //如果是主键,只作,查询和更新,并返回;
        if (null != columnEntity && null != tableEntity && columnEntity.isColumnKey()) {
            tableBean.setUpdateWhereDbList("\n\t\tdbList.add(")
                    .setUpdateWhereDbList(getJavaBeanGetName(tableEntity,columnEntity))
                    .setUpdateWhereDbList(");");
            return;
        }
        //insert 插入
        tableBean.setInsertSQL(columnEntity.getColumnName()).setInsertSQL(split_comma);
        tableBean.setInsertVal(" ?").setInsertVal(split_comma);
        tableBean.setInsertDbList("\n\t\tdbList.add(").setInsertDbList(getJavaBeanGetName(tableEntity,columnEntity)).setInsertDbList(");");

        //insert MybatisVal 插入  #{osModule.createDate},
        tableBean.setInsertMybatisVal(" #{").setInsertMybatisVal(getJavaBeanAndName(tableEntity,columnEntity)).setInsertMybatisVal("}").setInsertMybatisVal(split_comma);

        //update, 排除创建时间
        if (!isCreateDateColumnName(columnEntity)) {
            tableBean.setUpdateSQL(columnEntity.getColumnName() + " = ?") .setUpdateSQL(split_comma);
            tableBean.setUpdateDbList("\n\t\tdbList.add(").setUpdateDbList(getJavaBeanGetName(tableEntity,columnEntity)).setUpdateDbList(");");
        }

        tableBean.setServiceVo("\n \t\t\t")
                .setServiceVo("//")
                .setServiceVo(getJavaBeanSetName(tableEntity,columnEntity))
                .setServiceVo(";");

    }

    /**
     * 通过数据库列 生成对应java 对应的jdbc mapperVo对象
     * @param columnEntity
     * @return
     */
    public static String getColumnToFileName( ColumnClassEntity columnEntity){
        StringBuffer mapperVo = new StringBuffer();
        String columnType = Character.toUpperCase(columnEntity.getAttrType().charAt(0)) + columnEntity.getAttrType().substring(1);
        String setType = columnType;
        if("Integer".equals(columnEntity.getAttrType())){
            setType = "Int";
        }if("Date".equals(columnEntity.getAttrType()) ){
            setType = "Timestamp";
        }
        {
            mapperVo.append("\n \t\t\tvo.set").append(columnEntity.getAttrName()).append("(rs.get").append(setType);
            mapperVo.append("(\"" + columnEntity.getColumnName() + "\")").append(");");
        }
        return mapperVo.toString();
    }


    /**
     * 判断是否为创建时间类型列字段,用于修改时排除创建时间
     * @param columnEntity
     * @return
     */
    public static boolean isCreateDateColumnName(ColumnClassEntity columnEntity){
        if( ("create_time".equals(columnEntity.getColumnName()) || "create_date".equals(columnEntity.getColumnName()))){
            return true;
        }return false;
    }

    /**
     * 判断是否为创建时间类型列字段,用于修改时排除创建时间
     * @param columnEntity
     * @return
     */
    public static boolean isUpdateDateColumnName(ColumnClassEntity columnEntity){
        if( "update_time".equals(columnEntity.getColumnName()) || "update_date".equals(columnEntity.getColumnName())
                || "modify_date".equals(columnEntity.getColumnName()
        )){
            return true;
        }return false;
    }

    /**
     * 生成 java 属性对应的 get方法的字符串
     * @param tableEntity
     * @param columnEntity
     * @return
     */
    public static String getJavaBeanGetName(TableEntity tableEntity, ColumnClassEntity columnEntity) {
         String  getName = tableEntity.getClassname()+".get"+columnEntity.getAttrName()+ "()";
        return getName;
    }

    /**
     * 生成 java 属性对应的(对象.属性)字符串
     * @param tableEntity
     * @param columnEntity
     * @return
     */
    public static String getJavaBeanAndName(TableEntity tableEntity, ColumnClassEntity columnEntity) {
        String  getName = tableEntity.getClassname()+"."+columnEntity.getAttrname();
        return getName;
    }


    /**
     * 生成 java 属性对应的 set方法的字符串
     * @param tableEntity
     * @param columnEntity
     * @return
     */
    public static String getJavaBeanSetName(TableEntity tableEntity, ColumnClassEntity columnEntity) {
        String setName = tableEntity.getClassname()+".set"+columnEntity.getAttrName()+" (" + columnEntity.getAttrType() +" " + columnEntity.getAttrname() +")";
        return setName;
    }

    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "" );
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replaceFirst(tablePrefix, "" );
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties" );
        } catch (ConfigurationException e) {
            throw new RRException("获取配置文件失败，", e);
        }
    }




}
