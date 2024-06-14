package com.shenll.shelogisticsadminservice.driver;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DriverDTO {

    @Schema(example = "976348b3-a041-4078-93b1-3f3ed34808e8")
    private String agent;

    @Schema(example = "Driver")
    private String name;

    @Schema(example = "TN-10-20235678901")
    private String drivingLicence;

    @Schema(example = "9087654321")
    private String phone;

    @Schema(example = "voc street")
    private String address;

    @Schema(example = "voc street")
    private String confirmAddress;

    @Schema(example = "madurai")
    private String city;

    @Schema(example = "tamil nadu")
    private String state;

    @Schema(example = "Active")
    private String status;
}
