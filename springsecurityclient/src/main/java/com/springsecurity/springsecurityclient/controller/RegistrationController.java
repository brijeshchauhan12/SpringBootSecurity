package com.springsecurity.springsecurityclient.controller;

import com.springsecurity.springsecurityclient.entity.User;
import com.springsecurity.springsecurityclient.event.RegistrationCompleteEvent;
import com.springsecurity.springsecurityclient.model.PasswordModel;
import com.springsecurity.springsecurityclient.model.UserModel;
import com.springsecurity.springsecurityclient.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
public class RegistrationController {
    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher publisher;
    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest request){
            User user=userService.registeruser(userModel);
            publisher.publishEvent(
                    new RegistrationCompleteEvent(
                            user,
                            applicationUrl(request))
            );
         return "success";
    }

    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token){
        String result = userService.validateVarificationToken(token);
        if(result.equalsIgnoreCase("valid")){
            return "User varified successfully";
        }
        else{
            return "Bad User";
        }
    }
    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel,HttpServletRequest request){
          User user=userService.findUserByEmail(passwordModel.getEmail());
          String url="";
          if(user!=null){
              String token= UUID.randomUUID().toString();
              userService.createPasswordResetTokenForUser(user,token);
              url=passwordResetTokenMail(user,applicationUrl(request),token);
          }

         return url;
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token,
                               @RequestBody PasswordModel passwordModel){
        String result=userService.validatePasswordResetToken(token);
        if(!result.equalsIgnoreCase("valid")){
            return "Invalid Token";
        }
        Optional<User> user=userService.getUserByPasswordResetToken(token);
        if(user.isPresent()){
           userService.changePassword(user.get(),passwordModel.getNewPassword());
            return "Password Reset Successful";
        }
        else {
            return "Invalid User";
        }
    }
    private String passwordResetTokenMail(User user, String applicationUrl, String token) {
        String url=applicationUrl
                +"/savePassword"
                +"?token="
                +token;
        log.info("Please click on the link below to reset your password");
        return url;
    }

    @GetMapping("/hello")
    public String hellow(){
        return "Hellow Brijesh ";
    }
    private String applicationUrl(HttpServletRequest request){
          log.info("the application url is : "+"http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath());
          return "http://"+
                  request.getServerName()+
                  ":"+
                  request.getServerPort()+

                  request.getContextPath();
    }
}
