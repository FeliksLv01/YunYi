package com.kcqnly.application.entity;

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
    private Integer id;

    private String name;

    private String level;

    private Integer parentId;

    private String position;

}