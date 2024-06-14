package com.shenll.shelogisticsadminservice.vehicle;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class VehicleDTO {

    @Schema(example = "Market")
    private String vehicleType;

    @Schema(example = "976348b3-a041-4078-93b1-3f3ed34808e8")
    private String agentId;

    @Schema(example = "TN59AZ1234")
    private String vehicleNumber;

    @Schema(example = "Tvs Sport")
    private String model;

    @Schema(example = "speed thrills")
    private String description;
}
