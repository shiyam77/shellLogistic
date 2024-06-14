package com.shenll.shelogisticsadminservice.customer;

import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shelogistics")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("/createCustomer")
    public CommonResponse addCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.createCustomer(customerDTO);
    }
    @GetMapping("/getCustomer")
    public CommonResponse getCustomer() {
        return customerService.findAllCustomer();
    }

    @GetMapping("/getCustomer/{id}")
    public CommonResponse getCustomerById(@PathVariable String id) {
        return customerService.findByIdCustomer(id);
    }

    @PutMapping("updateCustomer/{id}")
    public CommonResponse update(@PathVariable String id, @RequestBody CustomerDTO customerDTO) {
        return customerService.updateCustomer(id, customerDTO);
    }

    @DeleteMapping("/deleteCustomer/{id}")
    public CommonResponse delete(@PathVariable String id) {
        return customerService.deleteCustomer(id);
    }

    @GetMapping("/get/customer/filters")
    public CommonResponse getCustomerWithFilters(@RequestParam(required = false) String agent,
                                                 @RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String email,
                                                 @RequestParam(required = false) String phone,
                                                 @RequestParam(required = false) Long page,
                                                 @RequestParam(required = false) Long size) {
        return customerService.findCustomerWithFilters(agent, name, email, phone, page, size);

    }
}
