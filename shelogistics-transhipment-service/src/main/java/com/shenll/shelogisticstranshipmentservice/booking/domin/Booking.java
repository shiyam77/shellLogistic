package com.shenll.shelogisticstranshipmentservice.booking.domin;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "booking")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String bookingId;
    private String fromAgent;
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
    private String booked_by;
    private String invoice;
    private String invoice_id;
    @OneToMany(mappedBy = "booking_id",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<BookingAddress> customer;

    private String created_by;
    private String modified_by;
    private Timestamp createdOn;
    private Timestamp updatedOn;
}
