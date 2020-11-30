package com.kk.cache.service;

import com.kk.cache.entity.CacheObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CacheObjectServiceTest {
    @Autowired
    private  CacheObjectService cacheObjectService;

    @Test
    void queryAll() throws InterruptedException {
        cacheObjectService.queryAll().stream().forEach(System.out::println);
        Thread.sleep(2000);
        cacheObjectService.queryAll().stream().forEach(System.out::println);
        Thread.sleep(2000);
        CacheObject cacheObject = new CacheObject();
        cacheObject.setName("test").setAge(22).setBirthday(new Date()).setId(4);
        cacheObjectService.saveObject(cacheObject);
        System.out.println(cacheObjectService.getOneObject(4));
    }

}
