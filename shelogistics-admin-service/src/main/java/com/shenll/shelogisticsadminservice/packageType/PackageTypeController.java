package com.shenll.shelogisticsadminservice.packageType;

import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/shelogistics")
@RestController
public class PackageTypeController {
    @Autowired
    PackageTypeService packageTypeService;

    @PostMapping("/addPackage")
    public CommonResponse addDriver(@RequestBody PackageTypeDTO packageTypeDTO) {
        return packageTypeService.createPackage(packageTypeDTO);
    }

    @GetMapping("/getPackage")
    public CommonResponse getPackage() {
        return packageTypeService.getPackage();
    }

    @GetMapping("/getPackage/{id}")
    public CommonResponse getPackageById(@PathVariable String id) {
        return packageTypeService.getPackageById(id);
    }

    @PutMapping("/updatePackage/{id}")
    public CommonResponse update(@RequestBody PackageTypeDTO packageTypeDTO, @PathVariable String id) {
        return packageTypeService.putPackage(packageTypeDTO, id);
    }

    @DeleteMapping("/deletePackage/{id}")
    public CommonResponse delete(@PathVariable String id) {
        return packageTypeService.deletePackage(id);
    }

    @GetMapping("/packageType/filter")
    public CommonResponse pacakgeFilter(@RequestParam(required = false) String packageName) {
        return packageTypeService.packageWithFilter(packageName);
    }
}
