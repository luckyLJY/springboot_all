package com.kk.async.threadpool.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 线程报警器
 *
 * @author kkmystery
 * @version 1.0 2021/8/31
 * @since 1.0.0
 */
@Component
@Async
@Slf4j
public class Alert {
    private Long lastActiveAlert;
    private Long lastCapacityAlert;

    public void alertActive(DynamicThreadPoolExecutor threadPool) {
        if (lastActiveAlert == null || System.currentTimeMillis() - lastActiveAlert >= 200) {
            lastActiveAlert = System.currentTimeMillis();
            log.warn("线程池线程即将达到最大阈值警告: {}", threadPool);
        }
    }

    public void alertCapacity(DynamicThreadPoolExecutor threadPool) {
        if (lastCapacityAlert == null || System.currentTimeMillis() - lastCapacityAlert >= 200) {
            lastCapacityAlert = System.currentTimeMillis();
            log.warn("线程池阻塞队列即将达到容量上限警告: {}", threadPool);
        }
    }

}
