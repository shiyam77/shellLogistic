package com.shenll.shelogisticsadminservice.route.routeDestination;

import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/routeDestination")
public class RouteDestinationController {

    @Autowired
    RouteDestinationService routeDestinationService;

    @PostMapping("/create/update/routeDestination")
    public CommonResponse createRouteDestiny(@RequestBody RouteDestinationDTO routeDestinationDTO) {
        return routeDestinationService.createRouteDestination(routeDestinationDTO);
    }

    @GetMapping("/get/destination")
    public CommonResponse getAllDestinations() {
        return routeDestinationService.getAllRouteDestinations();
    }

    @GetMapping("/get/destination/{id}")
    public CommonResponse getDestinationById(@PathVariable String id) {
        return routeDestinationService.getRouteDestinationById(id);
    }

    @DeleteMapping("/delete/route/destination/{id}")
    public CommonResponse deleteDestination(@PathVariable String id) {
        return routeDestinationService.deleteRouteDestiantion(id);
    }
}
