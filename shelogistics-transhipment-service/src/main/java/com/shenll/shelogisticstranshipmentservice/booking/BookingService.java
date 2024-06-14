package com.shenll.shelogisticstranshipmentservice.booking;

import com.shenll.shelogisticstranshipmentservice.responseDTO.CommonResponse;
import com.shenll.shelogisticstranshipmentservice.booking.dto.BookingDTO;

public interface BookingService {
    CommonResponse createBooking(String userId, BookingDTO bookingDTO);
    CommonResponse findAllBooking();
    CommonResponse getBookingById(String id);
    CommonResponse updateBooking(BookingDTO bookingDTO, String id);
    CommonResponse deleteBooking(String id);
}
