package com.suven.framework.util.createcode.swagger;



import com.suven.framework.common.api.ApiCmd;
import com.suven.framework.common.api.ApiDesc;
import com.suven.framework.common.api.ApiDoc;
import com.suven.framework.common.constants.GlobalConfigConstants;
import com.suven.framework.core.spring.SpringUtil;
import com.suven.framework.util.createcode.doc.SwaggerRequestVo;
import com.suven.framework.util.createcode.doc.SwaggerResponseVo;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.lang.reflect.*;
import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@ConditionalOnProperty(name = GlobalConfigConstants.TOP_SERVER_API_ENABLED,  matchIfMissing = false)
@ConfigurationProperties(prefix = GlobalConfigConstants.TOP_SERVER_API)
@Configuration
public class SwaggerReflectionsDoc {

    private static  Logger logger = LoggerFactory.getLogger(SwaggerReflectionsDoc.class);

    private String packages = "com";

    private static Set<String> urlSet = new HashSet<>();
    private static List<Class> controllerClass = new ArrayList<>();


    private static Map<String, Map<String, SwaggerMethodBean>> requestClass = new TreeMap<>();
//    private static Map<String, String > classDescription = new HashMap<>();
    private static Map<String,ApiDoc> classToGroup = new TreeMap<>();//k-v->class-group
    private static Map<String,SwaggerTagBean> groupToBean = new TreeMap<>();//k-v->group-bean

    private static Map<String, List<SwaggerParameterBean>> requestVoMap = new TreeMap<>();
    private static Map<String, SwaggerResponseParameterMap> responseVoMap = new TreeMap<>();
    private static Map<String, String> responseUrlMap = new TreeMap<>();

    private String spance = "&nbsp&nbsp&nbsp&nbsp";
    private static Map<String,SwaggerResultBean> apiDoc = new TreeMap<>();


    public static SwaggerResultBean getApiDoc(String search){
        SwaggerResultBean resultBean =  apiDoc.get("api");
        if(resultBean == null || search == null || "".equals(search)){
            return resultBean;

        }
        SwaggerResultBean bean = new SwaggerResultBean();
        bean.setSwagger(resultBean.getSwagger()).setInfo(resultBean.getInfo()).setHost(resultBean.getHost()).setBasePath(resultBean.getBasePath());
        SwaggerPathsMap searchPathsMap =  new SwaggerPathsMap<>();
        SwaggerPathsMap<SwaggerRequestMethodMap> pathsMap =  resultBean.getPaths();
        Set<String> tags = new HashSet<>();
        for (Object obj : pathsMap.keySet()){
            if (null == obj ){
                continue;
            }
            String path  = obj.toString();
            SwaggerRequestMethodMap requestMethodMap =  pathsMap.getTo(path);
            /** 通过url包括搜索**/
            if(path.toUpperCase().contains(search.toUpperCase()) ){
                searchPathsMap.put( path, requestMethodMap);
                for (Object object: requestMethodMap.entrySet()){
                    Map.Entry entry = (Map.Entry)object;
                    if (entry != null && entry.getValue() instanceof SwaggerRequestMethodBean ) {
                        SwaggerRequestMethodBean methodBean = (SwaggerRequestMethodBean)entry.getValue() ;
                        tags.addAll(methodBean.getTags());
                    }
                }
                continue;
            }

            if (null == requestMethodMap || requestMethodMap.isEmpty()){
                continue;
            }/**通过功能描述搜索**/
            for (Object object : requestMethodMap.entrySet()){
                if(object == null ){
                    continue;
                }
                if((object instanceof Map.Entry)){
                    Map.Entry entry = (Map.Entry)object;
                    if(entry != null && entry.getValue() instanceof SwaggerRequestMethodBean ){
                        SwaggerRequestMethodBean methodBean = (SwaggerRequestMethodBean)entry.getValue() ;
                        if(null !=  methodBean.getSummary() &&  methodBean.getSummary().contains(search) ){
                            searchPathsMap.put( path, requestMethodMap);
                            tags.addAll(methodBean.getTags());
                            continue;
                        }
                    }

                }
            }/** 通过方法编写作者搜索 **/
            for (Object object : requestMethodMap.entrySet()){
                if(object == null ){
                    continue;
                }
                if((object instanceof Map.Entry)){
                    Map.Entry entry = (Map.Entry)object;
                    if(entry != null && entry.getValue() instanceof SwaggerRequestMethodBean ){
                        SwaggerRequestMethodBean methodBean = (SwaggerRequestMethodBean)entry.getValue() ;
                        if(null !=  methodBean.getAuthor() &&  methodBean.getAuthor().toUpperCase().contains(search.toUpperCase()) ){
                            searchPathsMap.put( path, requestMethodMap);
                            tags.addAll(methodBean.getTags());
                            continue;
                        }
                    }

                }
            }
        }

        List<SwaggerTagBean> swaggerTagBeans =  new ArrayList<>();
        for (SwaggerTagBean tagBean:resultBean.getTags()) {
            if (tags.contains(tagBean.getName())) {
                swaggerTagBeans.add(tagBean);
            }
        }

        bean.setPaths(searchPathsMap);
        bean.setTags(swaggerTagBeans);
        bean.setDefinitions(resultBean.getDefinitions());
        return bean;
    }



