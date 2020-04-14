package com.kcqnly.application.controller;

import com.kcqnly.application.common.ConvertTree;
import com.kcqnly.application.common.Result;
import com.kcqnly.application.common.TreeNode;
import com.kcqnly.application.entity.Permission;
import com.kcqnly.application.entity.Role;
import com.kcqnly.application.model.PermissionTree;
import com.kcqnly.application.model.RoleParam;
import com.kcqnly.application.service.RoleService;
import com.kcqnly.application.utils.TreeUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    //给用户添加权限时使用的
    @PreAuthorize("hasAuthority('角色列表')")
    @GetMapping("/roles")
    public Result getRoleList() {
        List<Role> roleList = roleService.findAll();
        return Result.ok("成功", roleList);
    }

    //二级菜单角色列表使用的
    @PreAuthorize("hasAuthority('角色列表')")
    @GetMapping("/roles/tree")
    public Result getRoleTree() {
        List<Role> roleList = roleService.findAll();
        List<RoleParam> roleParams = new ArrayList<>();

        for (Role role : roleList
        ) {
            List<PermissionTree> res = TreeUtil.getForest(role.getPermissions());
            RoleParam roleParam = new RoleParam(role);
            roleParam.setChildren(res);
            roleParams.add(roleParam);
        }
        return Result.ok(roleParams);
    }

    @PostMapping("roles/addRole")
    @PreAuthorize("hasAuthority('添加角色')")
    public Result addRole(@RequestBody Role role) {
        roleService.save(role);
        return Result.ok("添加成功", null);
    }

    @GetMapping("roles/{id}")
    @PreAuthorize("hasAuthority('角色列表')")
    public Result findById(@PathVariable("id") int id) {
        return Result.ok(roleService.findById(id));
    }


    @PutMapping("/roles/{id}")
    @PreAuthorize("hasAuthority('更新角色信息')")
    public Result editRole(@PathVariable("id") int id, @RequestBody Role role) {
        roleService.updateRole(id, role);
        return Result.ok(null);
    }

    @DeleteMapping("/roles/{id}")
    @PreAuthorize("hasAuthority('删除角色')")
    public Result deleteById(@PathVariable("id") int id) {
        roleService.deleteById(id);
        return Result.ok("删除成功", null);
    }


    @DeleteMapping("/roles/{id}/rights/{rightId}")
    @PreAuthorize("hasAuthority('更新角色权限')")
    public Result removeRightById(@PathVariable("id") int id, @PathVariable("rightId") int rightId) {
//        if (rightId == 101 || rightId == 106) {
//            return Result.error("不能删除基本权限");
//        }
        List<Permission> res = roleService.deletePermissionById(id, rightId);
        List<PermissionTree> permissionTrees = TreeUtil.getForest(res);
        return Result.ok(permissionTrees);
    }

    @PostMapping("/roles/rights")
    @PreAuthorize("hasAuthority('更新角色权限')")
    public Result updateRights(int id, String rids) {
        String[] str = rids.split(",");
        List<Permission> permissionList = new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
            Permission permission = new Permission(Integer.parseInt(str[i]));
            permissionList.add(permission);
        }
        roleService.updatePermission(id, permissionList);

        return Result.ok(null);
    }
}
