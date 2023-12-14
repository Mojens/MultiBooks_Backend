package com.monero.multibooks.MultiBooks.Dto.Accounting.AccountingRecord;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class GetAccountingRecordRequest {
    Long accountingRecordCashId;
    Long accountingRecordCreditId;
}
