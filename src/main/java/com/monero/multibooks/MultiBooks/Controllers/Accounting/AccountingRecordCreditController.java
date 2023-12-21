package com.monero.multibooks.MultiBooks.Controllers.Accounting;

import com.monero.multibooks.MultiBooks.Dto.Accounting.AccountingRecordCredit.AccountingRecordCreditRequest;
import com.monero.multibooks.MultiBooks.Dto.Shared.ApiResponse;
import com.monero.multibooks.MultiBooks.Service.Accounting.AccountingRecordCreditService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/accounting-record-credit/")
public class AccountingRecordCreditController {


    private final AccountingRecordCreditService accountingRecordCreditService;

    public AccountingRecordCreditController(AccountingRecordCreditService accountingRecordCreditService) {
        this.accountingRecordCreditService = accountingRecordCreditService;
    }


    @GetMapping("/all/{CVRNumber}")
    public ResponseEntity<ApiResponse> getAccountingRecordCredit(@PathVariable int CVRNumber, Pageable pageable, HttpServletRequest httpRequest){
        return ResponseEntity.ok(new ApiResponse(accountingRecordCreditService.getAccountingRecordCredit(CVRNumber, pageable, httpRequest), "Accounting record credit retrieved successfully"));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse> getAccountingRecordCreditById(@PathVariable Long id, HttpServletRequest httpRequest){
        return ResponseEntity.ok(new ApiResponse(accountingRecordCreditService.getAccountingRecordCreditById(id, httpRequest), "Accounting record credit retrieved successfully"));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createAccountingRecordCredit(@RequestBody AccountingRecordCreditRequest request, HttpServletRequest httpRequest){
        return ResponseEntity.ok(new ApiResponse(accountingRecordCreditService.createAccountingRecordCredit(request, httpRequest), "Accounting record credit created successfully"));
    }

    @PatchMapping("/update")
    public ResponseEntity<ApiResponse> updateAccountingRecordCredit(@RequestBody AccountingRecordCreditRequest request, HttpServletRequest httpRequest){
        return ResponseEntity.ok(new ApiResponse(accountingRecordCreditService.updateAccountingRecordCredit(request, httpRequest), "Accounting record credit updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteAccountingRecordCredit(@PathVariable Long id, HttpServletRequest httpRequest){
        return ResponseEntity.ok(new ApiResponse(accountingRecordCreditService.deleteAccountingRecordCredit(id, httpRequest), "Accounting record credit deleted successfully"));
    }
}
