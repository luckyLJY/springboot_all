package com.kk.es.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @description:
 * @author: Kk
 * @create: 2020-11-19 22:07
 **/
@RestController
public class IndexController {
    @RequestMapping("index")
    public ModelAndView index(){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("index");
        return mv;
    }
}
