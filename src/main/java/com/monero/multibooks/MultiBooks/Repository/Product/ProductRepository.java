package com.monero.multibooks.MultiBooks.Repository.Product;

import com.monero.multibooks.MultiBooks.Entities.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
