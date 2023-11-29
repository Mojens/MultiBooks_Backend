package com.monero.multibooks.MultiBooks.Entities.Product;

import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private double productCode;
    private double productAmount;
    private String productUnit;
    private double productPriceExclVAT;
    private double productPriceInclVAT;
    private String productDescription;
    private String productAccount;
    @ManyToOne
    @JoinColumn(name = "business_team_id")
    private BusinessTeam businessTeam;


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
