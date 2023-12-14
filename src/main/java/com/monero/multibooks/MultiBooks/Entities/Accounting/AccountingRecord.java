package com.monero.multibooks.MultiBooks.Entities.Accounting;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class AccountingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int priceInclVat;
    private int vat;
    private String description;
    private String account;

    @ManyToOne
    @JoinColumn(name = "accounting_record_cash_id")
    private AccountingRecordCash accountingRecordCash;
    @ManyToOne
    @JoinColumn(name = "accounting_record_credit_id")
    private AccountingRecordCredit accountingRecordCredit;

}
