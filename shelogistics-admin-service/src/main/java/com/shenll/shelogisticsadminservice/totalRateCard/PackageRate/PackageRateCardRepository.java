package com.shenll.shelogisticsadminservice.totalRateCard.PackageRate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRateCardRepository extends JpaRepository<PackageRateCard,String> {
}
