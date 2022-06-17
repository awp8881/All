package com.example.es.bean;

import com.example.es.configTest.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import javax.annotation.Resource;
import java.util.function.Predicate;

public class MyImportSelector implements ImportSelector {

    @Resource(name = "UserImpl02")
    IUser user;

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        //前面可以是和业务相关的逻辑
        //返回需要导入的组件的全类名数组
        return new String[]{"com.example.es.bean.User"};
    }

    @Override
    public Predicate<String> getExclusionFilter() {
        return ImportSelector.super.getExclusionFilter();
    }
}
