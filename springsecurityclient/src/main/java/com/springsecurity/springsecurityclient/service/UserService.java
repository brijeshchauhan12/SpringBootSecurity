package com.springsecurity.springsecurityclient.service;

import com.springsecurity.springsecurityclient.entity.User;
import com.springsecurity.springsecurityclient.model.UserModel;

public interface UserService {
    public User registeruser(UserModel userModel);

    void saveVarificationTokenForUser(String token, User user);
}
