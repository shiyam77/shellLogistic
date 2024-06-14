package com.shenll.shelogisticsadminservice.route;

import com.shenll.shelogisticsadminservice.agent.Agent;
import com.shenll.shelogisticsadminservice.agent.AgentRepository;
import com.shenll.shelogisticsadminservice.exception.AppException;
import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import com.shenll.shelogisticsadminservice.vehicle.Vehicle;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    AgentRepository agentRepository;

    @Autowired
    RouteRepository routeRepository;

    @Override
    public CommonResponse createAndUpdateRoute(RouteDTO routeDTO) {
        if (routeDTO.getId() != null) {
            return updateRoute(routeDTO, routeDTO.getId());
        } else {
            return createRoute(routeDTO);
        }
    }

    private CommonResponse createRoute(RouteDTO routeDTO) {
        Agent from = agentRepository.findById(routeDTO.getFromDestination())
                .orElseThrow(() -> new AppException("from destination not found for code: " + routeDTO.getFromDestination(), HttpStatus.BAD_REQUEST));

        Agent to = agentRepository.findById(routeDTO.getToDestination())
                .orElseThrow(() -> new AppException("to destination not found for code: " + routeDTO.getToDestination(), HttpStatus.BAD_REQUEST));

        Route route = Route.builder()
                .routeCode(routeDTO.getRouteCode())
                .routeName(routeDTO.getRouteName())
                .routeType(routeDTO.getRouteType())
                .toDestination(to)
                .fromDestination(from)
                .status("Active")
                .build();

        routeRepository.save(route);
        return CommonResponse.builder()
                .data(route)
                .code(200)
                .message("Successfully saved")
                .build();
    }

    private CommonResponse updateRoute(RouteDTO routeDTO, String id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new AppException("Invalid Route Id", HttpStatus.BAD_REQUEST));

        Agent from = null;
        if (routeDTO.getFromDestination() != null) {
            from = agentRepository.findById(routeDTO.getFromDestination())
                    .orElseThrow(() -> new AppException("from destination not found for code: " + routeDTO.getFromDestination(), HttpStatus.BAD_REQUEST));
        } else {
            from = route.getFromDestination();
        }

        Agent to = null;

        if (routeDTO.getToDestination() != null) {
            to = agentRepository.findById(routeDTO.getToDestination())
                    .orElseThrow(() -> new AppException("to destination not found for code: " + routeDTO.getToDestination(), HttpStatus.BAD_REQUEST));
        } else {
            to = route.getToDestination();
        }
        route.setRouteCode(routeDTO.getRouteCode() != null ? routeDTO.getRouteCode() : route.getRouteCode());
        route.setRouteName(routeDTO.getRouteName() != null ? routeDTO.getRouteName() : route.getRouteName());
        route.setRouteType(routeDTO.getRouteType() != null ? routeDTO.getRouteType() : route.getRouteType());
        route.setToDestination(to);
        route.setFromDestination(from);
        route.setStatus(routeDTO.getStatus() != null ? routeDTO.getStatus() : route.getStatus());
        routeRepository.save(route);

        return CommonResponse.builder()
                .data(route)
                .code(200)
                .message("Successfully updated")
                .build();
    }


    @Override
    public CommonResponse findRouteById(String id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new AppException("Invalid route Id: " + id, HttpStatus.BAD_REQUEST));
        return CommonResponse.builder().data(route).code(200).build();
    }

    @Override
    public CommonResponse findAllRoute() {
        List<Route> routes = routeRepository.findAll();
        return CommonResponse.builder().data(routes).code(200).build();
    }

    @Override
    public CommonResponse deleteRoute(String id) {
        Optional<Route> optionalRoute = routeRepository.findById(id);
        if (optionalRoute.isPresent()) {
            routeRepository.deleteById(optionalRoute.get().getId());
            return CommonResponse.builder().code(200).message("successfully deleted").build();
        } else {
            return CommonResponse.builder().code(400).message("Invalid Id").build();

        }
    }

    @Override
    public CommonResponse routeWithFilter(String routeCode, String routeName, String fromDestination, String toDestination) {

        Specification<Route> specification = ((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (routeCode != null && !routeCode.isEmpty()) {
                predicates.add(builder.like(root.get("routeCode"), "%" + routeCode + "%"));
            }
            if (routeName != null && !routeName.isEmpty()) {
                predicates.add(builder.like(root.get("routeName"), "%" + routeName + "%"));
            }
            if (fromDestination != null && !fromDestination.isEmpty()) {
                predicates.add(builder.equal(root.get("fromDestination").get("id"), fromDestination));
            }
            if (toDestination != null && !toDestination.isEmpty()) {
                predicates.add(builder.equal(root.get("toDestination").get("id"), toDestination));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        });
        List<Route> customers = routeRepository.findAll(specification);
        if (customers.isEmpty()) {
            return CommonResponse.builder().message("No route found matching the criteria").code(HttpStatus.NOT_FOUND.value()).build();
        } else {
            return CommonResponse.builder().data(customers).message("route found").code(HttpStatus.OK.value()).build();
        }
    }
}
