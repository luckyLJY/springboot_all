package com.kk.es.controller;

import com.kk.es.service.RestClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: Kk
 * @create: 2020-11-20 10:52
 **/
@RestController
public class EsController {
    @Autowired
    RestClientService restClientService;

    @RequestMapping("/parse/{keyword}")
    public boolean insertGoods(@PathVariable("keyword") String keyword) throws Exception {
        Boolean flag = restClientService.parseContent(keyword);
        return flag;
    }

    @RequestMapping("/searchTerm/{keyword}/{page}/{limit}")
    public List<Map<String,Object>> searchTerm(@PathVariable("keyword")String keyword,@PathVariable("page")int page,@PathVariable("limit")int limit) throws IOException {
        return restClientService.searchTermPage(keyword, page, limit);
    }
    @RequestMapping("/searchMatch/{keyword}/{page}/{limit}")
    public List<Map<String,Object>> searchMatch(@PathVariable("keyword")String keyword,@PathVariable("page")int page,@PathVariable("limit")int limit) throws IOException {
        return restClientService.searchMatchPage(keyword, page, limit);
    }
}
