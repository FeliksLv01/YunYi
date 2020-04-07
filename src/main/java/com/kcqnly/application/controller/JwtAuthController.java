package com.kcqnly.application.controller;

import com.kcqnly.application.common.Result;
import com.kcqnly.application.entity.User;
import com.kcqnly.application.service.AuthService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
public class JwtAuthController {

    @Autowired
    private AuthService authService;
    // 登录
    @PostMapping( "/authentication/login")
    public Result createToken(@RequestBody  User user ) {
        Result result= authService.login(user.getUsername(), user.getPassword() );
        return result;
    }

    // 注册
    @PostMapping( "/authentication/register")
    public Result register( @RequestBody User addedUser ) {
        return authService.register(addedUser);
    }



}
