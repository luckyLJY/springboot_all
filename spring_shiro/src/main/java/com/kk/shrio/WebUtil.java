package com.kk.shrio;

import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * WebUtil
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */
public final class WebUtil {

    private WebUtil() {}

    /**
     * 将异常处理后写回
     *
     * @param errorStatus       Http错误码
     * @param request           请求
     * @param response          回应
     * @throws IOException IO异常
     */
    public static void writeError(HttpStatus errorStatus, ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setStatus(errorStatus.value());
        res.setContentType("application/json");
        // R<?> result = R.failed(errorStatus.value(), errorStatus.name(), null);
        String resJson = "{\"success\":false,\"errorCode\":\"401\",\"errorMsg\":\"Unauthorized\"}";
        res.getWriter().write(resJson);
    }
}
