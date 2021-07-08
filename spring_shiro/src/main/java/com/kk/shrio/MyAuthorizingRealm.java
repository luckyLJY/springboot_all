package com.kk.shrio;

import com.kk.entity.AuthUser;
import com.kk.entity.Role;
import com.kk.entity.User;
import com.kk.security.JwtUtil;
import com.kk.service.IRoleService;
import com.kk.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * MyAuthorizingRealm
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */
@Slf4j
@Component
public class MyAuthorizingRealm extends AuthorizingRealm {
    @Autowired
    @Lazy
    private IUserService userService;
    @Autowired
    @Lazy
    private IRoleService roleService;


    {
        this.setCredentialsMatcher(new JwtCredentialsMatcher());
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 授权：获取用户的角色、权限，给shiro做权限判断
     *
     * @param principalCollection principal
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        if (null == principalCollection) {
            throw new AuthorizationException("PrincipalCollection is null.");
        }
        AuthUser authUser = (AuthUser) getAvailablePrincipal(principalCollection);
        log.info("授权信息：{}", authUser);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(authUser.getRoles());
        return info;
    }

    /**
     * 认证：获取用户信息，给shiro做登录
     *
     * @param authenticationToken token
     * @return authentication info
     * @throws AuthenticationException exception
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        final JwtToken jwtToken = (JwtToken) authenticationToken;
        final String token = (String) jwtToken.getPrincipal();
        log.info("Token={}", token);
        // uuid必须传入
        final String uuid = jwtToken.getJwtSalt().getUuid();
        if (StringUtils.isBlank(uuid)) {
            throw new AuthenticationException("Invalid uuid");
        }
        // 校验claim
        final Optional<String> claimOptional = JwtUtil.getClaim(token);
        if (!claimOptional.isPresent()) {
            throw new AuthenticationException("Invalid token " + token);
        }
        final String claim = claimOptional.get();
        final Long userId = Long.parseLong(claim);
        final Optional<User> userOptional = userService.query(userId);
        if (!userOptional.isPresent()) {
            throw new AuthenticationException("user not exist or banned");
        }
        User user = userOptional.get();
        // 校验jwt
        if (!JwtUtil.verify(token, jwtToken.generateSecret(user.getSecret()), claim)) {
            throw new AuthenticationException("Token expire or unavailable");
        }
        List<Role> roles = roleService.queryByUserId(userId);
        AuthUser authUser = new AuthUser();
        authUser.setUserId(userId);
        authUser.setUsername(user.getUsername());
        authUser.setRoles(roles.stream().map(Role::getName).collect(Collectors.toSet()));
        return new SimpleAuthenticationInfo(authUser, new JwtToken(token), claim);
    }
}
