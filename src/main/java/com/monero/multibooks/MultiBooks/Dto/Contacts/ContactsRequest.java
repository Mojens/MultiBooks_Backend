package com.monero.multibooks.MultiBooks.Dto.Contacts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ContactsRequest {

    private String companyName;
    private int CVRNumber;
    private String email;
    private String phoneNumber;
    private String website;
    private String attentionPerson;
    private String eInvoiceRecipientType;
    private String paymentTermsMethod;
    private int paymentTermsDays;
}
