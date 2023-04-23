//package com.suven.framework.util.createcode.swagger;
//
//
//import com.suven.framework.common.api.ApiDesc;
//import com.suven.framework.common.api.ApiDoc;
//import com.suven.framework.common.api.DocConstants;
//import com.suven.framework.util.createcode.doc.SwaggerRequestVo;
//import com.suven.framework.util.createcode.doc.SwaggerResponseVo;
//import org.apache.commons.lang3.reflect.FieldUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.AnnotationUtils;
//
//import javax.annotation.PostConstruct;
//import java.lang.reflect.*;
//import java.util.*;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * @Author 作者 : suven.wang
// * @CreateDate 创建时间: 2022-01-13
// * @WeeK 星期: 星期四
// * @Version 版本: v1.0.0
// * <pre>
// *
// *  @Description (说明):  扫描控制类
// *
// * </pre>
// * <pre>
// * 修改记录
// *    修改后版本:  1.0   修改人：suven  修改日期: 20220113    修改内容:重构逻辑
// * </pre>
// * @Copyright: (c) 2021 gc by https://www.suven.top
// **/
//
//@ConditionalOnProperty(name = DocConstants.TOP_SERVER_API_ENABLED,  matchIfMissing = false)
//@ConfigurationProperties(prefix = DocConstants.TOP_SERVER_API)
//@Configuration
//public class SwaggerReflectionsDoc2 {
//
//    private static  Logger logger = LoggerFactory.getLogger(SwaggerReflectionsDoc2.class);
//
//
//
//    private static List<Class> controllerClassList = new ArrayList<>();
//
//
//    private static Map<String, Map<String, SwaggerMethodBean>> controllerRequestClass = new TreeMap<>();
////    private static Map<String, String > classDescription = new HashMap<>();
//    /** 统计所有接口控制层的实现map,key->类型名,value -> 类型的文档标签 **/
//    private static Map<String,ApiDoc> controllerClassToGroup = new TreeMap<>();//k-v->class-group
//
//
//    private static Map<String,String> controllerApiDocGroup = new HashMap<>();//k-v->class-group
//
//
//    private static Map<String, SwaggerResponseParameterMap> responseParameterVoMap = new TreeMap<>();
//    private static Map<String, String> responseUrlMap = new TreeMap<>();
//
//    private String spance = "&nbsp&nbsp&nbsp&nbsp";
//    private static Map<String,SwaggerResultBean> apiDoc = new TreeMap<>();
//
//
//    @Autowired
//    private SwaggerReflection swaggerReflection;
//
//    public static SwaggerResultBean getApiDoc(String search){
//        SwaggerResultBean resultBean =  apiDoc.get("api");
//        if(resultBean == null || search == null || "".equals(search)){
//            return resultBean;
//
//        }
//        SwaggerResultBean bean = new SwaggerResultBean();
//        bean.setSwagger(resultBean.getSwagger()).setInfo(resultBean.getInfo()).setHost(resultBean.getHost()).setBasePath(resultBean.getBasePath());
//        SwaggerPathsMap searchPathsMap =  new SwaggerPathsMap<>();
//        SwaggerPathsMap<SwaggerRequestMethodMap> pathsMap =  resultBean.getPaths();
//        Set<String> tags = new HashSet<>();
//        for (Object obj : pathsMap.keySet()){
//            if (null == obj ){
//                continue;
//            }
//            String path  = obj.toString();
//            SwaggerRequestMethodMap requestMethodMap =  pathsMap.getTo(path);
//            /** 通过url包括搜索**/
//            if(path.toUpperCase().contains(search.toUpperCase()) ){
//                searchPathsMap.put( path, requestMethodMap);
//                for (Object object: requestMethodMap.entrySet()){
//                    Map.Entry entry = (Map.Entry)object;
//                    if (entry != null && entry.getValue() instanceof SwaggerRequestMethodBean ) {
//                        SwaggerRequestMethodBean methodBean = (SwaggerRequestMethodBean)entry.getValue() ;
//                        tags.addAll(methodBean.getTags());
//                    }
//                }
//                continue;
//            }
//
//            if (null == requestMethodMap || requestMethodMap.isEmpty()){
//                continue;
//            }/**通过功能描述搜索**/
//            for (Object object : requestMethodMap.entrySet()){
//                if(object == null ){
//                    continue;
//                }
//                if((object instanceof Map.Entry)){
//                    Map.Entry entry = (Map.Entry)object;
//                    if(entry != null && entry.getValue() instanceof SwaggerRequestMethodBean ){
//                        SwaggerRequestMethodBean methodBean = (SwaggerRequestMethodBean)entry.getValue() ;
//                        if(null !=  methodBean.getSummary() &&  methodBean.getSummary().contains(search) ){
//                            searchPathsMap.put( path, requestMethodMap);
//                            tags.addAll(methodBean.getTags());
//                            continue;
//                        }
//                    }
//
//                }
//            }/** 通过方法编写作者搜索 **/
//            for (Object object : requestMethodMap.entrySet()){
//                if(object == null ){
//                    continue;
//                }
//                if((object instanceof Map.Entry)){
//                    Map.Entry entry = (Map.Entry)object;
//                    if(entry != null && entry.getValue() instanceof SwaggerRequestMethodBean ){
//                        SwaggerRequestMethodBean methodBean = (SwaggerRequestMethodBean)entry.getValue() ;
//                        if(null !=  methodBean.getAuthor() &&  methodBean.getAuthor().toUpperCase().contains(search.toUpperCase()) ){
//                            searchPathsMap.put( path, requestMethodMap);
//                            tags.addAll(methodBean.getTags());
//                            continue;
//                        }
//                    }
//
//                }
//            }
//        }
//
//        List<SwaggerTagBean> swaggerTagBeans =  new ArrayList<>();
//        for (SwaggerTagBean tagBean:resultBean.getTags()) {
//            if (tags.contains(tagBean.getName())) {
//                swaggerTagBeans.add(tagBean);
//            }
//        }
//
//        bean.setPaths(searchPathsMap);
//        bean.setTags(swaggerTagBeans);
//        bean.setDefinitions(resultBean.getDefinitions());
//        return bean;
//    }
//
//
//
//
//    /**
//     * 获取指定文件下面的RequestMapping方法保存在mapp中
//     * T extends Annotation
//     * @return
//     */
//    @PostConstruct
//    public void init() {
//        logger.info(" -------------Swagger Reflections document  start init --------- PostConstruct -------------"  );
//        ReflectionsController controller = new ReflectionsController();
//        Map controllerTreeClass = controller.getControllerClass(swaggerReflection);
//        controllerClassList.addAll(controllerTreeClass.values());
//        // 存放url和   RequestMapping 的对应关系
//        for (Class controllerClass : controllerClassList) {
//            //得到该类下面的所有方法
//            Method[] controllerMethodList = controllerClass.getDeclaredMethods();
//            if (null == controllerMethodList || controllerMethodList.length == 0){
//                continue;
//            }
//            String controllerClassName = controllerClass.getName();
//
//            ApiDoc table = AnnotationUtils.findAnnotation(controllerClass, ApiDoc.class);
//            if(table != null){
//                controllerClassToGroup.put(controllerClassName,table);
//            }
//            Map<String, SwaggerMethodBean> requestMethodGroupMap  = new TreeMap<>();
//            controllerRequestClass.put(controllerClassName,requestMethodGroupMap);
//
//            /** 按类解释每个方法的实现 **/
//            for (Method controllerMethod : controllerMethodList) {
//                try {
//                    //得到该类下面的RequestMapping注解
//                    ApiDoc apiDoc =  controllerMethod.getAnnotation(ApiDoc.class);
//                    if(null == apiDoc){
//                        continue;
//                    }
//                    SwaggerMethodBean methodBean =  SwaggerMethodBean.build();
//                    /** 解释方法的请求方式和url **/
//                     controller.getMappingMethodTypeUrl(controllerMethod,methodBean, apiDoc);
//                    String requestPath  = methodBean.getRequestUrl();
//                    if (null == requestPath ) {
//                        continue;
//                    }
//
//                    /** 解释或通过构造器,获得返回类信息 **/
//                    Class apiDocResponseClass = controller.getApiDocResponseClass(apiDoc);
//                    String apiDocResponseName = apiDocResponseClass != null ? apiDocResponseClass.getName() : null;
//
//                    /**通过指定实现分组 **/
//                    String moduleGroupName = table.group();
//                    moduleGroupName = (moduleGroupName != null)  ? moduleGroupName : controllerClass.getSimpleName();
//                    controllerApiDocGroup.put(controllerClassName,moduleGroupName);
//
//                    /**构造请求参数文档 **/
//                    List<SwaggerParameterBean>  requestParameterList = requestParameterConverterBean(apiDoc.request()[0]);
//
//                    /** 把每个方法的信息汇总到 methodBean 对象中**/
//                    methodBean.setRequestInfo(apiDoc.value())
//                            .setRequestUrl(requestPath)
//                            .setRequestName(apiDoc.request()[0].getName())
//                            .setResponseName(apiDocResponseName)
//                            .setRequestAuthor(apiDoc.author())
//                            .setSourceClassName(controllerClassName)
//                            .setModuleGroupName(moduleGroupName)
//                            .setRequestMethodName(controllerMethod.getName())
//                    ;
//                    /** 设置请求参数对象解释后对象信息 **/
//                    methodBean.setRequestParameterList(requestParameterList);
//
//                    requestMethodGroupMap.put(methodBean.getRequestUrl(),methodBean);
//                    /**构造返回参数文档 **/
//                    responseParameterConverterBean(apiDocResponseClass);
//
//                } catch (Exception e) {
//                    logger.error("SwaggerReflectionsDoc init exception: [{}]", e);
//                    e.printStackTrace();
//                }
//
//            }
//        }
//        outSwaggerDoc();
//        logger.info(" -------------Swagger Reflections document  end init --------- PostConstruct -------------"  );
//    }
//
//
//    private void setRequestMethodGroupMap(SwaggerMethodBean methodBean ){
//        Map<String, SwaggerMethodBean> requestMethodGroupMap =  controllerRequestClass.get(methodBean.getSourceClassName());
//        if(requestMethodGroupMap == null){
//            requestMethodGroupMap = new TreeMap<>();
//            controllerRequestClass.put(methodBean.getSourceClassName(),requestMethodGroupMap);
//        }
//        requestMethodGroupMap.put(methodBean.getRequestUrl(),methodBean);
//
//    }
//
//
//
//
//
//    private   List<SwaggerParameterBean>  requestParameterConverterBean(Class requestParameterClass){
////        List<Field> fieldList = FieldUtils.getAllFieldsList(entityClazz);
//        List<SwaggerParameterBean>  parameterList = new ArrayList();
//        /** 请求参数,是否有复合实现对象,如果有则执行分隔线类型 addSeparateInfo **/
//        Map<Class,String> compoundMap = new LinkedHashMap<>();
//
//        List<SwaggerParameterBean>  beanParameterList = converterParameterClassParameterBean(requestParameterClass,compoundMap);
//        parameterList.addAll(beanParameterList);
//
//        if(compoundMap.size() > 0){
//            for (Class classKey : compoundMap.keySet()) {
//                String fieldType = compoundMap.get(classKey);
//                /** 如果是复合类组,增加分隔线说明 **/
//                List<SwaggerParameterBean> separate =  addSeparateInfo(classKey.getSimpleName(),fieldType);
//                List<SwaggerParameterBean>  beanList2 =  converterParameterClassParameterBean(classKey, null);
//                parameterList.addAll(separate);
//                parameterList.addAll(beanList2);
//            }
//
//        }
//        return  parameterList;
//
//    }
//
//    private List<SwaggerParameterBean>  converterParameterClassParameterBean(Class entityClazz, Map<Class,String> compoundMap){
//        List<Field> fieldList = FieldUtils.getAllFieldsList(entityClazz);
//        List list = new ArrayList();
//
//        for (Field field : fieldList) {
//            ApiDesc apiDesc = field.getAnnotation(ApiDesc.class);
//            if(null == apiDesc){
//                continue;
//            }
//
//            String type =  "".equals(apiDesc.type()) ?  field.getType().getSimpleName() : apiDesc.type();
//            SwaggerParameterBean parameterBean = SwaggerParameterBean.build()
//                    .setName(field.getName())
//                    .setType(type)
//                    .setDescription( apiDesc.value())
//                    .setRequired(apiDesc.required() == 1);
//            if(0 == apiDesc.isHide()){
//                list.add(parameterBean);
//            }
//            /** 获取参数属性是否包括对象类 **/
//           converterParameterizedType(entityClazz,field,compoundMap,type);
//
//
//        }
//       return list;
//
//    }
//
//    /** 文档对象间的说明分隔描述 **/
//    private List<SwaggerParameterBean> addSeparateInfo(String className,String fieldType){
//        SwaggerParameterBean parameterBean1 = SwaggerParameterBean.build();
//        SwaggerParameterBean parameterBean2 = SwaggerParameterBean.build();
//        SwaggerParameterBean parameterBean3 = SwaggerParameterBean.build();
//        parameterBean1.setName("")
//                .setType("")
//                .setIn("")
//                .setDescription( "")
//                .setRequired(false);
//        parameterBean2.setName("<b>Parameter  «"+className+"» :</b>")
//                .setType("<b>"+fieldType+":</b>")
//                .setIn("")
//                .setDescription( "<b>该属性参数如下:</b>")
//                .setRequired(false);
//        parameterBean3.setName("<b>Name</b>")
//                .setType("<b>Type	Data</b>")
//                .setIn("<b>Parameter Type</b>")
//                .setDescription( "<b>Description</b>")
//                .setRequired(false);
//
//        return Arrays.asList(parameterBean1,parameterBean2,parameterBean3);
//    }
//
//    /**
//     * 判断类属性 是objet 对象或聚合,如果是则添加到compoundList聚合中
//     * @param entityClazz
//     * @param field
//     * @param compoundList 复合统计map
//     * @return
//     */
//    private boolean converterParameterizedType(Class entityClazz,Field field,Map<Class,String> compoundList,String paramType){
//        Class fieldType = field.getType();
//        /** 1.如果 全是基本属性正常结果; **/
//         if (SwaggerClassUtil.isPrimitiveType(fieldType) || compoundList == null ) {
//            return false;
//        }
//        /** 2.如果数组或不是字节数据组时,进入复合统计中;; **/
//        if (!SwaggerClassUtil.isIterable(fieldType)) {
//            compoundList.put(fieldType,paramType);
//            return true;
//        }
//        /** 获取属性是否复合类对象,并且不为基类对象 **/
//        Class genericClass =  SwaggerClassUtil.getGenericTypeToCompound(entityClazz, field);
//        if(genericClass != null){
//            compoundList.put(genericClass,paramType);
//        }
//        return false;
//    }
//
//
//
//
//
//    private  void responseParameterConverterBean(Class entityClazz){
//        if(entityClazz == null){
//            entityClazz = Long.class;
//        }
//        if(SwaggerClassUtil.isPrimitiveType(entityClazz)){
//            primitiveTypeClass(entityClazz);
//            return;
//        }
//
//        //排除map类
//        if(SwaggerClassUtil.isMapOrJson(entityClazz)){
//            logger.warn("entityClazz is map Object  pass return entityClazz[{}]..........,",entityClazz);
//            return;
//        }
//        List<Class> allClassList = new ArrayList();
//        AtomicInteger indexCount = new AtomicInteger();
//        List<Class> classList = converterResponse(entityClazz, allClassList, indexCount );
//        if(classList.isEmpty()){
//            return;
//        }
//        while (!classList.isEmpty()) {
//            final List<Class> allClass = new ArrayList<>(classList);
//            classList.clear();
//            for (Class clazz : allClass){
//                classList.addAll(converterResponse(clazz,allClassList,indexCount));
//            }
//        }
//
//    }
//    private   List<Class> converterResponse(Class entityClazz,List<Class> allClassList,AtomicInteger indexCount){
//        List<Class> classList = new ArrayList<>();
//        //排除map类
//        if(SwaggerClassUtil.isMapOrJson(entityClazz)){
//            return classList;
//        }
//        if(allClassList.contains(entityClazz)){
//            logger.warn(" allClassList is contains entityClazz   return classList:[{}] and entityClazz:[{}]..........,",classList,entityClazz);
//            return classList;
//        }
//        allClassList.add(entityClazz);
//
//       int count =  indexCount.getAndIncrement();
//       if(count > 1){
//           logger.info(" BaseClass:[{}] ====== entityClazz:[{}]  to converterResponse Object  pass return ..........",allClassList.get(0),entityClazz);
//       }if(count > 100){
//           return new ArrayList<>();
//        }
//
//        List<Field> fieldList = FieldUtils.getAllFieldsList(entityClazz);
//
//        Map<String, SwaggerResponseProperty> map = new TreeMap<>();
//        for (Field field : fieldList) {
//            try {
//                ApiDesc apiDesc = field.getAnnotation(ApiDesc.class);
//                String descValue = "";
//                if (null != apiDesc) {
//                    descValue = apiDesc.value();
//                }
//                //排除类和属性
//                boolean  isExcludeFieldOrClass= SwaggerClassUtil.excludeFieldOrClass(entityClazz, field);
//                if (isExcludeFieldOrClass){
//                    continue;
//                }
//
//                Class<?> fieldType = field.getType();
//                SwaggerResponseProperty property = SwaggerResponseProperty.build();
//                if (SwaggerClassUtil.isPrimitiveType(fieldType)) {
//                    property.setType(field.getType().getSimpleName()).setDescription(descValue);
//                    map.put(field.getName(), property);
//                    continue;
//                }
//                if (SwaggerClassUtil.isIterable(fieldType)) {
//                    Type genericType = field.getGenericType();
//                    if (genericType != null && genericType instanceof ParameterizedType) {
//                        //得到泛型里的class类型对象。
//                        Class<?> genericClazz = SwaggerClassUtil.getRawType(genericType);
//                        //过滤 类自己本身，如果出现迭归类，会出现死循环,和 genericClazz 为 null
//                        if(SwaggerClassUtil.isOneselfClass(genericClazz,entityClazz)){
//                            continue;
//                        }
//                        property.setType(fieldType.getSimpleName() + ":" + genericClazz.getSimpleName()).setRef(genericClazz.getSimpleName()).setDescription(descValue);
//                        map.put(field.getName(), property);
//                        classList.add(genericClazz);
//                    }
//                    continue;
//                }
//
//
//                if(null == fieldType || field.getGenericType()  instanceof TypeVariable){
//                    continue;
//                }
//                if (fieldType instanceof Object && null != apiDesc) {
//                    property.setRef(fieldType.getSimpleName()).setDescription(descValue);
//                    map.put(field.getName(), property);
//                    classList.add(fieldType);
//                } else {
//                    logger.warn("暂时不支持该类型:[{}], class 类为:[{}]", fieldType,entityClazz);
//                }
//            }catch (Exception e){
//                logger.error("返回文档类转换类型异常 converterResponse class Exception :[{}]", e);
//                e.printStackTrace();
//            }
//        }
//
//        converterParameterBean(entityClazz,map);
//
//        return classList;
//
//    }
//
//
//
//
//    private void primitiveTypeClass(Class clazz){
//        if( null !=  responseParameterVoMap.get(clazz.getName())){
//            return;
//        }
//        Map<String, SwaggerResponseProperty> map = new TreeMap<>();
//        SwaggerResponseProperty property = SwaggerResponseProperty.build();
//        String fieldName  = "id";
//        String descValue  = "id数据表对应的主键值";
//        if(clazz.equals(Boolean.class) || clazz.equals(boolean.class)){
//            fieldName  = "result";
//            descValue  = "操作数据后返回:1.成功, 0.失败";
//        }
//        if(clazz.equals(byte[].class)){
//            fieldName  = "byte[]";
//            descValue  = "字节数组";
//        }
//        property.setType(clazz.getSimpleName()).setDescription(descValue);
//        map.put(fieldName,property);
//        converterParameterBean(clazz,map);
//
//    }
//    private void converterParameterBean(Class clazz, Map<String, SwaggerResponseProperty> map){
//
//        SwaggerResponseParameterMap parameterBean = SwaggerResponseParameterMap.build();
//        parameterBean.responseResultVo(clazz.getSimpleName());
//        parameterBean.responseDataVo(clazz.getSimpleName(),map);
//        responseUrlMap.put(clazz.getName(),clazz.getSimpleName());
//        responseParameterVoMap.put(clazz.getName(),parameterBean);
//    }
//
//    public void outSwaggerDoc(){
//        try {
//
//
//
//            SwaggerResultBean bean = new SwaggerResultBean();
//            swaggerReflection.initSwaggerInfo(bean);
//            bean.setPaths(SwaggerPathsMap.build()).setDefinitionsAll(responseParameterVoMap.values());
//
//            /** 统计所有接口 分组实现 map,key->默认是类名,如果ApiDoc标签存在按标签 group值分组,value -> 具体的实现方法对象 **/
//            Map<String,SwaggerTagBean> requestMethodBeanGroup = new TreeMap<>();//k-v->group-bean
//
//            for(Class controllerClass : controllerClassList){
//                try {
//                    /** 通过类,获取所有的接口方法 **/
//                    Map<String, SwaggerMethodBean> controllerClassMethodMap =  controllerRequestClass.get(controllerClass.getName());
//                    if(null == controllerClassMethodMap || controllerClassMethodMap.isEmpty()){
//                        continue;
//                    }
//                    /** 获取类对象的分类 **/
//
//                    ApiDoc table   = controllerClassToGroup.get(controllerClass.getName());
//                    String moduleGroupName = controllerApiDocGroup.get(controllerClass.getName());
//
//
//                    SwaggerTagBean swaggerTagBean = requestMethodBeanGroup.get(moduleGroupName);
//                    if(null == swaggerTagBean) {
//                        swaggerTagBean = SwaggerTagBean.init(moduleGroupName, table);
//                        bean.setTags(swaggerTagBean);//只初始化一次
//                        requestMethodBeanGroup.put(moduleGroupName,swaggerTagBean);
//                    }
////
//
//                    for (String url : controllerClassMethodMap.keySet()){
//                        try {
//
//                            SwaggerMethodBean swaggerMethodBean =  controllerClassMethodMap.get(url);
//
//                            SwaggerRequestMethodBean methodBean = SwaggerRequestMethodBean.init();
//
//                            StringBuilder sb = new StringBuilder().append(swaggerMethodBean.getRequestMethodName()).
//                                    append(controllerClass.getSimpleName()).append(swaggerMethodBean.getRequestMethodType());
//
//                            String responseUrl = responseUrlMap.get(swaggerMethodBean.getResponseName());
//
////                            methodBean.setTags((classes.getSimpleName()));
//                            methodBean.setTags(moduleGroupName);
//                            methodBean.setSummary(swaggerMethodBean.getRequestMethodName() + spance + swaggerMethodBean.getRequestInfo());
//                            methodBean.setDescription(swaggerMethodBean.getRequestInfo());
//                            methodBean.setAuthor(swaggerMethodBean.getRequestAuthor());
//                            methodBean.setOperationId(sb.toString());
//                            methodBean.setParameters(swaggerMethodBean.getRequestParameterList());
//
//                            methodBean.setResponses(SwaggerResponseResultMap.build().setCode().setResponse(responseUrl));
//                            methodBean .setDeprecated(false);
//
//
//                            SwaggerRequestMethodMap methodMap = SwaggerRequestMethodMap.build().put(swaggerMethodBean.getRequestMethodType(),methodBean);
//
//                            //                paths.put(url,methodMap);
//                            bean.getPaths().put(url,methodMap);
//                        }catch (Exception e){
//                            logger.error("Exception classes :[{}] request methodUrl : [{}], printStackTrace :[{}] ",controllerClass, url,e);
//                            e.printStackTrace();
//                        }
//                    }
//                }catch (Exception e){
//                    logger.error("Exception classes :[{}] request SwaggerMethodBean bean : [{}], printStackTrace :[{}] ",controllerClass, bean.toString(),e);
//                    e.printStackTrace();
//                }
//
//            } apiDoc.put("api", bean);
//            cleanAllParam();
//        }catch (Exception e){
//            logger.error("Exception classes :[{}] request Method out , printStackTrace :[{}] ", SwaggerReflectionsDoc2.class,e);
//            e.printStackTrace();
//        }
//    }
//
//
//    private void cleanAllParam(){
//        controllerClassList = null;
//        responseParameterVoMap = null;
//        responseUrlMap = null;
//        controllerClassToGroup = null;
//    }
//
//    public static void main(String[] agr){
//        SwaggerReflectionsDoc2 doc =  new SwaggerReflectionsDoc2();
//        Class[] classes = {SwaggerResponseVo.class, SwaggerRequestVo.class};
////        doc.getConstructorClass(classes);
//
////        doc.out();
////        doc.converterResponseVo(SwaggerResponse2Vo.class);
//
////        Set<Class<?>> module = reflections.getSubTypesOf(RequestMapping.class);
////
////        for (Class claxx : module) {
////            //获取该类上指定Observer标签的方法
////           System.out.println(claxx);
////        }
//    }
//}
