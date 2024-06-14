package com.shenll.shelogisticsadminservice.totalRateCard.PackageRate;

import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;

public interface PackageRateCardService {

    CommonResponse createPackageRateCard(PackageRateCardDTO packageRateCardDTO);

    CommonResponse findPackagesRateCard();
}
