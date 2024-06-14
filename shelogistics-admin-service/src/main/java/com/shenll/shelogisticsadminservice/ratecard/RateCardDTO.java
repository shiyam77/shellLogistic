package com.shenll.shelogisticsadminservice.ratecard;

import lombok.Data;

@Data
public class RateCardDTO {

    private String agentId;
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
