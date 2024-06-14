package com.shenll.shelogisticsapigateway.responseDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AgentDTO {


    @Schema(example = "MDU")
    private String agentName;

    @Schema(example = "Agent")
    private String type;

    @Schema(example = "Local/mofussil")
    private String agentType;

    @Schema(example = "123")
    private String parentId;

    @Schema(example = "976348b3-a041-4078-93b1-3f3ed34808e8")
    private String code;

    @Schema(example = "mdu")
    private String oldCode;

    @Schema(example = "gandhi street")
    private String permanentAddress;

    @Schema(example = "gandhi street")
    private String currentAddress;

    @Schema(example = "madurai")
    private String city;

    @Schema(example = "tamil nadu")
    private String state;

    @Schema(example = "600028")
    private String pincode;

    @Schema(example = "agent@gmail.com")
    private String email;

    @Schema(example = "9087654321")
    private String phone;

    @Schema(example = "9123456789")
    private String mobile;

    @Schema(example = "Active")
    private String status;

    @Schema(example = "Active")
    private String bookingStatus;

}
