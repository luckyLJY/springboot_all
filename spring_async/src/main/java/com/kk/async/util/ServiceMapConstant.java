package com.kk.async.util;

import com.google.common.base.CaseFormat;

/**
 * 多实现常量工具类
 *
 * @author cuishuqiang
 * @version 1.0, 2021/7/20 10:31
 * @since 1.0.2
 */
public class ServiceMapConstant {
    private ServiceMapConstant() {
    }

    /**
     * 获取各渠道支付服务实例名
     *
     * @param bizName      业务名称，一般为枚举名称
     * @param interfaceCls 接口class
     * @return 实例bean名称
     */
    public static String getServiceBeanName(String bizName, Class<?> interfaceCls) {
        String result = String.format("转换服务结果：%s, %s", CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, bizName), interfaceCls.getSimpleName());
        System.out.println(result);
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, bizName) + interfaceCls.getSimpleName();
    }

}
