package com.kk.service;

import com.kk.entity.User;

import java.util.Optional;

public interface IUserService {

    /**
     * 查询用户
     *
     * @param userId userId
     * @return User
     */
    Optional<User> query(Long userId);

    /**
     * 查询
     *
     * @param username 用户名
     * @param pwd      密码
     * @return User
     */
    Optional<User> query(String username, String pwd);
}
