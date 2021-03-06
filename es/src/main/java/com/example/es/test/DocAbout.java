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
    //??????ES?????????
    private  static RestHighLevelClient edClient = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9200)));

    private User user;

    private List<User> userList = new ArrayList<>();

    //?????????????????????
    {
        User user1 = new User();
        user1.setName("??????");
        user1.setSex("??????");
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
     * ????????????
     */
    @Test
    public void insertDocs() throws  Exception{
        String userJsonStr = JSON.toJSONString(user);
        logger.info("???????????????"+userJsonStr);

        IndexRequest ybzRequest = new IndexRequest().index("ybz").id("0001");
        ybzRequest.source(userJsonStr, XContentType.JSON);
        IndexResponse response = edClient.index(ybzRequest, RequestOptions.DEFAULT);


        logger.info("?????????"+response);

        edClient.close();
    }


    /**
     * ????????????
     */
    @Test
    public void updateDocs() throws  Exception{

        UpdateRequest ybzRequest = new UpdateRequest();
        ybzRequest.index("ybz").id("0001");
        ybzRequest.doc(XContentType.JSON,"sex","??????");

        UpdateResponse response = edClient.update(ybzRequest, RequestOptions.DEFAULT);

        logger.info("?????????"+response);

        edClient.close();
    }


    /**
     * ????????????
     */
    @Test
    public void getDocs() throws  Exception{

        GetRequest ybzRequest = new GetRequest().index("ybz").id("0001");
        GetResponse response = edClient.get(ybzRequest, RequestOptions.DEFAULT);


        logger.info("?????????"+response);
        logger.info("???????????????"+response.getSource());
        edClient.close();
    }

    /**
     * ????????????
     */
    @Test
    public void delDocs() throws  Exception{

        DeleteRequest ybzRequest = new DeleteRequest().index("ybz").id("0001");
        DeleteResponse response = edClient.delete(ybzRequest, RequestOptions.DEFAULT);


        logger.info("?????????"+response);
        edClient.close();
    }


    /**
     * ????????????(??????)
     */
    @Test
    public void insertDocsBatch() throws  Exception{
        BulkRequest request = new BulkRequest();
        userList.forEach(x->{
            String userJsonStr = JSON.toJSONString(x);
            logger.info("???????????????"+userJsonStr);
            request.add(new IndexRequest().index("ybz").id(x.getAge()+"").source(userJsonStr,XContentType.JSON));
        });

        BulkResponse response = edClient.bulk(request, RequestOptions.DEFAULT);


        logger.info("?????????????????????"+response);

        edClient.close();
    }

    /**
     * ????????????(??????)
     */
    @Test
    public void delDocsBatch() throws  Exception{
        BulkRequest request = new BulkRequest();
        userList.forEach(x->{
            String userJsonStr = JSON.toJSONString(x);
            logger.info("???????????????"+userJsonStr);
            request.add(new DeleteRequest().index("ybz").id(x.getAge()+""));
        });

        BulkResponse response = edClient.bulk(request, RequestOptions.DEFAULT);


        logger.info("?????????????????????"+response);

        edClient.close();
    }


    /**
     * ????????????(?????????)
     */
    @Test
    public void getDocsBatch() throws  Exception{


        SearchRequest ybzRequest = new SearchRequest();
        ybzRequest.indices("fuck_*");
        //????????????
//        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());

        //???????????????
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.termQuery("message","??????creatSomeLogs"));

        //????????????
        //SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        //query.from(0).size(2);

        //??????
        //SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        //query.sort("age", SortOrder.DESC);

        //????????????
        //SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        //String[] excludes = {};
        //String[] includes = {"name"};
        //query.fetchSource(includes,excludes);

        //???????????????
        //SearchSourceBuilder builder = new SearchSourceBuilder();
        //BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //boolQueryBuilder.must(QueryBuilders.matchQuery("age",5))
                        //.must(QueryBuilders.matchQuery("sex","??????"));
                        //.mustNot(QueryBuilders.matchQuery("sex","??????"));
                        //.should(QueryBuilders.matchQuery("age",6));

        //RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age");
        //rangeQueryBuilder.gte(5).lt(9);    //>=5   <9

        //builder.query(rangeQueryBuilder);

        //????????????
        //SearchSourceBuilder builder = new SearchSourceBuilder();
        //builder.query(QueryBuilders.fuzzyQuery("name", "???5").fuzziness(Fuzziness.ONE));  //Fuzziness.?????????????????? ????????????

        //????????????
        //SearchSourceBuilder builder = new SearchSourceBuilder();
        //TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "zhang2");
        //HighlightBuilder highlightBuilder = new HighlightBuilder();
        //highlightBuilder.preTags("<font color ='red'>");
        //highlightBuilder.postTags("</font>");
        //highlightBuilder.field("name");
        //highlightBuilder.requireFieldMatch(true);
        //builder.highlighter(highlightBuilder);

        //????????????
        //SearchSourceBuilder builder = new SearchSourceBuilder();
        //AggregationBuilder aggregationBuilder = AggregationBuilders.max("maxAge").field("age"); //????????????
        //builder.aggregation(aggregationBuilder);

//        SearchSourceBuilder builder = new SearchSourceBuilder();
//        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("ageGroup").field("age"); //??????????????????
//        builder.aggregation(aggregationBuilder);
        ybzRequest.source(builder);
        SearchResponse response = edClient.search(ybzRequest, RequestOptions.DEFAULT);
        logger.info("?????????"+ response);
        logger.info("???????????????"+ Arrays.toString(response.getHits().getHits()));
        edClient.close();
    }

}
