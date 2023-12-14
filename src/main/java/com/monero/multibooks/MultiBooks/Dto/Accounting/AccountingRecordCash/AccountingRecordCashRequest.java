package com.monero.multibooks.MultiBooks.Dto.Accounting.AccountingRecordCash;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class AccountingRecordCashRequest {

    Long id;
    @JsonProperty("documentDate")
    Instant documentDate;
    String holdings;
    String boughtFrom;
    int subTotalVat;
    int subTotalNoVat;
    int total;
}

