package com.kcqnly.application.controller;

import cn.hutool.core.util.StrUtil;
import com.kcqnly.application.common.Result;
import com.kcqnly.application.entity.Permission;
import com.kcqnly.application.entity.Role;
import com.kcqnly.application.model.MenuParam;
import com.kcqnly.application.service.PermissionService;
import com.kcqnly.application.service.RoleService;
import com.kcqnly.application.service.UserService;
import com.kcqnly.application.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    // 测试普通权限
    @PreAuthorize("hasAuthority('控制台')")
    @RequestMapping(value = "/normal/test", method = RequestMethod.GET)
    public String test1() {
        return "101接口调用成功！";
    }

    // 测试管理员权限
    @PreAuthorize("hasAuthority('用户管理')")
    @RequestMapping(value = "/admin/test", method = RequestMethod.GET)
    public String test2() {
        return "102接口调用成功！";
    }

    @GetMapping("/menus")
    public Result getMenuList(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        String username = jwtTokenUtil.getUsernameFromToken(token);
        List<Permission> parent = roleService.getParentPermission(userService.getUserRole(username));
        List<MenuParam> menuParamList = new ArrayList<>();
        for (Permission permission : parent) {
            menuParamList.add(new MenuParam(permission));
        }
        for (MenuParam menuParam : menuParamList) {
            List<MenuParam> childList = new ArrayList<>();
            for (Permission permission : permissionService.getTwoLevelChild(menuParam.getId())) {
                childList.add(new MenuParam(permission));
            }
            menuParam.setChildren(childList);
        }
        return new Result(Result.AJAX_SUCCESS,"成功",menuParamList);
    }
}
