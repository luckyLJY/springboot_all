package com.kk.cache.controller;


import com.kk.cache.entity.CacheObject;
import com.kk.cache.service.CacheObjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import sun.misc.Cache;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kk
 * @since 2020-11-23
 */
@RestController
@RequestMapping("test")
@Api
public class CacheObjectController {

    @Autowired
    private CacheObjectService service;

    @GetMapping("getObject/{id}")
    public CacheObject getObject(@PathVariable("id") int id){
        return service.getById(id);
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") int id){
        service.deleteById(id);
        return "ok";
    }
}

