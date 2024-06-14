package com.shenll.shelogisticsadminservice.users;

import lombok.Data;

@Data
public class OAuthUser {

        private long createdTimestamp;
        private String username;
        private String firstName;
        private String lastName;
        private String email;
        private boolean enabled;

}
