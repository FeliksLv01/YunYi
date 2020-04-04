package com.kcqnly.application.dao;

import com.kcqnly.application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDao extends JpaRepository<User,Integer> , JpaSpecificationExecutor<User> {
    User findByUsername( String username );

    User save(User user);

}
