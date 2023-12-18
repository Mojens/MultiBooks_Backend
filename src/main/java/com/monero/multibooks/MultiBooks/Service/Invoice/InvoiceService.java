package com.monero.multibooks.MultiBooks.Service.Invoice;

import com.monero.multibooks.MultiBooks.DomainService.Auth.AuthDomainService;
import com.monero.multibooks.MultiBooks.DomainService.Invoice.InvoiceDomainService;
import com.monero.multibooks.MultiBooks.Dto.Invoice.InvoiceFillRequest;
import com.monero.multibooks.MultiBooks.Dto.Invoice.InvoiceResponse;
import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import com.monero.multibooks.MultiBooks.Entities.Contacts.Contacts;
import com.monero.multibooks.MultiBooks.Entities.Invoice.Invoice;
import com.monero.multibooks.MultiBooks.Entities.UserTeam.UserTeam;
import com.monero.multibooks.MultiBooks.Repository.BusinessTeam.BusinessTeamRepository;
import com.monero.multibooks.MultiBooks.Repository.Contacts.ContactsRepository;
import com.monero.multibooks.MultiBooks.Repository.Invoice.InvoiceRepository;
import com.monero.multibooks.MultiBooks.Repository.ProductToSale.ProductToSaleRepository;
import com.monero.multibooks.MultiBooks.Repository.UserTeam.UserTeamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final BusinessTeamRepository businessTeamRepository;
    private final UserTeamRepository userTeamRepository;
    private final ProductToSaleRepository productToSaleRepository;
    private final InvoiceDomainService invoiceDomainService;
    private final AuthDomainService authDomainService;
    private final ContactsRepository contactsRepository;

    public InvoiceService(InvoiceRepository invoiceRepository,
                          BusinessTeamRepository businessTeamRepository,
                          UserTeamRepository userTeamRepository,
                          ProductToSaleRepository productToSaleRepository,
                          InvoiceDomainService invoiceDomainService,
                          AuthDomainService authDomainService,
                          ContactsRepository contactsRepository) {
        this.invoiceRepository = invoiceRepository;
        this.businessTeamRepository = businessTeamRepository;
        this.userTeamRepository = userTeamRepository;
        this.productToSaleRepository = productToSaleRepository;
        this.invoiceDomainService = invoiceDomainService;
        this.authDomainService = authDomainService;
        this.contactsRepository = contactsRepository;
    }

    public InvoiceResponse getInvoice(@PathVariable Long invoiceNumber, HttpServletRequest httpRequest) {
        Invoice foundInvoice = invoiceRepository.findById(invoiceNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice not found"));
        BusinessTeam businessTeam = foundInvoice.getBusinessTeam();
        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(businessTeam);
        authDomainService.validateUserTeam(userTeams, httpRequest);
        return new InvoiceResponse(foundInvoice);
    }

    public Page<InvoiceResponse> getInvoices(@PathVariable int CVRNumber, Pageable pageable, HttpServletRequest httpRequest) {
        BusinessTeam businessTeam = businessTeamRepository.findById(CVRNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(businessTeam);
        authDomainService.validateUserTeam(userTeams, httpRequest);
        Page<Invoice> invoicePage = invoiceRepository.findAllByBusinessTeam(businessTeam, pageable);
        return invoicePage.map(InvoiceResponse::new);
    }

    public InvoiceResponse createInvoice(@PathVariable int teamInvoiceCvrNumber, HttpServletRequest httpRequest) {
        BusinessTeam businessTeam = businessTeamRepository.findById(teamInvoiceCvrNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(businessTeam);
        authDomainService.validateUserTeam(userTeams, httpRequest);
        Invoice invoiceToCreate = Invoice.builder()
                .businessTeam(businessTeam)
                .build();
        Invoice invoiceCreated = invoiceRepository.save(invoiceToCreate);
        return new InvoiceResponse(invoiceCreated);
    }

    public InvoiceResponse fillInvoice(@RequestBody InvoiceFillRequest request, HttpServletRequest httpRequest) {
        Invoice foundInvoice = invoiceRepository.findById(request.getInvoiceNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice not found"));
        BusinessTeam businessTeam = foundInvoice.getBusinessTeam();
        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(businessTeam);
        authDomainService.validateUserTeam(userTeams, httpRequest);
        Contacts contact = contactsRepository.findById(request.getContactId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));
        foundInvoice.setInvoiceComment(request.getInvoiceComment());
        foundInvoice.setInvoiceTitle(request.getInvoiceTitle());
        foundInvoice.setInvoiceDate(request.getInvoiceDate());
        foundInvoice.setSubTotal(request.getSubTotal());
        foundInvoice.setTotal(request.getTotal());
        foundInvoice.setContact(contact);
        foundInvoice.setSubTotalWithVat(request.getSubTotal() * 1.25);
        foundInvoice.setStatus(invoiceDomainService.getStatus(request.getStatusCode()));
        invoiceRepository.save(foundInvoice);
        return new InvoiceResponse(foundInvoice);
    }

    public InvoiceResponse editInvoice(@RequestBody InvoiceFillRequest request, HttpServletRequest httpRequest) {
        Invoice foundInvoice = invoiceRepository.findById(request.getInvoiceNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice not found"));
        BusinessTeam businessTeam = foundInvoice.getBusinessTeam();
        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(businessTeam);
        authDomainService.validateUserTeam(userTeams, httpRequest);
        foundInvoice.setInvoiceComment(request.getInvoiceComment());
        foundInvoice.setInvoiceTitle(request.getInvoiceTitle());
        foundInvoice.setInvoiceDate(request.getInvoiceDate());
        foundInvoice.setSubTotal(request.getSubTotal());
        foundInvoice.setTotal(request.getTotal());
        foundInvoice.setStatus(invoiceDomainService.getStatus(request.getStatusCode()));
        foundInvoice.setEditedBy(request.getEditedBy());
        invoiceRepository.save(foundInvoice);
        return new InvoiceResponse(foundInvoice);
    }

    @Transactional
    public InvoiceResponse deleteInvoice(@PathVariable Long invoiceNumber, HttpServletRequest httpRequest) {
        Invoice foundInvoice = invoiceRepository.findById(invoiceNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice not found"));
        BusinessTeam businessTeam = foundInvoice.getBusinessTeam();
        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(businessTeam);
        authDomainService.validateUserTeam(userTeams, httpRequest);
        productToSaleRepository.deleteAllByInvoice(foundInvoice);
        invoiceRepository.delete(foundInvoice);
        return new InvoiceResponse(foundInvoice);
    }


    public void setStatus(@PathVariable Long invoiceNumber, @PathVariable int statusCode, HttpServletRequest httpRequest) {
        Invoice foundInvoice = invoiceRepository.findById(invoiceNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice not found"));

        BusinessTeam businessTeam = foundInvoice.getBusinessTeam();
        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(businessTeam);
        authDomainService.validateUserTeam(userTeams, httpRequest);
        foundInvoice.setStatus(invoiceDomainService.getStatus(statusCode));
        invoiceRepository.save(foundInvoice);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void cleanupExpiredTokens() {
        // Mangler logik for at den skal slette hver 48 timer de der ligger i draft
        Instant now = Instant.now();
        List<Invoice> invoices = invoiceRepository.findAllByStatusIs(invoiceDomainService.getStatus(0));

    }
}
