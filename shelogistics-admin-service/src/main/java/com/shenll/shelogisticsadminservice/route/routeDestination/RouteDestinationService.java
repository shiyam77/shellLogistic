package com.shenll.shelogisticsadminservice.route.routeDestination;

import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;

public interface RouteDestinationService {
    CommonResponse createRouteDestination(RouteDestinationDTO routeDestinationDTO);

    CommonResponse getAllRouteDestinations();

    CommonResponse getRouteDestinationById(String id);

    CommonResponse deleteRouteDestiantion(String id);
}
