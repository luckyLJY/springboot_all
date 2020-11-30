package com.kk.security.controller;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @author : K k
 * @date : 11:15 2020/11/9
 */
@RestController
@RequestMapping("user")
public class UserController {

    /**
     * 获取当前用户
     * @param authentication
     * @return
     */
    @GetMapping("getCurrentUser")
    public Object getCurrentUser(Authentication authentication, HttpServletRequest request){
        String head = request.getHeader("Authorization");
        String token = head.substring(head.indexOf("bearer") + 7);

        return Jwts.parser()
                .setSigningKey("test_key".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }
}
