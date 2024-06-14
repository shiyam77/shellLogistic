package com.shenll.shelogisticsadminservice.commission;

import com.shenll.shelogisticsadminservice.agent.Agent;
import org.springframework.stereotype.Service;

@Service
public class CommissionMapper {
    public Commission addCommission(CommissionDTO commissionDTO, Agent agent) {
        return Commission.builder()
                .agentId(agent)
                .doorChargeCommission(commissionDTO.getDoorChargeCommission())
                .bookingId(commissionDTO.getBookingId())
                .freightCommissionPercentage(commissionDTO.getFreightCommissionPercentage())
                .freightCommission(commissionDTO.getFreightCommission())
                .doorDeliveryCommissionPercentage(commissionDTO.getDoorDeliveryCommissionPercentage())
                .doorDeliverCommission(commissionDTO.getDoorDeliverCommission())
                .doorChargeCommissionPercentage(commissionDTO.getDoorChargeCommissionPercentage())
                .doorChargeCommission(commissionDTO.getDoorChargeCommission())
                .IECommissionPercentage(commissionDTO.getIECommissionPercentage())
                .IECommission(commissionDTO.getIECommission())
                .otherChargeCommissionPercentage(commissionDTO.getOtherChargeCommissionPercentage())
                .otherChargeCommission(commissionDTO.getOtherChargeCommission())
                .bookingPaymentType(commissionDTO.getBookingPaymentType())
                .status(commissionDTO.getStatus())
                .paymentStatus(commissionDTO.getPaymentStatus())
                .drs_id(commissionDTO.getDrs_id())
                .build();
    }

    public Commission updateCommissionById(Commission commissionId, CommissionDTO commissionDTO, Agent updateAgentId) {
        commissionId.setAgentId(commissionDTO.getAgentId() != null ? updateAgentId : commissionId.getAgentId());
        commissionId.setDoorChargeCommission(commissionDTO.getDoorChargeCommission() != null ? commissionDTO.getDoorChargeCommission() : commissionId.getDoorChargeCommission());
        commissionId.setBookingId(commissionDTO.getBookingId() != null ? commissionDTO.getBookingId() : commissionId.getBookingId());
        commissionId.setFreightCommission(commissionDTO.getFreightCommission() != null ? commissionDTO.getFreightCommission() : commissionId.getFreightCommission());
        commissionId.setFreightCommissionPercentage(commissionDTO.getFreightCommissionPercentage() != null ? commissionDTO.getFreightCommissionPercentage() : commissionId.getFreightCommissionPercentage());
        commissionId.setDoorDeliverCommission(commissionDTO.getDoorDeliverCommission() != null ? commissionDTO.getDoorChargeCommission() : commissionId.getDoorChargeCommission());
        commissionId.setDoorDeliveryCommissionPercentage(commissionDTO.getDoorDeliveryCommissionPercentage() != null ? commissionDTO.getDoorDeliveryCommissionPercentage() : commissionId.getDoorChargeCommissionPercentage());
        commissionId.setIECommission(commissionDTO.getIECommission() != null ? commissionDTO.getIECommission() : commissionId.getIECommission());
        commissionId.setIECommissionPercentage(commissionDTO.getIECommissionPercentage() != null ? commissionDTO.getIECommissionPercentage() : commissionId.getIECommissionPercentage());
        commissionId.setOtherChargeCommission(commissionDTO.getOtherChargeCommission() != null ? commissionDTO.getOtherChargeCommission() : commissionId.getOtherChargeCommission());
        commissionId.setOtherChargeCommissionPercentage(commissionDTO.getOtherChargeCommissionPercentage() != null ? commissionDTO.getOtherChargeCommissionPercentage() : commissionId.getOtherChargeCommissionPercentage());
        commissionId.setBookingPaymentType(commissionDTO.getBookingPaymentType() != null ? commissionDTO.getBookingPaymentType() : commissionId.getBookingPaymentType());
        commissionId.setPaymentStatus(commissionDTO.getPaymentStatus() != null ? commissionDTO.getPaymentStatus() : commissionId.getPaymentStatus());
        commissionId.setStatus(commissionDTO.getStatus() != null ? commissionDTO.getStatus() : commissionId.getStatus());
        commissionId.setDrs_id(commissionDTO.getDrs_id() != null ? commissionDTO.getDrs_id() : commissionId.getDrs_id());
        return commissionId;
    }
}
