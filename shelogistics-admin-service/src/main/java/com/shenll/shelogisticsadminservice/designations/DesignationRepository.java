package com.shenll.shelogisticsadminservice.designations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DesignationRepository extends JpaRepository<Designation,String> {
}
