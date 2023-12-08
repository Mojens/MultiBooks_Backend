package com.monero.multibooks.MultiBooks.Repository.ProductToSale;

import com.monero.multibooks.MultiBooks.Entities.Invoice.Invoice;
import com.monero.multibooks.MultiBooks.Entities.ProductToSale.ProductToSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductToSaleRepository extends JpaRepository<ProductToSale, Long> {

    void deleteAllByInvoice(Invoice invoice);

    List<ProductToSale> findAllByInvoice(Invoice invoice);
}
