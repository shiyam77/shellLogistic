package com.shenll.shelogisticsadminservice.commission;

import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;

public interface CommissionService {
    CommonResponse createCommission(CommissionDTO commissionDTO);

    CommonResponse findAllCommissionData(Long page, Long size);

    CommonResponse findById(String id);

    CommonResponse deleteById(String id);
}
