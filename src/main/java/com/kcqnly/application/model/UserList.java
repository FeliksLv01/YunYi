package com.kcqnly.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserList {
    private long total;
    private int pageNum;
    private List<UserParam> users;
}
