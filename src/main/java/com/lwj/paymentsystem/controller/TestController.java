package com.lwj.paymentsystem.controller;

import com.lwj.paymentsystem.model.User;
import com.lwj.paymentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/test")
//@RestController
@Controller
public class TestController {
    @Autowired
    private UserService userService;

    @GetMapping("test")
    public String test(HttpServletRequest request) {
        List<User> users = userService.findOne();
        request.setAttribute("users", users);
        return "index";
    }
}
