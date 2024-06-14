package com.shenll.shelogisticsadminservice.roles;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "db_roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String role;
    private String status;

    private String keycloakRoleUUID;

}
