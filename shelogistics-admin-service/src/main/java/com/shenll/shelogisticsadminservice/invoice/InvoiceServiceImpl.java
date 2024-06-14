package com.shenll.shelogisticsadminservice.invoice;

import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;

    @Override
    public CommonResponse addInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = new Invoice().builder()
                .balanceAmount(invoiceDto.getBalanceAmount())
                .paidAmount(invoiceDto.getPaidAmount())
                .amount(invoiceDto.getAmount())
                .bookingId(invoiceDto.getBookingId())
                .customerId(invoiceDto.getCustomerId())
                .status(invoiceDto.getStatus())
                .issueDate(invoiceDto.getIssueDate())
                .build();
        invoiceRepository.save(invoice);
        return CommonResponse.builder().code(200).build();
    }

    @Override
    public CommonResponse findInvoice(Long page, Long size) {
        Pageable pageable = PageRequest.of(page == null ? 0 : page.intValue(), size == null ? Integer.MAX_VALUE : size.intValue(), Sort.Direction.DESC, "createdAt");
        Page<Invoice> invoices = invoiceRepository.findAll(pageable);
        if (invoices.hasContent()) {
            return CommonResponse.builder()
                    .code(200)
                    .message("Successfully fetched Invoice data")
                    .data(invoices.getContent())
                    .totalCount(invoices.getTotalElements())
                    .build();
        } else {
            return CommonResponse.builder()
                    .code(200)
                    .message("Invoice data is empty")
                    .data(Collections.emptyList())
                    .totalCount(0L)
                    .build();
        }
    }
}
