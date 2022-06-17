package com.example.es.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Csdn {


    public static void main(String[] args) throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        while (true){
//            ResponseEntity<String> forEntity2 = restTemplate.getForEntity("https://blog.csdn.net/J169YBZ/article/details/124200067?spm=1001.2014.3001.5502", String.class);
//
//            System.out.println(1);
//            Thread.sleep(1000);
            String a= "123";
            String intern = a.intern();
            ResponseEntity<String> forEntity = restTemplate.getForEntity("https://blog.csdn.net/J169YBZ/article/details/124636136?spm=1001.2014.3001.5502", String.class);
            System.out.println(2);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("x");
        }
    }
}
