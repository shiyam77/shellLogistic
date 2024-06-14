package com.shenll.shelogisticsadminservice.vehicle;


import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;

public interface VehicleService {
    CommonResponse createNewVehicle(VehicleDTO vehicleDTO);

    CommonResponse getAllVehicle(Long page, Long size);

    CommonResponse getVehicleById(String id);

    CommonResponse updateVehicle(VehicleDTO vehicleDTO, String id);

    CommonResponse deleteVehicle(String id);

    CommonResponse getFilterWithVehicle(String type, String model, String vehicleNo, Long page, Long size);
}
