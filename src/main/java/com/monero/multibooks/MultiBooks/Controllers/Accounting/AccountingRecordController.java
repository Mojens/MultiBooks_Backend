package com.monero.multibooks.MultiBooks.Controllers.Accounting;

import com.monero.multibooks.MultiBooks.Dto.Accounting.AccountingRecord.AccountingRecordRequest;
import com.monero.multibooks.MultiBooks.Dto.Accounting.AccountingRecord.AccountingRecordResponse;
import com.monero.multibooks.MultiBooks.Dto.Accounting.AccountingRecord.GetAccountingRecordRequest;
import com.monero.multibooks.MultiBooks.Dto.Shared.ApiResponse;
import com.monero.multibooks.MultiBooks.Service.Accounting.AccountingRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/accounting-record/")
public class AccountingRecordController {


    private final AccountingRecordService accountingRecordService;

    public AccountingRecordController(AccountingRecordService accountingRecordService) {
        this.accountingRecordService = accountingRecordService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createAccountingRecord(@RequestBody AccountingRecordRequest request, HttpServletRequest httpRequest) {
        AccountingRecordResponse response = accountingRecordService.createAccountingRecord(request, httpRequest);
        return ResponseEntity.ok(new ApiResponse(response, "AccountingRecord created"));
    }

    @PatchMapping("/update")
    public ResponseEntity<ApiResponse> updateAccountingRecord(@RequestBody AccountingRecordRequest request, HttpServletRequest httpRequest) {
        AccountingRecordResponse response = accountingRecordService.updateAccountingRecord(request, httpRequest);
        return ResponseEntity.ok(new ApiResponse(response, "AccountingRecord updated"));
    }

    @PostMapping("/get")
    public ResponseEntity<ApiResponse> getAccountingRecord(@RequestBody GetAccountingRecordRequest request, HttpServletRequest httpRequest) {
        List<AccountingRecordResponse> response = accountingRecordService.getAccountingRecords(request, httpRequest);
        return ResponseEntity.ok(new ApiResponse(response, "AccountingRecords retrieved"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteAccountingRecord(@PathVariable Long id, HttpServletRequest httpRequest) {
        AccountingRecordResponse accountingRecordResponse = accountingRecordService.deleteAccountingRecord(id, httpRequest);
        return ResponseEntity.ok(new ApiResponse(accountingRecordResponse, "AccountingRecord deleted"));
    }

}
