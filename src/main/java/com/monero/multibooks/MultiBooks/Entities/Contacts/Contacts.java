package com.monero.multibooks.MultiBooks.Entities.Contacts;

import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Contacts {

    @Id
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




}
