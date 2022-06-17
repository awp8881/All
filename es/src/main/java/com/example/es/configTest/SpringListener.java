package com.example.es.configTest;

import com.example.es.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component            //此类注入Spring容器，其中run方法会随项目启动时执行
//这个地方我们开启了BIO类型的Socket服务端，监听本机的
//8003端口，等待客户端连接
public class SpringListener implements ApplicationRunner {


    @Autowired
    User user;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("222222222222");
    }
}
