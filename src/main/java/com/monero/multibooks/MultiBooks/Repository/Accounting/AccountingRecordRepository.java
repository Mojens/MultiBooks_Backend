package com.monero.multibooks.MultiBooks.Repository.Accounting;

import com.monero.multibooks.MultiBooks.Entities.Accounting.AccountingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountingRecordRepository extends JpaRepository<AccountingRecord, Long> {
}
