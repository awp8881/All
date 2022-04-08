package com.example.es.test;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.es.bean.User;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DocAbout {

    private  final static Log logger = LogFactory.get(DocAbout.class);
    //创建ES客户端
    private  static RestHighLevelClient edClient = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9200)));

    private User user;

    private List<User> userList = new ArrayList<>();

    //初始化模拟数据
    {
        User user1 = new User();
        user1.setName("张三");
        user1.setSex("男人");
        user1.setAge(27);
        this.user = user1;

    for(int i=0; i <10;i++){
        User user2 = new User();
        user2.setName("zhang"+i);
        user2.setSex(i%2 == 0 ?"man":"woman");
        user2.setAge(i);
        userList.add(user2);
    }
 }


    /**
     * 插入数据
     */
    @Test
    public void insertDocs() throws  Exception{
        String userJsonStr = JSON.toJSONString(user);
        logger.info("数据信息："+userJsonStr);

        IndexRequest ybzRequest = new IndexRequest().index("ybz").id("0001");
        ybzRequest.source(userJsonStr, XContentType.JSON);
        IndexResponse response = edClient.index(ybzRequest, RequestOptions.DEFAULT);


        logger.info("返回："+response);

        edClient.close();
    }


    /**
     * 修改数据
     */
    @Test
    public void updateDocs() throws  Exception{

        UpdateRequest ybzRequest = new UpdateRequest();
        ybzRequest.index("ybz").id("0001");
        ybzRequest.doc(XContentType.JSON,"sex","女人");

        UpdateResponse response = edClient.update(ybzRequest, RequestOptions.DEFAULT);

        logger.info("返回："+response);

        edClient.close();
    }


    /**
     * 查询数据
     */
    @Test
    public void getDocs() throws  Exception{

        GetRequest ybzRequest = new GetRequest().index("ybz").id("0001");
        GetResponse response = edClient.get(ybzRequest, RequestOptions.DEFAULT);


        logger.info("返回："+response);
        logger.info("返回数据："+response.getSource());
        edClient.close();
    }

    /**
     * 删除数据
     */
    @Test
    public void delDocs() throws  Exception{

        DeleteRequest ybzRequest = new DeleteRequest().index("ybz").id("0001");
        DeleteResponse response = edClient.delete(ybzRequest, RequestOptions.DEFAULT);


        logger.info("返回："+response);
        edClient.close();
    }


    /**
     * 插入数据(批量)
     */
    @Test
    public void insertDocsBatch() throws  Exception{
        BulkRequest request = new BulkRequest();
        userList.forEach(x->{
            String userJsonStr = JSON.toJSONString(x);
            logger.info("数据信息："+userJsonStr);
            request.add(new IndexRequest().index("ybz").id(x.getAge()+"").source(userJsonStr,XContentType.JSON));
        });

        BulkResponse response = edClient.bulk(request, RequestOptions.DEFAULT);


        logger.info("批量插入返回："+response);

        edClient.close();
    }

    /**
     * 删除数据(批量)
     */
    @Test
    public void delDocsBatch() throws  Exception{
        BulkRequest request = new BulkRequest();
        userList.forEach(x->{
            String userJsonStr = JSON.toJSONString(x);
            logger.info("数据信息："+userJsonStr);
            request.add(new DeleteRequest().index("ybz").id(x.getAge()+""));
        });

        BulkResponse response = edClient.bulk(request, RequestOptions.DEFAULT);


        logger.info("批量删除返回："+response);

        edClient.close();
    }


    /**
     * 查询数据(带条件)
     */
    @Test
    public void getDocsBatch() throws  Exception{


        SearchRequest ybzRequest = new SearchRequest();
        ybzRequest.indices("fuck_*");
        //全量查询
//        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());

        //单条件查询
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.termQuery("message","进入creatSomeLogs"));

        //分页查询
        //SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        //query.from(0).size(2);

        //排序
        //SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        //query.sort("age", SortOrder.DESC);

        //过滤字段
        //SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        //String[] excludes = {};
        //String[] includes = {"name"};
        //query.fetchSource(includes,excludes);

        //多条件组合
        //SearchSourceBuilder builder = new SearchSourceBuilder();
        //BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //boolQueryBuilder.must(QueryBuilders.matchQuery("age",5))
                        //.must(QueryBuilders.matchQuery("sex","女的"));
                        //.mustNot(QueryBuilders.matchQuery("sex","男的"));
                        //.should(QueryBuilders.matchQuery("age",6));

        //RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age");
        //rangeQueryBuilder.gte(5).lt(9);    //>=5   <9

        //builder.query(rangeQueryBuilder);

        //模糊查询
        //SearchSourceBuilder builder = new SearchSourceBuilder();
        //builder.query(QueryBuilders.fuzzyQuery("name", "张5").fuzziness(Fuzziness.ONE));  //Fuzziness.？差几个字符 可以匹配

        //高亮查询
        //SearchSourceBuilder builder = new SearchSourceBuilder();
        //TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "zhang2");
        //HighlightBuilder highlightBuilder = new HighlightBuilder();
        //highlightBuilder.preTags("<font color ='red'>");
        //highlightBuilder.postTags("</font>");
        //highlightBuilder.field("name");
        //highlightBuilder.requireFieldMatch(true);
        //builder.highlighter(highlightBuilder);

        //聚合查询
        //SearchSourceBuilder builder = new SearchSourceBuilder();
        //AggregationBuilder aggregationBuilder = AggregationBuilders.max("maxAge").field("age"); //最大年龄
        //builder.aggregation(aggregationBuilder);

//        SearchSourceBuilder builder = new SearchSourceBuilder();
//        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("ageGroup").field("age"); //根据年龄分组
//        builder.aggregation(aggregationBuilder);
        ybzRequest.source(builder);
        SearchResponse response = edClient.search(ybzRequest, RequestOptions.DEFAULT);
        logger.info("返回："+ response);
        logger.info("返回数据："+ Arrays.toString(response.getHits().getHits()));
        edClient.close();
    }

}
