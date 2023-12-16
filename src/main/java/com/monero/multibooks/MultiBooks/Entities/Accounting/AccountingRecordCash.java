package com.monero.multibooks.MultiBooks.Entities.Accounting;

import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
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

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime edited;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "accounting_record_cash_id")
    private List<AccountingRecord> accountingRecords;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private BusinessTeam businessTeam;
}
