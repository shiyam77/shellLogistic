package com.shenll.shelogisticstranshipmentservice.webclient.User;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class UserDesignation {
    private String designationId;
    private String designation;
    private  String status;
    private Timestamp createdOn;
    private Timestamp updatedOn;
}
