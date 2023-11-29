package com.monero.multibooks.MultiBooks.Dto.Contacts;

import com.monero.multibooks.MultiBooks.Entities.Contacts.Contacts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactsResponse {

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


    public ContactsResponse(Contacts c) {
        this.id = c.getId();
        this.companyName = c.getCompanyName();
        this.companyAddress = c.getCompanyAddress();
        this.companyCity = c.getCompanyCity();
        this.companyZipCode = c.getCompanyZipCode();
        this.companyCountry = c.getCompanyCountry();
        this.CVRNumber = c.getCVRNumber();
        this.email = c.getEmail();
        this.phoneNumber = c.getPhoneNumber();
        this.website = c.getWebsite();
        this.attentionPerson = c.getAttentionPerson();
        this.eInvoiceRecipientType = c.getEInvoiceRecipientType();
        this.paymentTermsMethod = c.getPaymentTermsMethod();
        this.paymentTermsDays = c.getPaymentTermsDays();
    }
}
