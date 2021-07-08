package com.kk.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * Executor Properties
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@ToString
public class ExecutorProperties {

    /** 核心线程数 */
    private Integer corePoolSize;
    /** 是否允许核心线程超时 */
    private Boolean allowCoreThreadTimeout;
    /** 最大线程数 */
    private Integer maxPoolSize;
    /** 阻塞队列大小 */
    private Integer queueCapacity;
    /** 线程前缀 */
    private String threadNamePrefix;

    /* ForkJoin */
    /** the parallelism level */
    private Integer parallelism;
    /** true=FIFO, false=LIFO */
    private Boolean asyncMode;
}
