package com.shenll.shelogisticsadminservice.driver;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shenll.shelogisticsadminservice.agent.Agent;
import com.shenll.shelogisticsadminservice.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "driver")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Driver extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent agent;
    private String name;
    private String drivingLicence;
    private String phone;
    private String address;
    private String confirmAddress;
    private String city;
    private String state;
    private String status;


}
