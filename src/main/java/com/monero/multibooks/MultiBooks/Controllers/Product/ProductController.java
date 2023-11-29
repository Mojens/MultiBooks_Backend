package com.monero.multibooks.MultiBooks.Controllers.Product;

import com.monero.multibooks.MultiBooks.Dto.Product.ProductRequest;
import com.monero.multibooks.MultiBooks.Dto.Shared.ApiResponse;
import com.monero.multibooks.MultiBooks.Service.Product.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/api/product/")
public class ProductController {


    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getProduct(@PathVariable Long id, HttpServletRequest httpRequest){
        return ResponseEntity.ok(new ApiResponse(productService.getProduct(id, httpRequest),"Product retrieved successfully"));
    }

    @GetMapping("/all/{CVRNumber}")
    public ResponseEntity<ApiResponse> getProducts(@PathVariable int CVRNumber, HttpServletRequest httpRequest, Pageable pageable){
        return ResponseEntity.ok(new ApiResponse(productService.getProducts(CVRNumber, httpRequest, pageable), "Products retrieved successfully"));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductRequest productRequest, HttpServletRequest httpRequest){
        return ResponseEntity.ok(new ApiResponse(productService.createProduct(productRequest, httpRequest), "Product created successfully"));
    }

    @PatchMapping("/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductRequest productRequest, HttpServletRequest httpRequest){
        return ResponseEntity.ok(new ApiResponse(productService.updateProduct(productRequest, httpRequest), "Product updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id, HttpServletRequest httpRequest){
        return ResponseEntity.ok(new ApiResponse( productService.deleteProduct(id, httpRequest), "Product deleted successfully"));
    }



}
