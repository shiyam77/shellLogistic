package com.shenll.shelogisticsadminservice.packageType;


import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;

public interface PackageTypeService {
    CommonResponse createPackage(PackageTypeDTO packageTypeDTO);

    CommonResponse getPackage();

    CommonResponse getPackageById(String id);

    CommonResponse putPackage(PackageTypeDTO packageTypeDTO, String id);

    CommonResponse deletePackage(String id);

    CommonResponse packageWithFilter(String packageName);
}
