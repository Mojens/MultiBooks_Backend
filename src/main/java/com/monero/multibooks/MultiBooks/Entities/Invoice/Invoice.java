package com.monero.multibooks.MultiBooks.Entities.Invoice;

import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import com.monero.multibooks.MultiBooks.Entities.ProductToSale.ProductToSale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceNumber;
    private String invoiceComment;
    private String invoiceTitle;
    private Instant invoiceDate;
    private double subTotal;
    private double total;
    private double discount;

    @ManyToOne
    @JoinColumn(name = "business_team_id")
    private BusinessTeam businessTeam;

    @OneToMany
    @JoinColumn(name = "invoice_id")
    private List<ProductToSale> productToSales;

    public Invoice() {
    }
}
