package com.suven.framework.core.mybatis;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.suven.framework.common.api.IBaseApi;
import com.suven.framework.common.cat.CatDBSign;
import com.suven.framework.core.db.DataSourceGroup;
import com.suven.framework.core.db.DataSourceHolder;
import com.suven.framework.core.db.DataSourceTypeEnum;
import com.suven.framework.util.json.JsonUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.suven.framework.core.db.ext.DSClassAnnoExplain;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Title: MyBatisBaseEntityDao.java
 * @author Joven.wang
 * @date   2019-10-18 12:35:25
 * @version V1.0
 *  <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @Description: (说明) MyBatis dao 直接查询数据库实现类,（ 泛型：M 是 mapper 对象，T 是实体 ， PK 是主键泛型 ）
 * @Copyright: (c) 2018 gc by https://www.suven.top
 *
 */

@SuppressWarnings("unchecked")
public class MyBatisBaseEntityDao<M extends BaseMapper<T>, T extends IBaseApi> implements IService<T> {

    protected Log log = LogFactory.getLog(getClass());
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected M baseMapper;

    @Override
    public M getBaseMapper() {
        return baseMapper;
    }


    protected   void slaveDataSource(){
        Class<T> tClass =  currentModelClass();
        DataSourceGroup dataSourceGroup = DSClassAnnoExplain.getDataSourceGroupByClass(tClass);
        if(dataSourceGroup == null){
            return;
        }
        dataSourceGroup.setDataType(DataSourceTypeEnum.SLAVE);
        logger.info(" slaveDataSource DataSourceGroup[{}]", JsonUtils.toJson(dataSourceGroup));
        DataSourceHolder.putDataSource(dataSourceGroup);
    }
    protected  void masterDataSource(){
        Class<T> tClass =  currentModelClass();
        DataSourceGroup dataSourceGroup = DSClassAnnoExplain.getDataSourceGroupByClass(tClass);
        if(dataSourceGroup == null){
            return;
        }
        dataSourceGroup.setDataType(DataSourceTypeEnum.MASTER);
        logger.info(" masterDataSource DataSourceGroup[{}]", JsonUtils.toJson(dataSourceGroup));
        DataSourceHolder.putDataSource(dataSourceGroup);
    }

    /**
     * 判断数据库操作是否成功
     *
     * @param result 数据库操作返回影响条数
     * @return boolean
     */
    protected boolean retBool(Integer result) {
        return SqlHelper.retBool(result);
    }

