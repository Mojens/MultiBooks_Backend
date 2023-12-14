package com.monero.multibooks.MultiBooks.Repository.Accounting;

import com.monero.multibooks.MultiBooks.Entities.Accounting.AccountingRecord;
import com.monero.multibooks.MultiBooks.Entities.Accounting.AccountingRecordCash;
import com.monero.multibooks.MultiBooks.Entities.Accounting.AccountingRecordCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountingRecordRepository extends JpaRepository<AccountingRecord, Long> {


    List<AccountingRecord> findAllByAccountingRecordCash(AccountingRecordCash accountingRecordCash);
    List<AccountingRecord> findAllByAccountingRecordCredit(AccountingRecordCredit accountingRecordCredit);

}
