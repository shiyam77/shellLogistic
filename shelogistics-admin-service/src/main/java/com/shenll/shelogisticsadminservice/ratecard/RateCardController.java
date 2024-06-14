package com.shenll.shelogisticsadminservice.ratecard;

import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shelogistics")
public class RateCardController {
    @Autowired
    RateCardService rateCardService;

    @PostMapping("/createRateCard")
    public CommonResponse createRateCard(RateCardDTO rateCardDTO) {
        return rateCardService.rateCard(rateCardDTO);
    }

    @GetMapping("/getRateCard")
    public CommonResponse getRateCard() {
        return rateCardService.getAllRateCard();
    }

    @GetMapping("/getRateCard/{id}")
    public CommonResponse getRateCardById(@PathVariable String id) {
        return rateCardService.findRateCard(id);
    }

    @DeleteMapping("/deleteRateCard/{id}")
    public CommonResponse delete(@PathVariable String id) {
        return rateCardService.deleteRateCard(id);
    }
}
