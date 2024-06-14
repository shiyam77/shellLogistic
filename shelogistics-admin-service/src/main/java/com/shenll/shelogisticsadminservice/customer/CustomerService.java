package com.shenll.shelogisticsadminservice.customer;

import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;

public interface CustomerService {
    CommonResponse createCustomer(CustomerDTO customerDTO);

    CommonResponse findAllCustomer();

    CommonResponse findByIdCustomer(String id);

    CommonResponse updateCustomer(String id, CustomerDTO customerDTO);

    CommonResponse deleteCustomer(String id);

    CommonResponse findCustomerWithFilters(String agent, String name, String email, String phone, Long page, Long size);
}
