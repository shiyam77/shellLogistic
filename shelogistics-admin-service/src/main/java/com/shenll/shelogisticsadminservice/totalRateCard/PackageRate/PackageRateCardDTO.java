package com.shenll.shelogisticsadminservice.totalRateCard.PackageRate;

import lombok.Data;

@Data
public class PackageRateCardDTO {

    private String packageId;
    private String agentId;
    private String toDestination;
    private String rate;
    private String status;
}
