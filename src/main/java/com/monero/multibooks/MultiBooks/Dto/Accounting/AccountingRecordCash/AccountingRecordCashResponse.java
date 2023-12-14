package com.monero.multibooks.MultiBooks.Dto.Accounting.AccountingRecordCash;

import com.monero.multibooks.MultiBooks.Dto.Accounting.AccountingRecord.AccountingRecordResponse;
import com.monero.multibooks.MultiBooks.Entities.Accounting.AccountingRecordCash;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AccountingRecordCashResponse {

    private Long id;
    private Instant documentDate;
    private String holdings;
    private String boughtFrom;
    private int subTotalVat;
    private int subTotalNoVat;
    private int total;
    private List<AccountingRecordResponse> accountingRecords;

    public AccountingRecordCashResponse(AccountingRecordCash a){
        this.id = a.getId();
        this.documentDate = a.getDocumentDate();
        this.holdings = a.getHoldings();
        this.boughtFrom = a.getBoughtFrom();
        this.subTotalVat = a.getSubTotalVat();
        this.subTotalNoVat = a.getSubTotalNoVat();
        this.total = a.getTotal();
        if(a.getAccountingRecords() != null){
            this.accountingRecords = a.getAccountingRecords().stream().map(AccountingRecordResponse::new).toList();
        }
    }


}
