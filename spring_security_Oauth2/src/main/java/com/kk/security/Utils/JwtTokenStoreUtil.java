package com.kk.security.Utils;/**
 * @author : K k
 * @date : 11:22 2020/11/10
 */

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @description: JWT令牌
 * @author: Kk
 * @create: 2020-11-10 11:22
 **/
public class JwtTokenStoreUtil {

    public String createTokenByClaims(){
        //创建JwtBuilder对象
        JwtBuilder builder = Jwts.builder()
                //声明标识 jti：token唯一标识
                .setId("8888")
                //面向主体 sub：admin
                .setSubject("admin")
                //创建日期
                .setIssuedAt(new Date())
                //加密算法 算法+secret私钥
                .signWith(SignatureAlgorithm.HS256,"xxxx")
                //自定义声明
                .claim("roles","admin")
                .claim("logo","xxx.jpg");
                //.setClaims(map)
        return builder.compact();
    }

    public void parseTokenByClaims(String token){
        //解析token获取负载中声明的对象
        Claims claims = Jwts.parser()
                //私钥
                .setSigningKey("xxxx")
                //令牌
                .parseClaimsJws(token)
                .getBody();
        System.out.println("id:"+claims.getId());
        System.out.println("subject:"+claims.getSubject());
        System.out.println("IssuedAt:"+claims.getIssuedAt());
        System.out.println("roles:"+claims.get("roles"));
        System.out.println("logo:"+claims.get("logo"));
    }
}
