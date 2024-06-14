package com.shenll.shelogisticsadminservice.unit;

import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shelogistics/unit")
public class UnitController {
    @Autowired
    UnitService unitService;

    @PostMapping("/create/unit")
    public CommonResponse createUnit(@RequestBody UnitDTO unitDTO) {
        return unitService.addUnit(unitDTO);
    }

    @GetMapping("/get/unit")
    public CommonResponse getAllUnit() {
        return unitService.findAllUnit();
    }

    @GetMapping("/get/unit/{id}")
    public CommonResponse getUnitById(@PathVariable String id) {
        return unitService.findUnitById(id);
    }

    @DeleteMapping("/delete/unit/{id}")
    public CommonResponse deleteUnitById(@PathVariable String id) {
        return unitService.deleteUnitById(id);
    }

    @GetMapping("/unit/filter")
    public CommonResponse unitFilter(@RequestParam(required = false) String unitName) {
        return unitService.unitWithFilter(unitName);
    }
}
