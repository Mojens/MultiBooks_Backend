package com.monero.multibooks.MultiBooks.Service.Chart;

import com.monero.multibooks.MultiBooks.DomainService.Auth.AuthDomainService;
import com.monero.multibooks.MultiBooks.DomainService.Graph.GraphDomainService;
import com.monero.multibooks.MultiBooks.DomainService.Invoice.InvoiceDomainService;
import com.monero.multibooks.MultiBooks.Dto.Graph.Accounting.AccountingTotalResponse;
import com.monero.multibooks.MultiBooks.Dto.Graph.Invoice.Sales.InvoiceSalesResponse;
import com.monero.multibooks.MultiBooks.Dto.Graph.Invoice.Status.InvoiceStatusResponse;
import com.monero.multibooks.MultiBooks.Dto.Graph.Invoice.TotalInvoices.TotalInvoicesResponse;
import com.monero.multibooks.MultiBooks.Dto.Graph.TotalProducts.TotalProductsResponse;
import com.monero.multibooks.MultiBooks.Dto.Graph.TotalUsers.TotalUsersResponse;
import com.monero.multibooks.MultiBooks.Dto.Invoice.InvoiceResponse;
import com.monero.multibooks.MultiBooks.Entities.Accounting.AccountingRecordCash;
import com.monero.multibooks.MultiBooks.Entities.Accounting.AccountingRecordCredit;
import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import com.monero.multibooks.MultiBooks.Entities.Invoice.Invoice;
import com.monero.multibooks.MultiBooks.Entities.Invoice.InvoiceStatus;
import com.monero.multibooks.MultiBooks.Entities.Product.Product;
import com.monero.multibooks.MultiBooks.Entities.UserTeam.UserTeam;
import com.monero.multibooks.MultiBooks.Repository.Accounting.AccountingRecordCashRepository;
import com.monero.multibooks.MultiBooks.Repository.Accounting.AccountingRecordCreditRepository;
import com.monero.multibooks.MultiBooks.Repository.BusinessTeam.BusinessTeamRepository;
import com.monero.multibooks.MultiBooks.Repository.Contacts.ContactsRepository;
import com.monero.multibooks.MultiBooks.Repository.Invoice.InvoiceRepository;
import com.monero.multibooks.MultiBooks.Repository.Product.ProductRepository;
import com.monero.multibooks.MultiBooks.Repository.UserTeam.UserTeamRepository;
import com.monero.multibooks.MultiBooks.Service.UserTeam.UserTeamService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


@Service
public class ChartService {

    private final UserTeamService userTeamService;
    private final AuthDomainService authDomainService;
    private final InvoiceRepository invoiceRepository;
    private final AccountingRecordCashRepository accountingRecordCashRepository;
    private final AccountingRecordCreditRepository accountingRecordCreditRepository;
    private final ProductRepository productRepository;
    private final UserTeamRepository userTeamRepository;
    private final BusinessTeamRepository businessTeamRepository;
    private final GraphDomainService graphDomainService;
    private final InvoiceDomainService invoiceDomainService;

    public ChartService(UserTeamService userTeamService,
                        AuthDomainService authDomainService,
                        InvoiceRepository invoiceRepository,
                        AccountingRecordCashRepository accountingRecordCashRepository,
                        AccountingRecordCreditRepository accountingRecordCreditRepository,
                        ProductRepository productRepository,
                        UserTeamRepository userTeamRepository,
                        BusinessTeamRepository businessTeamRepository,
                        GraphDomainService graphDomainService,
                        InvoiceDomainService invoiceDomainService) {
        this.userTeamService = userTeamService;
        this.authDomainService = authDomainService;
        this.invoiceRepository = invoiceRepository;
        this.accountingRecordCashRepository = accountingRecordCashRepository;
        this.accountingRecordCreditRepository = accountingRecordCreditRepository;
        this.productRepository = productRepository;
        this.userTeamRepository = userTeamRepository;
        this.businessTeamRepository = businessTeamRepository;
        this.graphDomainService = graphDomainService;
        this.invoiceDomainService = invoiceDomainService;
    }