    /**
     * 获取指定文件下面的RequestMapping方法保存在mapp中
     * T extends Annotation
     * @return
     */
    @PostConstruct
    public void init() {
        List<String> packageList  = new ArrayList(16);
        packageList.add("com");
        if(packages != null && !"".equals(packages)){
            packageList.addAll(Arrays.asList(packages));
        }
        Reflections reflections = new Reflections(packageList);
        Set<Class<?>> classesList = reflections.getTypesAnnotatedWith(Controller.class);
        Set<Class<?>> restList = reflections.getTypesAnnotatedWith(RestController.class);

        Set<Class<?>> apiDocList = reflections.getTypesAnnotatedWith(ApiDoc.class);

        TreeMap<String,Class> controllerTreeClass = new TreeMap<>();
        controllerTreeClass.putAll( classesList.stream().collect(Collectors.toMap(Class::getSimpleName, clazz -> clazz)));
        controllerTreeClass.putAll(restList.stream().collect(Collectors.toMap(Class::getSimpleName, clazz -> clazz)));
        controllerTreeClass.putAll(apiDocList.stream().collect(Collectors.toMap(Class::getSimpleName, clazz -> clazz)));
        controllerClass.addAll(controllerTreeClass.values());
        // 存放url和   RequestMapping 的对应关系
        for (Class classes : controllerClass) {
            //得到该类下面的所有方法
            Method[] methods = classes.getDeclaredMethods();
            if (null == methods || methods.length == 0){
                continue;
            }

            ApiDoc table = AnnotationUtils.findAnnotation(classes, ApiDoc.class);
            if(table != null){
//                String desc = table.module() == null ? table.value() : table.module();
//                desc =  desc == null ? classes.getSimpleName() : desc;
//                desc = notEquals(table.groupDesc()) ? table.groupDesc() : desc;
//
//                String groupName = notEquals(table.group()) ? table.group():classes.getSimpleName();
                classToGroup.put(classes.getName(),table);
//                classDescription.put(classes.getName(), desc);
            }
            for (Method method : methods) {
                try {
                    //得到该类下面的RequestMapping注解
                    ApiDoc apiDoc =  method.getAnnotation(ApiDoc.class);
                    if(null == apiDoc){
                        continue;
                    }
                    SwaggerMethodBean methodBean =  SwaggerMethodBean.build();
                    String path  = methodMapping(method,methodBean, apiDoc);

                    if (null == path ) {
                        continue;
                    }

                    Class apiDocResponse = null;
                    String apiDocResponseName = null;
                    if(null !=  apiDoc.response()){
                        if(apiDoc.response().length == 1){
                            apiDocResponse = apiDoc.response()[0];
                            apiDocResponseName = apiDocResponse.getName();
                        }else {
                            apiDocResponse = getConstructorClass(apiDoc.response());
                            if(apiDocResponse != null){
                                apiDocResponseName = apiDocResponse.getName();
                            }
                        }
                    }

                    methodBean.setRequestInfo(apiDoc.value())
                            .setRequestName(apiDoc.request().getName())
                            .setResponseName(apiDocResponseName)
                            .setRequestAuthor(apiDoc.author())
                    ;

                    Map<String, SwaggerMethodBean> methodClass =  requestClass.get(classes.getName());
                    if(methodClass == null){
                        methodClass = new HashMap<>();
                    }
                    methodClass.put(path,methodBean);
                    requestClass.put(classes.getName(),methodClass);
                    converterParameterBean(apiDoc.request());


                    converterResponseVo(apiDocResponse);
                    urlSet.add(path);
                } catch (Exception e) {
                    logger.error("SwaggerReflectionsDoc init exception: [{}]", e);
                    e.printStackTrace();
                }

            }
        }
        out();
    }

