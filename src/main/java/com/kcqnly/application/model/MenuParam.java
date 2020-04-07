package com.kcqnly.application.model;

import com.kcqnly.application.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuParam {
    private Integer id;

    private String name;


    private String path;

    private List<MenuParam> children;

    public MenuParam(Permission permission)
    {
        this.id =permission.getId();
        this.name=permission.getName();
        this.path=permission.getPath();
    }
}
