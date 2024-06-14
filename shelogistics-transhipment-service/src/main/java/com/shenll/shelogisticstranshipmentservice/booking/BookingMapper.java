package com.shenll.shelogisticstranshipmentservice.booking;

import com.shenll.shelogisticstranshipmentservice.booking.domin.Booking;
import com.shenll.shelogisticstranshipmentservice.booking.domin.BookingAddress;
import com.shenll.shelogisticstranshipmentservice.booking.dto.BookingAddressDTO;
import com.shenll.shelogisticstranshipmentservice.booking.dto.BookingDTO;
import com.shenll.shelogisticstranshipmentservice.booking.repo.CustomerRepository;
import com.shenll.shelogisticstranshipmentservice.webclient.Agent.AgentServiceClient;
import com.shenll.shelogisticstranshipmentservice.webclient.Agent.AgentVehDTO;
import com.shenll.shelogisticstranshipmentservice.webclient.Customer.CustomerServiceClient;
import com.shenll.shelogisticstranshipmentservice.webclient.Customer.CustomerVehDTO;
import com.shenll.shelogisticstranshipmentservice.webclient.User.UserServiceClient;
import com.shenll.shelogisticstranshipmentservice.webclient.User.UserVehDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingMapper {
    @Autowired
    AgentServiceClient agentServiceClient;

    @Autowired
    CustomerServiceClient customerServiceClient;

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    UserServiceClient userServiceClient;

    public Booking addBooking(String userId, UserVehDTO userDetails, BookingDTO bookingDTO) {
        AgentVehDTO toAgent = agentServiceClient.getAgentById(bookingDTO.getToAgent())
                .orElseThrow(() -> new IllegalArgumentException("To Agent not found for ID: " + bookingDTO.getToAgent()));
        System.out.println(toAgent);
        Booking booking = Booking.builder()
                .fromAgent(userDetails.getCode().getAgentId())
                .toAgent(toAgent.getAgentId())
                .distance(bookingDTO.getDistance())
                .lr_serial_num(bookingDTO.getLr_serial_num())
                .lr_no(bookingDTO.getLr_no())
                .payment_type(bookingDTO.getPayment_type())
                .weight_type(bookingDTO.getWeight_type())
                .price_calculated_by(bookingDTO.getPrice_calculated_by())
                .booking_date(bookingDTO.getBooking_date())
                .booking_status(bookingDTO.getBooking_status())
                .payment_status(bookingDTO.getPayment_status())
                .instruction(bookingDTO.getInstruction())
                .value_of_goods(bookingDTO.getValue_of_goods())
                .eway_no(bookingDTO.getEway_no())
                .transporter_eway_no(bookingDTO.getTransporter_eway_no())
                .foc(bookingDTO.getFoc())
                .foc_type(bookingDTO.getFoc_type())
                .missing_lr_no(bookingDTO.getMissing_lr_no())
                .foc_desc(bookingDTO.getFoc_desc())
                .discount(bookingDTO.getDiscount())
                .customer_discount(bookingDTO.getCustomer_discount())
                .agent_discount(bookingDTO.getAgent_discount())
                .booked_by(userId)
                .invoice(bookingDTO.getInvoice())
                .invoice_id(bookingDTO.getInvoice_id())
                .created_by(userId)
                .createdOn(new Timestamp(System.currentTimeMillis()))
                .updatedOn(new Timestamp(System.currentTimeMillis()))
                .build();

        return booking;
    }

    public List<BookingAddress> addCustomer(List<BookingAddressDTO> customerDTOs, Booking bookingId) {
        List<BookingAddress> customers = new ArrayList<>();

        for (BookingAddressDTO customerDTO : customerDTOs) {
            CustomerVehDTO customerVehDTO = customerServiceClient.getCustomerById(customerDTO.getCustomerId())
                    .orElseThrow(() -> new IllegalArgumentException("Customer not found for ID: " + customerDTO.getCustomerId()));

            BookingAddress customer = BookingAddress.builder()
                    .booking_id(bookingId)
                    .customer_id(customerVehDTO.getName())
                    .address_type(customerDTO.getAddressType())
                    .name(customerVehDTO.getName())
                    .address1(customerVehDTO.getAddress())
                    .address2(customerVehDTO.getConfirmAddress())
                    .city(customerVehDTO.getCity())
                    .state(customerVehDTO.getState())
                    .pincode(customerVehDTO.getPincode())
                    .phone(customerVehDTO.getMobile())
                    .created_on(new Timestamp(System.currentTimeMillis()))
                    .updated_on(new Timestamp(System.currentTimeMillis()))
                    .build();

            customers.add(customer);
            customerRepository.save(customer);
        }

        return customers;
    }

}
