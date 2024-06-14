package com.shenll.shelogisticsadminservice.packageType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageTypeRepository  extends JpaRepository<PackageType, String>, JpaSpecificationExecutor<PackageType> {
    List<PackageType> findByStatus (String status);

    Optional<PackageType> findByPackageIdAndStatus (String id, String status);


    Optional<PackageType> findByPackageName(String packageName);
}
