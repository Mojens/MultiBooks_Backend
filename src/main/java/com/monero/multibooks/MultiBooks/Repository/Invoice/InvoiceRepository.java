package com.monero.multibooks.MultiBooks.Repository.Invoice;

import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import com.monero.multibooks.MultiBooks.Entities.Invoice.Invoice;
import com.monero.multibooks.MultiBooks.Entities.Invoice.InvoiceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Page<Invoice> findAllByBusinessTeam(BusinessTeam businessTeam, Pageable pageable);

    List<Invoice> findAllByStatusIs(InvoiceStatus status);

    List<Invoice> findAllByStatusIsAndBusinessTeam(InvoiceStatus status, BusinessTeam businessTeam);

    List<Invoice> findAllByStatusAndBusinessTeamAndInvoiceDateBetween(InvoiceStatus status, BusinessTeam businessTeam, Instant start, Instant end);


}
