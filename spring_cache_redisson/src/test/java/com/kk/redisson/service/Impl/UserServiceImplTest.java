package com.kk.redisson.service.Impl;

import com.kk.redisson.common.SignUpResponse;
import com.kk.redisson.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private IUserService userService;

    @Test
    void signUp() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(() -> {
                SignUpResponse signUpResponse = userService.signUp("1021123064@qq.com");
                System.out.println(signUpResponse);
            });
            thread.start();
        }
        Thread.sleep(10000);
    }

    @Test
    void add(){
        userService.add(1L);
    }

    @Test
    void pollExpiredUsers(){
        userService.pollExpiredUsers().stream().forEach(System.out::println);
    }
}