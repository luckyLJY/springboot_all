package com.kk.shrio;

import com.kk.security.JwtSalt;
import com.kk.security.JwtUtil;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * 适配Shiro的Jwt Token封装类
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */
@Setter
@NoArgsConstructor
public class JwtToken implements AuthenticationToken {
    private String token;
    private JwtSalt jwtSalt;
    private AccessType accessType;

    public JwtToken(String token, JwtSalt jwtSalt, AccessType accessType) {
        this.token = token;
        this.jwtSalt = jwtSalt;
        this.accessType = accessType;
    }

    public JwtToken(String token, JwtSalt jwtSalt) {
        this.token = token;
        this.jwtSalt = jwtSalt;
        this.accessType = AccessType.AUTHC;
    }

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    public String generateSecret(String secret) {
        return JwtUtil.generateSecret(secret, jwtSalt);
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public JwtSalt getJwtSalt() {
        return jwtSalt;
    }
}
