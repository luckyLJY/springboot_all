package com.kk.redisson.constant;

/**
 * @author luokexiong
 * @version 1.0 2020/12/25
 * @since 1.0.0
 */
public class RedisConstants {

    /** key：随机验证码 */
    public static final String RANDOM_CODE = "randomCode";

    /** 分布式锁 */
    public static final String DISTRIBUTED_LOCK = "distributed_lock";

    public static String generateUserCodeKey(Long userId) {
        return RANDOM_CODE + ":" + String.valueOf(userId);
    }
}
