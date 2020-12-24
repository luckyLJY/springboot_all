package com.kk.validation.controller;

import com.kk.validation.entity.Task;
import com.kk.validation.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

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
    public Task taskJson(@RequestBody @Valid Task task) {
        return task;
    }

    @PostMapping("/form/tasks")
    @ApiOperation(value = "获取蛇形命名格式的json")
    public Map<String, Object> taskForm(@Valid Task task) {
        // 转蛇形
        return JsonUtil.toStringMap(task);
    }
}
