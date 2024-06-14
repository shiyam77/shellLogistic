package com.shenll.shelogisticstranshipmentservice.booking.repo;

import com.shenll.shelogisticstranshipmentservice.booking.domin.BookingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<BookingAddress, String> {
}
