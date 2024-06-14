package com.shenll.shelogisticstranshipmentservice.booking;

import com.shenll.shelogisticstranshipmentservice.booking.dto.BookingDTO;
import com.shenll.shelogisticstranshipmentservice.responseDTO.CommonResponse;
import com.shenll.shelogisticstranshipmentservice.util.Helper;
import com.shenll.shelogisticstranshipmentservice.util.TokenData;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shelogistics")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @Autowired
    HttpServletRequest request;

    @PostMapping("/booking")
    public CommonResponse createBooking(@RequestBody BookingDTO bookingDTO) {
        TokenData tokenData = Helper.getTokenDetailsFromRequest(request);
        String userId = tokenData.getSub();
        return bookingService.createBooking(userId, bookingDTO);
    }

    @GetMapping("/booking")
    public CommonResponse getAllBooking() {
        TokenData tokenData = Helper.getTokenDetailsFromRequest(request);
        String userId = tokenData.getSub();
        return bookingService.findAllBooking();
    }

    @GetMapping("/booking/{id}")
    public CommonResponse findBookingById(@PathVariable String id) {
        return bookingService.getBookingById(id);
    }

    @PutMapping("/booking/{id}")
    public CommonResponse updateBooking(@RequestBody BookingDTO bookingDTO, @PathVariable String id) {
        return bookingService.updateBooking(bookingDTO, id);
    }
    @DeleteMapping("/booking/{id}")
    public CommonResponse deleteBooking(@PathVariable String id) {
        return bookingService.deleteBooking(id);
    }
}
