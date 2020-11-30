package com.kk.es;/**
 * @author : K k
 * @date : 13:25 2020/11/19
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * @description:
 * @author: Kk
 * @create: 2020-11-19 13:25
 **/
@SpringBootApplication
public class ElasticSearchApplication {
   /* @PostConstruct
    public void init(){
        //解决netty启动冲突问题
        System.setProperty("es.set.netty.runtime.available.processors","false");

    }*/
    public static void main(String[] args) {
        SpringApplication.run(ElasticSearchApplication.class,args);
    }
}
