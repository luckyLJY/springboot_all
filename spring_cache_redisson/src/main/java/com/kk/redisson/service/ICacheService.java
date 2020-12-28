package com.kk.redisson.service;

import com.kk.redisson.entity.User;

/**
 * 注解缓存使用
 *
 * @author luokexiong
 * @version 1.0 2020/12/25
 * @since 1.0.0
 */
public interface ICacheService {

    public void insert(User user);

    public void delete(Integer id);

    public User getOne(Integer id);

    public User update(User user);
}
