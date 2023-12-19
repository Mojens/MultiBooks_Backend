package com.monero.multibooks.MultiBooks.Dto.Graph.Invoice.Status;

import com.monero.multibooks.MultiBooks.Entities.Invoice.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceStatusResponse {

    private int totalInvoices;
    private InvoiceStatus status;
}
