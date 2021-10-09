package com.suven.framework.core.es.repository;

import com.alibaba.fastjson.JSON;
import com.suven.framework.core.es.EsBaseEntity;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * es java客户端 封装的基础操作类
 *
 * @Author dongxie
 * @Description: 使用了高级对象  RestHighLevelClient
 * @CreateDate 2019-09-11  16:11
 **/

public class ElasticsearchRepository<T> {

    private Logger logger = LoggerFactory.getLogger(ElasticsearchRepository.class);

    @Autowired
    private RestHighLevelClient client;

    public static final String INDEX_NAME = "es_index";


//    @PostConstruct
    public void init() {
        if (this.indexExist(INDEX_NAME)) {
            this.getIndexSettings(INDEX_NAME);
            return;
        }
        this.createIndex(INDEX_NAME);
        this.getIndexSettings(INDEX_NAME);

    }

    /**
     * 创建index
     * @param index
     */
    protected void createIndex(String index) {
        try {
            CreateIndexRequest request = new CreateIndexRequest(index);
            request.settings(Settings.builder().put("index.number_of_shards", 3).put("index.number_of_replicas", 2));

            CreateIndexResponse res = client.indices().create(request, RequestOptions.DEFAULT);
            if (res.isAcknowledged()) {
                logger.info("index初始化成功,名称:{}", index);
            }
        } catch (Exception e) {
            logger.error("es index 创建出现异常 ", e);
        }
    }

    /**
     * 获取 已经创建的index 参数信息
     * @param index
     */
    protected void getIndexSettings(String index) {
        try {
            GetSettingsRequest getSettingsRequest = new GetSettingsRequest().indices(index);
            GetSettingsResponse getSettingsResponse = client.indices().getSettings(getSettingsRequest, RequestOptions.DEFAULT);

            Settings indexSettings = getSettingsResponse.getIndexToSettings().get(index);
            Integer numberOfShards = indexSettings.getAsInt("index.number_of_shards", null);
            String refreshInterval = indexSettings.get("index.refresh_interval");
            String numberOfReplicas = indexSettings.get("index.number_of_replicas");
            Settings indexDefaultSettings = getSettingsResponse.getIndexToDefaultSettings().get(index);
            logger.info("numberOfShards:{}", numberOfShards);
            logger.info("refreshInterval:{}", refreshInterval);
            logger.info("numberOfReplicas:{}", numberOfReplicas);
            logger.info("indexDefaultSettings:{}", indexDefaultSettings);
        } catch (Exception e) {
            logger.error("获取es index设置的参数信息 出现异常 ", e);
        }
    }

