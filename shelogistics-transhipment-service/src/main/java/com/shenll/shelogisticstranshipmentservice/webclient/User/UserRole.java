package com.shenll.shelogisticstranshipmentservice.webclient.User;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class UserRole {
    private String id;
    private String role;
    private String status;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private String keycloakRoleUUID;
}
