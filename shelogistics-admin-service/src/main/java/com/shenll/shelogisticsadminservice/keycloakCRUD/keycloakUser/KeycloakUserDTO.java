package com.shenll.shelogisticsadminservice.keycloakCRUD.keycloakUser;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class KeycloakUserDTO {

    @Schema(example = "aeb79377-2f97-4610-90d5-d84a8b480b00")
    private String id;

    @Schema(example = "Tony")
    private String firstName;

    @Schema(example = "Stark")
    private String lastName;

    @Schema(example = "TonyStark")
    private String userName;

    @Schema(example = "Admin")
    private String userRole;

    @Schema(example = "Manager")
    private String designation;

    @Schema(example = "Mdu")
    @ManyToOne
    @JoinColumn(name = "agentId")
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

    @Schema(example = "")
    private Timestamp createdDate;

    @Schema(example = "")
    private Timestamp updatedDate;


}
