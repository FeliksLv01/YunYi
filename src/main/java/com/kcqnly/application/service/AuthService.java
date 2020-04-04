package com.kcqnly.application.service;


import com.kcqnly.application.common.Result;
import com.kcqnly.application.entity.User;

/**
 * @www.codesheep.cn
 * 20190312
 */
public interface AuthService {

    Result register(User userToAdd );
    Result login(String username, String password );
}
