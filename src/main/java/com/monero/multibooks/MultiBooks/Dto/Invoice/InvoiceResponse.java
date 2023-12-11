package com.monero.multibooks.MultiBooks.Dto.Invoice;

import com.monero.multibooks.MultiBooks.Dto.Contacts.ContactsResponse;
import com.monero.multibooks.MultiBooks.Dto.ProductToSale.ProductToSaleResponse;
import com.monero.multibooks.MultiBooks.Entities.Invoice.Invoice;
import com.monero.multibooks.MultiBooks.Entities.Invoice.InvoiceStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceResponse {

    private Long invoiceNumber;
    private String invoiceComment;
    private String invoiceTitle;
    private Instant invoiceDate;
    private LocalDateTime createdDate;
    private double subTotal;
    private double total;
    private double subTotalWithVat;
    private InvoiceStatus status;
    private ContactsResponse contact;
    private List<ProductToSaleResponse> productToSale;

    public InvoiceResponse(Invoice c) {
        this.invoiceNumber = c.getInvoiceNumber();
        this.invoiceComment = c.getInvoiceComment();
        this.invoiceTitle = c.getInvoiceTitle();
        this.invoiceDate = c.getInvoiceDate();
        this.createdDate = c.getCreated();
        this.subTotal = c.getSubTotal();
        this.total = c.getTotal();
        this.status = c.getStatus();
        this.subTotalWithVat = c.getSubTotalWithVat();
        if (c.getProductToSales() != null) {
            this.productToSale = c.getProductToSales().stream().map(ProductToSaleResponse::new).toList();
        }
        if (c.getContact() != null) {
            this.contact = new ContactsResponse(c.getContact());
        }
    }
}
