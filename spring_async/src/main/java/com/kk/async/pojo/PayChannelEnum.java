package com.kk.async.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * PayChannelEnum
 *
 * @author kkmystery
 * @version 1.0 2021/8/27
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
@SuppressWarnings("MagicNumber")
public enum PayChannelEnum {
    /**
     * 苹果渠道
     */
    APPLE_IAP((byte) 0),
    /**
     * 谷歌商店渠道
     */
    GOOGLE_PLAY((byte) 1),
    /**
     * 微信渠道
     */
    WECHAT_PAY((byte) 2);
    private final byte value;

    private static final Map<Byte, PayChannelEnum> VALUE_MAP = Stream.of(PayChannelEnum.values())
            .collect(Collectors.toMap(PayChannelEnum::getValue, Function.identity()));

    /**
     * byte to enum
     *
     * @param value value
     * @return enum
     */
    public static PayChannelEnum from(Byte value) {
        return null == value ? null : VALUE_MAP.get(value);
    }
}
