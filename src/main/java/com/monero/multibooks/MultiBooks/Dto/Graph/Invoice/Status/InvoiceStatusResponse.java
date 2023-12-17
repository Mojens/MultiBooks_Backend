package com.monero.multibooks.MultiBooks.Dto.Graph.Invoice.Status;

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
    private String status;
}
