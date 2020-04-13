package com.kcqnly.application;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.kcqnly.application.common.ConvertTree;
import com.kcqnly.application.common.TreeNode;
import com.kcqnly.application.dao.PermissionDao;
import com.kcqnly.application.entity.Permission;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ApplicationTests {

    @Autowired
    PermissionDao permissionDao;
    @Autowired
    ConvertTree<Permission> permissionConvertTree;
    @Test
    void contextLoads() {
        List<TreeNode<Permission>> res = permissionConvertTree.getForest(permissionDao.findAll());
        JSON object = JSONUtil.parse(res);
        System.out.println(object.toString());
    }

}
