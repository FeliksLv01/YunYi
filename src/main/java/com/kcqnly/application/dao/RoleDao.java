package com.kcqnly.application.dao;

import com.kcqnly.application.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleDao extends JpaRepository<Role, Integer> {

    Role findByName(String name);
}
