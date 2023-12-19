package com.monero.multibooks.MultiBooks.Repository.Invoice;

import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import com.monero.multibooks.MultiBooks.Entities.Invoice.Invoice;
import com.monero.multibooks.MultiBooks.Entities.Invoice.InvoiceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Page<Invoice> findAllByBusinessTeam(BusinessTeam businessTeam, Pageable pageable);

    List<Invoice> findAllByStatusIs(InvoiceStatus status);

    List<Invoice> findAllByBusinessTeam(BusinessTeam businessTeam);

    List<Invoice> findAllByStatusIsAndBusinessTeamAndInvoiceDateBetween(InvoiceStatus status, BusinessTeam businessTeam, Instant start, Instant end);

    @Query("SELECT i FROM Invoice i WHERE i.status IN :statuses AND i.businessTeam = :businessTeam AND i.invoiceDate BETWEEN :start AND :end")
    List<Invoice> findAllByStatusInAndBusinessTeamAndInvoiceDateBetween(
            @Param("statuses") List<InvoiceStatus> statuses,
            @Param("businessTeam") BusinessTeam businessTeam,
            @Param("start") Instant start,
            @Param("end") Instant end
    );


}
