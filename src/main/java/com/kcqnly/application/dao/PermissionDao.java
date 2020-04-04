package com.kcqnly.application.dao;

import com.kcqnly.application.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionDao extends JpaRepository<Permission,Integer> {
}
