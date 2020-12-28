package com.kk.redisson.service.Impl;

import com.kk.redisson.entity.User;
import com.kk.redisson.service.ICacheService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CacheServiceImplTest {
    @Autowired
    private ICacheService cacheService;

    @Test
    void test() {
        User user=new User();
        user.setId(4).setName("asd").setBirthday(LocalDateTime.now());
        cacheService.insert(user);

        cacheService.getOne(user.getId());
        System.out.println("----看查询是否进入缓存");
        cacheService.getOne(user.getId());

        cacheService.update(user.setName("sdasdasdasd"));
        cacheService.getOne(user.getId());

        System.out.println("---删除---");
        cacheService.delete(user.getId());

        cacheService.getOne(user.getId());
        System.out.println("----看查询是否进入婚缓存");
        cacheService.getOne(user.getId());

    }
}