package com.monero.multibooks.MultiBooks.Configuration;

import com.monero.multibooks.MultiBooks.Entities.Accounting.AccountingRecord;
import com.monero.multibooks.MultiBooks.Entities.Accounting.AccountingRecordCash;
import com.monero.multibooks.MultiBooks.Entities.Accounting.AccountingRecordCredit;
import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import com.monero.multibooks.MultiBooks.Entities.Contacts.Contacts;
import com.monero.multibooks.MultiBooks.Entities.Invoice.Invoice;
import com.monero.multibooks.MultiBooks.Entities.Invoice.InvoiceStatus;
import com.monero.multibooks.MultiBooks.Entities.Product.Product;
import com.monero.multibooks.MultiBooks.Entities.ProductToSale.ProductToSale;
import com.monero.multibooks.MultiBooks.Entities.User.User;
import com.monero.multibooks.MultiBooks.Entities.UserTeam.UserTeam;
import com.monero.multibooks.MultiBooks.Repository.Accounting.AccountingRecordCashRepository;
import com.monero.multibooks.MultiBooks.Repository.Accounting.AccountingRecordCreditRepository;
import com.monero.multibooks.MultiBooks.Repository.Accounting.AccountingRecordRepository;
import com.monero.multibooks.MultiBooks.Repository.BusinessTeam.BusinessTeamRepository;
import com.monero.multibooks.MultiBooks.Repository.Contacts.ContactsRepository;
import com.monero.multibooks.MultiBooks.Repository.Invoice.InvoiceRepository;
import com.monero.multibooks.MultiBooks.Repository.Product.ProductRepository;
import com.monero.multibooks.MultiBooks.Repository.ProductToSale.ProductToSaleRepository;
import com.monero.multibooks.MultiBooks.Repository.User.UserRepository;
import com.monero.multibooks.MultiBooks.Repository.UserTeam.UserTeamRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;


@Controller
public class Setup implements ApplicationRunner {

    private final UserRepository userRepository;
    private final BusinessTeamRepository businessTeamRepository;
    private final UserTeamRepository userTeamRepository;
    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;
    private final ProductToSaleRepository productToSaleRepository;
    private final AccountingRecordCreditRepository accountingRecordCreditRepository;
    private final AccountingRecordCashRepository accountingRecordCashRepository;
    private final AccountingRecordRepository accountingRecordRepository;
    private final ContactsRepository contactsRepository;


    public Setup(UserRepository userRepository,
                 BusinessTeamRepository businessTeamRepository,
                 UserTeamRepository userTeamRepository,
                 InvoiceRepository invoiceRepository,
                 ProductRepository productRepository,
                 ProductToSaleRepository productToSaleRepository,
                 AccountingRecordCreditRepository accountingRecordCreditRepository,
                 AccountingRecordCashRepository accountingRecordCashRepository,
                 AccountingRecordRepository accountingRecordRepository,
                 ContactsRepository contactsRepository) {
        this.userRepository = userRepository;
        this.businessTeamRepository = businessTeamRepository;
        this.userTeamRepository = userTeamRepository;
        this.invoiceRepository = invoiceRepository;
        this.productRepository = productRepository;
        this.productToSaleRepository = productToSaleRepository;
        this.accountingRecordCreditRepository = accountingRecordCreditRepository;
        this.accountingRecordCashRepository = accountingRecordCashRepository;
        this.accountingRecordRepository = accountingRecordRepository;
        this.contactsRepository = contactsRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
    }
}