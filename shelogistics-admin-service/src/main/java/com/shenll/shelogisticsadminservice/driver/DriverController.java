package com.shenll.shelogisticsadminservice.driver;


import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/shelogistics")
@RestController
public class DriverController {

    @Autowired
    DriverService driverService;

    @PostMapping("/addDriver")
    public CommonResponse addDriver(@RequestBody DriverDTO driverDTO) {
        return driverService.createDriver(driverDTO);
    }

    @GetMapping("/getDriver")
    public CommonResponse getAllDriver() {
        return driverService.findDriver();
    }

    @GetMapping("/getDriver/{id}")
    public CommonResponse getAllDriver(@PathVariable String id) {
        return driverService.findDriverById(id);
    }

    @PutMapping("/updateDriver/{id}")
    public CommonResponse update(@RequestBody DriverDTO driverDTO, @PathVariable String id) {
        return driverService.updateDriver(driverDTO, id);
    }

    @DeleteMapping("/deleteDriver/{id}")
    public CommonResponse delete(@PathVariable String id) {
        return driverService.deleteDriver(id);
    }

    @GetMapping("/driver/filter")
    public CommonResponse driverFilter(@RequestParam(required = false) String name,
                                       @RequestParam(required = false) String drivingLicenceNumber,
                                       @RequestParam(required = false) String phone,
                                       @RequestParam(required = false) Long page,
                                       @RequestParam(required = false) Long size) {
        return driverService.driverWithFilter(name, drivingLicenceNumber, phone, page, size);
    }

}
