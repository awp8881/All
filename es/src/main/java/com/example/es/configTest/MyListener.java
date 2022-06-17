package com.example.es.configTest;

import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

public class MyListener implements ApplicationListener {



    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

        System.out.println("ApplicationListener.........");
    }
}
