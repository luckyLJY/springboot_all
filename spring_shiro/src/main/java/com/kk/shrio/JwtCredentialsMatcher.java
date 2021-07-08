package com.kk.shrio;

import com.kk.entity.AuthUser;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * JWT Shrio密码校验
 *
 * @author liujingcheng
 * @version 1.0, 2020/4/7 11:30
 * @since 1.0.0
 */
public class JwtCredentialsMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo info) {
        // TODO What to do?
        final JwtToken jwtToken = (JwtToken) authenticationToken;
        if (jwtToken.getAccessType() == AccessType.DYNAMIC_ACCESS && null == jwtToken.getPrincipal()) {
            return false;
        }
        String token = (String) authenticationToken.getPrincipal();
        AuthUser authUser = (AuthUser) info.getPrincipals().getPrimaryPrincipal();
        return true;
    }
}
