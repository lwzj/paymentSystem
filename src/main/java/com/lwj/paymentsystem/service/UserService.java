package com.lwj.paymentsystem.service;

import com.lwj.paymentsystem.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> findOne();
}
