package com.shenll.shelogisticsadminservice.vehicle;

import com.shenll.shelogisticsadminservice.agent.Agent;
import com.shenll.shelogisticsadminservice.agent.AgentRepository;
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
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    AgentRepository agentRepository;

    @Override
    public CommonResponse createNewVehicle(VehicleDTO vehicleDTO) {
        try {

            Agent agent = agentRepository.findById(vehicleDTO.getAgentId())
                    .orElseThrow(() -> new AppException("Agent not found for code: ", HttpStatus.BAD_REQUEST));

            Vehicle vehicle = new Vehicle().builder()
                    .vehicleType(vehicleDTO.getVehicleType())
                    .vehicleNumber(vehicleDTO.getVehicleNumber())
                    .agentId(agent)
                    .model(vehicleDTO.getModel())
                    .description(vehicleDTO.getDescription())
                    .build();
            vehicleRepository.save(vehicle);
            return CommonResponse.builder().code(200).data(vehicle.getId()).build();

        } catch (Exception ex) {
            ex.printStackTrace();
            return CommonResponse.builder().code(500).message("Internal server error").build();
        }
    }

    @Override
    public CommonResponse getAllVehicle(Long page, Long size) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page.intValue(), size == null ? Integer.MAX_VALUE : size.intValue(), Sort.Direction.DESC, "createdAt");
        Page<Vehicle> vehicles = vehicleRepository.findAll(pageable);
        if (vehicles.hasContent()) {
            return CommonResponse.builder()
                    .code(200)
                    .message("Successfully fetched Vehicle data")
                    .data(vehicles.getContent())
                    .totalCount(vehicles.getTotalElements())
                    .build();

        } else {
            return CommonResponse.builder()
                    .code(200)
                    .message("Vehicle data is empty")
                    .data(Collections.emptyList())
                    .totalCount(0L)
                    .build();
        }
    }

    @Override
    public CommonResponse getVehicleById(String id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new AppException("Invalid vehicle Id: " + id, HttpStatus.BAD_REQUEST));
        return CommonResponse.builder().code(200).message("success").data(vehicle).build();
    }

    @Override
    public CommonResponse updateVehicle(VehicleDTO vehicleDTO, String id) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
        if (optionalVehicle.isPresent()) {
            Vehicle vehicle = optionalVehicle.get();
            Agent agent = agentRepository.findById(vehicleDTO.getAgentId())
                    .orElseThrow(() -> new AppException("Agent not found for code: ", HttpStatus.BAD_REQUEST));

            vehicle.setVehicleType(vehicleDTO.getVehicleType() != null ? vehicleDTO.getVehicleType() : vehicle.getVehicleType());
            vehicle.setVehicleNumber(vehicleDTO.getVehicleNumber() != null ? vehicleDTO.getVehicleNumber() : vehicle.getVehicleNumber());
            vehicle.setAgentId(agent);
            vehicle.setModel(vehicleDTO.getModel() != null ? vehicleDTO.getModel() : vehicle.getModel());
            vehicle.setDescription(vehicleDTO.getDescription() != null ? vehicleDTO.getDescription() : vehicle.getDescription());
            vehicleRepository.save(vehicle);
            return CommonResponse.builder().code(200).message("Success").data(vehicle).build();

        } else {
            return CommonResponse.builder().code(400).message("Invalid ID").build();
        }
    }

    @Override
    public CommonResponse deleteVehicle(String id) {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(id);
        if (vehicleOptional.isPresent()) {
            vehicleRepository.deleteById(vehicleOptional.get().getId());
            return CommonResponse.builder().code(200).message("deleted successfully").data(vehicleOptional.get().getId()).build();
        } else {
            return CommonResponse.builder().code(400).message("Invalid Id").data(vehicleOptional).build();

        }
    }

    @Override
    public CommonResponse getFilterWithVehicle(String type, String model, String vehicleNo, Long page, Long size) {
        Specification<Vehicle> specification = ((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (type != null && !type.isEmpty()) {
                predicates.add(builder.like(root.get("vehicleType"), "%" + type + "%"));
            }
            if (model != null && !model.isEmpty()) {
                predicates.add(builder.like(root.get("model"), "%" + model + "%"));
            }
            if (vehicleNo != null && !vehicleNo.isEmpty()) {
                predicates.add(builder.like(root.get("vehicleNumber"), "%" + vehicleNo + "%"));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        });
        Pageable pageable = PageRequest.of(page == null ? 0 : page.intValue(), size == null ? Integer.MAX_VALUE : size.intValue(), Sort.Direction.DESC, "createdAt");
        Page<Vehicle> vehicles = vehicleRepository.findAll(specification,pageable);
        if (vehicles.hasContent()) {
            return CommonResponse.builder()
                    .code(200)
                    .message("Successfully fetched Vehicle data")
                    .data(vehicles.getContent())
                    .totalCount(vehicles.getTotalElements())
                    .build();

        } else {
            return CommonResponse.builder()
                    .code(200)
                    .message("Vehicle data is empty")
                    .data(Collections.emptyList())
                    .totalCount(0L)
                    .build();
        }
    }
}
