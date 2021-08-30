package com.kk.async.threadpool.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 线程池参数VO
 *
 * @author kkmystery
 * @version 1.0 2021/8/30
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThreadPoolArgsVO {
    private Integer corePoolSize;
    private Integer maxPoolSize;
    private Integer keepAliveSeconds;
    private Integer queueCapacity;
    private Double capacityThreshold;
    private Double activeThreshold;
    private String queueType;
    private String appName;
    private String threadPoolName;
    private String threadFactoryType;
    private String rejectHandlerType;
}
