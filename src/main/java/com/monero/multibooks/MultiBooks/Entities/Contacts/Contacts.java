package com.monero.multibooks.MultiBooks.Entities.Contacts;

import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Contacts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    private String companyAddress;
    private String companyCity;
    private String companyZipCode;
    private String companyCountry;
    private int CVRNumber;
    private String email;
    private String phoneNumber;
    private String website;
    private String attentionPerson;
    private String eInvoiceRecipientType;
    private String paymentTermsMethod;
    private int paymentTermsDays;

    @ManyToOne
    @JoinColumn(name = "business_team_id")
    private BusinessTeam businessTeam;

}
