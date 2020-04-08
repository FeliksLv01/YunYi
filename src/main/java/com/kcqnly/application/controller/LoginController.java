package com.kcqnly.application.controller;

import com.kcqnly.application.common.Result;
import com.kcqnly.application.config.VerificationCode;
import com.kcqnly.application.entity.Permission;
import com.kcqnly.application.model.MenuParam;
import com.kcqnly.application.service.PermissionService;
import com.kcqnly.application.service.RoleService;
import com.kcqnly.application.service.UserService;
import com.kcqnly.application.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        VerificationCode code = new VerificationCode();
        BufferedImage image = code.getImage();
        String text = code.getText();
        HttpSession session = request.getSession(true);
        session.setAttribute("verify_code", text);
        VerificationCode.output(image, resp.getOutputStream());
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
        return Result.ok("成功",menuParamList);
    }
}
