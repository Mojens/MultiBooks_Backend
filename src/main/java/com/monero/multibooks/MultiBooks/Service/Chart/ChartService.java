package com.monero.multibooks.MultiBooks.Service.Chart;

import com.monero.multibooks.MultiBooks.DomainService.Auth.AuthDomainService;
import com.monero.multibooks.MultiBooks.Repository.Accounting.AccountingRecordCashRepository;
import com.monero.multibooks.MultiBooks.Repository.Accounting.AccountingRecordCreditRepository;
import com.monero.multibooks.MultiBooks.Repository.BusinessTeam.BusinessTeamRepository;
import com.monero.multibooks.MultiBooks.Repository.Contacts.ContactsRepository;
import com.monero.multibooks.MultiBooks.Repository.Invoice.InvoiceRepository;
import com.monero.multibooks.MultiBooks.Repository.Product.ProductRepository;
import com.monero.multibooks.MultiBooks.Repository.UserTeam.UserTeamRepository;
import com.monero.multibooks.MultiBooks.Service.UserTeam.UserTeamService;
import org.springframework.stereotype.Service;


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

    public ChartService(UserTeamService userTeamService,
                        AuthDomainService authDomainService,
                        InvoiceRepository invoiceRepository,
                        AccountingRecordCashRepository accountingRecordCashRepository,
                        AccountingRecordCreditRepository accountingRecordCreditRepository,
                        ContactsRepository contactsRepository,
                        ProductRepository productRepository,
                        UserTeamRepository userTeamRepository,
                        BusinessTeamRepository businessTeamRepository) {
        this.userTeamService = userTeamService;
        this.authDomainService = authDomainService;
        this.invoiceRepository = invoiceRepository;
        this.accountingRecordCashRepository = accountingRecordCashRepository;
        this.accountingRecordCreditRepository = accountingRecordCreditRepository;
        this.contactsRepository = contactsRepository;
        this.productRepository = productRepository;
        this.userTeamRepository = userTeamRepository;
        this.businessTeamRepository = businessTeamRepository;
    }





}
