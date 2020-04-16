package com.kcqnly.application.model;

import com.kcqnly.application.entity.User;
import lombok.Data;

@Data
public class UserInfo {
    private Integer id;

    private String username;

    private String mobile;

    private String email;

    public UserInfo(User user)
    {
        this.id=user.getId();
        this.username=user.getUsername();
        this.mobile=user.getMobile();
        this.email=user.getEmail();
    }

}
