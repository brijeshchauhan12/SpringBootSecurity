package com.springsecurity.springsecurityclient.service;

import com.springsecurity.springsecurityclient.entity.PasswordResetToken;
import com.springsecurity.springsecurityclient.entity.User;
import com.springsecurity.springsecurityclient.entity.VarificationToken;
import com.springsecurity.springsecurityclient.model.UserModel;
import com.springsecurity.springsecurityclient.repository.PasswordResetTokenRepository;
import com.springsecurity.springsecurityclient.repository.UserRepository;
import com.springsecurity.springsecurityclient.repository.VarificationTokenRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

     @Autowired
     private VarificationTokenRepository varificationTokenRepository;

     @Autowired
     private PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User registeruser(UserModel userModel) {

        User user=new User();
        user.setEmail(userModel.getEmail());
        user.setFirstName(userModel.getFirstName());
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

    @Override
    public String validateVarificationToken(String token) {
        VarificationToken varificationToken=varificationTokenRepository.findByToken(token);
        log.info("Fetching token   .....................");
        if(varificationToken==null){
            return "invalid";
        }
        User user =varificationToken.getUser();
        Calendar cal=Calendar.getInstance();

        if((varificationToken.getExpirationTime().getTime())-cal.getTime().getTime()<=0){
            varificationTokenRepository.delete(varificationToken);
            return "expired";
        }
        user.setEnabled(true);
        userRepository.save(user);

        return "valid";
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken passwordResetToken=new PasswordResetToken(user,token);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken=passwordResetTokenRepository.findByToken(token);
        log.info("Fetching token   .....................");
        if(passwordResetToken==null){
            return "invalid";
        }
        User user =passwordResetToken.getUser();
        Calendar cal=Calendar.getInstance();

        if((passwordResetToken.getExpirationTime().getTime())-cal.getTime().getTime()<=0){
            passwordResetTokenRepository.delete(passwordResetToken);
            return "expired";
        }
        return "valid";
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser());
    }

    @Override
    public void changePassword(User user, String newPassword) {
      user.setPassword( passwordEncoder.encode(newPassword));
      userRepository.save(user);
    }

    @Override
    public boolean checkIfValidPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword,user.getPassword());
    }
}
