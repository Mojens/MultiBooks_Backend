package com.monero.multibooks.MultiBooks.Repository.Contacts;

import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import com.monero.multibooks.MultiBooks.Entities.Contacts.Contacts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactsRepository extends JpaRepository<Contacts, Long> {

    Page<Contacts> findAllByBusinessTeam(BusinessTeam businessTeam, Pageable pageable);
    List<Contacts> findAllByBusinessTeam(BusinessTeam businessTeam);

}
