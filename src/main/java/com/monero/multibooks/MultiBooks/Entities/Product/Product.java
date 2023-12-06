package com.monero.multibooks.MultiBooks.Entities.Product;

import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import com.monero.multibooks.MultiBooks.Entities.ProductToSale.ProductToSale;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String productName;
    private double productCode;
    @Column(name = "amount")
    private double productAmount;
    @Column(name = "unit")
    private String productUnit;
    @Column(name = "price_excl_vat")
    private double productPriceExclVAT;
    @Column(name = "price_incl_vat")
    private double productPriceInclVAT;
    @Column(name = "description")
    private String productDescription;
    @Column(name = "account")
    private String productAccount;

    @ManyToOne
    @JoinColumn(name = "business_team_id")
    private BusinessTeam businessTeam;

    @OneToMany(mappedBy = "product")
    private List<ProductToSale> productToSale;


    public Product(String productName, double productCode, double productAmount, String productUnit, double productPriceExclVAT, double productPriceInclVAT, String productDescription, String productAccount) {
        this.productName = productName;
        this.productCode = productCode;
        this.productAmount = productAmount;
        this.productUnit = productUnit;
        this.productPriceExclVAT = productPriceExclVAT;
        this.productPriceInclVAT = productPriceInclVAT;
        this.productDescription = productDescription;
        this.productAccount = productAccount;
    }
}