    protected Class<T> currentModelClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 1);
    }

    /**
     * 批量操作 SqlSession
     */
    protected SqlSession sqlSessionBatch() {
        return SqlHelper.sqlSessionBatch(currentModelClass());
    }

    /**
     * 释放sqlSession
     *
     * @param sqlSession session
     */
    protected void closeSqlSession(SqlSession sqlSession) {
        SqlSessionUtils.closeSqlSession(sqlSession, GlobalConfigUtils.currentSessionFactory(currentModelClass()));
    }

    /**
     * 获取 SqlStatement
     *
     * @param sqlMethod ignore
     * @return ignore
     */
    protected String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(currentModelClass()).getSqlStatement(sqlMethod.getMethod());
    }

    @Override
    @CatDBSign
    public boolean save(T entity) {
        masterDataSource();
        return saveTr(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    protected boolean saveTr(T entity) {
        int id = baseMapper.insert(entity);
        return retBool(id);
    }

    /**
     * 批量插入
     *
     * @param entityList ignore
     * @param batchSize ignore
     * @return ignore
     */
    @Override
    @CatDBSign
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        masterDataSource();
       return saveBatchTr(entityList,batchSize);
    }

    @Transactional(rollbackFor = Exception.class)
    protected boolean saveBatchTr(Collection<T> entityList, int batchSize) {
        String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            int i = 0;
            for (T anEntityList : entityList) {
                batchSqlSession.insert(sqlStatement, anEntityList);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();

        }
        return true;
    }

    /**
     * TableId 注解存在更新记录，否插入一条记录
     *
     * @param entity 实体对象
     * @return boolean
     */
    @Override
    @CatDBSign
    public boolean saveOrUpdate(T entity) {
        masterDataSource();
       return saveOrUpdateTr(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    protected boolean saveOrUpdateTr(T entity) {
        if (null != entity) {
            masterDataSource();
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
            String keyProperty = tableInfo.getKeyProperty();
            Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
            Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
            return StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal)) ? save(entity) : updateById(entity);
        }
        return false;
    }

    @Override
    @CatDBSign
    public boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
        Assert.notEmpty(entityList, "error: entityList must not be empty");
        masterDataSource();
        return saveOrUpdateBatchTr(entityList,batchSize);
    }

    @Transactional(rollbackFor = Exception.class)
    protected boolean saveOrUpdateBatchTr(Collection<T> entityList, int batchSize) {


        Class<?> cls = currentModelClass();
        TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
        Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
        String keyProperty = tableInfo.getKeyProperty();
        Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            int i = 0;
            for (T entity : entityList) {
                Object idVal = ReflectionKit.getMethodValue(cls, entity, keyProperty);
                if (StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal))) {
                    batchSqlSession.insert(sqlStatement(SqlMethod.INSERT_ONE), entity);
                } else {
                    MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                    param.put(Constants.ENTITY, entity);
                    batchSqlSession.update(sqlStatement(SqlMethod.UPDATE_BY_ID), param);
                }
                // 不知道以后会不会有人说更新失败了还要执行插入 😂😂😂
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        }
        return true;
    }

    @Override
    @CatDBSign
    public boolean removeById(Serializable id) {
        masterDataSource();
        return SqlHelper.retBool(baseMapper.deleteById(id));
    }

    @Override
    @CatDBSign
    public boolean removeByMap(Map<String, Object> columnMap) {
        Assert.notEmpty(columnMap, "error: columnMap must not be empty");
        masterDataSource();
        return SqlHelper.retBool(baseMapper.deleteByMap(columnMap));
    }

    @Override
    @CatDBSign
    public boolean remove(Wrapper<T> wrapper) {
        masterDataSource();
        return SqlHelper.retBool(baseMapper.delete(wrapper));
    }

    @Override
    @CatDBSign
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        masterDataSource();
        return SqlHelper.retBool(baseMapper.deleteBatchIds(idList));
    }

    @Override
    @CatDBSign
    public boolean updateById(T entity) {

        masterDataSource();
        return retBool(baseMapper.updateById(entity));
    }

    @Override
    @CatDBSign
    public boolean update(T entity, Wrapper<T> updateWrapper) {
        masterDataSource();
        return retBool(baseMapper.update(entity, updateWrapper));
    }
    @Override
    @CatDBSign
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        return updateBatchTrById(entityList,batchSize);
    }

    @Transactional(rollbackFor = Exception.class)
    protected boolean updateBatchTrById(Collection<T> entityList, int batchSize) {
        Assert.notEmpty(entityList, "error: entityList must not be empty");
        masterDataSource();
        String sqlStatement = sqlStatement(SqlMethod.UPDATE_BY_ID);
        try (SqlSession batchSqlSession = sqlSessionBatch()) {
            int i = 0;
            for (T anEntityList : entityList) {
                MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                param.put(Constants.ENTITY, anEntityList);
                batchSqlSession.update(sqlStatement, param);
                if (i >= 1 && i % batchSize == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        }
        return true;
    }

    @Override
    @CatDBSign
    public T getById(Serializable id) {
        slaveDataSource();
        return baseMapper.selectById(id);
    }

    @Override
    @CatDBSign
        public Collection<T> listByIds(Collection<? extends Serializable> idList) {
        slaveDataSource();
        return baseMapper.selectBatchIds(idList);
    }

    @Override
    @CatDBSign
    public Collection<T> listByMap(Map<String, Object> columnMap) {

        slaveDataSource();
        return baseMapper.selectByMap(columnMap);
    }

    @Override
    @CatDBSign
    public T getOne(Wrapper<T> queryWrapper, boolean throwEx) {
        slaveDataSource();
        if (throwEx) {
            return baseMapper.selectOne(queryWrapper);
        }
        return SqlHelper.getObject(log, baseMapper.selectList(queryWrapper));
    }

    @Override
    @CatDBSign
    public Map<String, Object> getMap(Wrapper<T> queryWrapper) {
        slaveDataSource();
        return SqlHelper.getObject(log, baseMapper.selectMaps(queryWrapper));
    }

    @Override
    @CatDBSign
    public int count(Wrapper<T> queryWrapper) {
        slaveDataSource();
        return SqlHelper.retCount(baseMapper.selectCount(queryWrapper));
    }

    @Override
    @CatDBSign
    public List<T> list(Wrapper<T> queryWrapper) {
        slaveDataSource();
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    @CatDBSign
    public IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper) {
        slaveDataSource();
        IPage<T> tiPage = baseMapper.selectPage(page, queryWrapper);
        return tiPage;
    }

    @Override
    @CatDBSign
    public List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper)
    {
        slaveDataSource();
        return baseMapper.selectMaps(queryWrapper);
    }

    @Override
    @CatDBSign
    public <V> List<V> listObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        slaveDataSource();
        return baseMapper.selectObjs(queryWrapper).stream().filter(Objects::nonNull).map(mapper).collect(Collectors.toList());
    }

    @Override
    @CatDBSign
    public IPage<Map<String, Object>> pageMaps(IPage<T> page, Wrapper<T> queryWrapper) {
        slaveDataSource();
        return baseMapper.selectMapsPage(page, queryWrapper);
    }

    @Override
    @CatDBSign
    public <V> V getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        slaveDataSource();
        return SqlHelper.getObject(log, listObjs(queryWrapper, mapper));
    }
}
