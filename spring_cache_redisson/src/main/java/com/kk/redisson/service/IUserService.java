package com.kk.redisson.service;

import com.kk.redisson.common.SignUpResponse;

import java.util.List;

/**
 * @author luokexiong
 * @version 1.0 2020/12/25
 * @since 1.0.0
 */
public interface IUserService {

    SignUpResponse signUp(String email);

    boolean add(Long userId);

    List<Long> pollExpiredUsers();
}
