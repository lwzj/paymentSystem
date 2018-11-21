package com.lwj.paymentsystem.service.impl;

import com.lwj.paymentsystem.model.User;
import com.lwj.paymentsystem.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Override
    public User findOne() {
        User user = User.builder()
                .address("shanxi")
                .username("lw")
                .build();
        return user;
    }
}
