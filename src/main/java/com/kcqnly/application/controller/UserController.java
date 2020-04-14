package com.kcqnly.application.controller;

import cn.hutool.core.util.StrUtil;
import com.kcqnly.application.common.Result;
import com.kcqnly.application.entity.Role;
import com.kcqnly.application.entity.User;
import com.kcqnly.application.model.UserList;
import com.kcqnly.application.model.UserParam;
import com.kcqnly.application.service.RoleService;
import com.kcqnly.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;


    @PreAuthorize("hasAuthority('用户列表')")
    @GetMapping("/users")
    public Result getUserList(String query, int pageNum, int pageSize) {

        if (StrUtil.isEmpty(query) || StrUtil.isBlank(query)) {
            List<UserParam> userParams = new ArrayList<>();
            List<User> userList = userService.findAll(pageNum - 1, pageSize);
            for (User user : userList) {
                userParams.add(new UserParam(user));
            }
            UserList res = new UserList(userService.getTotal(), pageNum, userParams);
            return Result.ok("获取用户列表成功", res);
        } else {
            List<UserParam> userParams = new ArrayList<>();
            List<User> userList = userService.findByUsernameLike(query);
            for (User user : userList) {
                userParams.add(new UserParam(user));
            }
            UserList res = new UserList(userParams.size(), pageNum, userParams);
            return Result.ok("查询成功", res);
        }


    }

    @PreAuthorize("hasAuthority('添加用户')")
    @PostMapping("/users")
    public Result addUser(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()) != null) {
            return Result.error("用户名已存在");
        }
        String rawpassword = user.getPassword();
        user.setState(true);
        user.setRole(roleService.findById(1));
        user.setPassword(new BCryptPasswordEncoder().encode(rawpassword));
        user.setCreateTime(new Date());
        userService.save(user);
        return Result.ok("添加成功", new UserParam(user));
    }

    @PreAuthorize("hasAuthority('设置用户状态')")
    @PutMapping("/users/{id}/state/{state}")
    public Result changeState(@PathVariable("id") int id, @PathVariable("state") boolean state) {
        UserParam userParam = new UserParam(userService.updateState(id, state));
        return Result.ok("更新成功", userParam);
    }

    /**
     * 编辑用户信息时，弹框内的用户数据
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('用户列表')")
    @GetMapping("/users/{id}")
    public Result search(@PathVariable("id") int id) {
        UserParam userParam = new UserParam(userService.findById(id));
        if (userParam != null) {
            return Result.ok("搜索成功", userParam);
        }
        return Result.error("用户id不存在");
    }

    @PreAuthorize("hasAuthority('删除用户')")
    @DeleteMapping("/users/{id}")
    public Result deleteUser(@PathVariable("id") int id) {
        userService.deleteById(id);
        return Result.ok("删除成功", null);
    }

    @PreAuthorize("hasAuthority('获取用户详情')")
    @PutMapping("/users/{id}")
    public Result changeInformation(@PathVariable("id") int id, @RequestBody UserParam req) {
        UserParam userParam = new UserParam(userService.updateInformation(id, req.getMobile(), req.getEmail()));
        return Result.ok("更新成功", userParam);
    }

    @PreAuthorize("hasAuthority('分配用户角色')")
    @PutMapping("/users/{id}/role")
    public Result changeRole(@PathVariable("id") int id, @RequestBody Role role) {
        UserParam userParam = new UserParam(userService.updateRole(id, role.getId()));
        return Result.ok("成功", userParam);
    }
}
