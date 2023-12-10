package com.monero.multibooks.MultiBooks.Controllers.Invoice;

import com.monero.multibooks.MultiBooks.Dto.Invoice.InvoiceFillRequest;
import com.monero.multibooks.MultiBooks.Dto.Invoice.InvoiceResponse;
import com.monero.multibooks.MultiBooks.Dto.Shared.ApiResponse;
import com.monero.multibooks.MultiBooks.Service.Invoice.InvoiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/api/invoice/")
public class InvoiceController {


    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/get/{invoiceNumber}")
    public ResponseEntity<ApiResponse> getInvoice(@PathVariable Long invoiceNumber, HttpServletRequest httpRequest) {
        InvoiceResponse invoiceResponse = invoiceService.getInvoice(invoiceNumber, httpRequest);
        return ResponseEntity.ok(new ApiResponse(invoiceResponse, "Invoice retrieved successfully"));
    }

    @GetMapping("/all/{CVRNumber}")
    public ResponseEntity<ApiResponse> getInvoices(@PathVariable Long CVRNumber, HttpServletRequest httpRequest, Pageable pageable) {
        Page<InvoiceResponse> invoices = invoiceService.getInvoices(CVRNumber.intValue(), pageable, httpRequest);
        return ResponseEntity.ok(new ApiResponse(invoices, "Invoices retrieved successfully"));
    }


    @PostMapping("/create/{teamInvoiceCvrNumber}")
    public ResponseEntity<ApiResponse> createInvoice(@PathVariable int teamInvoiceCvrNumber, HttpServletRequest httpRequest) {
        InvoiceResponse invoice = invoiceService.createInvoice(teamInvoiceCvrNumber, httpRequest);
        return ResponseEntity.ok(new ApiResponse(invoice, "Invoice created successfully"));
    }

    @PostMapping("/fill")
    public ResponseEntity<ApiResponse> fillInvoice(@RequestBody InvoiceFillRequest request, HttpServletRequest httpRequest) {
        InvoiceResponse invoiceResponse = invoiceService.fillInvoice(request, httpRequest);
        return ResponseEntity.ok(new ApiResponse(invoiceResponse, "Invoice filled successfully"));
    }

    @PatchMapping("/edit")
    public ResponseEntity<ApiResponse> editInvoice(@RequestBody InvoiceFillRequest request, HttpServletRequest httpRequest) {
        InvoiceResponse invoiceResponse = invoiceService.editInvoice(request, httpRequest);
        return ResponseEntity.ok(new ApiResponse(invoiceResponse, "Invoice edited successfully"));
    }

    @PatchMapping("/status/{invoiceNumber}/{statusCode}")
    public ResponseEntity<ApiResponse> setStatus(@PathVariable Long invoiceNumber, @PathVariable int statusCode, HttpServletRequest httpRequest){
        invoiceService.setStatus(invoiceNumber, statusCode, httpRequest);
        return ResponseEntity.ok(new ApiResponse(true, "Invoice status changed successfully"));
    }

    @DeleteMapping("/delete/{invoiceNumber}")
    public ResponseEntity<ApiResponse> deleteInvoice(@PathVariable Long invoiceNumber, HttpServletRequest httpRequest) {
        InvoiceResponse invoiceResponse = invoiceService.deleteInvoice(invoiceNumber, httpRequest);
        return ResponseEntity.ok(new ApiResponse(invoiceResponse, "Invoice deleted successfully"));
    }


}
