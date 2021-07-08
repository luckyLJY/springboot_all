package com.kk.service.impl;

import com.kk.entity.User;
import com.kk.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

/**
 * user服务接口实现
 * 代替sql接入
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements IUserService {
    private static HashMap<Long, User> map;

    {
        map = new HashMap<>(3);
        map.put(1L, new User(1L, "kk1", "xxxxxx"));
        map.put(2L, new User(2L, "kk2", "cccccc"));
        map.put(3L, new User(3L, "kk3", "vvvvvv"));
    }

    @Override
    public Optional<User> query(Long userId) {
        return Optional.of(map.get(userId));
    }

    @Override
    public Optional<User> query(String username, String pwd) {
        if (username.equals("kk1")) {
            return Optional.of(new User(1L, "kk1", "xxxxxx"));
        }
        if (username.equals("kk2")) {
            return Optional.of(new User(2L, "kk2", "cccccc"));
        }
        if (username.equals("kk3")) {
            return Optional.of(new User(3L, "kk3", "vvvvvv"));
        }
        return Optional.empty();
    }
}
