package com.shenll.shelogisticsadminservice.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shenll.shelogisticsadminservice.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "invoice")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Invoice extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String invoiceId;
    private String customerId;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "dd-MM-yyyy", timezone = "UTC")
    @Column(name = "issue_date")
    private String issueDate;

    private String amount;
    private String balanceAmount;
    private String paidAmount;
    private String status;
    private List<String> bookingId;


}
