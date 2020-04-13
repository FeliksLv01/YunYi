package com.kcqnly.application.common;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TreeNode<T> {
    private T data;
    private List<TreeNode<T>> childrenNode = new ArrayList<>();

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<TreeNode<T>> getChildrenNode() {
        return childrenNode;
    }

    public void setChildrenNode(List<TreeNode<T>> childrenNode) {
        this.childrenNode = childrenNode;
    }

    public TreeNode(T data, List<TreeNode<T>> childrenNode) {
        this.data = data;
        this.childrenNode = childrenNode;
    }

    public TreeNode() {
    }

    /**
     * 获取子节点
     *
     * @param datas   数据集合
     * @param idName  id字段名
     * @param fidName fid字段名
     * @return 子节点集合
     */
    public List<TreeNode<T>> childrenNode(List<T> datas, String idName, String fidName) {
        ConvertTree<T> convertTree = new ConvertTree<>();
        String idValue = convertTree.getFieldValue(data, idName);
        List<T> collect = datas.stream()
                .filter(date -> idValue.equals(convertTree.getFieldValue(date, fidName)))
                .collect(Collectors.toList());
        datas.removeAll(collect);
        for (T node : collect) {
            TreeNode<T> treeNode = new TreeNode<>();
            treeNode.setData(node);
            childrenNode.add(treeNode);
        }
        return childrenNode;
    }
}
