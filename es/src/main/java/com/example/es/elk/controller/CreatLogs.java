package com.example.es.elk.controller;


import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.example.es.test.IndexAbout;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreatLogs {

    private  final static Log logger= LogFactory.get(CreatLogs.class);

    @GetMapping("/creatSomeLogs")
    public  void  creatSomeLogs(){
        logger.info("进入creatSomeLogs");
    }

}
