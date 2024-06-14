package com.shenll.shelogisticsadminservice.vehicle;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shenll.shelogisticsadminservice.agent.Agent;
import com.shenll.shelogisticsadminservice.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "vehicle")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vehicle extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String vehicleType;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent agentId;
    private String agentName;
    private String vehicleNumber;
    private String model;
    private String description;
}
