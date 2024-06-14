package com.shenll.shelogisticsadminservice.ratecard;


import com.shenll.shelogisticsadminservice.agent.Agent;
import com.shenll.shelogisticsadminservice.agent.AgentRepository;
import com.shenll.shelogisticsadminservice.enums.Status;
import com.shenll.shelogisticsadminservice.exception.AppException;
import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class RateCardServiceImpl implements RateCardService {
    @Autowired
    RateCardRepository rateCardRepository;
    @Autowired
    AgentRepository agentRepository;

    @Override
    public CommonResponse rateCard(RateCardDTO rateCardDTO) {


        Optional<Agent> agent = agentRepository.findById(rateCardDTO.getAgentId());
        if (agent.isPresent()) {
            RateCard rateCard = new RateCard().builder()
                    .agentId(agent.get())
                    .Lrcharges(rateCardDTO.getLrcharges())
                    .tollFees(rateCardDTO.getTollFees())
                    .freightSurcharges(rateCardDTO.getFreightSurcharges())
                    .doorDeliveryCharges(rateCardDTO.getDoorDeliveryCharges())
                    .doorCollectionCharges(rateCardDTO.getDoorCollectionCharges())
                    .doorCollectionCommision(rateCardDTO.getDoorCollectionCommision())
                    .IECharges(rateCardDTO.getIECharges())
                    .minimumWeight(rateCardDTO.getMinimumWeight())
                    .minimumFreight(rateCardDTO.getMinimumFreight())
                    .minimumDistance(rateCardDTO.getMinimumDistance())
                    .distanceRate(rateCardDTO.getDistanceRate())
                    .LrEndNum(rateCardDTO.getLrEndNum())
                    .LrSerialNum(rateCardDTO.getLrSerialNum())
                    .LrStartNum(rateCardDTO.getLrStartNum())
                    .maximumDistance(rateCardDTO.getMaximumDistance())
                    .distanceRate(rateCardDTO.getDistanceRate())
                    .cgst(rateCardDTO.getCgst())
                    .sgst(rateCardDTO.getSgst())
                    .agentCommission(rateCardDTO.getAgentCommission())
                    .agentDiscountCommision(rateCardDTO.getAgentDiscountCommision())
                    .ieCommision(rateCardDTO.getIeCommision())
                    .otherChargesCommission(rateCardDTO.getOtherChargesCommission())
                    .icvCommisionCharge(rateCardDTO.getIcvCommisionCharge())
                    .ddCommision(rateCardDTO.getDdCommision())
                    .status(Status.ACTIVE.name())
                    .build();
            rateCardRepository.save(rateCard);
            return CommonResponse.builder().code(200).data(rateCard.getId()).build();
        } else {
            throw new AppException("Agent list is empty", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Override
    public CommonResponse getAllRateCard() {

        List<RateCard> list = rateCardRepository.findAll();
        if (!list.isEmpty()) {
            return CommonResponse.builder().code(200).message("success").data(list).build();
        } else {
            throw new AppException("the list is empty", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CommonResponse findRateCard(String id) {
        RateCard rateCard = rateCardRepository.findById(id)
                .orElseThrow(() -> new AppException("Invalid rateCard Id: " + id, HttpStatus.BAD_REQUEST));
        return CommonResponse.builder().code(200).message("success").data(rateCard).build();

    }

    @Override
    public CommonResponse deleteRateCard(String id) {
        Optional<RateCard> rateCardOptional = rateCardRepository.findById(id);
        if (rateCardOptional.isPresent()) {
            rateCardRepository.deleteById(rateCardOptional.get().getId());
            return CommonResponse.builder().code(200).message("success").data(rateCardOptional.get()).build();
        } else {
            throw new AppException("Invalid Id", HttpStatus.BAD_REQUEST);

        }
    }
}
