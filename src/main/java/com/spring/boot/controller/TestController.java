package com.spring.boot.controller;

import com.spring.boot.util.model.Result;
import com.spring.boot.util.util.result.ResultUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yu Zhengmin
 */
@CrossOrigin
@RestController
public class TestController {

    /**
     * 测试是否可以连接
     *
     * @return String
     */
    @GetMapping("/test")
    public Result<String> test() {
        return ResultUtil.success("测试", "success test!");
    }

}
