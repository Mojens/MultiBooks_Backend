package com.monero.multibooks.MultiBooks.Service.Product;

import com.monero.multibooks.MultiBooks.Repository.Product.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {


    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
