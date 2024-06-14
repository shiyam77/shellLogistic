package com.shenll.shelogisticsadminservice.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CustomerDTO {


    @Schema(example = "Quotation/Concession")
    private String rateType;

    @Schema(example = "976348b3-a041-4078-93b1-3f3ed34808e8")
    private String agentCode;

    @Schema(example = "Cus001")
    private String code;

    @Schema(example = "customer")
    private String name;

    @Schema(example = "nehru street")
    private String address;

    @Schema(example = "madurai")
    private String confirmAddress;

    @Schema(example = "madurai")
    private String city;

    @Schema(example = "tamil nadu")
    private String state;

    @Schema(example = "600028")
    private String pincode;

    @Schema(example = "9087654321")
    private String mobile;

    @Schema(example = "customer@gmail.com")
    private String email;

    @Schema(example = "1.7")
    private String quotationRate;

    @Schema(example = "1.3")
    private String concessionRate;

    @Schema(example = "Active")
    private String status;

}
