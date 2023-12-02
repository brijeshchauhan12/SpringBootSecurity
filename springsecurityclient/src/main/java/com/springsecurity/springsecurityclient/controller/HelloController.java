package com.springsecurity.springsecurityclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/api/hello")
    public String Hello(){
        return "Hi From Brijesh Kumar Chauhan";
    }
}
