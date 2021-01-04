package com.kk.cache.service.impl;


import com.kk.cache.entity.User;
import com.kk.cache.service.ICacheService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * @author luokexiong
 * @version 1.0 2020/12/25
 * @since 1.0.0
 */
@Service
@CacheConfig(cacheNames = "myCache1")
public class CacheServiceImpl implements ICacheService {
    public static HashMap<Integer, User> map = new HashMap<>();

    @PostConstruct
    public void init(){
        map.put(1, new User(1,"kk", LocalDateTime.now()));
        map.put(2, new User(2,"xx", LocalDateTime.now()));
        map.put(3, new User(3,"eez", LocalDateTime.now()));
    }

    @Override
    public void insert(User user) {
        System.out.println("---插入用户---");
        map.put(user.getId(),user);
    }

    @CacheEvict(key = "'cache:user:'+#id")
    @Override
    public void delete(Integer id) {
        System.out.println("---删除用户---");
        map.remove(id);
    }

    @Cacheable(key = "'cache:user:'+#id")
    @Override
    public User getOne(Integer id) {
        System.out.println("---获取用户---");
        return map.get(id);
    }

    @CachePut(key = "'cache:user:'+#user.id")
    @Override
    public User update(User user) {
        System.out.println("---更新用户---");
        map.put(user.getId(),user);
        return map.get(user.getId());
    }
}
