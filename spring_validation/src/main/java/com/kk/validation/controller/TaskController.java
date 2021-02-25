package com.kk.validation.controller;

import com.kk.validation.controller.request.StringRequest;
import com.kk.validation.entity.Task;
import com.kk.validation.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

/**
 * Validation练习接口
 *
 * @author luokexiong
 * @version 1.0 2020/12/24
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api")
@Api
public class TaskController {

    @PostMapping("/json/tasks")
    @ApiOperation(value = "获取驼峰命名格式的json")
    public Task taskJson(@RequestBody Task task) {
        System.out.println(task.getTestEnum().getMsg());
        return task;
    }

    @PostMapping("/form/tasks")
    @ApiOperation(value = "获取蛇形命名格式的json")
    public Map<String, Object> taskForm(@Valid Task task) {
        // 转蛇形
        System.out.println(task.getTestEnum().getMsg());
        return JsonUtil.toStringMap(task);
    }

    @GetMapping("/string")
    public String getString(@Valid StringRequest request) {
        return request.getLength() + ":" + request.getNotBlank();
    }
}
