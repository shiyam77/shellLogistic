package com.shenll.shelogisticsadminservice.invoice;

import lombok.Data;

import java.util.List;

@Data
public class InvoiceDto {

    private String customerId;
    private String issueDate;
    private String amount;
    private String balanceAmount;
    private String paidAmount;
    private String status;
    private String createdBy;
    private List<String> bookingId;

}
