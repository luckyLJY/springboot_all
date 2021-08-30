package com.kk.async.threadpool.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Async
@Slf4j
public class Alert {
    private Long lastActiveAlert;
    private Long lastCapacityAlert;

    public synchronized void alertActive(DynamicThreadPoolExecutor threadPool){
        if(lastActiveAlert==null||System.currentTimeMillis()-lastActiveAlert>=300000){
            lastActiveAlert=System.currentTimeMillis();
            log.warn("线程池活跃线程达到阈值警告: {}", threadPool);
        }
    }

    public synchronized void alertCapacity(DynamicThreadPoolExecutor threadPool){
        if(lastCapacityAlert==null||System.currentTimeMillis()-lastCapacityAlert>=300000){
            lastCapacityAlert=System.currentTimeMillis();
            log.warn("线程池线程即将达到容量上限警告: {}", threadPool);
        }
    }

}
