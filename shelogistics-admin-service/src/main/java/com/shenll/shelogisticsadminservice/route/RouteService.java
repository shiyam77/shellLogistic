package com.shenll.shelogisticsadminservice.route;

import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;

public interface RouteService {
    CommonResponse createAndUpdateRoute(RouteDTO routeDTO);

    CommonResponse findRouteById(String id);

    CommonResponse findAllRoute();

    CommonResponse deleteRoute(String id);

    CommonResponse routeWithFilter(String routeCode, String routeName, String fromDestination, String toDestination);
}
