package com.kk.security;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 存储jwt-salt数据的对象，通过从header获取数据拼装至secret中实现
 * <p>
 * 如果需要在salt中修改字段，需要修改
 * 1.JwtSalt字段
 * 2.JwtSalt.form(HttpServletRequest request)方法，增加该字段获取
 * 3.JwtSalt.checkSelf()方法，增加对新增字段对判空，否则外部不传入时无法判断
 * 4.JwtUtil.generateSecret(String secret, JwtSalt salt)方法，修改新secret生成算法
 * 5.RequestUtils.RequestInfo.from增加读取新数据
 * </p>
 * <p>
 * UA如下:
 * com.xgd.quyan/1.2.0+5 Dalvik/2.1.0 (Linux; U; Android 8.0.0; EDI-AL10 Build/HUAWEIEDISON-AL10)
 * </p>
 *
 * @author Goody
 * @version 1.0, 2020/8/5
 * @since 1.0.2
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Builder
public class JwtSalt {
    private static final String UUID_HEADER_NAME = "uuid";

    private String uuid;

    /**
     * 从requestInfo内获取数据
     *
     * @param requestInfo request信息
     * @return 盐值对象
     */
    public static JwtSalt from(RequestInfoDto requestInfo) {
        return JwtSalt.builder()
            .uuid(requestInfo.getUuid())
            .build();
    }

    /**
     * 从request获取相应的值组成 jwt salt
     *
     * @param request http request
     * @return jwt salt
     */
    public static JwtSalt from(HttpServletRequest request) {
        return JwtSalt.builder()
            .uuid(request.getHeader(UUID_HEADER_NAME))
            .build();
    }

    public boolean checkSelf() {
        return StringUtils.isNotBlank(uuid);
    }

}
