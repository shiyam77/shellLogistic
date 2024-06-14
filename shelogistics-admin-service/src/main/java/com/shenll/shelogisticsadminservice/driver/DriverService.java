package com.shenll.shelogisticsadminservice.driver;


import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;

public interface DriverService {
    CommonResponse createDriver(DriverDTO driverDTO);

    CommonResponse findDriver();

    CommonResponse updateDriver(DriverDTO driverDTO, String id);

    CommonResponse findDriverById(String id);

    CommonResponse deleteDriver(String id);

    CommonResponse driverWithFilter(String name, String drivingLicenceNumber, String phone, Long page, Long size);
}