    public TotalUsersResponse getTotalUsers(@PathVariable int CVRNumber, HttpServletRequest httpRequest) {
        BusinessTeam businessTeam = businessTeamRepository.findById(CVRNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
        authDomainService.validateUserTeam(userTeamService.getUserTeams(businessTeam.getCVRNumber()), httpRequest);
        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(businessTeam);
        return new TotalUsersResponse(userTeams.size());
    }

    public TotalInvoicesResponse getTotalInvoices(@PathVariable int CVRNumber, HttpServletRequest httpRequest) {
        BusinessTeam businessTeam = businessTeamRepository.findById(CVRNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
        authDomainService.validateUserTeam(userTeamService.getUserTeams(businessTeam.getCVRNumber()), httpRequest);
        List<Invoice> invoices = invoiceRepository.findAllByBusinessTeam(businessTeam);
        return new TotalInvoicesResponse(invoices.size());
    }

    public TotalProductsResponse getTotalProducts(@PathVariable int CVRNumber, HttpServletRequest httpRequest) {
        BusinessTeam businessTeam = businessTeamRepository.findById(CVRNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
        authDomainService.validateUserTeam(userTeamService.getUserTeams(businessTeam.getCVRNumber()), httpRequest);
        List<Product> products = productRepository.findAllByBusinessTeam(businessTeam);
        return new TotalProductsResponse(products.size());
    }

    public List<InvoiceStatusResponse> getInvoiceCircleChart(@PathVariable int CVRNumber, @RequestParam Instant startDate, @RequestParam Instant endDate, HttpServletRequest httpRequest) {
        BusinessTeam businessTeam = businessTeamRepository.findById(CVRNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
        authDomainService.validateUserTeam(userTeamService.getUserTeams(businessTeam.getCVRNumber()), httpRequest);
        List<Invoice> confirmed = invoiceRepository.findAllByStatusIsAndBusinessTeamAndInvoiceDateBetween(InvoiceStatus.CONFIRMED, businessTeam, startDate, endDate);
        List<Invoice> paid = invoiceRepository.findAllByStatusIsAndBusinessTeamAndInvoiceDateBetween(InvoiceStatus.PAID, businessTeam, startDate, endDate);
        List<Invoice> cancelled = invoiceRepository.findAllByStatusIsAndBusinessTeamAndInvoiceDateBetween(InvoiceStatus.CANCELLED, businessTeam, startDate, endDate);
        List<Invoice> overdue = invoiceRepository.findAllByStatusIsAndBusinessTeamAndInvoiceDateBetween(InvoiceStatus.OVERDUE, businessTeam, startDate, endDate);
        List<Invoice> overPaid = invoiceRepository.findAllByStatusIsAndBusinessTeamAndInvoiceDateBetween(InvoiceStatus.OVERPAID, businessTeam, startDate, endDate);
        return List.of(
                new InvoiceStatusResponse(confirmed.size(), InvoiceStatus.CONFIRMED),
                new InvoiceStatusResponse(paid.size(), InvoiceStatus.PAID),
                new InvoiceStatusResponse(cancelled.size(), InvoiceStatus.CANCELLED),
                new InvoiceStatusResponse(overdue.size(), InvoiceStatus.OVERDUE),
                new InvoiceStatusResponse(overPaid.size(), InvoiceStatus.OVERPAID)
        );
    }

    public List<InvoiceSalesResponse> salesForTheYear(@PathVariable int CVRNumber, @RequestParam Instant startDate, @RequestParam Instant endDate, HttpServletRequest httpRequest) {
        BusinessTeam businessTeam = businessTeamRepository.findById(CVRNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
        authDomainService.validateUserTeam(userTeamService.getUserTeams(businessTeam.getCVRNumber()), httpRequest);
        List<Invoice> invoices = invoiceRepository.findAllByStatusInAndBusinessTeamAndInvoiceDateBetween(List.of(InvoiceStatus.PAID, InvoiceStatus.OVERPAID), businessTeam, startDate, endDate);
        List<Invoice> quarter1 = invoices.stream().filter(invoice -> {
            LocalDateTime localDateTime = invoice.getInvoiceDate().atZone(ZoneId.of("Europe/Paris")).toLocalDateTime();
            int month = localDateTime.getMonthValue();
            return month <= 3;
        }).toList();
        List<Invoice> quarter2 = invoices.stream().filter(invoice -> {
            LocalDateTime localDateTime = invoice.getInvoiceDate().atZone(ZoneId.of("Europe/Paris")).toLocalDateTime();
            int month = localDateTime.getMonthValue();
            return month <= 6 && month > 3;
        }).toList();
        List<Invoice> quarter3 = invoices.stream().filter(invoice -> {
            LocalDateTime localDateTime = invoice.getInvoiceDate().atZone(ZoneId.of("Europe/Paris")).toLocalDateTime();
            int month = localDateTime.getMonthValue();
            return month <= 9 && month > 6;
        }).toList();
        List<Invoice> quarter4 = invoices.stream().filter(invoice -> {
            LocalDateTime localDateTime = invoice.getInvoiceDate().atZone(ZoneId.of("Europe/Paris")).toLocalDateTime();
            int month = localDateTime.getMonthValue();
            return month > 9;
        }).toList();
        double total1 = quarter1.stream().mapToDouble(Invoice::getTotal).sum();
        double total2 = quarter2.stream().mapToDouble(Invoice::getTotal).sum();
        double total3 = quarter3.stream().mapToDouble(Invoice::getTotal).sum();
        double total4 = quarter4.stream().mapToDouble(Invoice::getTotal).sum();
        InvoiceSalesResponse q1 = new InvoiceSalesResponse(quarter1.size(), "Q1", total1);
        InvoiceSalesResponse q2 = new InvoiceSalesResponse(quarter2.size(), "Q2", total2);
        InvoiceSalesResponse q3 = new InvoiceSalesResponse(quarter3.size(), "Q3", total3);
        InvoiceSalesResponse q4 = new InvoiceSalesResponse(quarter4.size(), "Q4", total4);
        return List.of(q1, q2, q3, q4);
    }

    public AccountingTotalResponse totalVatForQuota(@PathVariable int CVRNumber, @RequestParam Instant startDate, @RequestParam Instant endDate, HttpServletRequest httpRequest) {
        BusinessTeam businessTeam = businessTeamRepository.findById(CVRNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
        authDomainService.validateUserTeam(userTeamService.getUserTeams(businessTeam.getCVRNumber()), httpRequest);
        List<AccountingRecordCash> accountingRecordCashes = accountingRecordCashRepository.findAllByBusinessTeamAndDocumentDateIsBetween(businessTeam, startDate, endDate);
        List<AccountingRecordCredit> accountingRecordCredits = accountingRecordCreditRepository.findAllByBusinessTeamAndDocumentDateIsBetween(businessTeam, startDate, endDate);
        System.out.println(accountingRecordCredits);
        System.out.println(accountingRecordCashes);
        double totalVat = (accountingRecordCashes.stream().mapToDouble(AccountingRecordCash::getSubTotalVat).sum() + accountingRecordCredits.stream().mapToDouble(AccountingRecordCredit::getSubTotalVat).sum()) * 0.25;
        System.out.println(totalVat);
        System.out.println(startDate.toString());
        System.out.println(endDate.toString());
        String quota = graphDomainService.getQuarter(startDate, endDate);
        return new AccountingTotalResponse(quota, totalVat);
    }

    public Page<InvoiceResponse> getInvoicesByStatus(@PathVariable int CVRNumber, @RequestParam int statusCode, Pageable pageable, HttpServletRequest httpRequest){
        BusinessTeam businessTeam = businessTeamRepository.findById(CVRNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
        authDomainService.validateUserTeam(userTeamService.getUserTeams(businessTeam.getCVRNumber()), httpRequest);
        InvoiceStatus status = invoiceDomainService.getStatus(statusCode);
        Page<Invoice> invoicePage = invoiceRepository.findAllByBusinessTeamAndStatus(businessTeam, status, pageable);
        return invoicePage.map(InvoiceResponse::new);
    }

}
