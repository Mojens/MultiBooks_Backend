package com.monero.multibooks.MultiBooks.Controllers.ProducToSale;

import com.monero.multibooks.MultiBooks.Dto.ProductToSale.ProductToSaleRequest;
import com.monero.multibooks.MultiBooks.Dto.ProductToSale.ProductToSaleResponse;
import com.monero.multibooks.MultiBooks.Dto.Shared.ApiResponse;
import com.monero.multibooks.MultiBooks.Service.ProductToSale.ProductToSaleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/product-to-sale/")
public class ProductToSaleController {

    private final ProductToSaleService productToSaleService;

    public ProductToSaleController(ProductToSaleService productToSaleService) {
        this.productToSaleService = productToSaleService;
    }

    @GetMapping("/invoice/{invoiceNumber}")
    public ResponseEntity<ApiResponse> getProductsOnInvoice(@PathVariable Long invoiceNumber, HttpServletRequest httpRequest) {
        List<ProductToSaleResponse> products = productToSaleService.getProductsOnInvoice(invoiceNumber, httpRequest);
        return ResponseEntity.ok(new ApiResponse(products, "Products on invoice fetched successfully"));
    }

    @PostMapping("/add/{invoiceNumber}")
    public ResponseEntity<ApiResponse> addProductToInvoice(@RequestBody ProductToSaleRequest request, @PathVariable Long invoiceNumber, HttpServletRequest httpRequest) {
        ProductToSaleResponse product = productToSaleService.addProductToInvoice(request, invoiceNumber, httpRequest);
        return ResponseEntity.ok(new ApiResponse(product, "Product added to invoice successfully"));
    }

    @PatchMapping("/edit/{invoiceNumber}")
    public ResponseEntity<ApiResponse> editProductOnInvoice(@RequestBody ProductToSaleRequest request, @PathVariable Long invoiceNumber, HttpServletRequest httpRequest) {
        ProductToSaleResponse product = productToSaleService.editProductToSale(request, invoiceNumber, httpRequest);
        return ResponseEntity.ok(new ApiResponse(product, "Product edited successfully"));
    }

    @DeleteMapping("/delete/all/{invoiceNumber}")
    public ResponseEntity<ApiResponse> deleteAllProductsOnInvoice(@PathVariable Long invoiceNumber, HttpServletRequest httpRequest) {
        productToSaleService.deleteAllProductsOnInvoice(invoiceNumber, httpRequest);
        return ResponseEntity.ok(new ApiResponse(true, "All products on invoice removed successfully"));
    }

    @DeleteMapping("/delete/{productId}/invoice/{invoiceNumber}")
    public ResponseEntity<ApiResponse> deleteProductOnInvoice(@PathVariable Long invoiceNumber, @PathVariable Long productId, HttpServletRequest httpRequest) {
        ProductToSaleResponse product = productToSaleService.deleteProductOnInvoice(invoiceNumber, productId, httpRequest);
        return ResponseEntity.ok(new ApiResponse(product, "Product removed from invoice successfully"));
    }

}
