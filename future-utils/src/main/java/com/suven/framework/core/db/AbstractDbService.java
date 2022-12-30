package com.suven.framework.core.db;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import com.suven.framework.common.cat.CatDBSign;
import com.suven.framework.util.json.JsonUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;
import java.util.*;

/**
 * 数据库连接服务抽象对象，各项目需要继承并初始化jdbc
 * @author joven
 *
 */
@Deprecated
public abstract class AbstractDbService {

	public static Logger log = LoggerFactory.getLogger(AbstractDbService.class);

	public final int LISTS_PARTITON_SIZE = 100;//Lists.partition


	@Autowired(required = false)
	private JdbcTemplate jdbc;



	@Resource(name="dataSource")
	public void setDataSource(DataSource dataSource) {
		jdbc = new JdbcTemplate(dataSource);
	}

	public DataSource getDataSource(){
		return jdbc.getDataSource();
	}

	public abstract DataSourceGroupEnumInterface initDataSourceGroupName();


	private  void slaveDataSource(){
        DataSourceGroupEnumInterface groupName = initDataSourceGroupName();
		DataSourceGroup dcp = new DataSourceGroup(groupName.getModule(), DataSourceTypeEnum.SLAVE);
        log.info(" slaveDataSource DataSourceGroup[{}]", JsonUtils.toJson(dcp));
		DataSourceHolder.putDataSource(dcp);
	}
	private  void masterDataSource(){
        DataSourceGroupEnumInterface groupName = initDataSourceGroupName();
		DataSourceGroup dcp = new DataSourceGroup(groupName.getModule(), DataSourceTypeEnum.MASTER);
        log.info(" masterDataSource DataSourceGroup[{}]", JsonUtils.toJson(dcp));
		DataSourceHolder.putDataSource(dcp);
	}

	/**
	 * 简单查询,PreparedStatement形式
	 * @param <T>
	 *
	 * @param sql
	 *            查询语句
	 * @param rowMapper
	 * @param values
	 *            查询参数
	 * @return
	 * @throws Exception
	 */
	@CatDBSign
	public <T> List<T> query(String sql, final RowMapper<T> rowMapper, Object... values) {
		log.info("query-sql:[{}],param:{}",sql, values);
		final List<T> list = new ArrayList<T>();
		slaveDataSource();
		jdbc.query(sql, replaceParamType(values), new ResultSetExtractor<List<T>>() {
			public List<T> extractData(ResultSet rs) {
				try {
					int i = 0;
					while (rs.next()) {
						list.add(rowMapper.mapRow(rs, i));
						i++;
					}
				} catch (SQLException e) {
					log.error("RowMapper-query-sql error", e);
				}
				return list;
			}
		});
		DataSourceHolder.clear();
		return list;
	}
	@CatDBSign
	public <T> List<T> queryByInToList(String sql , final RowMapper<T> rowMapper, Collection<Long>colle){
		try {
			slaveDataSource();

			String ids = StringUtils.join(colle, ",");
			String inSql = replaceByInToIds(sql, ids);

			log.info("query-sql:[{}],param:{}",inSql,ids);
			return jdbc.query(inSql, rowMapper);
		} catch(EmptyResultDataAccessException e) {
			log.error("query-sql error", e);
			return null;
		}finally {
			DataSourceHolder.clear();
		}
	}
	@CatDBSign
	public <T> List<T> queryByInToList(String sql , final RowMapper<T> rowMapper, Collection<Long>colle,Object... values){
		try {
			slaveDataSource();

			String ids = StringUtils.join(colle, ",");
			String inSql = replaceByInToIds(sql, ids);

			if(null == values || values.length == 0){
				return queryByInToList(sql,rowMapper,colle);
			}

			log.info("query-sql:[{}],param ids:{} , param:{}",inSql,ids,values);
			return jdbc.query(inSql, rowMapper,replaceParamType(values));
		} catch(EmptyResultDataAccessException e) {
			log.error("query-sql error", e);
			return null;
		}finally {
			DataSourceHolder.clear();
		}
	}
	@CatDBSign
	public <T> T queryT(String sql, final RowMapper<T> rowMapper, Object... values) {
		log.info("query-sql:[{}],param:{}",sql, values);
		try {
			slaveDataSource();
			List<T> list =  jdbc.query(sql, rowMapper, replaceParamType(values));
			if(null != list && !list.isEmpty()){
				return list.get(0);
			}return null;
		} catch(EmptyResultDataAccessException e) {
			log.error("query-sql error", e);
			return null;
		}finally {
			DataSourceHolder.clear();
		}
	}
	@CatDBSign
	public <T> List<T> queryForList(String sql , Class<T> elementType , Object... args){
		log.info("query-sql:[{}],param:{}",sql, args);
		try {
			slaveDataSource();
			return jdbc.queryForList(sql, elementType, replaceParamType(args));
		} catch(EmptyResultDataAccessException e) {
			log.error("query-sql error", e);
			return null;
		}finally {
			DataSourceHolder.clear();
		}
	}

