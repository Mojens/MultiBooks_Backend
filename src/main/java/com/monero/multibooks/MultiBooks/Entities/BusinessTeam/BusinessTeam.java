package com.monero.multibooks.MultiBooks.Entities.BusinessTeam;

import com.monero.multibooks.MultiBooks.Entities.Accounting.AccountingRecordCash;
import com.monero.multibooks.MultiBooks.Entities.Accounting.AccountingRecordCredit;
import com.monero.multibooks.MultiBooks.Entities.Contacts.Contacts;
import com.monero.multibooks.MultiBooks.Entities.Invoice.Invoice;
import com.monero.multibooks.MultiBooks.Entities.Product.Product;
import com.monero.multibooks.MultiBooks.Entities.User.User;
import com.monero.multibooks.MultiBooks.Entities.UserTeam.UserTeam;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BusinessTeam {

    @Id
    @Column(name = "cvr_number", nullable = false)
    private int CVRNumber;

    @Column(name = "vat_number", nullable = false, unique = true)
    private String VATNumber;

    @Column(nullable = false, length = 100)
    private String companyName;

    @Column(length = 200)
    private String address;

    @Column(length = 50)
    private String city;

    @Column(name = "zip_code")
    private int zipCode;

    @Column(length = 50)
    private String country;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 100)
    private String website;

    private String accNumber;
    private int regNumber;
    private String bankName;

    @ManyToOne
    @JoinColumn(name = "team_owner")
    private User teamOwner;

    @OneToMany(mappedBy = "businessTeam")
    private List<UserTeam> userTeams;

    @OneToMany(mappedBy = "businessTeam")
    private List<Contacts> contacts;

    @OneToMany(mappedBy = "businessTeam")
    private List<Product> products;

    @OneToMany(mappedBy = "businessTeam")
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "businessTeam")
    private List<AccountingRecordCash> accountingRecordCash;

    @OneToMany(mappedBy = "businessTeam")
    private List<AccountingRecordCredit> accountingRecordCredit;


    public BusinessTeam(int CVRNumber, String VATNumber, String companyName, String address, String city, int zipCode, String country, String phoneNumber, String email, String website) {
        this.CVRNumber = CVRNumber;
        this.VATNumber = VATNumber;
        this.companyName = companyName;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.website = website;
    }
}
