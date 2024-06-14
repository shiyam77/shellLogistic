package com.shenll.shelogisticstranshipmentservice.booking;

import com.shenll.shelogisticstranshipmentservice.booking.domin.Booking;
import com.shenll.shelogisticstranshipmentservice.booking.domin.BookingAddress;
import com.shenll.shelogisticstranshipmentservice.booking.dto.BookingDTO;
import com.shenll.shelogisticstranshipmentservice.booking.repo.BookingRepository;
import com.shenll.shelogisticstranshipmentservice.responseDTO.CommonResponse;
import com.shenll.shelogisticstranshipmentservice.webclient.User.UserServiceClient;
import com.shenll.shelogisticstranshipmentservice.webclient.User.UserVehDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    BookingMapper bookingMapper;
    @Autowired
    UserServiceClient userServiceClient;

    @Override
    public CommonResponse createBooking(String userId, BookingDTO bookingDTO) {
        Optional<UserVehDTO> userVehDTO = userServiceClient.getUserById(userId);
        System.out.println("userDetials:>>>>" + userVehDTO);
        System.out.println("userId:>>>>" + userId);
        if(userVehDTO.isPresent()) {
            UserVehDTO userDetaitls = userVehDTO.get();
            Booking booking = bookingMapper.addBooking(userId, userDetaitls, bookingDTO);
            bookingRepository.save(booking);
            List<BookingAddress> customers = bookingMapper.addCustomer(bookingDTO.getCustomer(), booking);
            booking.setCustomer(customers);
            bookingRepository.save(booking);
            return CommonResponse.builder().code(200).data(booking).build();
        }else {
            return CommonResponse.builder().code(400).message("Invalid user id").build();
        }
    }

    @Override
    public CommonResponse findAllBooking() {
        List<Booking> bookings = bookingRepository.findAll();
        if (bookings.isEmpty()) {
            return CommonResponse.builder()
                    .code(204)
                    .message("No active Booking found")
                    .build();
        } else {
            return CommonResponse.builder()
                    .code(200)
                    .message("Booking retrieved successfully")
                    .data(bookings)
                    .build();
        }
    }

    @Override
    public CommonResponse getBookingById(String id) {
        return null;
    }

    @Override
    public CommonResponse updateBooking(BookingDTO bookingDTO, String id) {
        return null;
    }

    @Override
    public CommonResponse deleteBooking(String id) {
        return null;
    }
}
