package com.kcqnly.application.controller;

import com.kcqnly.application.common.Result;
import com.kcqnly.application.entity.Role;
import com.kcqnly.application.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PreAuthorize("hasAuthority('角色列表')")
    @GetMapping("/roles")
    public Result getRoleList() {
        List<Role> roleList =roleService.findAll();
        return Result.ok("成功",roleList);
    }
}
