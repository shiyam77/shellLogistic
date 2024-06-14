package com.shenll.shelogisticsadminservice.route;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RouteDTO {

    @Schema(example = "976348b3-a041-4078-93b1-3f3ed34808e8")
    private String id;

    @Schema(example = "Regular")
    private String routeType;

    @Schema(example = "coimbatore - madurai")
    private String routeName;

    @Schema(example = "R001")
    private String routeCode;

    @Schema(example = "976348b3-a041-4078-93b1-3f3ed34808e8")
    private String fromDestination;

    @Schema(example = "976348b3-a041-4078-93b1-3f3ed34808e8")
    private String toDestination;

    @Schema(example = "Active")
    private String status;
}
