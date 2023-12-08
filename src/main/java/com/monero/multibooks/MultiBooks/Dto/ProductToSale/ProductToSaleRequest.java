package com.monero.multibooks.MultiBooks.Dto.ProductToSale;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class ProductToSaleRequest {

    private Long productToSaleId;
    private String productUnit;
    private double productAmount;
    private double productDiscount;
    private double productPriceExclVAT;
    private double productPriceAfterDiscount;
    private Long invoiceNumber;
}
