package com.shenll.shelogisticstranshipmentservice.webclient.User;

import lombok.Data;

import java.util.Optional;
@Data
public class UserVehDTO {
    private String firstName;
    private String lastName;
    private String userName;
    private UserRole userRole;
    private UserDesignation designation;
    private UserAgent code;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String phone;
    private String mobile;
    private String email;
    private String password;
    private String confirmPassword;
    private String status;
}
