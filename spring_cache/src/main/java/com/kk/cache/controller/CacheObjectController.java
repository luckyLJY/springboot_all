package com.kk.cache.controller;

import com.kk.cache.controller.request.SetRequest;
import com.kk.cache.entity.CacheObject;
import com.kk.cache.service.CacheObjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

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

    @GetMapping("set1")
    public Set<Long> setTest(Set<Long> ids) {
        System.out.println(ids);
        return ids;
    }

    @GetMapping("getSet")
    public Set<Long> putGet(SetRequest request) {
        System.out.println(request.getIds());
        return request.getIds();
    }

    @PostMapping("postSet")
    public Set<Long> postSet(SetRequest request) {
        System.out.println(request.getIds());
        return request.getIds();
    }

    @PutMapping("putSet")
    public Set<Long> putSet(SetRequest request) {
        System.out.println(request.getIds());
        return request.getIds();
    }

    @DeleteMapping("deleteSet")
    public Set<Long> putDelete(SetRequest request) {
        System.out.println(request.getIds());
        return request.getIds();
    }
}

