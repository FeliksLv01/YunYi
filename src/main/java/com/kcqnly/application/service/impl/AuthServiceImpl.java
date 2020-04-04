package com.kcqnly.application.service.impl;


import com.kcqnly.application.common.Result;
import com.kcqnly.application.dao.RoleDao;
import com.kcqnly.application.dao.UserDao;
import com.kcqnly.application.entity.User;
import com.kcqnly.application.model.UserParam;
import com.kcqnly.application.service.AuthService;
import com.kcqnly.application.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @www.codesheep.cn 20190312
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Qualifier("userService")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDao userRepository;
    @Autowired
    private RoleDao roleDao;

    // 登录
    @Override
    public Result login(String username, String password) {

        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null)
        {
            return new Result(Result.AJAX_ERROR,"用户名不存在",null);
        }
        else if (new BCryptPasswordEncoder().matches(password,userDetails.getPassword()))
        {
            String token = jwtTokenUtil.generateToken(userDetails);
            UserParam userParam=new UserParam((User) userDetails,token);
            return new Result(Result.AJAX_SUCCESS,"登陆成功",userParam);

        }
        return new Result(Result.AJAX_ERROR,"密码错误",null);
    }

    // 注册
    @Override
    public Result register(User userToAdd) {

        String username = userToAdd.getUsername();
        if (userRepository.findByUsername(username) != null) {
            return new Result(Result.AJAX_ERROR,"该用户名已存在",null);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = userToAdd.getPassword();
        userToAdd.setPassword(encoder.encode(rawPassword));
        userToAdd.setRole(roleDao.findById(1).get());
        userToAdd.setState(true);
        userToAdd.setCreateTime(new Date());
        UserParam param = new UserParam(userRepository.save(userToAdd));
        return new Result(Result.AJAX_SUCCESS,"注册成功",param);
    }
}
