package com.shenll.shelogisticsadminservice.commission;

import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/commission")
@RestController
public class CommissionController {

    @Autowired
    CommissionService commissionService;

    @PostMapping()
    public CommonResponse createCommission(@RequestBody CommissionDTO commissionDTO){
      return  commissionService.createCommission(commissionDTO);
    }

    @GetMapping()
    public CommonResponse findCommissionData(@RequestParam(required = false) Long page,
                                             @RequestParam(required = false) Long size){
        return commissionService.findAllCommissionData(page, size);
    }

    @GetMapping("/{id}")
    public CommonResponse findCommissionById(@PathVariable String id){
        return commissionService.findById(id);
    }

    @DeleteMapping("/{id}")
    public CommonResponse deleteByCommissionId(@PathVariable String id){
        return commissionService.deleteById(id);
    }

}
