package com.shenll.shelogisticsadminservice.route.routeDestination;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteDestinationRepository extends JpaRepository<RouteDestination,String> {
}
