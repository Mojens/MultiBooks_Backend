package com.monero.multibooks.MultiBooks.Controllers.Contacts;

import com.monero.multibooks.MultiBooks.Service.ContactsService.ContactsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/contacts/")
public class ContactsController {


    private final ContactsService contactsService;

    public ContactsController(ContactsService contactsService) {
        this.contactsService = contactsService;
    }
}
