package com.lwj.paymentsystem.service.impl;

import com.lwj.paymentsystem.dao.UserMapper;
import com.lwj.paymentsystem.model.User;
import com.lwj.paymentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public List<User> findOne() {
        return userMapper.findAll();
    }
}
