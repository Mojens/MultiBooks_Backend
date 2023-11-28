package com.monero.multibooks.MultiBooks.Repository.ContactsRepository;

import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import com.monero.multibooks.MultiBooks.Entities.Contacts.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactsRepository extends JpaRepository<Contacts, Long> {


    List<Contacts> findAllByBusinessTeam(BusinessTeam businessTeam);

}
