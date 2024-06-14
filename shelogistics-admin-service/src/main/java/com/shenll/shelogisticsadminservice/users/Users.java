package com.shenll.shelogisticsadminservice.users;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shenll.shelogisticsadminservice.agent.Agent;
import com.shenll.shelogisticsadminservice.audit.Auditable;
import com.shenll.shelogisticsadminservice.designations.Designation;
import com.shenll.shelogisticsadminservice.roles.Roles;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "db_users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String firstName;
    private String lastName;
    private String userName;


    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Roles userRole;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "designation_id")
    private Designation designation;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent code;

    private String address;
    private String city;
    private String state;
    private String pincode;
    private String phone;
    private String mobile;
    private String email;
    private String password;
    private String confirmPassword;
    private String status;
    private String keycloakId;

}
