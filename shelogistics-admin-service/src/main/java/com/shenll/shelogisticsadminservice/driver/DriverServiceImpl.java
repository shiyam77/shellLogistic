package com.shenll.shelogisticsadminservice.driver;


import com.shenll.shelogisticsadminservice.agent.Agent;
import com.shenll.shelogisticsadminservice.agent.AgentRepository;
import com.shenll.shelogisticsadminservice.enums.Status;
import com.shenll.shelogisticsadminservice.exception.AppException;
import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import jakarta.persistence.criteria.Predicate;
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
public class DriverServiceImpl implements DriverService {
    @Autowired
    DriverRepository driverRepository;
    @Autowired
    AgentRepository agentRepository;

    @Override
    public CommonResponse createDriver(DriverDTO driverDTO) {
        try {
            Agent agent = agentRepository.findById(driverDTO.getAgent())
                    .orElseThrow(() -> new AppException("Agent not found for code: ", HttpStatus.BAD_REQUEST));

            Driver driver = new Driver().builder().
                    name(driverDTO.getName())
                    .city(driverDTO.getCity())
                    .address(driverDTO.getAddress())
                    .confirmAddress(driverDTO.getConfirmAddress())
                    .phone(driverDTO.getAddress())
                    .state(driverDTO.getState())
                    .agent(agent)
                    .status(Status.ACTIVE.name())
                    .drivingLicence(driverDTO.getDrivingLicence())
                    .build();
            driverRepository.save(driver);
            return CommonResponse.builder().code(200).data(driver.getId()).build();

        } catch (Exception e) {
            throw new AppException("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CommonResponse findDriver() {
        try {
            List<Driver> list = driverRepository.findAll();
            return CommonResponse.builder().code(200).message("Success").data(list).build();
        } catch (Exception e) {
            throw new AppException("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CommonResponse updateDriver(DriverDTO driverDTO, String id) {
        Optional<Driver> optionalDriver = driverRepository.findById(id);
        Agent agent = agentRepository.findById(driverDTO.getAgent())
                .orElseThrow(() -> new AppException("Agent not found for code: ", HttpStatus.BAD_REQUEST));

        if (optionalDriver.isPresent()) {
            Driver driver = optionalDriver.get();
            driver.setName(driverDTO.getName() != null ? driverDTO.getName() : driver.getName());
            driver.setCity(driverDTO.getCity() != null ? driverDTO.getCity() : driver.getCity());
            driver.setAddress(driverDTO.getAddress() != null ? driverDTO.getAddress() : driver.getAddress());
            driver.setConfirmAddress(driverDTO.getConfirmAddress() != null ? driverDTO.getConfirmAddress() : driver.getConfirmAddress());
            driver.setPhone(driverDTO.getPhone() != null ? driverDTO.getPhone() : driver.getPhone());
            driver.setState(driverDTO.getState() != null ? driverDTO.getState() : driver.getState());
            driver.setAgent(agent);
            driver.setDrivingLicence(driverDTO.getDrivingLicence() != null ? driverDTO.getDrivingLicence() : driver.getDrivingLicence());
            driverRepository.save(driver);
            return CommonResponse.builder().code(200).message("Success").data(driver).build();

        } else {
            return CommonResponse.builder().code(400).message("Invalid ID").build();
        }
    }

    @Override
    public CommonResponse findDriverById(String id) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new AppException("Invalid Driver Id: " + id, HttpStatus.BAD_REQUEST));
        return CommonResponse.builder().code(200).message("success").data(driver).build();

    }

    @Override
    public CommonResponse deleteDriver(String id) {
        Optional<Driver> driverOptional = driverRepository.findById(id);
        if (driverOptional.isPresent()) {
            driverRepository.deleteById(driverOptional.get().getId());
            return CommonResponse.builder().code(200).message("success").data(driverOptional.get()).build();
        } else {
            return CommonResponse.builder().code(200).message("Invalid Id").build();

        }
    }

    @Override
    public CommonResponse driverWithFilter(String name, String drivingLicenceNumber, String phone, Long page, Long size) {
        Specification<Driver> specification = ((root, query, builder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.isEmpty()) {
                predicates.add(builder.like(root.get("name"), "%" + name + "%"));
            }
            if (drivingLicenceNumber != null && !drivingLicenceNumber.isEmpty()) {
                predicates.add(builder.like(root.get("drivingLicence"), "%" + drivingLicenceNumber + "%"));
            }
            if (phone != null && !phone.isEmpty()) {
                predicates.add(builder.like(root.get("phone"), "%" + phone + "%"));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        });
        Pageable pageable = PageRequest.of(page == null ? 0 : page.intValue(), size == null ? Integer.MAX_VALUE : size.intValue(), Sort.Direction.DESC, "createdAt");
        Page<Driver> drivers = driverRepository.findAll(specification, pageable);
        if (drivers.hasContent()) {
            return CommonResponse.builder()
                    .code(200)
                    .message("Successfully fetched Driver data")
                    .data(drivers.getContent())
                    .totalCount(drivers.getTotalElements())
                    .build();
        } else {
            return CommonResponse.builder()
                    .code(200)
                    .message("Driver data is empty")
                    .data(Collections.emptyList())
                    .totalCount(0L)
                    .build();
        }
    }
}
