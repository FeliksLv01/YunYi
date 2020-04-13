package com.kcqnly.application.service;

import com.kcqnly.application.dao.RoleDao;
import com.kcqnly.application.entity.Permission;
import com.kcqnly.application.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public List<Role> findAll() {
        return roleDao.findAll();
    }

    public List<Permission> findAllPermisiion(int id) {
        Role role = roleDao.findById(id).get();
        return role.getPermissions();
    }

    public List<Permission> getParentPermission(Role role) {
        List<Permission> permissionList = role.getPermissions();
        List<Permission> permissionList1 = new ArrayList<>();
        for (Permission permission : permissionList) {
            int level = permission.getLevel();
            if (level == 1) {
                permissionList1.add(permission);
            }
        }
        return permissionList1;
    }

    public Role findById(int id) {
        return roleDao.findById(id).get();
    }

}
