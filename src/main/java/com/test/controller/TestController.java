package com.test.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: create by wangmh
 * @name: TestController.java
 * @description:
 * @date:2020/4/8
 **/
@RestController
@RequestMapping("/wmh/book")
public class TestController {


    @GetMapping("list")
    @RequiresPermissions("wmh:book:list")
    public Object list(){
        return "wmh:book:list success";
    }
}
