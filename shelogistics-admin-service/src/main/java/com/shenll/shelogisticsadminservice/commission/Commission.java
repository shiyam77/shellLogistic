package com.shenll.shelogisticsadminservice.commission;


import com.shenll.shelogisticsadminservice.agent.Agent;
import com.shenll.shelogisticsadminservice.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "commission")
@Builder
public class Commission extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String commissionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "agentId")
    private Agent agentId;

    private String bookingId;
    private String freightCommissionPercentage;
    private String freightCommission;
    private String doorChargeCommissionPercentage;
    private String doorChargeCommission;
    private String doorDeliveryCommissionPercentage;
    private String doorDeliverCommission;
    private String IECommissionPercentage;
    private String IECommission;
    private String otherChargeCommissionPercentage;
    private String otherChargeCommission;
    private String bookingPaymentType;
    private String status;
    private String paymentStatus;
    private String drs_id;

}
