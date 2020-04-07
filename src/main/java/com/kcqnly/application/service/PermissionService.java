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

   public List<Permission> getTwoLevelChild(int parentId)
   {
       List<Permission> children = permissionDao.getPermissionsByParentId(parentId);
       List<Permission> result = new ArrayList<>();
       for (Permission key:children
            ) {
           if(key.getLevel()==2)
           {
               result.add(key);
           }
       }
       return result;
   }
}
