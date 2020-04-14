package com.kcqnly.application;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.kcqnly.application.common.ConvertTree;
import com.kcqnly.application.common.TreeNode;
import com.kcqnly.application.dao.PermissionDao;
import com.kcqnly.application.dao.RoleDao;
import com.kcqnly.application.entity.Permission;
import com.kcqnly.application.entity.Role;
import com.kcqnly.application.model.PermissionTree;
import com.kcqnly.application.utils.TreeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ApplicationTests {

    @Autowired
    PermissionDao permissionDao;
    @Autowired
    RoleDao roleDao;

    @Test
    void contextLoads() {
        List<PermissionTree> res = TreeUtil.getForest(permissionDao.findAll());
        JSON object = JSONUtil.parse(res);
        System.out.println(object.toString());
    }

}
