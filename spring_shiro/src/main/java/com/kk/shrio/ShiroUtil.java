package com.kk.shrio;


import com.kk.entity.AuthUser;
import org.apache.shiro.SecurityUtils;

import java.util.Optional;

/**
 * shiro工具类
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */
public final class ShiroUtil {

    private ShiroUtil() {
    }

    /**
     * 获取已授权用户
     *
     * @return 已授权用户
     */
    public static AuthUser getAuthUser() {
        return (AuthUser) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取已授权用户，用户可能没有登录态
     *
     * @return 已授权用户optional
     */
    public static Optional<AuthUser> getAuthUserOrEmpty() {
        return Optional.ofNullable(getAuthUser());
    }

    /**
     * 获取已授权用户id，用户可能没有登录态
     *
     * @return 已授权用户id optional
     */
    public static Optional<Long> getAuthUserIdOrEmpty() {
        return getAuthUserOrEmpty().map(AuthUser::getUserId);
    }

    /**
     * 获取已授权用户id
     *
     * @return 已授权用户id
     */
    public static Long getAuthUserId() {
        return getAuthUserIdOrEmpty().orElse(null);
    }
}
