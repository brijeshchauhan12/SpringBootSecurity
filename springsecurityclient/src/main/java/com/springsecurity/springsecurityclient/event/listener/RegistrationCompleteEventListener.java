package com.springsecurity.springsecurityclient.event.listener;

import com.springsecurity.springsecurityclient.entity.User;
import com.springsecurity.springsecurityclient.event.RegistrationCompleteEvent;
import com.springsecurity.springsecurityclient.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements
        ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private UserService userService;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event){
       //Create the varification token for the user with the link
        User user =event.getUser();
        String token= UUID.randomUUID().toString();

        userService.saveVarificationTokenForUser(token,user);

        //Send mail to user

        String url= event.getApplicationUrl()+"/verifyRegistration?token="+token;

        log.info("Click the link to varify the account : {}",url);
    }
}
