package com.kcqnly.application.utils;

import com.kcqnly.application.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {
    /**
     * 获取已登录用户信息
     * @return User
     */
    public User getUser(){
        UserDetails userDetails= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user=new User();
        BeanUtils.copyProperties(userDetails, user);
        return user;
    }
}
