package com.shenll.shelogisticsadminservice.packageType;

import com.shenll.shelogisticsadminservice.enums.Status;
import com.shenll.shelogisticsadminservice.exception.AppException;
import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import com.shenll.shelogisticsadminservice.vehicle.Vehicle;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PackageTypeImpl implements PackageTypeService {
    @Autowired
    PackageTypeRepository packageTypeRepository;

    @Override
    public CommonResponse createPackage(PackageTypeDTO packageTypeDTO) {
        try {
            PackageType newPackageType = new PackageType();
            newPackageType.setPackageName(packageTypeDTO.getPackageName());
            newPackageType.setStatus(Status.ACTIVE.name());
            packageTypeRepository.save(newPackageType);
            return CommonResponse.builder().code(200).message("Package created successfully").data(newPackageType.getPackageId()).build();
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CommonResponse getPackage() {
        try {
            List<PackageType> packageTypeList = packageTypeRepository.findByStatus(Status.ACTIVE.name());
            if (!packageTypeList.isEmpty()) {
                return CommonResponse.builder().code(200).message("Package retrieved successfully").data(packageTypeList).build();
            } else {
                return CommonResponse.builder().code(404).message("No package found").build();
            }
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CommonResponse getPackageById(String id) {
        try {
            PackageType packageTypeList = packageTypeRepository.findByPackageIdAndStatus(id, Status.ACTIVE.name())
                    .orElseThrow(() -> new AppException("Invalid PackageType Id: " + id, HttpStatus.BAD_REQUEST));
            return CommonResponse.builder().code(200).message("Package retrieved successfully").data(packageTypeList).build();

        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CommonResponse putPackage(PackageTypeDTO packageTypeDTO, String id) {
        try {
            Optional<PackageType> packageTypeList = packageTypeRepository.findByPackageIdAndStatus(id, Status.ACTIVE.name());
            if (packageTypeList.isPresent()) {
                PackageType packageType = packageTypeList.get();
                packageType.setPackageName(packageTypeDTO.getPackageName() == null ? packageType.getPackageName() : packageTypeDTO.getPackageName());
                packageType.setStatus(Status.UPDATED.name());
                return CommonResponse.builder().code(200).message("Package updated successfully").data(packageTypeList).build();
            } else {
                return CommonResponse.builder().code(404).message("No package found").build();
            }
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CommonResponse deletePackage(String id) {
        try {
            Optional<PackageType> packageTypeList = packageTypeRepository.findByPackageIdAndStatus(id, Status.ACTIVE.name());
            if (packageTypeList.isPresent()) {
                PackageType packageType = packageTypeList.get();
                packageType.setStatus(Status.INACTIVE.name());
                packageTypeRepository.save(packageType);
                return CommonResponse.builder().code(200).message("Package deleted successfully").build();
            } else {
                return CommonResponse.builder().code(404).message("No package found").build();
            }
        } catch (Exception e) {
            throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CommonResponse packageWithFilter(@RequestParam(required = false) String packageName) {
        Specification<PackageType> specification = (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (packageName != null && !packageName.isEmpty()) {
                predicates.add(builder.like(root.get("packageName"), "%" + packageName + "%"));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
        List<PackageType> customers = packageTypeRepository.findAll(specification);
        if (customers.isEmpty()) {
            return CommonResponse.builder().message("No packageType found matching the criteria").code(HttpStatus.NOT_FOUND.value()).build();
        } else {
            return CommonResponse.builder().data(customers).message("PackageType found").code(HttpStatus.OK.value()).build();
        }
    }
}
