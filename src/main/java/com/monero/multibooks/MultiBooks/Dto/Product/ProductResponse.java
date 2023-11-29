package com.monero.multibooks.MultiBooks.Dto.Product;

import com.monero.multibooks.MultiBooks.Entities.Product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String productName;
    private double productCode;
    private double productAmount;
    private String productUnit;
    private double productPriceExclVAT;
    private double productPriceInclVAT;
    private String productDescription;
    private String productAccount;

    public ProductResponse(Product p) {
        this.id = p.getId();
        this.productName = p.getProductName();
        this.productCode = p.getProductCode();
        this.productAmount = p.getProductAmount();
        this.productUnit = p.getProductUnit();
        this.productPriceExclVAT = p.getProductPriceExclVAT();
        this.productPriceInclVAT = p.getProductPriceInclVAT();
        this.productDescription = p.getProductDescription();
        this.productAccount = p.getProductAccount();
    }
}
