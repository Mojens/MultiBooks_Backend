package com.monero.multibooks.MultiBooks.Dto.ProductToSale;

import com.monero.multibooks.MultiBooks.Dto.Product.ProductResponse;
import com.monero.multibooks.MultiBooks.Entities.ProductToSale.ProductToSale;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductToSaleResponse {

    private Long id;
    private String productUnit;
    private double productAmount;
    private double productDiscount;
    private double productPriceExclVAT;
    private double productPriceAfterDiscount;
    private ProductResponse product;

    public ProductToSaleResponse(ProductToSale p) {
        this.id = p.getId();
        this.productUnit = p.getProductUnit();
        this.productAmount = p.getProductAmount();
        this.productDiscount = p.getProductDiscount();
        this.productPriceExclVAT = p.getProductPriceExclVAT();
        this.productPriceAfterDiscount = p.getProductPriceAfterDiscount();
        this.product = new ProductResponse(p.getProduct());
    }
}
