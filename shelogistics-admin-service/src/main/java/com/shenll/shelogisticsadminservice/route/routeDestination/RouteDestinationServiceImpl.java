package com.shenll.shelogisticsadminservice.route.routeDestination;

import com.shenll.shelogisticsadminservice.agent.Agent;
import com.shenll.shelogisticsadminservice.agent.AgentRepository;
import com.shenll.shelogisticsadminservice.exception.AppException;
import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import com.shenll.shelogisticsadminservice.route.Route;
import com.shenll.shelogisticsadminservice.route.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteDestinationServiceImpl implements RouteDestinationService {

    @Autowired
    RouteDestinationRepository routeDestinationRepository;

    @Autowired
    AgentRepository agentRepository;

    @Autowired
    RouteRepository routeRepository;


    @Override
    public CommonResponse createRouteDestination(RouteDestinationDTO routeDestinationDTO) {
        if (routeDestinationDTO.getId() != null) {
            return updateRouteDestination(routeDestinationDTO, routeDestinationDTO.getId());
        } else {
            return createNewRouteDestination(routeDestinationDTO);
        }
    }

    private CommonResponse createNewRouteDestination(RouteDestinationDTO routeDestinationDTO) {
        Agent start = agentRepository.findById(routeDestinationDTO.getStartDestination())
                .orElseThrow(() -> new AppException("Invalid Start destination ID ", HttpStatus.BAD_REQUEST));

        Agent end = agentRepository.findById(routeDestinationDTO.getEndDestination())
                .orElseThrow(() -> new AppException("Invalid End Destination ID: ", HttpStatus.BAD_REQUEST));

        Route route = routeRepository.findById(routeDestinationDTO.getRoute())
                .orElseThrow(() -> new AppException("Invalid Route ID: ", HttpStatus.BAD_REQUEST));

        RouteDestination routeDestination = RouteDestination.builder()
                .startDestination(start)
                .endDestination(end)
                .route(route)
                .startDate(routeDestinationDTO.getStartDate())
                .endDate(routeDestinationDTO.getEndDate())
                .startTiming(routeDestinationDTO.getStartTiming())
                .endTiming(routeDestinationDTO.getEndTiming())
                .status(routeDestinationDTO.getStatus())
                .build();

        routeDestinationRepository.save(routeDestination);

        return CommonResponse.builder()
                .data(routeDestination)
                .code(200)
                .message("Route destination created successfully")
                .build();
    }

    private CommonResponse updateRouteDestination(RouteDestinationDTO routeDestinationDTO, String id) {
        RouteDestination routeDestination = routeDestinationRepository.findById(id)
                .orElseThrow(() -> new AppException("Invalid RouteDestination  ID: ", HttpStatus.BAD_REQUEST));

        if (routeDestinationDTO.getStartDestination() != null) {
            Agent start = agentRepository.findById(routeDestinationDTO.getStartDestination())
                    .orElseThrow(() -> new AppException("Invalid Start destination ID ", HttpStatus.BAD_REQUEST));
            routeDestination.setStartDestination(start);
        }

        if (routeDestinationDTO.getEndDestination() != null) {
            Agent end = agentRepository.findById(routeDestinationDTO.getEndDestination())
                    .orElseThrow(() -> new AppException("Invalid End Destination ID", HttpStatus.BAD_REQUEST));
            routeDestination.setEndDestination(end);
        }

        if (routeDestinationDTO.getRoute() != null) {
            Route route = routeRepository.findById(routeDestinationDTO.getRoute())
                    .orElseThrow(() -> new AppException("Invalid Route ID: ", HttpStatus.BAD_REQUEST));
            routeDestination.setRoute(route);
        }
        routeDestination.setStartDate(routeDestinationDTO.getStartDate() != null ? routeDestinationDTO.getStartDate() : routeDestination.getStartDate());
        routeDestination.setEndDate(routeDestinationDTO.getEndDate() != null ? routeDestinationDTO.getEndDate() : routeDestination.getEndDate());
        routeDestination.setStartTiming(routeDestinationDTO.getStartTiming() != null ? routeDestinationDTO.getStartTiming() : routeDestination.getStartTiming());
        routeDestination.setEndTiming(routeDestinationDTO.getEndTiming() != null ? routeDestinationDTO.getEndTiming() : routeDestination.getEndTiming());
        routeDestination.setStatus(routeDestinationDTO.getStatus() != null ? routeDestinationDTO.getStatus() : routeDestination.getStatus());
        routeDestinationRepository.save(routeDestination);

        return CommonResponse.builder()
                .data(routeDestination)
                .code(200)
                .message("Route destination updated successfully")
                .build();
    }


    @Override
    public CommonResponse getAllRouteDestinations() {
        List<RouteDestination> routeDestinationList = routeDestinationRepository.findAll();
        return CommonResponse.builder().code(200).data(routeDestinationList).build();
    }

    @Override
    public CommonResponse getRouteDestinationById(String id) {
        RouteDestination routeDestination = routeDestinationRepository.findById(id)
                .orElseThrow(() -> new AppException("Invalid Id", HttpStatus.BAD_REQUEST));
        return CommonResponse.builder().data(routeDestination).code(200).build();
    }

    @Override
    public CommonResponse deleteRouteDestiantion(String id) {
        RouteDestination routeDestination = routeDestinationRepository.findById(id)
                .orElseThrow(() -> new AppException("Route destination not found for ID: " + id, HttpStatus.NOT_FOUND));
        routeDestinationRepository.deleteById(id);
        return CommonResponse.builder()
                .data("Route destination with ID " + id + " deleted successfully")
                .code(200)
                .message("Route destination deleted successfully")
                .build();
    }
}
