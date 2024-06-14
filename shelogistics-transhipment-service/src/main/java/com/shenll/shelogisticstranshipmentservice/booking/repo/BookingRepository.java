package com.shenll.shelogisticstranshipmentservice.booking.repo;

import com.shenll.shelogisticstranshipmentservice.booking.domin.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
}
