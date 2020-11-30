package com.kk.learning.entity;

import org.springframework.stereotype.Component;

/**
 * @author : K k
 * @date : 8:33 2020/11/3
 */
@Component("demoTask")
public class DemoTask {
    public void taskWithParams(String params) {
        System.out.println("执行有参示例任务：" + params);
    }

    public void taskNoParams() {
        System.out.println("执行无参示例任务");
    }
}
