package com.example.es;

import com.example.es.configTest.IUser;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class EsApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(EsApplication.class, args);
        run.getBean("1");

    }

}
