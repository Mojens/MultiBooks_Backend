package com.monero.multibooks.MultiBooks.Repository.ProductToSale;

import com.monero.multibooks.MultiBooks.Entities.ProductToSale.ProductToSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductToSaleRepository extends JpaRepository<ProductToSale, Long> {
}
