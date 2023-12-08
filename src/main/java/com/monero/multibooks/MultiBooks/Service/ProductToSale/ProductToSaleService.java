package com.monero.multibooks.MultiBooks.Service.ProductToSale;

import com.monero.multibooks.MultiBooks.DomainService.Auth.AuthDomainService;
import com.monero.multibooks.MultiBooks.Dto.ProductToSale.ProductToSaleRequest;
import com.monero.multibooks.MultiBooks.Dto.ProductToSale.ProductToSaleResponse;
import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import com.monero.multibooks.MultiBooks.Entities.Invoice.Invoice;
import com.monero.multibooks.MultiBooks.Entities.Product.Product;
import com.monero.multibooks.MultiBooks.Entities.ProductToSale.ProductToSale;
import com.monero.multibooks.MultiBooks.Entities.UserTeam.UserTeam;
import com.monero.multibooks.MultiBooks.Repository.Invoice.InvoiceRepository;
import com.monero.multibooks.MultiBooks.Repository.Product.ProductRepository;
import com.monero.multibooks.MultiBooks.Repository.ProductToSale.ProductToSaleRepository;
import com.monero.multibooks.MultiBooks.Repository.UserTeam.UserTeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductToSaleService {


    private final ProductToSaleRepository productToSaleRepository;
    private final InvoiceRepository invoiceRepository;
    private final AuthDomainService authDomainService;
    private final UserTeamRepository userTeamRepository;
    private final ProductRepository productRepository;

    public ProductToSaleService(ProductToSaleRepository productToSaleRepository,
                                InvoiceRepository invoiceRepository,
                                AuthDomainService authDomainService,
                                UserTeamRepository userTeamRepository,
                                ProductRepository productRepository) {
        this.productToSaleRepository = productToSaleRepository;
        this.invoiceRepository = invoiceRepository;
        this.authDomainService = authDomainService;
        this.userTeamRepository = userTeamRepository;
        this.productRepository = productRepository;
    }


    public List<ProductToSaleResponse> getProductsOnInvoice(@PathVariable Long invoiceNumber, HttpServletRequest httpRequest) {
        Invoice foundInvoice = invoiceRepository.findById(invoiceNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice not found"));
        BusinessTeam businessTeam = foundInvoice.getBusinessTeam();
        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(businessTeam);
        authDomainService.validateUserTeam(userTeams, httpRequest);
        List<ProductToSale> products = productToSaleRepository.findAllByInvoice(foundInvoice);
        return products.stream().map(ProductToSaleResponse::new).toList();
    }

    @Transactional
    public void deleteAllProductsOnInvoice(@PathVariable Long invoiceNumber, HttpServletRequest httpRequest) {
        Invoice foundInvoice = invoiceRepository.findById(invoiceNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice not found"));
        BusinessTeam businessTeam = foundInvoice.getBusinessTeam();
        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(businessTeam);
        authDomainService.validateUserTeam(userTeams, httpRequest);
        productToSaleRepository.deleteAllByInvoice(foundInvoice);
    }

    public ProductToSaleResponse deleteProductOnInvoice(@PathVariable Long invoiceNumber, @PathVariable Long productId, HttpServletRequest httpRequest) {
        Invoice foundInvoice = invoiceRepository.findById(invoiceNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice not found"));
        BusinessTeam businessTeam = foundInvoice.getBusinessTeam();
        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(businessTeam);
        authDomainService.validateUserTeam(userTeams, httpRequest);
        ProductToSale product = productToSaleRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        productToSaleRepository.delete(product);
        return new ProductToSaleResponse(product);
    }

    public ProductToSaleResponse addProductToInvoice(@RequestBody ProductToSaleRequest request, @PathVariable Long invoiceNumber, HttpServletRequest httpRequest) {
        Invoice foundInvoice = invoiceRepository.findById(invoiceNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice not found"));
        BusinessTeam businessTeam = foundInvoice.getBusinessTeam();
        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(businessTeam);
        authDomainService.validateUserTeam(userTeams, httpRequest);

        Product foundProduct = productRepository.findById(request.getProductId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        ProductToSale product = ProductToSale.builder()
                .productUnit(request.getProductUnit())
                .productAmount(request.getProductAmount())
                .productDiscount(request.getProductDiscount())
                .productPriceExclVAT(request.getProductPriceExclVAT())
                .productPriceAfterDiscount(request.getProductPriceAfterDiscount())
                .invoice(foundInvoice)
                .product(foundProduct)
                .build();
        productToSaleRepository.save(product);
        return new ProductToSaleResponse(product);
    }

    public ProductToSaleResponse editProductToSale(@RequestBody ProductToSaleRequest request, @PathVariable Long invoiceNumber, HttpServletRequest httpRequest){
        Invoice foundInvoice = invoiceRepository.findById(invoiceNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice not found"));
        BusinessTeam businessTeam = foundInvoice.getBusinessTeam();
        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(businessTeam);
        authDomainService.validateUserTeam(userTeams, httpRequest);

        ProductToSale foundProductToSale = productToSaleRepository.findById(request.getProductToSaleId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product to sale not found"));
        Product foundProduct = productRepository.findById(request.getProductId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        foundProductToSale.setProductUnit(request.getProductUnit());
        foundProductToSale.setProductAmount(request.getProductAmount());
        foundProductToSale.setProductDiscount(request.getProductDiscount());
        foundProductToSale.setProductPriceExclVAT(request.getProductPriceExclVAT());
        foundProductToSale.setProductPriceAfterDiscount(request.getProductPriceAfterDiscount());
        foundProductToSale.setProduct(foundProduct);

        productToSaleRepository.save(foundProductToSale);
        return new ProductToSaleResponse(foundProductToSale);
    }


}
