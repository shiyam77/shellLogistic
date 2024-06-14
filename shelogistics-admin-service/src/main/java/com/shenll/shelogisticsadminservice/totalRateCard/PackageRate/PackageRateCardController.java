package com.shenll.shelogisticsadminservice.totalRateCard.PackageRate;

import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/pacakageRateCard")
@RestController
public class PackageRateCardController {
    @Autowired
    PackageRateCardService packageRateCardService;

    @PostMapping("/create/pacakage/rateCard")
    public CommonResponse createPackage(@RequestBody PackageRateCardDTO packageRateCardDTO) {
        return packageRateCardService.createPackageRateCard(packageRateCardDTO);
    }

    @GetMapping("/getAll/package")
    public CommonResponse getAllPackages() {
        return packageRateCardService.findPackagesRateCard();
    }
}
