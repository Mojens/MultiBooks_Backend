package com.monero.multibooks.MultiBooks.Service.Product;

import com.monero.multibooks.MultiBooks.Dto.Product.ProductRequest;
import com.monero.multibooks.MultiBooks.Dto.Product.ProductResponse;
import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import com.monero.multibooks.MultiBooks.Entities.Product.Product;
import com.monero.multibooks.MultiBooks.Entities.UserTeam.UserTeam;
import com.monero.multibooks.MultiBooks.Repository.BusinessTeam.BusinessTeamRepository;
import com.monero.multibooks.MultiBooks.Repository.Product.ProductRepository;
import com.monero.multibooks.MultiBooks.Repository.UserTeam.UserTeamRepository;
import com.monero.multibooks.MultiBooks.Service.Auth.AuthService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class ProductService {


    private final ProductRepository productRepository;
    private final BusinessTeamRepository businessTeamRepository;
    private final UserTeamRepository userTeamRepository;
    private final AuthService authService;

    public ProductService(ProductRepository productRepository, AuthService authService,
                          BusinessTeamRepository businessTeamRepository, UserTeamRepository userTeamRepository) {
        this.productRepository = productRepository;
        this.authService = authService;
        this.businessTeamRepository = businessTeamRepository;
        this.userTeamRepository = userTeamRepository;
    }

    public Page<ProductResponse> getProducts(@PathVariable int CVRNumber, HttpServletRequest httpRequest, Pageable pageable){
        BusinessTeam businessTeam = businessTeamRepository.findById(CVRNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));

        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(businessTeam);
        authService.validateUserTeam(userTeams, httpRequest);

        Page<Product> productPage = productRepository.findAllByBusinessTeam(businessTeam, pageable);
        return productPage.map(ProductResponse::new);
    }

    public ProductResponse createProduct(@RequestBody ProductRequest productRequest, HttpServletRequest httpRequest){
        BusinessTeam businessTeam = businessTeamRepository.findById(productRequest.getBusinessCVRNumber()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(businessTeam);
        authService.validateUserTeam(userTeams, httpRequest);

        Product product = new Product(productRequest.getProductName(), productRequest.getProductCode(),
                productRequest.getProductAmount(), productRequest.getProductUnit(), productRequest.getProductPriceExclVAT(),
                productRequest.getProductPriceInclVAT(), productRequest.getProductDescription(), productRequest.getProductAccount());
        product.setBusinessTeam(businessTeam);

        productRepository.save(product);
        return new ProductResponse(product);
    }

    public ProductResponse getProduct(@PathVariable Long productId, HttpServletRequest httpRequest){
        Product foundProduct = productRepository.findById(productId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        BusinessTeam businessTeam = foundProduct.getBusinessTeam();
        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(businessTeam);
        authService.validateUserTeam(userTeams, httpRequest);
        return new ProductResponse(foundProduct);
    }

    public ProductResponse updateProduct(@RequestBody ProductRequest request, HttpServletRequest httpRequest){
        Product foundProduct = productRepository.findById(request.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        BusinessTeam businessTeam = foundProduct.getBusinessTeam();
        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(businessTeam);
        authService.validateUserTeam(userTeams, httpRequest);

        foundProduct.setProductName(request.getProductName());
        foundProduct.setProductCode(request.getProductCode());
        foundProduct.setProductAmount(request.getProductAmount());
        foundProduct.setProductUnit(request.getProductUnit());
        foundProduct.setProductPriceExclVAT(request.getProductPriceExclVAT());
        foundProduct.setProductPriceInclVAT(request.getProductPriceInclVAT());
        foundProduct.setProductDescription(request.getProductDescription());
        foundProduct.setProductAccount(request.getProductAccount());

        productRepository.save(foundProduct);
        return new ProductResponse(foundProduct);
    }

    public ProductResponse deleteProduct(@PathVariable Long id, HttpServletRequest httpRequest){
        Product foundProduct = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        BusinessTeam businessTeam = foundProduct.getBusinessTeam();
        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(businessTeam);
        authService.validateUserTeam(userTeams, httpRequest);

        productRepository.delete(foundProduct);
        return new ProductResponse(foundProduct);
    }


}
