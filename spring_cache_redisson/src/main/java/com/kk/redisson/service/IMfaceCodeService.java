package com.kk.redisson.service;

/**
 * IMface操作接口
 *
 * @author luokexiong
 * @version 1.0 2020/12/25 10:29 上午
 * @since 1.0.0
 */
public interface IMfaceCodeService {

    /**
     * 生成验证码
     *
     * <P>生成6位数字验证码</P>
     *
     * @param userId 用户id
     * @return true - 生成成功；false - 生成失败
     */
    boolean generate(long userId);

    /**
     * 校验验证码
     *
     * <P>校验通过时，需要删除验证码</P>
     *
     * @param userId
     * @param code
     * @return
     */
    boolean valid(long userId, String code);
}
