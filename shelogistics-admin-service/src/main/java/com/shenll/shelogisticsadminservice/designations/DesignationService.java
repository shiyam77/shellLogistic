package com.shenll.shelogisticsadminservice.designations;

import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;

public interface DesignationService {
    CommonResponse createDesignations(DesignationDTO designationDTO);

    CommonResponse findDesignations();

    CommonResponse findDesignationById(String id);

    CommonResponse deleteDesignationById(String id);
}
