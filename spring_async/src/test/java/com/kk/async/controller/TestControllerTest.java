package com.kk.async.controller;


import com.kk.async.AsyncApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = AsyncApplication.class)
class TestControllerTest {
   /* @Autowired
    private CustomService customService;

    @Test
    void test() {
        System.out.println(customService.toString());
    }*/
}
