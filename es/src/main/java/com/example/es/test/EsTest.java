package com.example.es.test;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;

import java.io.IOException;

public class EsTest {

    private  final static Log logger= LogFactory.get(EsTest.class);

    public static void main(String[] args) throws IOException {

        //创建ES客户端
        RestHighLevelClient edClient = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9200)));


        //关闭ES客户端
        edClient.close();
    }
}
