package com.monero.multibooks.MultiBooks.Dto.Invoice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class InvoiceFillRequest {

    private Long invoiceNumber;
    private String invoiceComment;
    private String invoiceTitle;
    private Instant invoiceDate;
    private double subTotal;
    private double total;
    private int statusCode;
    private String EditedBy;
}
