package com.kcqnly.application.model;

import com.kcqnly.application.entity.Role;
import lombok.Data;

import java.util.List;

@Data
public class RoleParam {
    private Integer id;

    private String name;

    private String description;

    private List<PermissionTree> children;

    public RoleParam(Role role)
    {
        this.id=role.getId();
        this.name=role.getName();
        this.description=role.getDescription();
    }
}
