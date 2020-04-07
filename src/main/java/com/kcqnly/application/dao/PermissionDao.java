package com.kcqnly.application.dao;

import com.kcqnly.application.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionDao extends JpaRepository<Permission,Integer> {

    List<Permission> getPermissionsByParentId(Integer parentId);
}
