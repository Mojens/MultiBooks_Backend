package com.monero.multibooks.MultiBooks.Configuration;

import com.monero.multibooks.MultiBooks.Dto.Auth.LoginRequest;
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

        User user3 = new User("mohammadmurtada@outlook.dk", "test123");
        userRepository.save(user3);

        BusinessTeam team = BusinessTeam.builder()
                .CVRNumber(12345321)
                .VATNumber("DK12345321")
                .companyName("Monero ApS")
                .address("Testvej 1")
                .city("Testby")
                .zipCode(1234)
                .country("Denmark")
                .phoneNumber("12345678")
                .email("gg@hotmail.com")
                .website("www.monero.dk")
                .accNumber("1234567890")
                .regNumber(1234)
                .bankName("Danske Bank")
                .teamOwner(user3)
                .build();
        businessTeamRepository.save(team);
        UserTeam userTeam = new UserTeam(user3, team);
        userTeamRepository.save(userTeam);

        Contacts contacts = createContact(team);
        Product product = createProduct(team);
        Invoice invoice = createInvoice(team, contacts);
        Invoice invoiceTwo = createInvoiceTwo(team, contacts);
        Invoice invoiceThree = createInvoiceThree(team, contacts);
        Invoice invoiceFour = createInvoiceFour(team, contacts);
        Invoice invoiceFive = createInvoiceFive(team, contacts);
        Invoice invoiceSix = createInvoiceSix(team, contacts);
        Invoice invoiceSeven = createInvoiceSeven(team, contacts);
        createProductToSale(product, invoice);
        createProductToSale(product, invoiceTwo);
        createProductToSale(product, invoiceThree);
        createProductToSale(product, invoiceFour);
        createProductToSale(product, invoiceFive);
        createProductToSale(product, invoiceSix);
        createProductToSale(product, invoiceSeven);
    }

    private void createProductToSale(Product product, Invoice invoice) {
        ProductToSale productToSale = ProductToSale.builder()
                .productPriceAfterDiscount(100)
                .productDiscount(0)
                .product(product)
                .invoice(invoice)
                .productPriceExclVAT(100)
                .productUnit("stk")
                .productAmount(1)
                .build();
        productToSaleRepository.save(productToSale);
    }

    private Invoice createInvoice(BusinessTeam team, Contacts contacts) {
        Invoice invoice = Invoice.builder()
                .businessTeam(team)
                .contact(contacts)
                .total(100)
                .invoiceDate(Instant.now())
                .status(InvoiceStatus.PAID)
                .editedBy("")
                .invoiceTitle("Invoice")
                .invoiceComment("Invoice comment")
                .subTotal(100)
                .subTotalWithVat(125)
                .build();
        invoiceRepository.save(invoice);
        return invoice;
    }
    private Invoice createInvoiceTwo(BusinessTeam team, Contacts contacts) {
        LocalDate fourMonthsAgo = LocalDate.now().minusMonths(4);
        Instant instant = fourMonthsAgo.atStartOfDay().toInstant(ZoneOffset.UTC);
        Invoice invoice = Invoice.builder()
                .businessTeam(team)
                .contact(contacts)
                .total(100)
                .invoiceDate(instant)
                .status(InvoiceStatus.PAID)
                .editedBy("")
                .invoiceTitle("Invoice")
                .invoiceComment("Invoice comment")
                .subTotal(100)
                .subTotalWithVat(125)
                .build();
        invoiceRepository.save(invoice);
        return invoice;
    }
    private Invoice createInvoiceThree(BusinessTeam team, Contacts contacts) {
        LocalDate eighthMonthsAgo = LocalDate.now().minusMonths(8);
        Instant instant = eighthMonthsAgo.atStartOfDay().toInstant(ZoneOffset.UTC);
        Invoice invoice = Invoice.builder()
                .businessTeam(team)
                .contact(contacts)
                .total(100)
                .invoiceDate(instant)
                .status(InvoiceStatus.CONFIRMED)
                .editedBy("")
                .invoiceTitle("Invoice")
                .invoiceComment("Invoice comment")
                .subTotal(100)
                .subTotalWithVat(125)
                .build();
        invoiceRepository.save(invoice);
        return invoice;
    }
    private Invoice createInvoiceSeven(BusinessTeam team, Contacts contacts) {
        LocalDate eighthMonthsAgo = LocalDate.now().minusMonths(8);
        Instant instant = eighthMonthsAgo.atStartOfDay().toInstant(ZoneOffset.UTC);
        Invoice invoice = Invoice.builder()
                .businessTeam(team)
                .contact(contacts)
                .total(150)
                .invoiceDate(instant)
                .status(InvoiceStatus.PAID)
                .editedBy("")
                .invoiceTitle("Invoice")
                .invoiceComment("Invoice comment")
                .subTotal(150)
                .subTotalWithVat(175)
                .build();
        invoiceRepository.save(invoice);
        return invoice;
    }
    private Invoice createInvoiceFour(BusinessTeam team, Contacts contacts) {
        LocalDate elevenMonthsAgo = LocalDate.now().minusMonths(11);
        Instant instant = elevenMonthsAgo.atStartOfDay().toInstant(ZoneOffset.UTC);
        Invoice invoice = Invoice.builder()
                .businessTeam(team)
                .contact(contacts)
                .total(100)
                .invoiceDate(instant)
                .status(InvoiceStatus.OVERDUE)
                .editedBy("")
                .invoiceTitle("Invoice")
                .invoiceComment("Invoice comment")
                .subTotal(100)
                .subTotalWithVat(125)
                .build();
        invoiceRepository.save(invoice);
        return invoice;
    }
    private Invoice createInvoiceFive(BusinessTeam team, Contacts contacts) {
        LocalDate elevenMonthsAgo = LocalDate.now().minusMonths(11);
        Instant instant = elevenMonthsAgo.atStartOfDay().toInstant(ZoneOffset.UTC);
        Invoice invoice = Invoice.builder()
                .businessTeam(team)
                .contact(contacts)
                .total(100)
                .invoiceDate(instant)
                .status(InvoiceStatus.CANCELLED)
                .editedBy("")
                .invoiceTitle("Invoice")
                .invoiceComment("Invoice comment")
                .subTotal(100)
                .subTotalWithVat(125)
                .build();
        invoiceRepository.save(invoice);
        return invoice;
    }
    private Invoice createInvoiceSix(BusinessTeam team, Contacts contacts) {
        LocalDate elevenMonthsAgo = LocalDate.now().minusMonths(11);
        Instant instant = elevenMonthsAgo.atStartOfDay().toInstant(ZoneOffset.UTC);
        Invoice invoice = Invoice.builder()
                .businessTeam(team)
                .contact(contacts)
                .total(100)
                .invoiceDate(instant)
                .status(InvoiceStatus.OVERPAID)
                .editedBy("")
                .invoiceTitle("Invoice")
                .invoiceComment("Invoice comment")
                .subTotal(100)
                .subTotalWithVat(125)
                .build();
        invoiceRepository.save(invoice);
        return invoice;
    }

    private Product createProduct(BusinessTeam team) {
        Product product = Product.builder()
                .productName("Test")
                .productDescription("Test")
                .productPriceExclVAT(100)
                .productPriceInclVAT(125)
                .businessTeam(team)
                .productAccount("1000")
                .productCode(1000)
                .productUnit("stk")
                .productDescription("Test description")
                .build();
        productRepository.save(product);
        return product;
    }

    private Contacts createContact(BusinessTeam team) {
        Contacts contact = Contacts.builder()
                .CVRNumber(34987654)
                .attentionPerson("Murtada")
                .companyName("cap ApS")
                .businessTeam(team)
                .companyAddress("Testvej 1")
                .companyCity("Testby")
                .companyCountry("Denmark")
                .phoneNumber("12345678")
                .companyZipCode("1234")
                .website("www.cap.dk")
                .eInvoiceRecipientType("CVR")
                .paymentTermsDays(14)
                .paymentTermsMethod("Bank")
                .build();
        contactsRepository.save(contact);
        return contact;
    }
}