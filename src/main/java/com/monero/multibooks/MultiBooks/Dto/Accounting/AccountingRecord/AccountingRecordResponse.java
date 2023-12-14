package com.monero.multibooks.MultiBooks.Dto.Accounting.AccountingRecord;

import com.monero.multibooks.MultiBooks.Entities.Accounting.AccountingRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountingRecordResponse {

    private Long id;
    private int priceInclVat;
    private int vat;
    private String description;
    private String account;

    public AccountingRecordResponse(AccountingRecord c) {
        this.id = c.getId();
        this.priceInclVat = c.getPriceInclVat();
        this.vat = c.getVat();
        this.description = c.getDescription();
        this.account = c.getAccount();
    }
}
