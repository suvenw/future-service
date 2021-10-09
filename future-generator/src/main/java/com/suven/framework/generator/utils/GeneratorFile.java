package com.suven.framework.generator.utils;

import com.suven.framework.generator.config.ProjectPathConfig;
import com.suven.framework.generator.config.SysDataConfig;
import com.suven.framework.generator.entity.ClassConfigEntity;
import com.suven.framework.generator.entity.TableEntity;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.suven.framework.generator.temp.CreateCodeEnum;
import com.suven.framework.generator.temp.VuePageEnum;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 *
 * @author suven
 * @email suvenw@gmail.com
 * @date 2016年12月19日 下午11:40:24
 */
public class GeneratorFile {


    private static Logger logger = LoggerFactory.getLogger(GeneratorFile.class);

    private final static String UTF_8 = "UTF-8";






    public static void zipGeneratorClassFile(ZipOutputStream zip, Map<String, Object> map,
                                             ClassConfigEntity classEntity, TableEntity tableEntity ,
                                             List<CreateCodeEnum> enumList,
                                             SysDataConfig sysDataConfig) {
        VelocityContext context = new VelocityContext(map);
        for (CreateCodeEnum codeEnum : enumList) {
            String template = ProjectPathConfig.templates + codeEnum.getTemp();
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, UTF_8 );
            tpl.merge(context, sw);

            try {
                //添加到zip
                String htmlPath  =  sysDataConfig.getProjectPathConfig().getHtmlPath();
                int pageVauType = sysDataConfig.getSysConfig().getPageVal();
                String filePath = null;
                //生成界面实现
                if (codeEnum instanceof VuePageEnum){
                    filePath = getViewsFilePath(codeEnum, tableEntity.getClassName(), classEntity.getModuleName(),htmlPath,pageVauType);
                    if(filePath == null){
                        continue;
                    }
                }else {//生成java类实现
                    filePath = getZipJavaClassFilePath(codeEnum, classEntity,tableEntity);
                }
                if(filePath == null){
                    continue;
                }
                zip.putNextEntry(new ZipEntry(filePath));
                IOUtils.write(sw.toString(), zip, UTF_8 );
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (Exception e) {
                throw new RRException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
            }
        }
    }


    public static void writeGeneratorClassFile(Map<String, Object> map, List<CreateCodeEnum> templates, ClassConfigEntity classEntity, TableEntity tableEntity, SysDataConfig sysDataConfig ){
        VelocityContext context = new VelocityContext(map);
        for (CreateCodeEnum codeEnum : templates) {
            try {
                String template = ProjectPathConfig.templates + codeEnum.getTemp();
                String outfile = getGeneratorJavaClassAndPagePath(codeEnum,sysDataConfig,classEntity,tableEntity);
                if(outfile == null){
                    continue;
                }
                System.out.println("outfile :" + outfile);
                File outFile = new File(outfile);
                if(!outFile.getParentFile().exists()){
                    outFile.getParentFile().mkdirs();
                }
                if(outFile.exists()){
                    System.err.println("java Constant is exists >>: " + outfile + "<<----------" );
                    continue ;
                }
                //渲染模板
                StringWriter sw = new StringWriter();
                Template tpl = Velocity.getTemplate(template, UTF_8 );
                tpl.merge(context, sw);

                FileUtils.write(outFile,sw.toString(),  UTF_8,true );
                System.out.println("write file: " + outFile.getPath() + "; use " + template);
            }catch (Exception e){
                logger.error("渲染模板失败，表名：[{}],Exception:[{}] " , tableEntity.getTableName(), e);
            }
        }
    }

    public void writer(Map<String, Object> objectMap, String templatePath, String outputFile) {
        if (StringUtils.isEmpty(templatePath)) {
            return;
        }
        Template template = Velocity.getTemplate(templatePath, UTF_8);
        try (FileOutputStream fos = new FileOutputStream(outputFile);
             OutputStreamWriter ow = new OutputStreamWriter(fos, UTF_8);
             BufferedWriter writer = new BufferedWriter(ow)) {
            template.merge(new VelocityContext(objectMap), writer);
        }catch (Exception e){
            e.printStackTrace();
        }
//        logger.debug("模板:" + templatePath + ";  文件:" + outputFile);
    }


