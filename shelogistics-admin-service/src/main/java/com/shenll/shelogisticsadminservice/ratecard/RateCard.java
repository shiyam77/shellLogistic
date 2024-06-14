package com.shenll.shelogisticsadminservice.ratecard;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shenll.shelogisticsadminservice.agent.Agent;
import com.shenll.shelogisticsadminservice.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;


@Entity
@Table(name = "rateCard")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RateCard extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent agentId;

    private Double Lrcharges;
    private Double tollFees;
    private Double freightSurcharges;
    private Double doorDeliveryCharges;
    private Double doorCollectionCharges;
    private Double IECharges;
    private Double minimumWeight;
    private Double minimumDistance;
    private Double minimumFreight;
    private String LrSerialNum;
    private String LrStartNum;
    private String LrEndNum;
    private Double maximumDistance;
    private Double distanceRate;
    private Double cgst;
    private Double sgst;
    private Double agentCommission;
    private Double agentDiscountCommision;
    private Double doorCollectionCommision;
    private Double ieCommision;
    private Double otherChargesCommission;
    private Double icvCommisionCharge;
    private Double ddCommision;
    private String status;

}
