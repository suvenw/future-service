package com.suven.framework.core.db;

import com.suven.framework.util.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import com.suven.framework.util.random.RandomUtils;

import javax.sql.DataSource;
import java.util.*;


/**
 * 获取数据源
 *
 * @author joven
 * @version 2018/6/15 13:18
 */

/**
 * 将 DataSourceGroupNameEnum 数据源组 生成bean对象注入到spring管理容器中；
 * 通过 datasource_druid_enabled 对应模块数据库配置总开关，默认值为true
 * 通过 datasource_druid_slave_enabled 对应模块从数据库配置总开关，默认值为true
 * 将模块初始化后数据源信息，并将结果生成spring bean 名称缓存到DataSourceGroupNameEnum对象中
 * 将所有模块通过initDataByGroup 初始化后的数据库的聚群，初始化到目标的动态数据池子里；
 * 再根据模块名称字符串 作为模块key 从targetDataSources 的Map中获取指定模块的数据库的连接池；
 * 从而通过动态桥接的设计模式来达到数据源连接池动态切换原理；
 */
@Deprecated
public class DynamicDataSourceEnum extends AbstractRoutingDataSource {

	private final Logger logger = LoggerFactory.getLogger(getClass());


	private boolean isDefaultTargetDataSource = true;

	private Map<Object, Object> targetDataSources = new LinkedHashMap<>();


	public Map<Object, Object> getTargetDataSources(){
		return targetDataSources;
	}

	/**优点：避免了线程不安全，延迟加载，效率高。
	 静态内部类的方式利用了类装载机制来保证线程安全，只有在第一次调用getInstance方法时，
	 才会装载SingletonInstance内部类，完成Singleton的实例化，所以也有懒加载的效果。 **/
	private DynamicDataSourceEnum() {}

	private static class DynamicDataSourceInstance {
		private static final DynamicDataSourceEnum INSTANCE = new DynamicDataSourceEnum();
	}

	public static DynamicDataSourceEnum getInstance() {
		return DynamicDataSourceInstance.INSTANCE;
	}

	/**
	 * 将所有模块通过initDataByGroup 初始化后的数据库的聚群，初始化到目标的动态数据池子里；
	 */
	public void setTargetDataSources(){
		super.setTargetDataSources(targetDataSources);
	}


	/**
	 * DataSourceGroupNameEnum 枚举类型的实现初始化指定模块数据源信息；
	 * @param dataSourceGroupEnum
	 */
	public void initDataByGroup(DataSourceGroupEnumInterface dataSourceGroupEnum, ApplicationContext applicationContext){
		Map<Object, Object> targetDataSources = new LinkedHashMap<>();

        DataSourceGroup group = DatasourceEnumManager.getDataSourceGroupByModule(dataSourceGroupEnum.getModule());
		if(null == group || null == group.getMasterSources()){
			return;
		}
        logger.info("DataSourceGroup [{}]", JsonUtils.toJson(group));
		String master = group.getMasterSources();
		DataSource defaultTargetDataSource = applicationContext.getBean(master,DataSource.class);
		targetDataSources.put(master, defaultTargetDataSource);
		List<String> list = group.getSlaveSources();
		if(null != list && !list.isEmpty()){
			for (String slave : list){
				try{
					DataSource datasource = applicationContext.getBean(slave,DataSource.class);
					if(null == datasource){
						continue;
					}
					targetDataSources.put(slave,datasource );
				}catch (Exception e){
					logger.info("初始化[{}】 slave 数据源失败,检查slave开关是否开启!", slave);
				}
			}
		}
		if(isDefaultTargetDataSource){
			this.setDefaultTargetDataSource(defaultTargetDataSource);
		}

		this.targetDataSources.putAll(targetDataSources);

	}

