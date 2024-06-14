package com.shenll.shelogisticsadminservice.unit;

import com.shenll.shelogisticsadminservice.enums.Status;
import com.shenll.shelogisticsadminservice.packageType.PackageType;
import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UnitServiceImpl implements UnitService {
    @Autowired
    UnitRepository unitRepository;

    @Override
    public CommonResponse addUnit(@RequestBody UnitDTO unitDTO) {
        Unit unit = new Unit().builder()
                .unitName(unitDTO.getUnitName())
                .status(Status.ACTIVE.name())
                .build();
        unitRepository.save(unit);
        return CommonResponse.builder().code(200).data(unit.getUnitId()).message("saved successfully").build();
    }

    @Override
    public CommonResponse findAllUnit() {
        List<Unit> unitList = unitRepository.findAll();
        return CommonResponse.builder().code(200).data(unitList).message("successfully viewed").build();
    }

    @Override
    public CommonResponse findUnitById(@RequestParam String id) {
        Optional<Unit> unitOptional = unitRepository.findById(id);
        if (unitOptional.isPresent()) {
            return CommonResponse.builder().code(200).data(unitOptional.get()).message("successfully viewed").build();
        } else {
            return CommonResponse.builder().code(400).message("Invalid id").build();

        }
    }

    @Override
    public CommonResponse deleteUnitById(@RequestParam String id) {
        Optional<Unit> unitOptional = unitRepository.findById(id);
        if (unitOptional.isPresent()) {
            unitOptional.get().setStatus("Inactive");
            unitRepository.save(unitOptional.get());
            return CommonResponse.builder().code(200).data(unitOptional.get()).message("deleted successfully").build();
        } else {
            return CommonResponse.builder().code(400).message("Invalid id").build();

        }
    }

    @Override
    public CommonResponse unitWithFilter(String unitName) {
        Specification<Unit> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (unitName != null && !unitName.isEmpty()) {
                predicates.add(builder.like(root.get("unitName"), "%" + unitName + "%"));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
        List<Unit> customers = unitRepository.findAll(specification);
        if (customers.isEmpty()) {
            return CommonResponse.builder().message("No unit found matching the criteria").code(HttpStatus.NOT_FOUND.value()).build();
        } else {
            return CommonResponse.builder().data(customers).message("unit found").code(HttpStatus.OK.value()).build();
        }

    }
}
