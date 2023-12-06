package com.monero.multibooks.MultiBooks.Repository.Invoice;

import com.monero.multibooks.MultiBooks.Entities.Invoice.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
