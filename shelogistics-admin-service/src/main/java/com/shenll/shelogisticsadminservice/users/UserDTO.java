package com.shenll.shelogisticsadminservice.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserDTO {


    @Schema(example = "Tony")
    private String firstName;

    @Schema(example = "Stark")
    private String lastName;

    @Schema(example = "TonyStark")
    private String userName;

    @Schema(example = "97b02909-e72d-43f0-a918-70bfdeafe5e5")
    private String userRole;

    @Schema(example = "be043de0-39ad-40c1-8c93-757900c95c79")
    private String designation;

    @Schema(example = "976348b3-a041-4078-93b1-3f3ed34808e8")
    private String code;

    @Schema(example = " old agaram street")
    private String address;

    @Schema(example = "madurai")
    private String city;

    @Schema(example = "tamil nadu")
    private String state;

    @Schema(example = "625218")
    private String pincode;

    @Schema(example = "0452-9073333678")
    private String phone;

    @Schema(example = "9073333678")
    private String mobile;

    @Schema(example = "tony@gmail.com")
    private String email;

    @Schema(example = "123")
    private String password;

    @Schema(example = "123")
    private String confirmPassword;

    @Schema(example = "Active")
    private String status;

//
//    @Schema(example = "aeb79377-2f97-4610-90d5-d84a8b480b00")
//    private String keycloakId;
}
