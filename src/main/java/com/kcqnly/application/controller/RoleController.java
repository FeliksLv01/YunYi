package com.kcqnly.application.controller;

import com.kcqnly.application.common.ConvertTree;
import com.kcqnly.application.common.Result;
import com.kcqnly.application.common.TreeNode;
import com.kcqnly.application.entity.Permission;
import com.kcqnly.application.entity.Role;
import com.kcqnly.application.model.RoleParam;
import com.kcqnly.application.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    ConvertTree<Permission> permissionConvertTree;

    //给用户添加权限时使用的
    @PreAuthorize("hasAuthority('角色列表')")
    @GetMapping("/roles")
    public Result getRoleList() {
        List<Role> roleList =roleService.findAll();
        return Result.ok("成功",roleList);
    }

    //二级菜单角色列表使用的
    @PreAuthorize("hasAuthority('角色列表')")
    @GetMapping("/roles/tree")
    public Result getRoleTree()
    {
        List<Role> roleList=roleService.findAll();
        List<RoleParam> roleParams =new ArrayList<>();

        for (Role role:roleList
             ) {
            List<TreeNode<Permission>> res = permissionConvertTree.getForest(role.getPermissions());
            RoleParam roleParam =new RoleParam(role);
            roleParam.setChildren(res);
            roleParams.add(roleParam);
        }
        return Result.ok(roleParams);
    }
}
