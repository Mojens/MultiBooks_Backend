package com.monero.multibooks.MultiBooks.Entities.ProductToSale;

import com.monero.multibooks.MultiBooks.Entities.Invoice.Invoice;
import com.monero.multibooks.MultiBooks.Entities.Product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
public class ProductToSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "unit")
    private String productUnit;
    @Column(name = "quantity")
    private double productAmount;
    @Column(name = "discount")
    private double productDiscount;
    @Column(name = "price_excl_vat")
    private double productPriceExclVAT;
    @Column(name = "price_incl_discount")
    private double productPriceAfterDiscount;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;


    public ProductToSale() {
    }
}
