package com.lwj.paymentsystem.model;

import lombok.Builder;
import lombok.Data;
import org.apache.ibatis.annotations.ConstructorArgs;

/**
 * @author L.W
 */
@Data
@Builder
public class User {
    private String uid;
    private String username;
    private String password;
    private int sex;
    private String address;

}