	@CatDBSign
	public List<Map<String, Object>> queryList(String sql, Object... args) {
		log.info("query-sql:[{}],param:{}",sql, args);
		try {
			slaveDataSource();
			return jdbc.queryForList(sql, replaceParamType(args));
		} catch(DataAccessException e) {
			log.error("query-sql error", e);
			return null;
		}finally {
			DataSourceHolder.clear();
		}
	}
	@CatDBSign
	public int queryInt(String sql, Object... value) {
		log.info("query-sql:[{}],param:{}",sql, value);
		try {
			slaveDataSource();
			return jdbc.queryForObject(sql, replaceParamType(value), Integer.class);
		} catch(EmptyResultDataAccessException e) {
			log.error("query-sql error", e);
			return 0;
		}finally {
			DataSourceHolder.clear();
		}
	}
	@CatDBSign
	public int queryForInt(String sql) {
		log.info("query-sql:[{}]",sql);
		try {
			slaveDataSource();
			return jdbc.queryForObject(sql, Integer.class);
		} catch(EmptyResultDataAccessException e) {
			log.error("query-sql error", e);
			return 0;
		}finally {
			DataSourceHolder.clear();
		}
	}

	@CatDBSign
	public Long queryLong(String sql, Object... value) {
		log.info("query-sql:[{}],param:{}",sql, value);
		try {
			slaveDataSource();
			return jdbc.queryForObject(sql, replaceParamType(value), Long.class);
		} catch(EmptyResultDataAccessException e) {
			log.error("query-sql error", e);
			return 0L;
		}finally {
			DataSourceHolder.clear();
		}
	}

	@CatDBSign
	public String queryString(String sql, Object... value) {
		log.info("query-sql:[{}],param:{}",sql, value);
		try {
			slaveDataSource();
			return jdbc.queryForObject(sql, replaceParamType(value), String.class);
		} catch(EmptyResultDataAccessException e) {
			log.error("query-sql error", e);
			return null;
		}finally {
			DataSourceHolder.clear();
		}
	}

	/**
	 * 执行update / insert 语句
	 *
	 * @param sql
	 *            sql 语句
	 * @param args
	 *            参数数组
	 * @return
	 * @throws Exception
	 */
	@CatDBSign
	public boolean update(String sql, Object... args) {
		log.info("update-sql:[{}],param:{}",sql, args);
		try {
			masterDataSource();
			int c = jdbc.update(sql, replaceParamType(args));
			return c > 0;
		} catch (DuplicateKeyException dke) {
			log.error("update error", dke);
		}catch (DataAccessException dae) {
			log.error("update error", dae);
		}finally {
			DataSourceHolder.clear();
		}
		return false;
	}

	/**
	 * 执行update / insert 语句
	 *
	 * @param sql
	 *            sql 语句
	 * @param inIds
	 *            参数数组
	 * @return
	 * @throws Exception
	 */
	@CatDBSign
	public int updateByInToList(String sql,  Collection<Long> inIds, Object ... args) {
		log.info("update-sql:[{}],ids:{}, param:{}",sql, inIds,args);
		try {
			masterDataSource();
			String ids = StringUtils.join(inIds, ",");
			String inSql = replaceByInToIds(sql, ids);

			int count = jdbc.update(inSql, replaceParamType(args));
			return count;
		} catch (DuplicateKeyException dke) {
			log.error("update error", dke);
		}catch (DataAccessException dae) {
			log.error("update error", dae);
		}finally {
			DataSourceHolder.clear();
		}
		return 0;
	}


