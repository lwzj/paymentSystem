package com.lwj.paymentsystem.controller;

import com.lwj.paymentsystem.model.User;
import com.lwj.paymentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/test")
//@RestController
@Controller
public class TestController {
    @Autowired
    private UserService userService;

    @GetMapping("test")
    public String test(HttpServletRequest request) {
        User user = userService.findOne();
        request.setAttribute("user", user);
        return "index";
    }
}
