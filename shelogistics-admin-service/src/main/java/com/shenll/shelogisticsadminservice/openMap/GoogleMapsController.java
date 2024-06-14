package com.shenll.shelogisticsadminservice.openMap;

import com.google.maps.DirectionsApi;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.*;
import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/googleMap")
public class GoogleMapsController {
    @Value("${googlemap.secret}")
    private String apiKey;

    @GetMapping("/distance/calculation")
    public CommonResponse getLocationDistance(String origin, String destination) {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context);

        DistanceMatrix query = req.origins(origin)
                .destinations(destination)
                .mode(TravelMode.DRIVING)
                .avoid(DirectionsApi.RouteRestriction.TOLLS)
                .language("en-US")
                .awaitIgnoreError();

        if (query != null && query.rows.length > 0) {
            DistanceMatrixRow row = query.rows[0];
            DistanceMatrixElement element = row.elements[0];
            if (element.status == DistanceMatrixElementStatus.OK) {
                String distanceInMeters = element.distance.humanReadable;
                String status = element.status.name();
                String duration = element.duration.humanReadable;

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("Distance", distanceInMeters);
                hashMap.put("Duration", duration);
                hashMap.put("Status", status);

                return CommonResponse.builder()
                        .data(hashMap)
                        .code(200)
                        .build();
            }
        }
        return CommonResponse.builder()
                .data("Unable to fetch distance information.")
                .code(400)
                .build();
    }

}