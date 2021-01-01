package com.kk.redisson.constant;

/**
 * @author luokexiong
 * @version 1.0 2020/12/25
 * @since 1.0.0
 */
public class RedisConstants {

    /** key：随机验证码 */
    public static final String RANDOM_CODE = "randomCode";
    /** key：用户列表 */
    public static final String USER_LIST = "userList";
    /** key：素材 */
    public static final String MATERIAL = "material";
    /** key：用户 */
    public static final String USER = "user";
    /** key：任务 */
    public static final String TASK = "task";
    /** 分布式锁 */
    public static final String DISTRIBUTED_LOCK = "distributed_lock";

    public static String generateUserKey(Long userId) {
        return USER + ":" + userId;
    }

    public static String generateUserCodeKey(Long userId) {
        return RANDOM_CODE + ":" + String.valueOf(userId);
    }

    public static String generateMaterialKey(String userId) {
        return MATERIAL + ":" + userId;
    }

    public static String generateTaskKey(Long taskId) {
        return TASK + ":" + taskId;
    }
}
