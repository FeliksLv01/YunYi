package com.kcqnly.application.entity;

import com.kcqnly.application.common.TreeFid;
import com.kcqnly.application.common.TreeId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Permission implements Serializable {
    @Id
    @GeneratedValue
    @TreeId
    private Integer id;

    private String name;

    private Integer level;

    @TreeFid
    private Integer parentId;

    private String path;

    public Permission(int id)
    {
        this.id=id;
    }

}