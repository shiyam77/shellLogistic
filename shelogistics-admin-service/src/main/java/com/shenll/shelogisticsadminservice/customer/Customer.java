package com.shenll.shelogisticsadminservice.customer;

import com.shenll.shelogisticsadminservice.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name = "customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String customerId;

    private String rateType;
    private String agentCode;
    private String code;
    private String name;
    private String address;
    private String confirmAddress;
    private String city;
    private String state;
    private String pincode;
    private String mobile;
    private String email;
    private String quotationRate;
    private String concessionRate;
    private String status;

}