    private  String methodMapping(Method method, SwaggerMethodBean methodBean, ApiDoc apiDoc){
        methodBean.setRequestMethodName(method.getName());

        RequestMapping mapping =  method.getAnnotation(RequestMapping.class);
        if (notNull(mapping) && notOne(mapping.value())) {
            methodBean.setRequestUrl(mapping.value()[0]);
            if (null != mapping.method() && mapping.method().length >= 1){
                methodBean.setRequestMethodType(mapping.method()[0].name());
            }else {
                methodBean.setRequestMethodType("GET");
            }
            return mapping.value()[0];
        }
        PostMapping post =  method.getAnnotation(PostMapping.class);
        if (notNull(post) && notOne(post.value())) {
            methodBean.setRequestUrl(post.value()[0]);
            methodBean.setRequestMethodType("POST");
            return post.value()[0];
        }
        GetMapping get =  method.getAnnotation(GetMapping.class);
        if (notNull(get) && notOne(get.value())) {
            methodBean.setRequestUrl(get.value()[0]);
            methodBean.setRequestMethodType("GET");
            return get.value()[0];
        }
        DeleteMapping delete =  method.getAnnotation(DeleteMapping.class);
        if (notNull(delete) && notOne(delete.value())) {
            methodBean.setRequestUrl(delete.value()[0]);
            methodBean.setRequestMethodType("DELETE");
            return delete.value()[0];
        }
        PatchMapping patch =  method.getAnnotation(PatchMapping.class);
        if (notNull(patch) && notOne(patch.value())) {
            methodBean.setRequestUrl(patch.value()[0]);
            methodBean.setRequestMethodType("PATCH");
            return patch.value()[0];
        }
        ApiCmd apiCmd =  method.getAnnotation(ApiCmd.class);
        if (notNull(apiCmd) && notOne(apiCmd.cmd())) {
            String cmd = apiDoc.cmd();
            methodBean.setRequestUrl(cmd);
            methodBean.setRequestMethodType(apiCmd.methodType());
            return cmd;
        }
        if(notNull(apiDoc) ){
            String apiMethodType  = apiDoc.methodType();
            String cmd = apiDoc.cmd();
            methodBean.setRequestUrl(cmd);
            methodBean.setRequestMethodType(apiMethodType);
            return cmd;
        }
        return null;

    }

    private boolean notEquals(Object patch){
        return patch != null && !"".equals(patch) ;
    }
    private boolean notNull(Object patch){
        return patch != null;
    }
    private boolean notOne(String... path){
        if (null != path && path.length == 1 )  {
            return true;
        }
        return false;

    }

