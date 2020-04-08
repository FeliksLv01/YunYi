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
        userDao.updateState(10,false);
    }

}
