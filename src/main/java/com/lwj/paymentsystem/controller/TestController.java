package com.lwj.paymentsystem.controller;

import com.alibaba.fastjson.JSONObject;
import com.lwj.paymentsystem.model.User;
import com.lwj.paymentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        List<User> users = userService.findOne();
        request.setAttribute("users", users);
        return "index";
    }
    @RequestMapping("/login")
    public JSONObject login(HttpServletRequest request){
        JSONObject jsonObject = new JSONObject();
        String uid = request.getParameter("uid");
        String account = request.getParameter("account");
        String pass = request.getParameter("pass");
        System.out.println(uid+"="+account+"="+pass);
        jsonObject.put("user",userService.findOne().get(0));
        return jsonObject;
    }
}
