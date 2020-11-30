package com.kk.learning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : K k
 * @date : 9:57 2020/11/3
 */
@Controller
public class IndexController {

    @RequestMapping("index")
    public String index(){
        return "index";
    }
}
