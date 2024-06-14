package com.shenll.shelogisticstranshipmentservice.util;

import lombok.Data;

@Data
public class TokenData {
    private String sub;
    private boolean email_verified;
    private String name;
    private String preferred_username;
    private String given_name;
    private String email;
}
