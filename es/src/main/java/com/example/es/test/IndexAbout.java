package com.example.es.test;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.junit.Test;

import java.io.IOException;

public class IndexAbout {

    private  final static Log logger= LogFactory.get(IndexAbout.class);
    //创建ES客户端
    private  static RestHighLevelClient edClient = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9200)));
    @Test
    public  void creatIndex() throws  Exception{

        CreateIndexResponse ybz = edClient.indices().create(new CreateIndexRequest("ybz"), RequestOptions.DEFAULT);
        boolean acknowledged = ybz.isAcknowledged();
        logger.info("添加索引是否成功："+acknowledged);

        //关闭ES客户端
        edClient.close();

    }

    @Test
    public  void  getIndex() throws IOException {
        GetIndexResponse ybz = edClient.indices().get(new GetIndexRequest("ybz"), RequestOptions.DEFAULT);
        logger.info("索引："+ybz);
        logger.info("索引Aliases："+ybz.getAliases());
        logger.info("索引Settings："+ybz.getSettings());
        logger.info("索引Mappings："+ybz.getMappings());

        edClient.close();
    }
}
