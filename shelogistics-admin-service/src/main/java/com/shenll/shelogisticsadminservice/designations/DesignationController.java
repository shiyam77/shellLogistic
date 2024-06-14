package com.shenll.shelogisticsadminservice.designations;

import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/shelogistics")
@RestController
public class DesignationController {

    @Autowired
    DesignationService designationService;

    @PostMapping("/create/designations")
    public CommonResponse createDesignation(@RequestBody DesignationDTO designationDTO) {
        return designationService.createDesignations(designationDTO);
    }

    @GetMapping("/get/designations")
    public CommonResponse getDesignations() {
        return designationService.findDesignations();
    }
    @GetMapping("/getDesignation/{id}")
    public CommonResponse getDesignationById(@PathVariable String id){
        return designationService.findDesignationById(id);
    }
    @DeleteMapping("/delete/designation/{id}")
    public CommonResponse deleteDesignation(@PathVariable String id)
    {
        return designationService.deleteDesignationById(id);
    }
}