	/**
	 *  插入数据,返回自增id
	 * @param sql
	 * @param key 主键字段名
	 * @param args	参数列表
	 * @return -1-异常
	 */
	@CatDBSign
	public long insert(final String sql, final String key ,final Object... args ){
		log.info("insert-sql:[{}],param:{}",sql, args);
		try {
			masterDataSource();
			long result = -1L;
			KeyHolder keyHolder = new GeneratedKeyHolder();
			PreparedStatementCreator psc  = new PreparedStatementCreator() {
				 public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					 final PreparedStatement ps = connection.prepareStatement(sql,new String[] { key });
					if ( args != null) {
						for(int i= 0; i<args.length;i++ ){
							Object param = args[i];
							if(param instanceof Date){
								param = new Timestamp(((Date)param).getTime());
							}
							ps.setObject(i+1, param);
						}
					}
					return ps;
				}
			};
			result = jdbc.update(psc, keyHolder);
			long id = result > 0 ? keyHolder.getKey().longValue() : -1;
			if(id < 0){
				log.error("insert-sql:{}, ,param:{}", sql, args);
			}
			return id;
		} catch (DataAccessException e) {
			log.error("insert error", e);
		} catch (Exception e) {
			log.error("insert error", e);
		}finally {
			DataSourceHolder.clear();
		}
		return -1;
	}

	/**
	 * SqlParameterSource beanParam = new BeanPropertySqlParameterSource(object);
	 * ParameterizedBeanPropertyRowMapper.newInstance(UserInfo.class)
	 * String sqlStr="update tableName set filed_name=:name,filed_value=:value where id=:id";
	 * @param object
	 * @return
	 */
	public int updateBean(String sql ,Object object) {
		SqlParameterSource beanParam = new BeanPropertySqlParameterSource(object);
		return jdbc.update(sql, beanParam);
	}

	/**
	 * SqlParameterSource beanParam = new BeanPropertySqlParameterSource(object);
	 * ParameterizedBeanPropertyRowMapper.newInstance(UserInfo.class)
	 * String sqlStr="update tableName set filed_name=:name,filed_value=:value where id=:id";
	 * @param list
	 * @return
	 */
	public <T> int[][]   batchBean(String sql, List<T> list,ParameterizedPreparedStatementSetter setter)
	{
		int[][]  ids = jdbc.batchUpdate(sql,list,list.size()-1,setter) ;
		return ids;
	}


//	/**
//	 * 翻页计算
//	 * @param page 0-第一页 1-第二页
//	 * @param pagesize
//	 * @return
//	 */
//	@CatDBSign
//	public int calStart(int page , int pagesize ) {
//		if(page<1) {
//			page=1;
//		}
//		return (page-1)*pagesize;
//	}


	/**
	 * 批量执行update / insert 语句
	 * @param sql
	 * @return
	 */
//	@CatDbSign
	public int[] batchUpdate(String sql,BatchPreparedStatementSetter setter) {
		log.info("update-sql:[{}],param:{}",sql, setter.toString());
		try {

			masterDataSource();
			int[] ids = jdbc.batchUpdate(sql,setter);
			return ids;

		} catch (DataAccessException dae) {
			log.error("batch update-sql error", dae);
			return null;
		}finally {
			DataSourceHolder.clear();
		}
	}
	public <T>int[][] batchUpdate(String sql,Collection<T> batchArgs, ParameterizedPreparedStatementSetter setter){
		log.info("update-sql:[{}],param:{}",sql, setter.toString());
		try {

			masterDataSource();
			int[][] ids = jdbc.batchUpdate(sql,batchArgs,batchArgs.size(), setter);
			return ids;

		} catch (DataAccessException dae) {
			log.error("batch update-sql error", dae);
			return null;
		}finally {
			DataSourceHolder.clear();
		}
	}

	/**
	 * 批量执行update / insert 语句
	 * @param sql
	 * @return
	 */
//	@CatDbSign
	public boolean batchUpdate(String sql,List<Object[]> batchArgs) {
		log.info("update-sql:[{}],param:{}",sql, batchArgs);
		try {
			if(!sql.toLowerCase().contains("where")){
				log.error("update-sql not where condition sql:[{}],param:{}",sql, batchArgs);
				return false;
			}
			masterDataSource();
			int[] ids = jdbc.batchUpdate(sql, batchArgs);
			return null != ids && ids.length > 0;

		} catch (DataAccessException dae) {
			log.error("batch update-sql error", dae);
			return false;
		}finally {
			DataSourceHolder.clear();
		}
	}
	/**
	 * 批量更新
	 * @author ruan
	 * @param sql sql语句数组
	 * @return 每一条sql语句对应影响的行数
	 */
//	@CatDbSign
	public boolean batchUpdate(String[] sql){
		log.info("update-sql:[{}]",String.join(",",sql));
		try {
			masterDataSource();
			int[] ids =  jdbc.batchUpdate(sql);
			return null != ids && ids.length > 0;
		} catch (DataAccessException e) {
			log.error("batch update-sql error", e);
		}finally {
			DataSourceHolder.clear();
		}
		return false;
	}


	private String replaceByInToIds(String sql, String replacement){
		final String searchString = "?";
		final String searchIn = " IN ";
		int beginIndex = sql.toUpperCase().indexOf(searchIn);
		if( beginIndex > 0 && (sql.indexOf(searchString) > 0)){
			String s = sql.substring(0,beginIndex) + StringUtils.replace(sql.substring(beginIndex), searchString, replacement,1);
			return s;
		}
		return sql;
	}


	private Object[] replaceParamType(Object... args){
		return args;
//		return Arrays.stream(args).map(org -> {
//			if(org instanceof Date){
//				return new Timestamp(((Date)org).getTime());
//			}
//			return org;
//		}).collect(Collectors.toList()).toArray();
	}



}