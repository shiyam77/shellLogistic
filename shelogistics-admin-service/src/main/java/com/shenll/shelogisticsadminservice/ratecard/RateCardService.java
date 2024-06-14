package com.shenll.shelogisticsadminservice.ratecard;

import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;

public interface RateCardService {
    CommonResponse rateCard(RateCardDTO rateCardDTO);

    CommonResponse getAllRateCard();

    CommonResponse findRateCard(String id);

    CommonResponse deleteRateCard(String id);
}
