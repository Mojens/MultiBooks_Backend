package com.monero.multibooks.MultiBooks.Entities.Accounting;

import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import com.monero.multibooks.MultiBooks.Entities.Contacts.Contacts;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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
public class AccountingRecordCredit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant documentDate;
    private Instant dueDate;
    private String valuta;
    private String boughtFrom;
    private int subTotalVat;
    private int subTotalNoVat;
    private int total;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime edited;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Contacts supplier;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private BusinessTeam businessTeam;

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinColumn(name = "accounting_record_credit_id")
    private List<AccountingRecord> accountingRecords;

}
