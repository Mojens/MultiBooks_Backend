package com.monero.multibooks.MultiBooks.Service.Chart;

import com.monero.multibooks.MultiBooks.DomainService.Auth.AuthDomainService;
import com.monero.multibooks.MultiBooks.DomainService.Invoice.InvoiceDomainService;
import com.monero.multibooks.MultiBooks.Dto.Graph.Invoice.Status.InvoiceStatusResponse;
import com.monero.multibooks.MultiBooks.Dto.Graph.TotalProducts.TotalProductsResponse;
import com.monero.multibooks.MultiBooks.Dto.Graph.TotalUsers.TotalUsersResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Service
public class ChartService {

    private final UserTeamService userTeamService;
    private final AuthDomainService authDomainService;
    private final InvoiceRepository invoiceRepository;
    private final AccountingRecordCashRepository accountingRecordCashRepository;
    private final AccountingRecordCreditRepository accountingRecordCreditRepository;
    private final ContactsRepository contactsRepository;
    private final ProductRepository productRepository;
    private final UserTeamRepository userTeamRepository;
    private final BusinessTeamRepository businessTeamRepository;
    private final InvoiceDomainService invoiceDomainService;

    public ChartService(UserTeamService userTeamService,
                        AuthDomainService authDomainService,
                        InvoiceRepository invoiceRepository,
                        AccountingRecordCashRepository accountingRecordCashRepository,
                        AccountingRecordCreditRepository accountingRecordCreditRepository,
                        ContactsRepository contactsRepository,
                        ProductRepository productRepository,
                        UserTeamRepository userTeamRepository,
                        BusinessTeamRepository businessTeamRepository,
                        InvoiceDomainService invoiceDomainService) {
        this.userTeamService = userTeamService;
        this.authDomainService = authDomainService;
        this.invoiceRepository = invoiceRepository;
        this.accountingRecordCashRepository = accountingRecordCashRepository;
        this.accountingRecordCreditRepository = accountingRecordCreditRepository;
        this.contactsRepository = contactsRepository;
        this.productRepository = productRepository;
        this.userTeamRepository = userTeamRepository;
        this.businessTeamRepository = businessTeamRepository;
        this.invoiceDomainService = invoiceDomainService;
    }

    public TotalUsersResponse getTotalUsers(@PathVariable int CVRNumber, HttpServletRequest httpRequest) {
        BusinessTeam businessTeam = businessTeamRepository.findById(CVRNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
        authDomainService.validateUserTeam(userTeamService.getUserTeams(businessTeam.getCVRNumber()), httpRequest);
        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(businessTeam);
        return new TotalUsersResponse(userTeams.size());
    }

    public TotalProductsResponse getTotalProducts(@PathVariable int CVRNumber, HttpServletRequest httpRequest) {
        BusinessTeam businessTeam = businessTeamRepository.findById(CVRNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
        authDomainService.validateUserTeam(userTeamService.getUserTeams(businessTeam.getCVRNumber()), httpRequest);
        List<Product> products = productRepository.findAllByBusinessTeam(businessTeam);
        return new TotalProductsResponse(products.size());
    }

    public InvoiceStatusResponse getTotalInvoicesByStatus(@PathVariable int CVRNumber, @PathVariable int statusCode, HttpServletRequest httpRequest){
        InvoiceStatus status = invoiceDomainService.getStatus(statusCode);
        BusinessTeam businessTeam = businessTeamRepository.findById(CVRNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
        authDomainService.validateUserTeam(userTeamService.getUserTeams(businessTeam.getCVRNumber()), httpRequest);
        List<Invoice> invoices = invoiceRepository.findAllByStatusIsAndBusinessTeam(status, businessTeam);
        return new InvoiceStatusResponse(invoices.size(), status.toString());
    }


}