	//
	/**
	 * 获取数据源名称,采用轮询的方式实现
	 * 1.如果没有设置 数据库模块切换值,则按初始化时默认的数据库处理;
	 * 2.如果有设置
	 *  2.1.数据库模块切换值如果是主数据库, 即返回当前模块的主数据对应的key
	 *  2.2.数据库模块切换值如果是从数据库, 先判断从数据集合是否包括从数据key,如果没有则返回主数据库key, 否则轮询返回从数据库key
	 */

//	protected Object determineCurrentLookupKey() {
//		return "sys_master";
//	}
	@Override
	protected Object determineCurrentLookupKey() {
		try{
			DataSourceGroup ds = DataSourceHolder.getDataSource();
			/**
			 * 如果没有设置 数据库模块切换值,则按初始化时默认的数据库处理;
			 */
			if (null == ds) {
				logger.warn("Property 'determineCurrentLookupKey' is DataSourceHolder.getDataSource() get DataSourceGroup is null .... ");
				return null;
			}
			//从枚举中获取完整的组信息;
			DataSourceGroup dataSourceGroup = DatasourceEnumManager.getDataSourceGroupAndDataType(ds.getGroupName(),ds.getDataType());
			if (null == dataSourceGroup) {
				throw new IllegalArgumentException("Property 'determineCurrentLookupKey' is DataSourceGroupNameEnum.getDataSourceGroupByName() not  dataSourceGroup:[" + ds.toString() + "]");
			}
			String dataSourceKey = null;
			DataSourceTypeEnum dataEnum = dataSourceGroup.getDataType();
			/** 如果是主数据库, 即返回当前模块的主数据对应的key **/
			if (DataSourceTypeEnum.MASTER == dataEnum) {
				dataSourceKey = dataSourceGroup.getMasterSources();
				dataSourceGroup.setDataClient(dataSourceKey);
//				logger.info(" AbstractRoutingDataSource determineCurrentLookupKey DataSourceTypeEnum.MASTER DataSourceGroup [{}]" , JsonUtils.toJson(dataSourceGroup));
				logger.info(" AbstractRoutingDataSource determineCurrentLookupKey DataSourceTypeEnum.MASTER " );
				return dataSourceKey;
			}
			/** 如果是从数据库, 先判断从数据集合是否包括从数据key,如果没有则返回主数据库key, 否则轮询返回从数据库key **/
			if (DataSourceTypeEnum.SLAVE == dataEnum) {
				List<String> list = dataSourceGroup.getSlaveSources();
				if (null == list || list.isEmpty()) {
					dataSourceKey = dataSourceGroup.getMasterSources();
					dataSourceGroup.setDataClient(dataSourceKey);
//					logger.info(" AbstractRoutingDataSource determineCurrentLookupKey DataSourceTypeEnum.SLAVE from MASTER DataSourceGroup [{}]" , dataSourceGroup.toString());
					logger.info(" AbstractRoutingDataSource determineCurrentLookupKey DataSourceTypeEnum.SLAVE from MASTER " );
					return dataSourceKey;
				}
//			throw new IllegalArgumentException("Property 'determineCurrentLookupKey' is DataSourceHolder.map.get(dataType) list isEmpty or null ");
				int size = list.size();
				if (size == 1) {
					dataSourceKey = list.get(0);
				} else {
					//随机获取;
					dataSourceKey = list.get(RandomUtils.num(size));
					dataSourceGroup.setDataClient(dataSourceKey);
					return dataSourceKey;
				}
			}
			dataSourceGroup.setDataClient(dataSourceKey);
//			logger.info(" AbstractRoutingDataSource determineCurrentLookupKey DataSourceTypeEnum.SLAVE DataSourceGroup [{}]" , dataSourceGroup.toString());
			logger.info(" AbstractRoutingDataSource determineCurrentLookupKey DataSourceTypeEnum.SLAVE " );
			return dataSourceKey;
		}catch (Exception e){
			e.printStackTrace();
			logger.warn("AbstractRoutingDataSource determineCurrentLookupKey  DataSourceGroup exception :[{}]", e);
		}
		return null;


	}


//	public void setDataSourcesGroup(Set<DataSourceGroup> dataSourcesGroup) {
//		if (null == dataSourcesGroup || dataSourcesGroup.isEmpty()){
//			throw new IllegalArgumentException("Property 'dataSourcesGroup' is null");
//		}
//		for (DataSourceGroup group : dataSourcesGroup) {
//			Map<String,List<String>> map = dataSourceGroupMap.get(group.getGroupName());
//			if(map == null){
//				map = new HashMap<>();
//				dataSourceGroupMap.put(group.getGroupName(),map);
//			}
//			String master = DataSourceEnum.MASTER.name();
//			String slave = DataSourceEnum.SLAVE.name();
//			map.put(master,strToList(group.getMasterSources()));
//			map.put(slave,strToList(group.getMasterSources()));
//
//			methodType.put(master,strToList(group.getMasterMethod()));
//			methodType.put(slave,strToList(group.getSlaveMethod()));
//		}
//		dataSourceGroupMapBack.putAll(dataSourceGroupMap);
////		DataSourceHolder.putDataSource("show","SLAVE");
////		Object client = determineCurrentLookupKey();
////		boolean isboolean = dataSourceConnCheck(client.toString());
////		new DataSourceGroupStatsThread("datasourcegroupstats").run();
//
//	}

//	public void setDataSourceHeatTime(int dataSourceHeatTime){
//		if(dataSourceHeatTime > 0 ){
//			this.DATA_SOURCE_HEAT_TIME = dataSourceHeatTime * DATA_SOURCE_INIT_HEAT_TIME;
//		}else {
//			DATA_SOURCE_HEAT_TIME *=  DATA_SOURCE_INIT_HEAT_TIME;
//		}
//	}
//
//	public void setDataSourceHeatState(int dataSourceHeatState){
//		if(dataSourceHeatState > 0 ){
//			DATA_SOURCE_HEAT_STATE = true;
//		}
//	}
//
//	private List<String> strToList(String splitStr){
//		String[] str = splitStr.split(",|;");
//		List<String> list = new ArrayList<>();
//		for (String s : str){
//			list.add(s.trim());
//		}
//		return list;
//	}
//
//
//	/**
//	 * 检查数据源是否可用
//	 * @param dataSourceCliect
//	 * @return
//     */
//	public boolean dataSourceConnCheck(String dataSourceCliect){
//		logger.info(" ChooseDataSource to dataSourceConnCheck dataSourceCliect={}" ,dataSourceCliect);
//		int repeatCount = 5;
//		Connection conn = null;
//		try {
//			this.afterPropertiesSet();
//			DataSourceHolder.putDataSource(new DataSourceGroup(dataSourceCliect));
//			DruidDataSource dataSource = (DruidDataSource) this.determineTargetDataSource();
//			if(null == dataSource ){
//				return false;
//			}
//			conn = dataSource.getConnection();
//			for (int i = 0 ; i < repeatCount ; i++){
//				try {
//					dataSource.validateConnection(conn);
//					return true;//验证没有异常,返回正常结束重试
//				}catch (SQLException e){
//					logger.error(" ChooseDataSource to dataSourceConnCheck dataSourceCliect=[{}], SQLException=[]" ,dataSourceCliect, e);
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e1) {
//						e1.printStackTrace();
//					}
//				}
//			}//如果循环没有命中,则认为是失败的
//			return false;
//
//		} catch (SQLException e) {
//			return false;
//		}finally {
//			if(null !=conn){
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//	}
//
//	/**
//	 * 心跳监控数据源是否可用
//	 */
//	public class DataSourceGroupHeatThread extends Thread {
//
//		public DataSourceGroupHeatThread(String name){
//			super(name);
//			this.setDaemon(true);
//		}
//
//		public void run() {
//			try {
//
//				L : for (;;) {
//					logger.info(" ChooseDataSource to DataSourceGroupHeatThread start, DATA_SOURCE_HEAT_STATE={}" ,DATA_SOURCE_HEAT_STATE);
//					if(!DATA_SOURCE_HEAT_STATE){
//						break L;
//					}
//					try {
//						logger.info(" DataSourceGroupHeatThread run  dataSourceGroupMapBack size ={}" ,dataSourceGroupMapBack.size());
//						for (Map.Entry<String,Map<String,List<String>>> group :dataSourceGroupMapBack.entrySet()) {
//							String groupName = group.getKey();
//							Map<String,List<String>> map = group.getValue();
//							logger.info(" DataSourceGroupHeatThread run  groupName={}, client map={}" ,groupName, JSON.toJSON(map));
//							for (Map.Entry<String,List<String>> client : map.entrySet()) {
//								String masterSlavekey = client.getKey();
//								List<String> datasourceList = client.getValue();
//								logger.info(" DataSourceGroupHeatThread while 2  masterSlavekey={}, datasourceList={}" ,groupName, JSON.toJSON(datasourceList));
//								for (String dataSourceCliect : datasourceList){//对应数据库的每个连接检查;
//									//验证 dataSourceConnCheck 对应的数据库 是否可以正常;
//									boolean isconnect = dataSourceConnCheck(dataSourceCliect);
//									if(isconnect) {
//										this.dataSourceGroupMapCheck(groupName, masterSlavekey, dataSourceCliect, isconnect);
//									}
//
//								}
//							}
//						}
//					} catch (Exception e) {
//						logger.error("DataSourceGroupStatsThread error", e);
//					}
//
//					Thread.sleep(DATA_SOURCE_INIT_HEAT_TIME);
//
//				}
//			} catch (Exception e) {
//				logger.error("DataSourceGroupHeatThread error", e);
//			}
//		}
//
//		/**
//		 * 缓存dataSourceGroupMap
//		 * @param groupName
//		 * @param masterSlavekey
//		 * @param dataSourceKey
//         * @param isconnect
//         */
//		public void dataSourceGroupMapCheck(String groupName, String masterSlavekey, String dataSourceKey, boolean isconnect){
//			Map<String,List<String>> lineGroupMap = dataSourceGroupMap.get(groupName);
//			if(null != lineGroupMap){
//				if(isconnect){
//					List<String> lineSourceList = lineGroupMap.get(masterSlavekey);
//					logger.info(" DataSourceGroupHeatThread dataSourceGroupMapCheck  lineGroupMap for  dataSourceKey={} masterSlavekey={} "
//							, dataSourceKey ,masterSlavekey);
//					if(null == lineSourceList){
//						lineSourceList = new ArrayList();
//						lineGroupMap.put(masterSlavekey,lineSourceList);
//					}if (!lineSourceList.contains(dataSourceKey)){//如果数据库结点可用时,并不在集合时,增加集合中;
//						lineSourceList.add(dataSourceKey);
//					}
//				}else{//删除坏结点;
//					logger.info(" DataSourceGroupHeatThread dataSourceGroupMapCheck  lineGroupMap to remove  dataSourceKey={}" ,dataSourceKey);
//					List<String> lineSourceList = lineGroupMap.get(masterSlavekey);
//					if(null != lineSourceList && lineSourceList.contains(dataSourceKey)){
//						lineSourceList.remove(dataSourceKey);
//					}
//
//
//				}
//			}
//		}
//
//
//	}

//	@PostConstruct
//	public void initCache(){
//		if(DATA_SOURCE_HEAT_STATE && !Env.isDev()) {
//			new DataSourceGroupHeatThread("DataSourceGroupHeatThread").run();
//		}
////		Runtime.getRuntime().addShutdownHook(new DataSourceGroupHeatThread("DataSourceGroupHeatThread"));
//	}


}
