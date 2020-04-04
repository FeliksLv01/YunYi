package com.kcqnly.application;


import com.kcqnly.application.dao.RoleDao;
import com.kcqnly.application.dao.UserDao;
import com.kcqnly.application.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
class ApplicationTests {

    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;

    @Test
    void contextLoads() {
        User user=new User();
        user.setState(true);
        user.setRole(roleDao.findById(1).get());
        user.setPassword(new BCryptPasswordEncoder().encode("123456"));
        user.setEmail("1345678@qq.com");
        user.setMobile("13658943455");
        user.setCreateTime(new Date());
        userDao.save(user);
    }

}
