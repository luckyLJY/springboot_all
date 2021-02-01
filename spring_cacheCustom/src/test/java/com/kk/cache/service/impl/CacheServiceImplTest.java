package com.kk.cache.service.impl;

import com.kk.cache.entity.User;
import com.kk.cache.mybatis.mapper.CacheMapper;
import com.kk.cache.service.ICacheService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class CacheServiceImplTest {
    @Autowired
    private ICacheService cacheService;
    @Autowired
    private CacheMapper cacheMapper;

    @BeforeEach
    @AfterEach
    void init(){
        cacheMapper.delete(null);
    }

    @Test
    void test1(){
        User user=new User();
        System.out.println(user.getClass().getName());
    }

    @Test
    void test() {
        User user=new User();
        user.setId(4).setName("asd").setBirthday(LocalDateTime.now());

        cacheService.insert(user);
        System.out.println("---------");
        cacheService.getOne(user.getId());
        System.out.println("----看查询是否进入缓存");
        cacheService.getOne(user.getId());
        System.out.println("---------");
        cacheService.update(user.setName("sdasdasdasd"));
        cacheService.getOne(user.getId());
        System.out.println("---------");
        System.out.println("---删除---");
        cacheService.delete(user.getId());
        System.out.println("---------");
        cacheService.getOne(user.getId());
        System.out.println("----看查询是否进入缓存");
        cacheService.getOne(user.getId());
    }
}