    private static String getGeneratorJavaClassAndPagePath(CreateCodeEnum codeEnum, SysDataConfig sysDataConfig, ClassConfigEntity classEntity, TableEntity tableEntity  ){

        /** 生成 vau 界面 的路径**/
        if (codeEnum instanceof  VuePageEnum){
            int pageVauType = sysDataConfig.getSysConfig().getPageVal();
            String htmlPath = sysDataConfig.getProjectPathConfig().getHtmlPath();
            String viewsPath = getViewsFilePath(codeEnum, tableEntity.getClassName(), classEntity.getModuleName(),htmlPath,pageVauType);
            if(null == viewsPath){
                return null;
            }
            String outputFilePath =  sysDataConfig.getProjectPathConfig().getBaseProjectPath() + Constant.separator + viewsPath;
            outputFilePath = outputFilePath.replaceAll(Constant.separator2,Constant.separator);
            return outputFilePath;
        }

        /** 生成 java 类的路径**/
        //组合生成对应实现类;
        String projectRootPath = getProjectRootPath(codeEnum.getIndex(),sysDataConfig);
        //环境目录_项目目录_包目录_模块目录_类名称;
        String packageName =  classEntity.getPackageName().replaceAll("\\.", Constant.separator);
        String classPath =   packageName + Constant.separator +classEntity.getModuleName()
                + Constant.separator + codeEnum.getPath() + tableEntity.getClassName() + codeEnum.getExt();
        String outputFilePath =   projectRootPath + classPath;
        outputFilePath = outputFilePath.replaceAll(Constant.separator2,Constant.separator);
        return outputFilePath;

    }
    private static String getProjectRootPath(int index, SysDataConfig sysDataConfig){
        ProjectPathConfig pathConfig = sysDataConfig.getProjectPathConfig();

        String  outFilePath =  pathConfig.getBaseProjectPath() + Constant.separator + pathConfig.getProjectPath(index)   + Constant.separator + pathConfig.srcMainJava ;
        if (null == outFilePath){
            throw new RuntimeException("项目的ProjectRoot根目录无法找到,请检查模板CreateCodeEnum的实现类 ");
        }
        return outFilePath;

    }

    public static String getZipJavaClassFilePath(CreateCodeEnum codeEnum, ClassConfigEntity classEntity, TableEntity tableEntity){
        String packagePath = ProjectPathConfig.srcMainJava;
        String packageName =  classEntity.getPackageName().replaceAll("\\.", Constant.separator);
        String outFilePath = packageName + Constant.separator + classEntity.getModuleName()+ codeEnum.getPath();
        outFilePath = outFilePath.replace("\\.", Constant.separator);
        packagePath += outFilePath + tableEntity.getClassName()  + codeEnum.getExt();

        return packagePath;
    }

    /**
     * 获取界面文件名
     */
    public static String getViewsFilePath(CreateCodeEnum codeEnum , String className, String moduleName, String views, int pageValType) {
        String template  = null;
        if(views ==  null || "".equals(views)){
            views =  ProjectPathConfig.srcMainResourcesViews;
        }

        switch ((VuePageEnum)codeEnum){

            case MENU_SQL_VM:
                template  =  codeEnum.getPath()  + className + codeEnum.getExt();
                break;
            case PAGE_LIST_VUE:
            case PAGE_MODAL_VUE:
                if(pageValType == 1){//生成jeecg界面
                    template = views + Constant.separator + moduleName + codeEnum.getPath() + className + codeEnum.getExt();
                }
                break;
            case RR_ADD_VUE:
            case RR_INDEX_VUE:
                if(pageValType == 2){//生成renren界面
                    template  =  views + Constant.separator + moduleName +codeEnum.getPath() + className.toLowerCase() + codeEnum.getExt();
                }
                break;

        }
        return template;

//        String template  = null;
//        if (template.contains("menu.sql.vm" )) {
//            return "db" + Constant.separator +"menu"+ Constant.separator + className.toLowerCase() + "_menu.sql";
//        }
//
//
//        if (template.contains("index.vue.vm" )) {
//            return views + Constant.separator + moduleName + Constant.separator + className.toLowerCase() + ".vue";
//        }
//
//        if (template.contains("add-or-update.vue.vm" )) {
//            return views  + Constant.separator + moduleName + Constant.separator + className.toLowerCase() + "-add-or-update.vue";
//        }
//
//        if (template.contains("code_page_modal.vue.vm" )) {
//            return views + Constant.separator + moduleName + Constant.separator +ProjectPathConfig.modules+ Constant.separator+ className + "Modal.vue";
//        }
//
//        if (template.contains("code_page_list.vue.vm" )) {
//            return views  + Constant.separator + moduleName + Constant.separator + className + "List.vue";
//        }
//        return null;
    }







}
