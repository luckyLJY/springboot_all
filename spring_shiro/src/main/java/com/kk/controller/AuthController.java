package com.kk.controller;

import com.kk.common.R;
import com.kk.config.LocalJwtConfig;
import com.kk.controller.form.LoginForm;
import com.kk.entity.AuthUser;
import com.kk.entity.User;
import com.kk.security.JwtSalt;
import com.kk.security.JwtUtil;
import com.kk.security.RequestInfoDto;
import com.kk.service.IUserService;
import com.kk.shrio.ShiroUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 认证controller
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */
@RestController
@RequestMapping(value ="/auth")
@RequiredArgsConstructor
public class AuthController {
    private static final String UUID_HEADER_NAME = "uuid";
    private final IUserService userService;
    private final LocalJwtConfig jwtConfig;

    @PostMapping("/user/login")
    public R<String> login(LoginForm form, HttpServletRequest request) {
        final JwtSalt salt = JwtSalt.from(new RequestInfoDto(getIpFromRequest(request), request.getHeader(UUID_HEADER_NAME)));
        if (!salt.checkSelf()) {
            return R.failed(401, "登录信息缺失", null);
        }
        Optional<User> userOptional = userService.query(form.getUsername(), form.getPwd());
        if (!userOptional.isPresent()) {
            return R.failed(401, "用户不存在", form.getUsername());
        }
        User user = userOptional.get();
        final String secret = JwtUtil.generateSecret(user.getSecret(), salt);
        final String token = JwtUtil.sign(user.getId().toString(), secret, jwtConfig.getExpired());
        return R.success("登陆成功", token);
    }

    @GetMapping("/user/info")
    public R<AuthUser> getUserInfo() {
        return R.success("用户信息", ShiroUtil.getAuthUser());
    }

    /**
     * 从request中提取IP。
     *
     * @param request http request
     * @return ip address
     */
    public static String getIpFromRequest(HttpServletRequest request) {
        final String xRealIp = request.getHeader("X-REAL-IP");
        final String xForwardedFor = request.getHeader("X-Forwarded-For");
        final String xForwardedForClientIp = StringUtils.substringBefore(xForwardedFor, ", ");
        return StringUtils.defaultIfBlank(StringUtils.defaultIfBlank(xRealIp, xForwardedForClientIp), request.getRemoteAddr());
    }

}
