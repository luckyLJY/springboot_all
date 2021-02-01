package com.kk.cache.service;

import com.kk.cache.entity.User;

/**
 * 注解缓存使用
 *
 * @author luokexiong
 * @version 1.0 2020/12/25
 * @since 1.0.0
 */
public interface ICacheService {

    void insert(User user);

    void delete(Integer id);

    User getOne(Integer id);

    User update(User user);
}
