package com.example.es.bean;

import org.springframework.beans.factory.FactoryBean;

public class MyFactoryBean implements FactoryBean {


    @Override
    public Object getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
