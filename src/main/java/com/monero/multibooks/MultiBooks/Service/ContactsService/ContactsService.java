package com.monero.multibooks.MultiBooks.Service.ContactsService;

import com.monero.multibooks.MultiBooks.Dto.Contacts.ContactsResponse;
import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import com.monero.multibooks.MultiBooks.Entities.Contacts.Contacts;
import com.monero.multibooks.MultiBooks.Entities.UserTeam.UserTeam;
import com.monero.multibooks.MultiBooks.Repository.BusinessTeam.BusinessTeamRepository;
import com.monero.multibooks.MultiBooks.Repository.ContactsRepository.ContactsRepository;
import com.monero.multibooks.MultiBooks.Repository.UserTeam.UserTeamRepository;
import com.monero.multibooks.MultiBooks.Service.Auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
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


    private List<ContactsResponse> getContacts(@PathVariable int CVRNumber, HttpServletRequest request){
        BusinessTeam businessTeam = businessTeamRepository.findById(CVRNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(businessTeam);

        authService.validateUserTeam(userTeams, request);

        List<Contacts> contacts = contactsRepository.findAllByBusinessTeam(businessTeam);
        return contacts.stream().map(ContactsResponse::new).toList();
    }




}
