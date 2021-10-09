
//
//package top.suven.future.generator.config;
//
//import org.apache.commons.lang3.EnumUtils;
//import com.suven.framework.generator.dao.*;
//import com.suven.framework.generator.temp.CreateCodeEnum;
//import com.suven.framework.generator.temp.JdbcCodeCacheEnum;
//import com.suven.framework.generator.temp.JdbcCodeStatusEnum;
//import com.suven.framework.generator.temp.MybatisCodeEnum;
//import com.suven.framework.generator.utils.RRException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.*;
//
///**
// * 数据库配置
// *
// * @author Mark sunlightcs@gmail.com
// */
//@Configuration
//public class DbConfig {
//    @Value("${renren.database: mysql}")
//    private String database;
//
//    @Autowired
//    private SysDataConfig sysDataConfig;
//
//    @Autowired
//    private MySQLGeneratorDao mySQLGeneratorDao;
//    @Autowired
//    private OracleGeneratorDao oracleGeneratorDao;
//    @Autowired
//    private SQLServerGeneratorDao sqlServerGeneratorDao;
//    @Autowired
//    private PostgreSQLGeneratorDao postgreSQLGeneratorDao;
//
////    @Bean
////    @Primary
//    public GeneratorDao getGeneratorDao(){
//        database =  sysDataConfig.getSysConfig().getDatabaseType();
//        if("mysql".equalsIgnoreCase(database)){
//            return mySQLGeneratorDao;
//        }else if("oracle".equalsIgnoreCase(database)){
//            return oracleGeneratorDao;
//        }else if("sqlserver".equalsIgnoreCase(database)){
//            return sqlServerGeneratorDao;
//        }else if("postgresql".equalsIgnoreCase(database)){
//            return postgreSQLGeneratorDao;
//        }else {
//            throw new RRException("不支持当前数据库：" + database);
//        }
//    }
//
//
//}