    private Class<?> getConstructorClass(Class<?>[] response){
        try{
            Class[] classes = new Class[response.length - 1];
            System.arraycopy(response, 1, classes, 0, classes.length);
            Constructor constructor = response[0].getDeclaredConstructor(classes);
            return constructor.getClass();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private  void converterParameterBean(Class entityClazz){
        List<Field> fieldList = FieldUtils.getAllFieldsList(entityClazz);
        List list = new ArrayList();
        for (Field field : fieldList) {
            ApiDesc apiDesc = field.getAnnotation(ApiDesc.class);
            if(null == apiDesc){
                continue;
            }
            String type =  "".equals(apiDesc.type()) ?  field.getType().getSimpleName() : apiDesc.type();
            SwaggerParameterBean parameterBean = SwaggerParameterBean.build()
                    .setName(field.getName())
                    .setType(type)
                    .setDescription( apiDesc.value())
                    .setRequired(apiDesc.required() == 1);
            list.add(parameterBean);

        }
        requestVoMap.put(entityClazz.getName(),list);
    }
    private  void converterResponseVo(Class entityClazz){
        if(entityClazz == null){
            entityClazz = Long.class;
        }
        if(isPrimitiveType(entityClazz)){
            primitiveTypeClass(entityClazz);
            return;
        }

        //排除map类
        if(isMapOrJson(entityClazz)){
            logger.warn("entityClazz is map Object  pass return entityClazz[{}]..........,",entityClazz);
            return;
        }
        List<Class> allClassList = new ArrayList();
        AtomicInteger indexCount = new AtomicInteger();
        List<Class> classList = converterResponse(entityClazz, allClassList, indexCount );
        if(classList.isEmpty()){
            return;
        }
        while (!classList.isEmpty()) {
            final List<Class> allClass = new ArrayList<>(classList);
            classList.clear();
            for (Class clazz : allClass){
                classList.addAll(converterResponse(clazz,allClassList,indexCount));
            }
        }

    }
    private   List<Class> converterResponse(Class entityClazz,List<Class> allClassList,AtomicInteger indexCount){
        List<Class> classList = new ArrayList<>();
        //排除map类
        if(isMapOrJson(entityClazz)){
            return classList;
        }
        if(allClassList.contains(entityClazz)){
            logger.warn(" allClassList is contains entityClazz   return classList:[{}] and entityClazz:[{}]..........,",classList,entityClazz);
            return classList;
        }
        allClassList.add(entityClazz);

       int count =  indexCount.getAndIncrement();
       if(count > 1){
           logger.info(" BaseClass:[{}] ====== entityClazz:[{}]  to converterResponse Object  pass return ..........",allClassList.get(0),entityClazz);
       }if(count > 100){
           return new ArrayList<>();
        }

        List<Field> fieldList = FieldUtils.getAllFieldsList(entityClazz);

        Map<String, SwaggerResponseProperty> map = new TreeMap<>();
        for (Field field : fieldList) {
            try {
                ApiDesc apiDesc = field.getAnnotation(ApiDesc.class);
                String descValue = "";
                if (null != apiDesc) {
                    descValue = apiDesc.value();
                }
                // 如果是类或集合,从新回调;
                Class<?> fieldType = field.getType();
                if (isTypeClass(field)) { //过滤 排除 静态,或final类型
                    continue;
                } //过滤
                if(isMapOrJson(fieldType)){
                    logger.warn("entityClazz is map Object  pass return fieldType[{}]..........,",fieldType);
                    continue;
                } //过滤 类自己本身，如果出现迭归类，会出现死循环
                if(isOneselfClass(fieldType,entityClazz)){
                    continue;
                }

                SwaggerResponseProperty property = SwaggerResponseProperty.build();
                if (isPrimitiveType(fieldType)) {
                    property.setType(field.getType().getSimpleName()).setDescription(descValue);
                    map.put(field.getName(), property);
                    continue;
                }
                if (isIterable(fieldType)) {
                    Type genericType = field.getGenericType();
                    if (genericType != null && genericType instanceof ParameterizedType) {
                        ParameterizedType parameterizedType = (ParameterizedType) genericType;
                        //得到泛型里的class类型对象。
                        Class<?> genericClazz = getRawType(genericType);
                        if(genericClazz == null){
                            continue;
                        }
                        //过滤 类自己本身，如果出现迭归类，会出现死循环
                        if(isOneselfClass(genericClazz,entityClazz)){
                            continue;
                        }
                        property.setType(fieldType.getSimpleName() + ":" + genericClazz.getSimpleName()).setRef(genericClazz.getSimpleName()).setDescription(descValue);
                        map.put(field.getName(), property);
                        classList.add(genericClazz);
                    }
                    continue;
                }


                if(null == fieldType || field.getGenericType()  instanceof TypeVariable){
                    continue;
                }
                if (fieldType instanceof Object && null != apiDesc) {
                    property.setRef(fieldType.getSimpleName()).setDescription(descValue);
                    map.put(field.getName(), property);
                    classList.add(fieldType);
                } else {
                    logger.warn("暂时不支持该类型:[{}], class 类为:[{}]", fieldType,entityClazz);
                }
            }catch (Exception e){
                logger.error("返回文档类转换类型异常 converterResponse class Exception :[{}]", e);
                e.printStackTrace();
            }
        }

        converterParameterBean(entityClazz,map);

        return classList;

    }

    // type不能直接实例化对象，通过type获取class的类型，然后实例化对象
    public static Class<?> getRawType(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] rawType = parameterizedType.getActualTypeArguments();
//            Type rawType = parameterizedType.getRawType();
            if(rawType[0] instanceof TypeVariable){
                return null;
            }
            return (Class) rawType[0];
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            return Array.newInstance(getRawType(componentType), 0).getClass();
        } else if (type instanceof TypeVariable) {
            return Object.class;
        } else if (type instanceof WildcardType) {
            return getRawType(((WildcardType) type).getUpperBounds()[0]);
        } else {
            String className = type == null ? "null" : type.getClass().getName();
            throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + className);
        }
    }

    private void primitiveTypeClass(Class clazz){
        if( null !=  responseVoMap.get(clazz.getName())){
            return;
        }
        Map<String, SwaggerResponseProperty> map = new TreeMap<>();
        SwaggerResponseProperty property = SwaggerResponseProperty.build();
        String fieldName  = "id";
        String descValue  = "id数据表对应的主键值";
        if(clazz.equals(Boolean.class) || clazz.equals(boolean.class)){
            fieldName  = "result";
            descValue  = "操作数据后返回:1.成功, 0.失败";
        }
        if(clazz.equals(byte[].class)){
            fieldName  = "byte[]";
            descValue  = "字节数组";
        }
        property.setType(clazz.getSimpleName()).setDescription(descValue);
        map.put(fieldName,property);
        converterParameterBean(clazz,map);

    }
    private void converterParameterBean(Class clazz, Map<String, SwaggerResponseProperty> map){

        SwaggerResponseParameterMap parameterBean = SwaggerResponseParameterMap.build();
        parameterBean.responseResultVo(clazz.getSimpleName());
        parameterBean.responseDataVo(clazz.getSimpleName(),map);
        responseUrlMap.put(clazz.getName(),clazz.getSimpleName());
        responseVoMap.put(clazz.getName(),parameterBean);
    }
    /**
     * 排除 静态,或final类型
     * @param field
     * @return
     */
    private static boolean isTypeClass(Field field) {
        boolean isStatic = Modifier.isStatic(field.getModifiers());
        boolean isFinal = Modifier.isFinal(field.getModifiers());
//			boolean isTyep = RedisSetEnum.isContains(property.getType().getSimpleName());
        return isStatic || isFinal;
    }
    /**属性类型**/
    private static boolean isPrimitiveType(Class<?> fieldType) {
        return ClassUtils.isPrimitiveOrWrapper(fieldType)
                || fieldType == String.class || fieldType == Date.class || fieldType == byte[].class ;
    }

    //排除map类
    private static  boolean isMapOrJson(Class<?> fieldType){
        boolean isLog = isMapOrJsonClass(fieldType);
        if(isLog){
            logger.warn("entityClazz is map Object  pass return entityClazz[{}]..........,",fieldType);
        }
        return isLog;
    }
    private static  boolean isMapOrJsonClass(Class<?> fieldType){
        if(fieldType.isAssignableFrom(Map.class)){
            return true;
        }

        Class<?> interfaces[] = fieldType.getInterfaces();
        if(null != interfaces && Arrays.asList(interfaces).contains(Map.class)){
            return true;
        }
        if("JSONOBJECT".equals(fieldType.getSimpleName().toUpperCase())){
            return true;
        }
        return false;
    }

    /** 排队类本身，解决迭归类死循环问题 **/
    private static  boolean isOneselfClass(Class<?> fieldType,Class<?> entityClazz){
        boolean isOwnerClazz = fieldType.equals(entityClazz) || entityClazz.getSimpleName().equals(fieldType.getSimpleName());
        if(isOwnerClazz){
            logger.warn(" class isOneselfClass is contains    fieldType:[{}] and entityClazz:[{}]..........,",fieldType,entityClazz);
        }
        return isOwnerClazz;
    }


    /**
     * 数组或集合类型
     * @param clazz
     * @return
     */
    private static boolean isIterable(Class<?> clazz) {
        return (clazz.isArray() || Collection.class.isAssignableFrom(clazz))
                && !byte[].class.isAssignableFrom(clazz);
    }

    public void out(){
        try {

            SwaggerInfo swaggerInfo = SpringUtil.getBean(SwaggerInfo.class);
            Environment env = SpringUtil.getApplicationContext().getEnvironment();
            String ip = InetAddress.getLocalHost().getHostAddress();
            String port = env.getProperty("server.port");
            String path = env.getProperty("server.servlet.context-path");
            if(swaggerInfo == null){
                swaggerInfo =   SwaggerInfo.init();
                swaggerInfo.setTermsOfService(ip+":"+port+path);
            }
            SwaggerResultBean bean = new SwaggerResultBean();
            bean.setInfo(swaggerInfo);

            bean.setBasePath(path).setHost(ip+":"+port).setSwagger(swaggerInfo.getVersion());

            bean.setPaths(SwaggerPathsMap.build()).setDefinitionsAll(responseVoMap.values());
            for(Class classes : controllerClass){
//            SwaggerRequestMethodBean methodBean = SwaggerRequestMethodBean.init();
                try {
                    Map<String, SwaggerMethodBean> methodUrlMap =  requestClass.get(classes.getName());
                    if(null == methodUrlMap){
                        continue;
                    }

                    ApiDoc table   = classToGroup.get(classes.getName());


                    SwaggerTagBean swaggerTagBean = null;
                    /**通过指定实现分组 **/
                   String className = table.group();
                   if(className == null) {
                       //若没有指定分级,则通过类名组分组;
                       className =  classes.getSimpleName();
                   }

                    swaggerTagBean = groupToBean.get(className);
                    if(null == swaggerTagBean) {
                        swaggerTagBean = SwaggerTagBean.init(className, table);
                        bean.setTags(swaggerTagBean);
                        groupToBean.put(className,swaggerTagBean);
                    }
//                    swaggerTagBean = SwaggerTagBean.init(groupName, classDesc);
//                    bean.setTags(swaggerTagBean);

                    for (String url : methodUrlMap.keySet()){
                        try {

                            SwaggerMethodBean swaggerMethodBean =  methodUrlMap.get(url);
                            SwaggerRequestMethodBean methodBean = SwaggerRequestMethodBean.init();

                            StringBuilder sb = new StringBuilder().append(swaggerMethodBean.getRequestMethodName()).
                                    append(classes.getSimpleName()).append(swaggerMethodBean.getRequestMethodType());

                            String responseUrl = responseUrlMap.get(swaggerMethodBean.getResponseName());

//                            methodBean.setTags((classes.getSimpleName()));
                            methodBean.setTags(className);
                            methodBean.setSummary(swaggerMethodBean.getRequestMethodName() + spance + swaggerMethodBean.getRequestInfo());
                            methodBean.setDescription(swaggerMethodBean.getRequestInfo());
                            methodBean.setAuthor(swaggerMethodBean.getRequestAuthor());
                            methodBean.setOperationId(sb.toString());
                            methodBean.setParameters(requestVoMap.get(swaggerMethodBean.getRequestName()));

                            methodBean.setResponses(SwaggerResponseResultMap.build().setCode().setResponse(responseUrl));
                            methodBean .setDeprecated(false);


                            SwaggerRequestMethodMap methodMap = SwaggerRequestMethodMap.build().put(swaggerMethodBean.getRequestMethodType(),methodBean);

                            //                paths.put(url,methodMap);
                            bean.getPaths().put(url,methodMap);
                        }catch (Exception e){
                            logger.error("Exception classes :[{}] request methodUrl : [{}], printStackTrace :[{}] ",classes, url,e);
                            e.printStackTrace();
                        }
                    }
                }catch (Exception e){
                    logger.error("Exception classes :[{}] request SwaggerMethodBean bean : [{}], printStackTrace :[{}] ",classes, bean.toString(),e);
                    e.printStackTrace();
                }

            } apiDoc.put("api", bean);
            cleanAllParam();
        }catch (Exception e){
            logger.error("Exception classes :[{}] request Method out , printStackTrace :[{}] ",SwaggerReflectionsDoc.class,e);
            e.printStackTrace();
        }
    }

    private void cleanAllParam(){
        urlSet = null;
        controllerClass = null;
//        classDescription = null;
        requestVoMap = null;
        responseVoMap = null;
        responseUrlMap = null;
        classToGroup = null;
        groupToBean = null;
    }

    public static void main(String[] agr){
        SwaggerReflectionsDoc doc =  new SwaggerReflectionsDoc();
        Class[] classes = {SwaggerResponseVo.class, SwaggerRequestVo.class};
        doc.getConstructorClass(classes);

//        doc.out();
//        doc.converterResponseVo(SwaggerResponse2Vo.class);

//        Set<Class<?>> module = reflections.getSubTypesOf(RequestMapping.class);
//
//        for (Class claxx : module) {
//            //获取该类上指定Observer标签的方法
//           System.out.println(claxx);
//        }
    }
}
