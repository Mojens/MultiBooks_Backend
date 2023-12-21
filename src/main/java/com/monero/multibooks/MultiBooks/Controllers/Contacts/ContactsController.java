package com.monero.multibooks.MultiBooks.Controllers.Contacts;

import com.monero.multibooks.MultiBooks.Dto.Contacts.ContactsRequest;
import com.monero.multibooks.MultiBooks.Dto.Contacts.ContactsResponse;
import com.monero.multibooks.MultiBooks.Dto.Shared.ApiResponse;
import com.monero.multibooks.MultiBooks.Service.Contacts.ContactsService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/contacts/")
public class ContactsController {


    private final ContactsService contactsService;

    public ContactsController(ContactsService contactsService) {
        this.contactsService = contactsService;
    }

    @GetMapping("/all/{CVRNumber}")
    public ResponseEntity<ApiResponse> getContacts(@PathVariable int CVRNumber,
                                                   HttpServletRequest httpRequest,
                                                   Pageable pageable){
        return ResponseEntity.ok(new ApiResponse(contactsService.getContacts(CVRNumber, httpRequest, pageable), "Contacts retrieved successfully"));
    }


    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createContact(@RequestBody ContactsRequest contactsRequest, HttpServletRequest httpRequest){
        return ResponseEntity.ok(new ApiResponse(contactsService.createContact(contactsRequest, httpRequest), "Contact created successfully"));
    }

    @PatchMapping("/update")
    public ResponseEntity<ApiResponse> updateContact( @RequestBody ContactsRequest contactsRequest, HttpServletRequest httpRequest){
        return ResponseEntity.ok(new ApiResponse(contactsService.updateContact(contactsRequest, httpRequest), "Contact updated successfully"));
    }

    @DeleteMapping("/delete/{contactId}")
    public ResponseEntity<ApiResponse> deleteContact(@PathVariable Long contactId, HttpServletRequest httpRequest){
        ContactsResponse response = contactsService.deleteContact(contactId, httpRequest);
        return ResponseEntity.ok(new ApiResponse(response, "Contact deleted successfully"));
    }


}
