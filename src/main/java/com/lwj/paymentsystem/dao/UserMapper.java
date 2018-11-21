package com.lwj.paymentsystem.dao;

import com.lwj.paymentsystem.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<User> findAll();
}
