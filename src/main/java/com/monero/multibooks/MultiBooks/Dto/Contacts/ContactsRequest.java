package com.monero.multibooks.MultiBooks.Dto.Contacts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class ContactsRequest {

    private Long id;
    private String companyName;
    @JsonProperty("CVRNumber")
    private int CVRNumber;
    private String email;
    private String phoneNumber;
    private String website;
    private String attentionPerson;
    @JsonProperty("eInvoiceRecipientType")
    private String eInvoiceRecipientType;
    private String paymentTermsMethod;
    private int paymentTermsDays;
    private int businessTeamCVRNumber;
}
