package com.suven.framework.core.db;


import com.suven.framework.common.constants.ReflectionsScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Title: DatasourceEnumManager.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) DruidDataSource 数据库聚群加载实现和管理类,通过扫描动态加载
 *  标注上该  @DatasourceEnumAnno 标签和实现 DataSourceGroupEnumInterface 接口的枚举实现类;
 * @Copyright: (c) 2018 gc by https://www.suven.top
 *
 */
@Deprecated
public class DatasourceEnumManager <E extends Enum<E>>  {

    private static final Logger logger = LoggerFactory.getLogger(DatasourceEnumManager.class);

    /** 用于存储 模块名称 对应的数据库的实现枚举信息 k,v - >  groupEnum.getModule, new DataSourceGroup() **/
//    static Map<String,DataSourceGroupEnumInterface> groupEnumValueToEnumMap = new LinkedHashMap<>();

    /** 用于存储 数据库对应的模块名称组信息 k,v - >  groupEnum.getModule(), new DataSourceGroup() **/
    static Map<String,DataSourceGroup> dataSourceGroupMapByModule = new ConcurrentHashMap<>();

    /** 用于检查数据库对应的 模块名称 是否重复定义 **/
    static Map<String,Class> checkDataSourceNameMapIsDouble = new HashMap<>();

    static {
        Set<Class<?>> classList = ReflectionsScan.reflections.getTypesAnnotatedWith(DatasourceEnumAnno.class);
        if(null == classList || classList.isEmpty()){
            throw new RuntimeException(" please create  Enum Class not  implements DataSourceGroupEnumInterface Class ........eg: class => DataSourceGroupNameEnum " );
        }
        for ( Class enumClass : classList) {
            addDatasourceEnum(enumClass);
        }
        checkDataSourceNameMapIsDouble.clear();
//        dataSourceNameMap = null;
    }
    /**
     * 将数据库模块化枚举实现例 初始化到DatasourceEnumManager容器中;
     * @param enumClass
     * @param <E>
     */
    public static <E extends Enum<E>>void addDatasourceEnum( Class<E> enumClass ) {
        if( ! DataSourceGroupEnumInterface.class.isAssignableFrom(enumClass) ){
            throw new RuntimeException(" Enum Class not  implements DataSourceGroupEnumInterface Class ........ " );
        }
        EnumSet<E> values = EnumSet.allOf(enumClass);
        for(Enum groupEnum : values) {
            DataSourceGroupEnumInterface dsGroup = (DataSourceGroupEnumInterface)groupEnum;
            Class<Enum> dbEnumClass =  checkDataSourceNameMapIsDouble.get(dsGroup.getModule());
            if(dbEnumClass != null){
                StringBuilder sb = new StringBuilder();
                sb.append(" Class DatasourceEnumManager method addDatasourceEnum() ");
                sb.append(" Pass Enum class convert to dataSourceName EnumKey[").append(groupEnum.name()).append("]");
                sb.append(" ,EnumValue[").append(dsGroup.getModule()).append("] is exist ");
                sb.append("from EnumClass[").append(dbEnumClass).append(",").append(enumClass).append("] to Double");
                sb.append(" please modify datasource name value");
                throw new RuntimeException(sb.toString());
            }
            checkDataSourceNameMapIsDouble.put(dsGroup.getModule(),enumClass);

//            groupEnumValueToEnumMap.put(dsGroup.getModule(), dsGroup);


            if(dataSourceGroupMapByModule.keySet().contains(dsGroup.getModule())){
                throw new RuntimeException("pass Enum class convert dataSourceGroupMap key["+groupEnum.name()+"] is exist " );
            }
            dataSourceGroupMapByModule.put(dsGroup.getModule(), new DataSourceGroup().setGroupName(dsGroup.getModule()));
        }
    }

    /**
     * 获取所有实例化的数据库对应的枚举实现Map
     * @return
     */

    public static Map<String,Enum> getDatasourceEnumMap() {
        return  new LinkedHashMap( dataSourceGroupMapByModule);
    }



    /**
     * 通过 枚举例 获取指定模块对应的数据库组(主--从)数据库聚群
     * 通过组名，获取数据源集合对象
     * @param groupModule
     * @return
     */
    public static DataSourceGroup getDataSourceGroupByModule(DataSourceGroupEnumInterface groupModule){
        if(null == groupModule ){
            logger.warn("DataSourceGroup getDataSourceGroupByEnumName by groupEnumName[{}]", groupModule);
            return null;
        }
        DataSourceGroup dataSourceGroup = dataSourceGroupMapByModule.get(groupModule.getModule());
        return dataSourceGroup;
    }

        /**
     * 通过 枚举例 获取指定模块对应的数据库组(主--从)数据库聚群
     * 通过组名，获取数据源集合对象
     * @param groupModule
     * @return
     */
    public static DataSourceGroup getDataSourceGroupByModule(String groupModule){
        if(null == groupModule ){
            logger.warn("DataSourceGroup getDataSourceGroupByEnumName by groupEnumValue[{}]", groupModule);
            return null;
        }

        DataSourceGroup dataSourceGroup = dataSourceGroupMapByModule.get(groupModule);
        return dataSourceGroup;
    }

    /**通过组名，主或从类型 获取数据源集合对象； **/
    public  static DataSourceGroup getDataSourceGroupAndDataType(String groupEnumName, DataSourceTypeEnum dataType){
        if(null == groupEnumName ){
            logger.warn("DataSourceGroup getDataSourceGroupByName by groupName[{}]", groupEnumName);
            return null;
        }
        DataSourceGroup dataSourceGroup = dataSourceGroupMapByModule.get(groupEnumName);
        if(dataSourceGroup == null){
            return null;
        }
        return dataSourceGroup.setDataType(dataType);
    }

    /**
     * 初始 业务模块组数据源聚合;
     * @param groupNameEnum  业务模块组名称;
     * @param masterDatasourceKey  主数据库名称字符串
     * @param slaveDatasourceList 从数据库名称聚合列表
     */
    static void setDataSourceGroup(DataSourceGroupEnumInterface groupNameEnum, String masterDatasourceKey, List<String> slaveDatasourceList ){

        DataSourceGroup dataSourceGroup = dataSourceGroupMapByModule.get(groupNameEnum.getModule());
        if( null == dataSourceGroup){
            logger.warn("DataSourceGroup init setMasterDataSourceKey by dataSourceGroup groupNameEnum[{}]", dataSourceGroup);
            return ;
        }
        dataSourceGroup.setMasterSources(masterDatasourceKey);
        if(slaveDatasourceList != null){
            dataSourceGroup.setSlaveSources(slaveDatasourceList);
        }


    }





    public static void main(String[] args) {
//        DatasourceEnumManager.addDatasourceEnum(DataSourceGroupEnum.class);
        DatasourceEnumManager.addDatasourceEnum(DataSourceGroupNameEnum.class);
    }
}
