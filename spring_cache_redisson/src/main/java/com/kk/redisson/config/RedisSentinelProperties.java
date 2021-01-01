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
public class RedisSentinelProperties {
    /** 哨兵master 名称 */
    private String masterName;

    /** 哨兵节点 */
    private String nodes;

    /** 从节点连接池大小，默认值：64 */
    private int slaveConnectionPoolSize = 64;

    /** 从节点最小空闲连接数，默认值：32 */
    private int slaveConnectionMinimumIdleSize = 32;

    /** 主节点连接池大小，默认值：64 */
    private int masterConnectionPoolSize = 64;

    /** 主节点最小空闲连接数，默认值：32 */
    private int masterConnectionMinimumIdleSize = 32;
}
