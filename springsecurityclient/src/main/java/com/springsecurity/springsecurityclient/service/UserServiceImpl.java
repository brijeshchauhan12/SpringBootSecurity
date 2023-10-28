package com.springsecurity.springsecurityclient.service;

import com.springsecurity.springsecurityclient.entity.User;
import com.springsecurity.springsecurityclient.entity.VarificationToken;
import com.springsecurity.springsecurityclient.model.UserModel;
import com.springsecurity.springsecurityclient.repository.UserRepository;
import com.springsecurity.springsecurityclient.repository.VarificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

     @Autowired
     private VarificationTokenRepository varificationTokenRepository;

    @Override
    public User registeruser(UserModel userModel) {

        User user=new User();
        user.setEmail(userModel.getEmail());
        user.setFirtName(userModel.getFirtName());
        user.setLastName(userModel.getLastName());
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        userRepository.save(user);
        return user;
    }


    @Override
    public void saveVarificationTokenForUser(String token, User user) {
        VarificationToken varificationToken=new VarificationToken(user,token);
        varificationTokenRepository.save(varificationToken);


    }
}
