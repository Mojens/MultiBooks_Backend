package com.monero.multibooks.MultiBooks.Dto.Graph;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GraphResponse {

    private int totalUsers;
    private int totalProducts;
    private int totalInvoices;
    private int totalCashRecords;
    private int totalCreditRecords;
    private int totalContacts;
    private int totalBusinessTeams;
    private int totalUserTeams;
}
