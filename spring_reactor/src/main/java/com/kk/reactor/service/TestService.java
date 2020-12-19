package com.kk.reactor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @description: 服务实现类
 * @author: Kk
 * @create: 2020-12-19 11:12
 **/
@Service
@Slf4j
public class TestService {

    /**
     * 异步方法
     *
     * @return
     */
    @Async
    public String actionAsync(){
        try {
            log.info("异步方法："+Thread.currentThread().getName()+" 正在执行任务");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "处理完成";
    }

    /**
     * 同步方法
     *
     * @return
     */
    public String actionSync(){
        try {
            //int i=1/0;
            log.info("同步方法："+Thread.currentThread().getName()+" 正在执行任务");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "处理完成";
    }
}
