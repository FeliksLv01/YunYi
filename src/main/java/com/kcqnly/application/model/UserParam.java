package com.kcqnly.application.model;

import com.kcqnly.application.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录时返回的用户
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserParam {
    private Integer id;

    private String username;

    private String mobile;

    private String email;

    private Boolean state;

    private String token;

    private String roleName;

    public UserParam (User user,String token)
    {
        id=user.getId();
        username=user.getUsername();
        mobile=user.getMobile();
        email=user.getEmail();
        this.token=token;
    }
    public UserParam (User user)
    {
        id=user.getId();
        username=user.getUsername();
        mobile=user.getMobile();
        email=user.getEmail();
        state = user.getState();
        roleName= user.getRole().getName();
    }

}
