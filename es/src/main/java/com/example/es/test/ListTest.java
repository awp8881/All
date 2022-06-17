package com.example.es.test;


import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListTest {

    public static void main(String[] args) throws IOException {

        URL url = new URL("http://192.168.2.237:81/OfficeFileResult/DownLoadFile?Token=&keyId=5a73eb22a0474c36aca72bd97accc90c");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

       // File out = new File("C:\\Users\\Administrator\\Desktop\\123321.xlsx");
        File out = File.createTempFile("partTimeFile","xlsx");//提供临时文件的前缀和后缀
        out.deleteOnExit();//JVM退出时自动删除
        InputStream inputStream =  conn.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream(out);
        int len = -1;
        byte[] bytes = new byte[1024];
        while ((len = inputStream.read(bytes)) != -1){
            fileOutputStream.write(bytes,0,len);
            System.out.println(1);
        }

    }


}
