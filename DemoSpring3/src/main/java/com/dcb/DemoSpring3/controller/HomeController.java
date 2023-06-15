package com.dcb.DemoSpring3.controller;

import com.dcb.DemoSpring3.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String name() {
        return "Hello";
    }

    @GetMapping("/users")
    public User user() {
        User user = new User();
        user.setId(123);
        user.setName("V");
        user.setEmailId("@gmail.com");
        return user;
    }
}
