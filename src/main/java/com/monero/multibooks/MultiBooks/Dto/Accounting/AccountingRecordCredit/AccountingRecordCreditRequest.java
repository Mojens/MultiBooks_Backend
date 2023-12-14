package com.monero.multibooks.MultiBooks.Dto.Accounting.AccountingRecordCredit;

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
public class AccountingRecordCreditRequest {

    Long id;
    Instant documentDate;
    Instant dueDate;
    String valuta;
    Long supplierId;
    String boughtFrom;
    int subTotalVat;
    int subTotalNoVat;
    int total;
    @JsonProperty("businessTeamCVRNumber")
    int businessTeamCVRNumber;
}
