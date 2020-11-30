package com.kk.learning.service.impl;

import org.springframework.stereotype.Service;

/**
 * @author : K k
 * @date : 9:09 2020/11/3
 */
@Service
public class OrderService {
    public void delete() {
        try {
            Thread.sleep(6*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("delete data sucess....");
    }
}