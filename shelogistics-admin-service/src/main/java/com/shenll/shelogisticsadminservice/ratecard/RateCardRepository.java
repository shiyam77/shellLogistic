package com.shenll.shelogisticsadminservice.ratecard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateCardRepository extends JpaRepository<RateCard,String> {

}
