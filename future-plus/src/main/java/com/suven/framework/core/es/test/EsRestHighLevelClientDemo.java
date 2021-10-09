package com.suven.framework.core.es.test;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 *  es api 测试
 * @Author dongxie
 * @Description //TODO
 * @CreateDate 2019-09-10  17:22
 **/
public class EsRestHighLevelClientDemo {

    static RestHighLevelClient client = null;

    static {
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("47.107.116.210", 9200, "http")));
    }

    public static void main(String args[]) throws IOException {
        search();
        multiSearch();

    }

    /**
     * 搜索
     * @throws IOException
     */
    private static void search() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //索引的选项以开始搜索 默认为0
        searchSourceBuilder.from(0);
        // 返回数量 默认为10
        searchSourceBuilder.size(3);
        //超时设置,控制允许搜索的时间
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //按 id排序 倒序
        searchSourceBuilder.sort(new FieldSortBuilder("_id").order(SortOrder.DESC));

        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("content", "中韩")
                .fuzziness(Fuzziness.AUTO)
                .prefixLength(3)
                .maxExpansions(10);


        //构建查询参数对象
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //查询参数
        boolQueryBuilder.must(QueryBuilders.matchQuery("content", "中韩"));

        //
        searchSourceBuilder.query(matchQueryBuilder);
        //构建查询对象
        SearchRequest searchRequest = new SearchRequest();
        //
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        RestStatus restStatus = searchResponse.status();
        if (restStatus != RestStatus.OK) {
            System.out.println("es 返回状态吗 restStatus：" + restStatus);
        }
        //获取查询的结果集
        SearchHits searchHits = searchResponse.getHits();
        for (SearchHit hit : searchHits.getHits()) {
            //获取字段信息
            String source = hit.getSourceAsString();
            //输出所有信息
            System.out.println(hit);
        }
        //返回总行数
        long totalHits = searchHits.getTotalHits().value;
        System.out.println("返回总行数" + totalHits);
        //用时
        TimeValue took = searchResponse.getTook();
        System.out.println("查询成功！请求参数:  " + searchRequest.source().toString());
        System.out.println("用时毫秒：" + took.millis());

    }


    private static void multiSearch() throws IOException {

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            //searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            //索引的选项以开始搜索 默认为0
            searchSourceBuilder.from(0);
            // 返回数量 默认为10
            searchSourceBuilder.size(3);
            //超时设置,控制允许搜索的时间
            searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
            //按 id排序 倒序
            searchSourceBuilder.sort(new FieldSortBuilder("_id").order(SortOrder.ASC));

            MultiSearchRequest request = new MultiSearchRequest();
            //第一个请求参数 构建
            SearchRequest firstSearchRequest = new SearchRequest();
            //SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchQuery("content", "中韩"));
            firstSearchRequest.source(searchSourceBuilder);
            request.add(firstSearchRequest);
            //第二个请求参数 构建
            SearchRequest secondSearchRequest = new SearchRequest();
            searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.matchQuery("content", "美国"));
            secondSearchRequest.source(searchSourceBuilder);
            request.add(secondSearchRequest);

            MultiSearchResponse response = client.msearch(request, RequestOptions.DEFAULT);


            //第一个请求查询第 返回内容
            MultiSearchResponse.Item firstResponse = response.getResponses()[0];
            SearchResponse searchResponse = firstResponse.getResponse();


            RestStatus restStatus = searchResponse.status();
            if (restStatus != RestStatus.OK) {
                System.out.println("es 返回状态吗 restStatus：" + restStatus);
            }
            //获取查询的结果集
            SearchHits searchHits = searchResponse.getHits();
            for (SearchHit hit : searchHits.getHits()) {
                //获取字段信息
                String source = hit.getSourceAsString();
                //输出所有信息
                System.out.println(hit);
            }
            //返回总行数
            long totalHits = searchHits.getTotalHits().value;
            System.out.println("返回总行数" + totalHits);
            //用时
            TimeValue took = searchResponse.getTook();
            System.out.println("查询成功！请求参数:  " + firstSearchRequest.source().toString());
            System.out.println("用时毫秒：" + took.millis());


            //第二个请求查询第 返回内容
            MultiSearchResponse.Item secondResponse = response.getResponses()[1];
            searchResponse = secondResponse.getResponse();


            restStatus = searchResponse.status();
            if (restStatus != RestStatus.OK) {
                System.out.println("es 返回状态吗 restStatus：" + restStatus);
            }
            //获取查询的结果集
            searchHits = searchResponse.getHits();
            for (SearchHit hit : searchHits.getHits()) {
                //获取字段信息
                String source = hit.getSourceAsString();
                //输出所有信息
                System.out.println(hit);
            }
            //返回总行数
            totalHits = searchHits.getTotalHits().value;
            System.out.println("返回总行数" + totalHits);
            //用时
            took = searchResponse.getTook();
            System.out.println("查询成功！请求参数:  " + secondSearchRequest.source().toString());
            System.out.println("用时毫秒：" + took.millis());

    }



}
