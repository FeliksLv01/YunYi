package com.kcqnly.application.utils;


import com.kcqnly.application.common.TreeFid;
import com.kcqnly.application.common.TreeId;
import com.kcqnly.application.entity.Permission;
import com.kcqnly.application.model.PermissionTree;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 形成权限树形结构的工具类
 */
public class TreeUtil {

    /**
     * 形成森林
     */
    public static List<PermissionTree> getForest(List<Permission> datas, String idName, String fidName) {
        List<PermissionTree> forest = new ArrayList<>();
        while (!datas.isEmpty()) {
            PermissionTree tree = getTree(datas, idName, fidName);
            forest.add(tree);
        }
        return forest;
    }

    /**
     * 形成森林(使用注解)
     */
    public static List<PermissionTree> getForest(List<Permission> datas) {
        //获取idname和fidName
        String idName = null;
        String fidName = null;
        if (!datas.isEmpty()) {
            //得到class
            Class cls = datas.get(0).getClass();
            //得到所有属性
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                TreeId treeId = field.getAnnotation(TreeId.class);
                if (treeId != null) {
                    idName = field.getName();
                }
                TreeFid treeFid = field.getAnnotation(TreeFid.class);
                if (treeFid != null) {
                    fidName = field.getName();
                }
            }
        }

        return getForest(datas,idName,fidName);
    }

    /**
     * 形成树
     */
    private static PermissionTree getTree(List<Permission> datas, String idName, String fidName) {
        //获取树根
        PermissionTree rootNode = getRootNode(datas, idName, fidName);
        //遍历树根的子集
        List<PermissionTree> childrenNode = rootNode.getChildren();
        forChildren(datas, idName, fidName, childrenNode);
        //此时树已经形成
        return rootNode;
    }

    /**
     * 递归遍历子节点
     */
    private static void forChildren(List<Permission> datas, String idName, String fidName, List<PermissionTree> childrenNode) {
        //需要遍历的集合
        List<PermissionTree> needForList = new ArrayList<>();
        for (PermissionTree tTreeNode : childrenNode) {
            List<PermissionTree> treeNodes = tTreeNode.children(datas, idName, fidName);
            needForList.addAll(treeNodes);
        }
        if (!needForList.isEmpty()) {
            forChildren(datas, idName, fidName, needForList);
        }
    }

    /**
     * 获取根节点
     *
     * @param datas
     * @param idName
     * @param fidName
     * @return
     */
    private static PermissionTree getRootNode(List<Permission> datas, String idName, String fidName) {
        if (datas.isEmpty()) {
            return null;
        }
        Permission node = datas.get(0);
        Permission rootNode = getRootNode(datas, idName, fidName, node);
        PermissionTree rootTreeNode = new PermissionTree();
        datas.remove(rootNode);
        rootTreeNode.setData(rootNode);
        rootTreeNode.children(datas, idName, fidName);
        return rootTreeNode;
    }

    /**
     * 递归获取根节点
     *
     * @param datas
     * @param idName
     * @param fidName
     * @param node
     * @return
     */
    private static Permission getRootNode(List<Permission> datas, String idName, String fidName, Permission node) {
        Permission fNode = null;
        String fieldValue = getFieldValue(node, fidName);
        for (Permission data : datas) {
            if (getFieldValue(data, idName).equals(fieldValue)) {
                fNode = data;
                break;
            }
        }
        if (fNode == null) {
            return node;
        } else {
            return getRootNode(datas, idName, fidName, fNode);
        }
    }


    /**
     * 获取字段值
     *
     * @param o         class
     * @param fieldName 字段名
     * @return String
     */
    public static String getFieldValue(Permission o, String fieldName) {
        //得到class
        Class cls = o.getClass();
        //得到所有属性
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            try {
                //打开私有访问
                field.setAccessible(true);
                //获取属性
                if (field.getName().equals(fieldName)) {
                    Object result = field.get(o);
                    if (result == null) {
                        return null;
                    }
                    return result.toString();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException("获取属性值失败");
    }
}
