package com.suven.framework.generator.config;

import com.suven.framework.generator.dao.*;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.suven.framework.generator.enums.BaseEntityEnum;
import com.suven.framework.generator.temp.CreateCodeEnum;
import com.suven.framework.generator.temp.JdbcCodeCacheEnum;
import com.suven.framework.generator.temp.JdbcCodeStatusEnum;
import com.suven.framework.generator.temp.MybatisCodeEnum;
import com.suven.framework.generator.utils.RRException;
import com.suven.framework.generator.dao.*;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class SysDataConfig {

    private LinkedHashMap<Integer, ProjectPathConfig> pathMap = new LinkedHashMap<>();

    private SysConfig sysConfig = new SysConfig();
    private ProjectPathConfig projectPathConfig;

    @Autowired
    private MySQLGeneratorDao mySQLGeneratorDao;
    @Autowired
    private OracleGeneratorDao oracleGeneratorDao;
    @Autowired
    private SQLServerGeneratorDao sqlServerGeneratorDao;
    @Autowired
    private PostgreSQLGeneratorDao postgreSQLGeneratorDao;


    @PostConstruct
    public void init(){
//        projectPathConfig = ProjectPathConfig.init();
//        pathMap.putIfAbsent(1,projectPathConfig);
        pathMap.putIfAbsent(2,ProjectPathConfig.init2());
        pathMap.putIfAbsent(3,ProjectPathConfig.init3());
        pathMap.putIfAbsent(4,ProjectPathConfig.init4());
        pathMap.putIfAbsent(5,ProjectPathConfig.init5());
    }

    public GeneratorDao getGeneratorDao(){
        String database =  sysConfig.getDatabaseType();
        if("mysql".equalsIgnoreCase(database)){
            return mySQLGeneratorDao;
        }else if("oracle".equalsIgnoreCase(database)){
            return oracleGeneratorDao;
        }else if("sqlserver".equalsIgnoreCase(database)){
            return sqlServerGeneratorDao;
        }else if("postgresql".equalsIgnoreCase(database)){
            return postgreSQLGeneratorDao;
        }else {
            throw new RRException("不支持当前数据库：" + database);
        }
    }


    public boolean put(Integer key, ProjectPathConfig value){
        pathMap.put(key,value);
        if(value.getIsUse() == 1){
            projectPathConfig = value;
        }
        return true;
    }
    public boolean putIfAbsent(Integer key, ProjectPathConfig value){
        pathMap.putIfAbsent(key,value);
        if(value.getIsUse() == 1){
            projectPathConfig = value;
        }
        return true;
    }


    public List<ProjectPathConfig> getList(){
        return new ArrayList<>(pathMap.values());
    }

    public ProjectPathConfig getProjectPathConfig(){
        if(projectPathConfig == null){
            for (ProjectPathConfig path : getList()){
                if(path.getIsUse() == 1 ){
                    projectPathConfig = path;
                    break;
                }//获取最大id
            }
        }
        return projectPathConfig;
    }


    public Class getTempCodeEnumClass(){
        String tempEnum =  sysConfig.getTempEnum();
        if("JdbcCodeCacheEnum".equalsIgnoreCase(tempEnum)){
            return  JdbcCodeCacheEnum.class;
        }else if("JdbcCodeStatusEnum".equalsIgnoreCase(tempEnum)){
            return JdbcCodeStatusEnum.class;
        }else if("MybatisCodeEnum".equalsIgnoreCase(tempEnum)){
            return MybatisCodeEnum.class;
        }else {
            throw new RRException("不支持当前模板：" + tempEnum);
        }
    }

    public String getEntityClass(){
        String entity =  sysConfig.getEntity();
       return entity;
    }

    public BaseEntityEnum getBaseEntityNo(){
        String entity =  sysConfig.getEntity();
        if(BaseEntityEnum.BASE_ENTITY.getValue().equalsIgnoreCase(entity)){
            return  BaseEntityEnum.BASE_ENTITY;
        }else if(BaseEntityEnum.BASE_STATUS_ENTITY.getValue().equalsIgnoreCase(entity)){
            return  BaseEntityEnum.BASE_STATUS_ENTITY;
        }
        throw new RRException("不支持当前模板：" + entity);
    }


    /**
     * 1.JdbcBaseEntityDao, 2.JdbcBaseCacheDao,3.MyBatisBaseEntityDao,4.MyBatisBaseCacheDao
     * @return
     */
    public String getEntityDao(){
        String entity =  sysConfig.getEntityDao();
        return entity;
//        if(entity == 1){
//            return "JdbcBaseEntityDao";
//        }else if(entity == 2){
//            return "JdbcBaseCacheDao";
//        }else if(entity == 3){
//            return "MyBatisBaseEntityDao";
//        }else if(entity == 4){
//            return "MyBatisBaseCacheDao";
//        }else {
//            throw new RRException("不支持当前JAVA实现类BEAN：" + entity);
//        }
    }

    public static Map<String, CreateCodeEnum> getEnumMap(Class enumClass){
        Map<String, CreateCodeEnum> map = new HashMap<>();
        Collection<Enum> enums =  EnumUtils.getEnumMap(enumClass).values();
        enums.forEach( e -> {
            if(e instanceof CreateCodeEnum){
                CreateCodeEnum code =  (CreateCodeEnum)e;
                map.put(code.getTemp(),code);
            }
        });
        return map;
    }

    public SysConfig getSysConfig() {
        return sysConfig;
    }

    public SysConfig updateSysConfig(SysConfig sys){
        BeanUtils.copyProperties(sys,sysConfig);
        return sysConfig;
    }

    public static CreateCodeEnum getEnum(String key, Class<Enum> codeEnum){
        return getEnumMap(codeEnum).get(key);
    }




}
