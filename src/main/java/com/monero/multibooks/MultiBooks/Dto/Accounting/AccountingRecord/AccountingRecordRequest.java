package com.monero.multibooks.MultiBooks.Dto.Accounting.AccountingRecord;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class AccountingRecordRequest {

    Long id;
    int priceInclVat;
    int vat;
    String description;
    String account;
    Long accountingRecordCashId;
    Long accountingRecordCreditId;
}
