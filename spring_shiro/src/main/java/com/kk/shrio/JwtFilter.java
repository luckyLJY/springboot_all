package com.kk.shrio;

import com.kk.security.JwtSalt;
import com.kk.security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * JWT过滤器
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);

    @Override
    protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        // 不需要重定向
        // 认证失败直接返回未认证信息提示
        LOGGER.info("JwtFilter#redirectToLogin, UNAUTHORIZED");
        WebUtil.writeError(HttpStatus.UNAUTHORIZED, request, response);
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        log.debug("AnonJwtFilter#createToken for Request");
        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = getRequestToken(httpServletRequest);
        // 获取jwt salt中的uuid
        JwtSalt jwtSalt = JwtSalt.from(httpServletRequest);
        return new JwtToken(token, jwtSalt, AccessType.DYNAMIC_ACCESS);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        LOGGER.debug("JwtFilter#executeLogin");
        // 生成token
        AuthenticationToken token = createToken(request, response);
        if (null == token) {
            throw new UnauthorizedException();
        }
        try {
            Subject subject = getSubject(request, response);
            subject.login(token);
            return this.onLoginSuccess(token, subject, request, response);
        } catch (AuthenticationException e) {
            log.info("AuthenticationException: {}", e.getMessage());
            WebUtil.writeError(HttpStatus.UNAUTHORIZED, request, response);
        }
        return false;
    }

    /**
     * 在访问controller前判断是否登录
     *
     * @param request     请求
     * @param response    回应
     * @param mappedValue 参数
     * @return 是否登录
     * @throws Exception 异常
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        LOGGER.debug("JwtFilter#onAccessDenied");
        String token = getRequestToken((HttpServletRequest) request);
        if (StringUtils.isBlank(token)) {
            LOGGER.info("JwtFilter#onAccessDenied No token found");
            WebUtil.writeError(HttpStatus.UNAUTHORIZED, request, response);
            return false;
        }
        return executeLogin(request, response);
    }

    /**
     * 获取token信息
     *
     * @param request request
     * @return token
     */
    private String getRequestToken(HttpServletRequest request) {
        String token = request.getHeader(JwtUtil.TOKEN_NAME);
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(JwtUtil.TOKEN_NAME);
        }
        return token;
    }
}
