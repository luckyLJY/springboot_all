package com.kk.redisson.config;

import lombok.Data;
import lombok.ToString;

/**
 * @author luokexiong
 * @version 1.0 2020/12/25
 * @since 1.0.0
 */
@Data
@ToString
public class RedisClusterProperties {
    /** 集群状态扫描间隔时间，单位：毫秒，默认值：1000 */
    private int scanInterval = 2000;

    /** 集群节点 */
    private String nodes;

    /**
     * 默认值： SLAVE（只在从服务节点里读取）设置读取操作选择节点的模式。 可用值为： SLAVE - 只在从服务节点里读取。
     * MASTER - 只在主服务节点里读取。 MASTER_SLAVE - 在主从服务节点里都可以读取
     */
    private String readMode = "SLAVE";

    /** 从节点连接池大小，默认值：64 */
    private int slaveConnectionPoolSize = 64;

    /** 从节点最小空闲连接数，默认值：32 */
    private int slaveConnectionMinimumIdleSize = 32;

    /** 主节点连接池大小，默认值：64 */
    private int masterConnectionPoolSize = 64;

    /** 主节点最小空闲连接数，默认值：32 */
    private int masterConnectionMinimumIdleSize = 32;

    /** 命令失败重试次数，默认值：3 */
    private int retryAttempts = 3;

    /** 命令重试发送时间间隔，单位：毫秒 默认值：1500 */
    private int retryInterval = 1500;

    /** 执行失败最大次数，默认值：3 */
    private int failedAttempts = 3;
}
