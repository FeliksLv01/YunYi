package com.kcqnly.application.controller;

import com.kcqnly.application.common.ConvertTree;
import com.kcqnly.application.common.Result;
import com.kcqnly.application.common.TreeNode;
import com.kcqnly.application.entity.Permission;
import com.kcqnly.application.model.PermissionTree;
import com.kcqnly.application.service.PermissionService;
import com.kcqnly.application.utils.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PermissionController {
    @Autowired
    private PermissionService permissionService;


    @GetMapping("/rights/list")
    @PreAuthorize("hasAuthority('权限列表')")
    public Result findALLPermission() {
        List<Permission> permissionList = permissionService.findAll();

        return Result.ok("成功", permissionList);
    }

    @GetMapping("/rights/tree")
    @PreAuthorize("hasAuthority('权限列表')")
    public Result findALLPermissionTree() {
        List<PermissionTree> res = TreeUtil.getForest(permissionService.findAll());

        return Result.ok("成功",res);
    }



}
