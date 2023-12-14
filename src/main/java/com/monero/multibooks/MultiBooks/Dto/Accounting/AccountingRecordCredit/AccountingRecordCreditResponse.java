package com.monero.multibooks.MultiBooks.Dto.Accounting.AccountingRecordCredit;

import com.monero.multibooks.MultiBooks.Dto.Accounting.AccountingRecord.AccountingRecordResponse;
import com.monero.multibooks.MultiBooks.Dto.Contacts.ContactsResponse;
import com.monero.multibooks.MultiBooks.Entities.Accounting.AccountingRecordCredit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AccountingRecordCreditResponse {

    private Long id;
    private Instant documentDate;
    private Instant dueDate;
    private String valuta;
    private String boughtFrom;
    private int subTotalVat;
    private int subTotalNoVat;
    private int total;
    private ContactsResponse supplier;
    private List<AccountingRecordResponse> accountingRecords;

    public AccountingRecordCreditResponse(AccountingRecordCredit a) {
        this.id = a.getId();
        this.documentDate = a.getDocumentDate();
        this.dueDate = a.getDueDate();
        this.valuta = a.getValuta();
        this.boughtFrom = a.getBoughtFrom();
        this.subTotalVat = a.getSubTotalVat();
        this.subTotalNoVat = a.getSubTotalNoVat();
        this.total = a.getTotal();
        if (a.getSupplier() != null) {
            this.supplier = new ContactsResponse(a.getSupplier());
        }
        if (a.getAccountingRecords() != null) {
            this.accountingRecords = a.getAccountingRecords().stream().map(AccountingRecordResponse::new).toList();
        }
    }
}
