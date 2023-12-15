package com.monero.multibooks.MultiBooks.Repository.Accounting;

import com.monero.multibooks.MultiBooks.Entities.Accounting.AccountingRecordCredit;
import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountingRecordCreditRepository extends JpaRepository<AccountingRecordCredit, Long> {
    Page<AccountingRecordCredit> findAllByBusinessTeam(BusinessTeam businessTeam, Pageable pageable);
}
