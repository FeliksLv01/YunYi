package com.kcqnly.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kcqnly.application.common.ConvertTree;
import com.kcqnly.application.common.TreeFid;
import com.kcqnly.application.common.TreeId;
import com.kcqnly.application.common.TreeNode;
import com.kcqnly.application.entity.Permission;
import com.kcqnly.application.utils.TreeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PermissionTree {
    @TreeId
    private Integer id;

    private String name;

    private Integer level;

    @TreeFid
    private Integer parentId;

    private String path;

    private List<PermissionTree> children=new ArrayList<>();

    @JsonIgnore
    public void setData(Permission permission)
    {
        this.id=permission.getId();
        this.name=permission.getName();
        this.level=permission.getLevel();
        this.parentId=permission.getParentId();
        this.path=permission.getPath();
    }

    public Permission getData()
    {
        return new Permission(id,name,level,parentId,path);
    }

    public PermissionTree(Permission permission)
    {
        this.id=permission.getId();
        this.name=permission.getName();
        this.level=permission.getLevel();
        this.parentId=permission.getParentId();
        this.path=permission.getPath();
    }


    /**
     * 获取子节点
     *
     * @param datas   数据集合
     * @param idName  id字段名
     * @param fidName fid字段名
     * @return 子节点集合
     */
    public List<PermissionTree> children(List<Permission> datas, String idName, String fidName) {
        String idValue = TreeUtil.getFieldValue(getData(), idName);
        List<Permission> collect = datas.stream()
                .filter(date -> idValue.equals(TreeUtil.getFieldValue(date, fidName)))
                .collect(Collectors.toList());
        datas.removeAll(collect);
        for (Permission node : collect) {
            PermissionTree treeNode = new PermissionTree(node);
            children.add(treeNode);
        }
        return children;
    }
}
