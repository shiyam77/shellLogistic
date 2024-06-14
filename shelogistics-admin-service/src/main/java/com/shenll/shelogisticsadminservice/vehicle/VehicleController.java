package com.shenll.shelogisticsadminservice.vehicle;


import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shelogistics")
public class VehicleController {
    @Autowired
    VehicleService vehicleService;

    @PostMapping("/addVehicle")
    public CommonResponse addVehicle(@RequestBody VehicleDTO vehicleDTO) {
        return vehicleService.createNewVehicle(vehicleDTO);
    }

    @GetMapping("/getVehicle")
    public CommonResponse getVehicle(@RequestParam(required = false) Long page,
                                     @RequestParam(required = false) Long size) {
        return vehicleService.getAllVehicle(page, size);
    }

    @GetMapping("/getVehicle/{id}")
    public CommonResponse getVehicle(@PathVariable String id) {
        return vehicleService.getVehicleById(id);
    }

    @PutMapping("/updateVehicle/{id}")
    public CommonResponse update(@RequestBody VehicleDTO vehicleDTO, @PathVariable String id) {
        return vehicleService.updateVehicle(vehicleDTO, id);
    }

    @DeleteMapping("/deleteVehicle/{id}")
    public CommonResponse delete(@RequestParam String id) {
        return vehicleService.deleteVehicle(id);
    }

    @GetMapping("/filter/vehicle")
    public CommonResponse getFilterVehicle(@RequestParam(required = false) String type,
                                           @RequestParam(required = false) String model,
                                           @RequestParam(required = false) String vehicleNo,
                                           @RequestParam(required = false) Long page,
                                           @RequestParam(required = false) Long size) {

        return vehicleService.getFilterWithVehicle(type, model, vehicleNo, page, size);
    }
}
