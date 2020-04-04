package com.kcqnly.application.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    // 测试普通权限
    @PreAuthorize("hasAuthority('控制台')")
    @RequestMapping( value="/normal/test", method = RequestMethod.GET )
    public String test1() {
        return "101接口调用成功！";
    }

    // 测试管理员权限
    @PreAuthorize("hasAuthority('用户管理')")
    @RequestMapping( value = "/admin/test", method = RequestMethod.GET )
    public String test2() {
        return "102接口调用成功！";
    }
}
