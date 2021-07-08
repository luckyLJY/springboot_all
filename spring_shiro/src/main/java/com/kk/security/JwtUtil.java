package com.kk.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;

/**
 * JWT工具类
 *
 * @author liujingcheng
 * @version 1.0, 2020/3/31 14:02
 * @since 1.0.0
 */
public final class JwtUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);
    public static final String TOKEN_NAME = "Authorization";
    private static final String CLAIM_NAME = "userId";

    private JwtUtil() {
    }

    /**
     * 校验JWT字符串
     *
     * @param token  jwt token
     * @param secret 私钥
     * @param claim  用户标识
     * @return true -> 成功 ; false -> 失败
     */
    public static boolean verify(String token, String secret, String claim) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            JWTVerifier verifier = JWT.require(algorithm).withClaim(CLAIM_NAME, claim).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            LOGGER.debug("Verify jwt token {} failed.", token);
        }
        return false;
    }

    /**
     * 获取jwt关键信息，不用解码
     *
     * @param token jwt token
     * @return claim
     */
    public static Optional<String> getClaim(String token) {
        try {
            return Optional.of(JWT.decode(token).getClaim(CLAIM_NAME).asString());
        } catch (JWTDecodeException e) {
            LOGGER.warn("Can't decode jwt {}", token);
        }
        return Optional.empty();
    }

    /**
     * 签名jwt token串
     *
     * @param claim  用户标识
     * @param secret 私钥
     * @param expire 过期时间
     * @return token
     */
    public static String sign(String claim, String secret, Duration expire) {
        Algorithm algorithm = Algorithm.HMAC512(secret);
        Date expiredDate = new Date(System.currentTimeMillis() + expire.toMillis());
        return JWT.create().withClaim(CLAIM_NAME, claim).withExpiresAt(expiredDate).sign(algorithm);
    }

    /** 根据传入的secret和salt，生成新的secret，增加破解密码的难度 */
    public static String generateSecret(String secret, JwtSalt salt) {
        return String.format("%s-%s", secret, salt.getUuid());
    }
}
