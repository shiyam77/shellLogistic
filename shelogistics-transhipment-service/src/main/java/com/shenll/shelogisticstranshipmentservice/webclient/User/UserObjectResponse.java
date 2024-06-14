package com.shenll.shelogisticstranshipmentservice.webclient.User;

import lombok.Data;

import java.util.Optional;
@Data
public class UserObjectResponse {
    private Optional<UserVehDTO> data;
}
