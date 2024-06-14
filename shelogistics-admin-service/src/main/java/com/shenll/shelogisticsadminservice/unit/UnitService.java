package com.shenll.shelogisticsadminservice.unit;

import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;

public interface UnitService {

    CommonResponse addUnit(UnitDTO unitDTO);

    CommonResponse findAllUnit();

    CommonResponse findUnitById(String id);

    CommonResponse deleteUnitById(String id);

    CommonResponse unitWithFilter(String unitName);
}
