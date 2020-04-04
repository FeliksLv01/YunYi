package com.kcqnly.application.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
public class Role implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String description;

    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    private List<Permission> permissions;
}