package com.shenll.shelogisticsadminservice.route;

import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/route")
public class RouteController {

    @Autowired
    RouteService routeService;


    @PostMapping("/create/update/route")
    public CommonResponse route(@RequestBody RouteDTO routeDTO) {
        return routeService.createAndUpdateRoute(routeDTO);
    }

    @GetMapping("/get/route/{id}")
    public CommonResponse getRouteById(@PathVariable String id) {
        return routeService.findRouteById(id);
    }

    @GetMapping("/getAll/route")
    public CommonResponse getAllRoute() {
        return routeService.findAllRoute();
    }

    @DeleteMapping("delete/route/{id}")
    public CommonResponse removeRoute(@PathVariable String id) {
        return routeService.deleteRoute(id);
    }

    @GetMapping("/route/filter")
    public CommonResponse routeWithFilter(@RequestParam(required = false) String routeName,
                                          @RequestParam(required = false) String routeCode,
                                          @RequestParam(required = false) String fromDestination,
                                          @RequestParam(required = false) String toDestination) {
        return routeService.routeWithFilter(routeCode, routeName, fromDestination, toDestination);
    }

}
