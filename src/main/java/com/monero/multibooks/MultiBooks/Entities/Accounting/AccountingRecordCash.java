package com.monero.multibooks.MultiBooks.Entities.Accounting;

import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class AccountingRecordCash {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant documentDate;
    private String boughtFrom;
    private String holdings;
    private int subTotalVat;
    private int subTotalNoVat;
    private int total;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "accounting_record_cash_id")
    private List<AccountingRecord> accountingRecords;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private BusinessTeam businessTeam;
}
