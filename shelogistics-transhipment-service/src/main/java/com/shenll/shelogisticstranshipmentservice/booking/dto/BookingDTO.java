package com.shenll.shelogisticstranshipmentservice.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDTO {
    private String toAgent;
    private String distance;
    private String lr_serial_num;
    private String lr_no;
    private String payment_type;
    private String weight_type;
    private String price_calculated_by;
    private Date booking_date;
    private String booking_status;
    private String payment_status;
    private String instruction;
    private String value_of_goods;
    private String eway_no;
    private String transporter_eway_no;
    private String foc;
    private String foc_type;
    private String missing_lr_no;
    private String foc_desc;
    private String discount;
    private String customer_discount;
    private String agent_discount;
    private String invoice;
    private String invoice_id;
    private List<BookingAddressDTO> customer;
}
