package com.monero.multibooks.MultiBooks.DomainService.Invoice;

import com.monero.multibooks.MultiBooks.Entities.Invoice.InvoiceStatus;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class InvoiceDomainService {
    public InvoiceStatus getStatus(int statusCode) {
        return switch (statusCode) {
            case 0 -> InvoiceStatus.CREATION;
            case 1 -> InvoiceStatus.DRAFT;
            case 2 -> InvoiceStatus.CONFIRMED;
            case 3 -> InvoiceStatus.PAID;
            case 4 -> InvoiceStatus.OVERDUE;
            case 5 -> InvoiceStatus.OVERPAID;
            case 6 -> InvoiceStatus.CANCELLED;
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status code not valid");
        };
    }
}
