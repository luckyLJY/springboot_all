package com.kk.async.threadpool.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 线程池详细vo
 *
 * @author kkmystery
 * @version 1.0 2021/8/30
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThreadPoolDetailInfoVO {
    private Integer corePoolSize;
    private Integer maxPoolSize;
    private Integer keepAliveSeconds;
    private Integer queueCapacity;
    private Integer activeCount;
    private Long completedTaskCount;
    private Integer largestPoolSize;
    private Integer rejectCount;
    private Long taskCount;
    private Integer poolSize;
    private Integer queueSize;
    private Integer queueRemainingCapacity;
    private Boolean allowCoreThreadTimeOut;
    private String queueType;
    private String appName;
    private String threadPoolName;
    private Date currentTime;
    private String threadFactoryType;
    private String rejectHandlerType;
}
