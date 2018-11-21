package com.lwj.paymentsystem.service;

import com.lwj.paymentsystem.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User findOne();
}
