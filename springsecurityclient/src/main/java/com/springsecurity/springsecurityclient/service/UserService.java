package com.springsecurity.springsecurityclient.service;

import com.springsecurity.springsecurityclient.entity.User;
import com.springsecurity.springsecurityclient.model.UserModel;

import java.util.Optional;

public interface UserService {
     User findUserByEmail(String email);


    public User registeruser(UserModel userModel);

    void saveVarificationTokenForUser(String token, User user);

    String validateVarificationToken(String token);

    void createPasswordResetTokenForUser(User user, String token);

    String validatePasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    void changePassword(User user, String newPassword);

    boolean checkIfValidPassword(User user, String oldPassword);
}
