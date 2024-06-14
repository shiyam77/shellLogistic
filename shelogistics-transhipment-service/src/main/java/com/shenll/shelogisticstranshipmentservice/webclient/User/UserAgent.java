package com.shenll.shelogisticstranshipmentservice.webclient.User;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class UserAgent {
    private String agentId;
    private String agentName;
    private String type;
    private String agentType;
    private String parentId;
    private String code;
    private String oldCode;
    private String permanentAddress;
    private String currentAddress;
    private String city;
    private String state;
    private String pincode;
    private String email;
    private String phone;
    private String mobile;
    private String status;
    private String bookingStatus;
    private Timestamp createdOn;
    private Timestamp updatedOn;
}
