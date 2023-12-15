package com.monero.multibooks.MultiBooks.Controllers.Accounting;

import com.monero.multibooks.MultiBooks.Dto.Accounting.AccountingRecordCash.AccountingRecordCashRequest;
import com.monero.multibooks.MultiBooks.Dto.Shared.ApiResponse;
import com.monero.multibooks.MultiBooks.Service.Accounting.AccountingRecordCashService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/api/accounting-record-cash/")
public class AccountingRecordCashController {

    private final AccountingRecordCashService accountingRecordCashService;

    public AccountingRecordCashController(AccountingRecordCashService accountingRecordCashService) {
        this.accountingRecordCashService = accountingRecordCashService;
    }

    @GetMapping("/all/{CVRNumber}")
    public ResponseEntity<ApiResponse> getAccountingRecordCash(@PathVariable int CVRNumber, Pageable pageable, HttpServletRequest httpRequest){
        return ResponseEntity.ok(new ApiResponse(accountingRecordCashService.getAccountingRecordCash(CVRNumber, pageable, httpRequest), "Accounting record cash retrieved successfully"));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createAccountingRecordCash(@RequestBody AccountingRecordCashRequest request, HttpServletRequest httpRequest){
        return ResponseEntity.ok(new ApiResponse(accountingRecordCashService.createAccountingRecordCash(request, httpRequest), "Accounting record cash created successfully"));
    }

    @PatchMapping("/update")
    public ResponseEntity<ApiResponse> updateAccountingRecordCash(@RequestBody AccountingRecordCashRequest request, HttpServletRequest httpRequest){
        return ResponseEntity.ok(new ApiResponse(accountingRecordCashService.updateAccountingRecordCash(request, httpRequest), "Accounting record cash updated successfully"));
    }

    @GetMapping("/get/{accountingRecordCashId}")
    public ResponseEntity<ApiResponse> getAccountingRecordCashById(@PathVariable Long accountingRecordCashId, HttpServletRequest httpRequest){
        return ResponseEntity.ok(new ApiResponse(accountingRecordCashService.getAccountingRecordCashById(accountingRecordCashId, httpRequest), "Accounting record cash retrieved successfully"));
    }

    @DeleteMapping("/delete/{accountingRecordCashId}")
    public ResponseEntity<ApiResponse> deleteAccountingRecordCash(@PathVariable Long accountingRecordCashId, HttpServletRequest httpRequest){
        return ResponseEntity.ok(new ApiResponse(accountingRecordCashService.deleteAccountingRecordCash(accountingRecordCashId, httpRequest), "Accounting record cash deleted successfully"));
    }






}
