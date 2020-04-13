package com.kcqnly.application.service;

import com.kcqnly.application.dao.PermissionDao;
import com.kcqnly.application.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    public List<Permission> getTwoLevelChild(int parentId) {
        List<Permission> children = permissionDao.getPermissionsByParentId(parentId);
        List<Permission> result = new ArrayList<>();
        for (Permission key : children
        ) {
            if (key.getLevel() == 2) {
                result.add(key);
            }
        }
        return result;
    }

    public List<Permission> findAll() {
        return permissionDao.findAll();
    }

//    public PermissionTree findByParentId(int id) {
//        //一级权限
//        PermissionTree parentNode = new PermissionTree(permissionDao.findById(id).get());
//        //二级权限
//        List<PermissionTree> children = new ArrayList<>();
//        List<Permission> level2 = getTwoLevelChild(id);
//        for (int i = 0; i < level2.size(); i++) {
//            children.add(new PermissionTree(level2.get(i)));
//        }
//        //添加三级权限
//        for (PermissionTree childTree : children) {
//            List<Permission> level3 = permissionDao.getPermissionsByParentId(childTree.getId());
//            List<PermissionTree> level3Tree = new ArrayList<>();
//            for (int i = 0; i < level3.size(); i++) {
//                level3Tree.add(new PermissionTree(level3.get(i)));
//            }
//            childTree.setChildren(level3Tree);
//        }
//        parentNode.setChildren(children);
//        return parentNode;
//    }
}
