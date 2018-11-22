package com.lwj.paymentsystem.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author L.W
 */
@Data
@Builder
public class User {
    private Integer uid;
    private String username;
    private String password;
    private String sex;
    private String address;

}
