package com.shenll.shelogisticsadminservice.invoice;


import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;

public interface InvoiceService {
    CommonResponse addInvoice(InvoiceDto invoiceDto);

    CommonResponse findInvoice(Long page, Long size);
}
