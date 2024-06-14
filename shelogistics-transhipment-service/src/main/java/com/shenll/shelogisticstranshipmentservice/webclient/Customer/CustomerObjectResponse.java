package com.shenll.shelogisticstranshipmentservice.webclient.Customer;

import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class CustomerObjectResponse {
    private Optional<CustomerVehDTO> data;
}
