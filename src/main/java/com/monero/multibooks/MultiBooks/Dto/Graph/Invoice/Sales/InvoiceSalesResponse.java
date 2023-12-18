package com.monero.multibooks.MultiBooks.Dto.Graph.Invoice.Sales;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceSalesResponse {

        private int totalInvoices;
        private String quarter;
        private double total;
}
