package com.shenll.shelogisticsadminservice.unit;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UnitDTO {

    @Schema(example = "Bicycle/motorbike")
    private String unitName;

    @Schema(example = "Active")
    private String status;
}
