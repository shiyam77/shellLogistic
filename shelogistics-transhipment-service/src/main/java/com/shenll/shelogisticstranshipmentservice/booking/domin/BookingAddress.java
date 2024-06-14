package com.shenll.shelogisticstranshipmentservice.booking.domin;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "booking_address")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    @JsonBackReference
    private Booking booking_id;
    
    private String customer_id;
    private String address_type;
    private String name;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String pincode;
    private String phone;
    private Timestamp created_on;
    private Timestamp updated_on;
}
