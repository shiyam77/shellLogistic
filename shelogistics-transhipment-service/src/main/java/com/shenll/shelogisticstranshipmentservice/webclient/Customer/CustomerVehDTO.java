package com.shenll.shelogisticstranshipmentservice.webclient.Customer;

import lombok.Data;

@Data
public class CustomerVehDTO {
    private String rateType;
    private String agentCode;
    private String code;
    private String name;
    private String address;
    private String confirmAddress;
    private String city;
    private String state;
    private String pincode;
    private String mobile;
    private String email;
    private String quotationRate;
    private String concessionRate;
    private String status;
}
