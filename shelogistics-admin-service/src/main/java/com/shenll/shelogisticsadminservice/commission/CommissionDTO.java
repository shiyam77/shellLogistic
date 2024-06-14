package com.shenll.shelogisticsadminservice.commission;
import lombok.Data;

@Data
public class CommissionDTO {

    private String commissionId;
    private String agentId;
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
