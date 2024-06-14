package com.shenll.shelogisticsapigateway.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequest {
    @Schema(example = "edith")
    private String userName;
    @Schema(example = "123")
    private String password;
}
