package com.shenll.shelogisticsadminservice.agent;

import com.shenll.shelogisticsadminservice.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "agent")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Agent extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String agentId;
    private String agentName;
    private String type;
    private String agentType;
    private String parentId;
    private String code;
    private String oldCode;
    private String permanentAddress;
    private String currentAddress;
    private String city;
    private String state;
    private String pincode;
    private String email;
    private String phone;
    private String mobile;
    private String status;
    private String bookingStatus;


}
