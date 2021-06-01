package com.kuang.controller;

import com.kuang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    @Autowired
    private UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void addUser() {
        userService.addUser();
    }
}
