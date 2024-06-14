package com.shenll.shelogisticsadminservice.invoice;

import com.shenll.shelogisticsadminservice.responseDTO.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/invoice")
@RestController
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @PostMapping("/add/invoice")
    public CommonResponse createInvoice(@RequestBody InvoiceDto invoiceDto) {
        return invoiceService.addInvoice(invoiceDto);
    }

    @GetMapping("/get/invoice")
    public CommonResponse getInvoice(@RequestParam(required = false) Long page,
                                     @RequestParam(required = false) Long size) {
        return invoiceService.findInvoice(page, size);
    }

}
