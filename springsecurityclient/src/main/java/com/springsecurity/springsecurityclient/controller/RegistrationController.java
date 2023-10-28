package com.springsecurity.springsecurityclient.controller;

import com.springsecurity.springsecurityclient.entity.User;
import com.springsecurity.springsecurityclient.event.RegistrationCompleteEvent;
import com.springsecurity.springsecurityclient.model.UserModel;
import com.springsecurity.springsecurityclient.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
    @GetMapping("/hello")
    public String hellow(){
        return "Hellow Brijesh ";
    }
    private String applicationUrl(HttpServletRequest request){
          return "https://"+
                  request.getServerName()+
                  ":"+
                  request.getServerPort()+
                  request.getContextPath();
    }
}
