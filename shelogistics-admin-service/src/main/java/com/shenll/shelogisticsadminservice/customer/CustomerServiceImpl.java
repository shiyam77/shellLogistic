package com.shenll.shelogisticsadminservice.customer;

import com.shenll.shelogisticsadminservice.exception.AppException;
import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import jakarta.persistence.criteria.Predicate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    HttpServletRequest request;

    @Override
    public CommonResponse createCustomer(CustomerDTO customerDTO) {
        try {
            Customer customer = new Customer().builder().code(customerDTO.getCode())
                    .rateType(customerDTO.getRateType())
                    .agentCode(customerDTO.getAgentCode())
                    .name(customerDTO.getName())
                    .address(customerDTO.getAddress())
                    .confirmAddress(customerDTO.getConfirmAddress())
                    .city(customerDTO.getCity())
                    .state(customerDTO.getState())
                    .pincode(customerDTO.getPincode())
                    .mobile(customerDTO.getMobile())
                    .email(customerDTO.getEmail())
                    .concessionRate(customerDTO.getConcessionRate())
                    .quotationRate(customerDTO.getQuotationRate())
                    .status(customerDTO.getStatus())
                    .build();
            customerRepository.save(customer);
            return CommonResponse.builder().code(200).message("success").data(customer).build();
        } catch (Exception e) {
            System.out.println(e);
            throw new AppException("error occures", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CommonResponse findAllCustomer() {
        List<Customer> customerList = customerRepository.findAll();
        if (!customerList.isEmpty()) {
            System.out.println("inside the condtional loop");
            String s = request.getHeader("Authorization");
            System.out.println(s);
            return CommonResponse.builder().code(200).message("success").data(customerList).build();
        } else {
//            return CommonResponse.builder().code(200).message("success").data(customer.getCustomerId()).build();
            throw new AppException("error", HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    @Override
    public CommonResponse findByIdCustomer(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new AppException("Invalid Customer Id: " + id, HttpStatus.BAD_REQUEST));
        return CommonResponse.builder().code(200).message("success").data(customer).build();

    }

    @Override
    public CommonResponse updateCustomer(String id, CustomerDTO customerDTO) {
        return null;
    }

    @Override
    public CommonResponse deleteCustomer(String id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            customerRepository.deleteById(customerOptional.get().getCustomerId());
            return CommonResponse.builder().code(200).message("successfully deleted ").data(customerOptional.get().getCustomerId()).build();
        } else {
            throw new AppException("Invalid Id", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CommonResponse findCustomerWithFilters(String agent, String name, String email, String phone, Long page, Long size) {

        Specification<Customer> spec = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.isEmpty()) {
                predicates.add(builder.like(root.get("name"), "%" + name + "%"));
            }
            if (agent != null && !agent.isEmpty()) {
                predicates.add(builder.like(root.get("agentCode"), "%" + agent + "%"));
            }
            if (email != null && !email.isEmpty()) {
                predicates.add(builder.like(root.get("email"), "%" + email + "%"));
            }
            if (phone != null && !phone.isEmpty()) {
                predicates.add(builder.like(root.get("mobile"), "%" + phone + "%"));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
        Pageable pageable = PageRequest.of(page == null ? 0 : page.intValue(), size == null ? Integer.MAX_VALUE : size.intValue(), Sort.Direction.DESC, "createdAt");
        Page<Customer> customers = customerRepository.findAll(spec, pageable);
        if (customers.hasContent()) {
            return CommonResponse.builder()
                    .code(200)
                    .message("Successfully fetched Customer data")
                    .data(customers.getContent())
                    .totalCount(customers.getTotalElements())
                    .build();
        } else {
            return CommonResponse.builder()
                    .code(HttpStatus.OK.value())
                    .message("Customers data is empty")
                    .data(Collections.emptyList())
                    .build();
        }

    }
}
