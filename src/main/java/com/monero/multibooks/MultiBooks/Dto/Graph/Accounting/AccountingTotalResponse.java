package com.monero.multibooks.MultiBooks.Dto.Graph.Accounting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountingTotalResponse {
    private String quarter;
    private double total;
}
