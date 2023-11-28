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

    public Contacts(String companyName, int CVRNumber, String email, String phoneNumber, String website, String attentionPerson, String eInvoiceRecipientType, String paymentTermsMethod, int paymentTermsDays) {
        this.companyName = companyName;
        this.CVRNumber = CVRNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.website = website;
        this.attentionPerson = attentionPerson;
        this.eInvoiceRecipientType = eInvoiceRecipientType;
        this.paymentTermsMethod = paymentTermsMethod;
        this.paymentTermsDays = paymentTermsDays;
    }
}
