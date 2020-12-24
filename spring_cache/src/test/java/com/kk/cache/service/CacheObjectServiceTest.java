package com.kk.cache.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CacheObjectServiceTest {
    @Autowired
    private  CacheObjectService cacheObjectService;

    @Test
    void getById() throws InterruptedException {
        System.out.println(cacheObjectService.getById(1));
    }

}
