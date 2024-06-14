package com.shenll.shelogisticsadminservice.route.routeDestination;

import lombok.Data;

@Data
public class RouteDestinationDTO {

    private String id;
    private String startDestination;
    private String endDestination;
    private String startDate;
    private String route;
    private String endDate;
    private String startTiming;
    private String endTiming;
    private String status;


}
