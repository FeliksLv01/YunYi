package com.kcqnly.application.entity;

import com.kcqnly.application.common.TreeFid;
import com.kcqnly.application.common.TreeId;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
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

}