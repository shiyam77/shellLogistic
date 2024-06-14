package com.shenll.shelogisticsadminservice.commission;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CommissionRepository extends JpaRepository<Commission, String>, JpaSpecificationExecutor<Commission> {

    Page<Commission> findByStatus(String active, Pageable pageable);
}
