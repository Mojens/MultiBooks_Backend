package com.monero.multibooks.MultiBooks.Service.ContactsService;

import com.monero.multibooks.MultiBooks.Dto.Contacts.ContactsRequest;
import com.monero.multibooks.MultiBooks.Dto.Contacts.ContactsResponse;
import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import com.monero.multibooks.MultiBooks.Entities.Contacts.Contacts;
import com.monero.multibooks.MultiBooks.Entities.UserTeam.UserTeam;
import com.monero.multibooks.MultiBooks.Repository.BusinessTeam.BusinessTeamRepository;
import com.monero.multibooks.MultiBooks.Repository.Contacts.ContactsRepository;
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
public class ContactsService {


    private final ContactsRepository contactsRepository;
    private final AuthService authService;
    private final BusinessTeamRepository businessTeamRepository;
    private final UserTeamRepository userTeamRepository;

    public ContactsService(ContactsRepository contactsRepository,
                           AuthService authService, UserTeamRepository userTeamRepository,
                           BusinessTeamRepository businessTeamRepository) {
        this.contactsRepository = contactsRepository;
        this.authService = authService;
        this.businessTeamRepository = businessTeamRepository;
        this.userTeamRepository = userTeamRepository;
    }


    public Page<ContactsResponse> getContacts(int CVRNumber, HttpServletRequest httpRequest, Pageable pageable){
        BusinessTeam businessTeam = businessTeamRepository.findById(CVRNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));

        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(businessTeam);
        authService.validateUserTeam(userTeams, httpRequest);

        Page<Contacts> contactsPage = contactsRepository.findAllByBusinessTeam(businessTeam, pageable);
        return contactsPage.map(ContactsResponse::new);
    }


    public ContactsResponse createContact(@RequestBody ContactsRequest contactsRequest, HttpServletRequest httpRequest){
        BusinessTeam businessTeam = businessTeamRepository.findById(contactsRequest.getBusinessTeamCVRNumber()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(businessTeam);
        authService.validateUserTeam(userTeams, httpRequest);
        System.out.println(contactsRequest);
        Contacts contact = new Contacts(contactsRequest.getCompanyName(),
                contactsRequest.getCompanyAddress(),contactsRequest.getCompanyCity(),
                contactsRequest.getCompanyZipCode(), contactsRequest.getCompanyCountry(),
                contactsRequest.getCVRNumber(), contactsRequest.getEmail(), contactsRequest.getPhoneNumber(),
                contactsRequest.getWebsite(), contactsRequest.getAttentionPerson(), contactsRequest.getEInvoiceRecipientType(),
                contactsRequest.getPaymentTermsMethod(), contactsRequest.getPaymentTermsDays());
        contact.setBusinessTeam(businessTeam);
        Contacts createdContact = contactsRepository.save(contact);
        return new ContactsResponse(createdContact);
    }

    public ContactsResponse updateContact(@RequestBody ContactsRequest contactsRequest, HttpServletRequest httpRequest){
        BusinessTeam businessTeam = businessTeamRepository.findById(contactsRequest.getBusinessTeamCVRNumber()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(businessTeam);
        authService.validateUserTeam(userTeams, httpRequest);
        Contacts contact = contactsRepository.findById(contactsRequest.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        contact.setCompanyName(contactsRequest.getCompanyName());
        contact.setCVRNumber(contactsRequest.getCVRNumber());
        contact.setEmail(contactsRequest.getEmail());
        contact.setPhoneNumber(contactsRequest.getPhoneNumber());
        contact.setWebsite(contactsRequest.getWebsite());
        contact.setAttentionPerson(contactsRequest.getAttentionPerson());
        contact.setEInvoiceRecipientType(contactsRequest.getEInvoiceRecipientType());
        contact.setPaymentTermsMethod(contactsRequest.getPaymentTermsMethod());
        contact.setPaymentTermsDays(contactsRequest.getPaymentTermsDays());

        Contacts updatedContact = contactsRepository.save(contact);
        return new ContactsResponse(updatedContact);
    }

    public ContactsResponse deleteContact(@PathVariable Long id, HttpServletRequest httpRequest){
        Contacts contact = contactsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));
        BusinessTeam businessTeam = contact.getBusinessTeam();
        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(businessTeam);
        authService.validateUserTeam(userTeams, httpRequest);
        contactsRepository.delete(contact);
        return new ContactsResponse(contact);
    }




}
