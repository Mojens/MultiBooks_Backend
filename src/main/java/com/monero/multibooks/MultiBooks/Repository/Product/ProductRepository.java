package com.monero.multibooks.MultiBooks.Repository.Product;

import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import com.monero.multibooks.MultiBooks.Entities.Product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByBusinessTeam(BusinessTeam businessTeam, Pageable pageable);

    List<Product> findAllByBusinessTeam(BusinessTeam businessTeam);

}