    /**
     * 判断某个index是否存在
     *
     * @param index
     * @return
     * @throws Exception
     */
    protected boolean indexExist(String index) {
        if (StringUtils.isBlank(index))
            return false;
        try {
            GetIndexRequest request = new GetIndexRequest(index);
            request.local(false);
            request.humanReadable(true);
            request.includeDefaults(false);
            return client.indices().exists(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            logger.error("indexExist error", e);
        }
        return false;
    }

    /**
     * 传入index和id 判断文档是否存在
     *
     * @param index
     * @param id
     * @return
     */
    private boolean documentExist(String index, String id) {
        if (StringUtils.isBlank(index) || StringUtils.isBlank(id))
            return false;
        try {
            GetRequest getRequest = new GetRequest(index, id);
            getRequest.fetchSourceContext(new FetchSourceContext(false));
            getRequest.storedFields("_none_");
            return client.exists(getRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            logger.error("documentExist error", e);
        }
        return false;
    }

    /**
     * 保存
     *
     * @param index  index索引名称
     * @param entity 要保存的对象
     */
    protected boolean save(String index, EsBaseEntity entity) {
        if (StringUtils.isBlank(index) || entity == null)
            return false;
        IndexRequest request = new IndexRequest(index);
        request.id(entity.getId());
        request.source(JSON.toJSONString(entity.getData()), XContentType.JSON);
        try {
            client.index(request, RequestOptions.DEFAULT);
            return true;
        } catch (Exception e) {
            logger.error("save error", e);
        }
        return false;
    }


    /**
     * 批量保存
     *
     * @param index index索引名称
     * @param list  要保存的list 对象
     */
    protected boolean saveBatch(String index, List<EsBaseEntity> list) {
        if (StringUtils.isBlank(index) || (list == null || list.isEmpty()))
            return false;
        BulkRequest request = new BulkRequest();
        list.forEach(item -> request.add(new IndexRequest(index).id(item.getId())
                .source(JSON.toJSONString(item.getData()), XContentType.JSON)));
        try {
            client.bulk(request, RequestOptions.DEFAULT);
            return true;
        } catch (Exception e) {
            logger.error("saveBatch error", e);
        }
        return false;
    }

    /**
     * 更新
     *
     * @param index  index索引名称
     * @param entity 更新的对象
     * @return
     */
    protected boolean update(String index, EsBaseEntity entity) {
        if (StringUtils.isBlank(index) || entity == null)
            return false;

        if (!documentExist(index, entity.getId()))
            return false;

        UpdateRequest request = new UpdateRequest(index, String.valueOf(entity.getId()));
        request.doc(JSON.toJSONString(entity.getData()), XContentType.JSON);
        try {
            UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
            if (updateResponse.status() == RestStatus.OK) {
                logger.info("更新成功！id: {}", entity.getId());
                return true;
            }
        } catch (IOException e) {
            logger.error("update error", e);
        }
        return false;
    }


    /**
     * 根据id 删除
     *
     * @param index
     * @param id
     * @return
     */
    protected boolean deleteById(String index, String id) {
        if (StringUtils.isBlank(index) || StringUtils.isBlank(id))
            return false;
        DeleteRequest request = new DeleteRequest(index, id);
        try {
            DeleteResponse deleteResponse = client.delete(request, RequestOptions.DEFAULT);
            if (deleteResponse.status() == RestStatus.OK) {
                logger.info("删除成功！id: {}", id);
                return true;
            }
        } catch (Exception e) {
            logger.error("deleteById error", e);
        }
        return false;
    }

    /**
     * 根据id 获取对象数据  get方式
     *
     * @param index
     * @param id
     * @param c
     * @param <T>
     * @return
     */
    protected <T> T getById(String index, String id, Class<T> c) {
        try {
            GetRequest getRequest = new GetRequest(index, id);
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            if (getResponse.isExists()) {
                String source = getResponse.getSourceAsString();
                T t = JSON.parseObject(source, c);
                return t;
            }
        } catch (Exception e) {
            logger.error("getById error", e);
        }
        return null;

    }

    /**
     * 批量删除
     *
     * @param index
     * @param idList 要删除的id 列表
     */
    protected <T> boolean deleteBatch(String index, Collection<T> idList) {
        if (StringUtils.isBlank(index) || (idList == null || idList.isEmpty()))
            return false;
        BulkRequest request = new BulkRequest();
        idList.forEach(item -> request.add(new DeleteRequest(index, item.toString())));
        try {
            client.bulk(request, RequestOptions.DEFAULT);
            return true;
        } catch (Exception e) {
            logger.error("deleteBatch error", e);
        }
        return false;
    }


    /**
     * 根据搜索条件 查找数据，返回封装好的 list （对象为 c参数
     *
     * @param index
     * @param builder 搜索对象构建
     * @param c       接收的 对象
     * @param <T>
     * @return
     */
    protected <T> List<T> search(String index, SearchSourceBuilder builder, Class<T> c) {
        if (StringUtils.isBlank(index))
            return null;
        SearchRequest request = new SearchRequest(index);
        request.source(builder);
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            List<T> res = new ArrayList<>(hits.length);
            for (SearchHit hit : hits) {
                res.add(JSON.parseObject(hit.getSourceAsString(), c));
            }
            return res;
        } catch (Exception e) {
            logger.error("search error", e);
        }
        return null;
    }


    /**
     * 根据搜索条件 直接返回 SearchResponse对象
     *
     * @param index
     * @param builder
     * @return
     */
    protected SearchResponse search(String index, SearchSourceBuilder builder) {
        if (StringUtils.isBlank(index))
            return null;
        SearchRequest request = new SearchRequest(index);
        request.source(builder);
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            return response;
        } catch (Exception e) {
            logger.error("search error", e);
        }
        return null;
    }

    /**
     * 根据搜索对象 直接返回 SearchResponse对象
     *
     * @param request
     * @return
     */
    protected SearchResponse search(SearchRequest request) {
        try {
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            return response;
        } catch (Exception e) {
            logger.error("search error", e);
        }
        return null;
    }


    /**
     * 根据查询条件 删除查询出来的结果数据
     *
     * @param index
     * @param builder
     * @param batchSize
     * @param conflicts
     */
//    protected void deleteByQuery(String index, QueryBuilder builder, int batchSize, String conflicts) {
//        DeleteByQueryRequest request = new DeleteByQueryRequest(index);
//        request.setQuery(builder);
//        //设置批量操作数量
//        request.setBatchSize(batchSize);
//        request.setConflicts(conflicts);
//        try {
//            client.deleteByQuery(request, RequestOptions.DEFAULT);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }


    /**
     * 删除index
     *
     * @param index
     */
//    protected void deleteIndex(String index) {
//        try {
//            client.indices().delete(new DeleteIndexRequest(index), RequestOptions.DEFAULT);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }


//    /**
//     * 通过info()方法检索群集信息：
//     *
//     * @return
//     */
//    protected MainResponse info() {
//        try {
//            return client.info(RequestOptions.DEFAULT);
//        } catch (Exception e) {
//            logger.error("info error", e);
//        }
//        return null;
//    }


}