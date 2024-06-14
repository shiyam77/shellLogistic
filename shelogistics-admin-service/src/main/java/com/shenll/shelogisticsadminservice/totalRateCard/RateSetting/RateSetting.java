package com.shenll.shelogisticsadminservice.totalRateCard.RateSetting;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "rate_setting")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RateSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String agentId;
    private Double Lrcharges;
    private Double doorDeliveryCharges;
    private Double IECharges;
    private Double minimumWeight;
    private Double minimumDistance;
    private Double minimumFreight;
    private Double maximumDistance;
    private Double agentCommission;
    private Double distanceRate;
    private String status;
    private Timestamp createdOn;
    private Timestamp updatedOn;


}
