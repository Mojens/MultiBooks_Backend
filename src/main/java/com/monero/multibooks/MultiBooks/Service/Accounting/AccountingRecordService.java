package com.monero.multibooks.MultiBooks.Service.Accounting;

import com.monero.multibooks.MultiBooks.DomainService.Auth.AuthDomainService;
import com.monero.multibooks.MultiBooks.Dto.Accounting.AccountingRecord.AccountingRecordRequest;
import com.monero.multibooks.MultiBooks.Dto.Accounting.AccountingRecord.AccountingRecordResponse;
import com.monero.multibooks.MultiBooks.Dto.Accounting.AccountingRecord.GetAccountingRecordRequest;
import com.monero.multibooks.MultiBooks.Entities.Accounting.AccountingRecord;
import com.monero.multibooks.MultiBooks.Entities.Accounting.AccountingRecordCash;
import com.monero.multibooks.MultiBooks.Entities.Accounting.AccountingRecordCredit;
import com.monero.multibooks.MultiBooks.Repository.Accounting.AccountingRecordCashRepository;
import com.monero.multibooks.MultiBooks.Repository.Accounting.AccountingRecordCreditRepository;
import com.monero.multibooks.MultiBooks.Repository.Accounting.AccountingRecordRepository;
import com.monero.multibooks.MultiBooks.Repository.BusinessTeam.BusinessTeamRepository;
import com.monero.multibooks.MultiBooks.Service.UserTeam.UserTeamService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AccountingRecordService {


    private final AccountingRecordRepository accountingRecordRepository;
    private final AccountingRecordCashRepository accountingRecordCashRepository;
    private final AccountingRecordCreditRepository accountingRecordCreditRepository;
    private final AuthDomainService authDomainService;
    private final BusinessTeamRepository businessTeamRepository;
    private final UserTeamService userTeamService;

    public AccountingRecordService(AccountingRecordRepository accountingRecordRepository,
                                   AccountingRecordCashRepository accountingRecordCashRepository,
                                   AccountingRecordCreditRepository accountingRecordCreditRepository,
                                   AuthDomainService authDomainService, BusinessTeamRepository businessTeamRepository,
                                   UserTeamService userTeamService) {
        this.accountingRecordRepository = accountingRecordRepository;
        this.accountingRecordCashRepository = accountingRecordCashRepository;
        this.accountingRecordCreditRepository = accountingRecordCreditRepository;
        this.authDomainService = authDomainService;
        this.businessTeamRepository = businessTeamRepository;
        this.userTeamService = userTeamService;
    }

    public AccountingRecordResponse createAccountingRecord(@RequestBody AccountingRecordRequest request, HttpServletRequest httpRequest) {
        if (request.getAccountingRecordCashId() == 0) {
            AccountingRecordCredit accountingRecordCredit = accountingRecordCreditRepository.findById(request.getAccountingRecordCreditId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AccountingRecordCredit not found"));
            authDomainService.validateUserTeam(userTeamService.getUserTeams(accountingRecordCredit.getBusinessTeam().getCVRNumber()), httpRequest);
            AccountingRecord accountingRecord = AccountingRecord.builder()
                    .vat(request.getVat())
                    .account(request.getAccount())
                    .description(request.getDescription())
                    .priceInclVat(request.getPriceInclVat())
                    .accountingRecordCredit(accountingRecordCredit)
                    .build();
            accountingRecordRepository.save(accountingRecord);
            return new AccountingRecordResponse(accountingRecord);
        } else {
            AccountingRecordCash accountingRecordCash = accountingRecordCashRepository.findById(request.getAccountingRecordCashId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AccountingRecordCash not found"));
            authDomainService.validateUserTeam(userTeamService.getUserTeams(accountingRecordCash.getBusinessTeam().getCVRNumber()), httpRequest);
            AccountingRecord accountingRecord = AccountingRecord.builder()
                    .vat(request.getVat())
                    .account(request.getAccount())
                    .description(request.getDescription())
                    .priceInclVat(request.getPriceInclVat())
                    .accountingRecordCash(accountingRecordCash)
                    .build();
            accountingRecordRepository.save(accountingRecord);
            return new AccountingRecordResponse(accountingRecord);
        }
    }

    public AccountingRecordResponse updateAccountingRecord(@RequestBody AccountingRecordRequest request, HttpServletRequest httpRequest) {
        AccountingRecord accountingRecord = accountingRecordRepository.findById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AccountingRecord not found"));
        if (accountingRecord.getAccountingRecordCash() != null) {
            authDomainService.validateUserTeam(userTeamService.getUserTeams(accountingRecord.getAccountingRecordCash().getBusinessTeam().getCVRNumber()), httpRequest);
        } else {
            authDomainService.validateUserTeam(userTeamService.getUserTeams(accountingRecord.getAccountingRecordCredit().getBusinessTeam().getCVRNumber()), httpRequest);
        }
        accountingRecord.setVat(request.getVat());
        accountingRecord.setAccount(request.getAccount());
        accountingRecord.setDescription(request.getDescription());
        accountingRecord.setPriceInclVat(request.getPriceInclVat());
        accountingRecordRepository.save(accountingRecord);
        return new AccountingRecordResponse(accountingRecord);
    }

    public AccountingRecordResponse deleteAccountingRecord(@RequestBody AccountingRecordRequest request, HttpServletRequest httpRequest) {
        AccountingRecord accountingRecord = accountingRecordRepository.findById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AccountingRecord not found"));
        if (accountingRecord.getAccountingRecordCash() != null) {
            authDomainService.validateUserTeam(userTeamService.getUserTeams(accountingRecord.getAccountingRecordCash().getBusinessTeam().getCVRNumber()), httpRequest);
        } else {
            authDomainService.validateUserTeam(userTeamService.getUserTeams(accountingRecord.getAccountingRecordCredit().getBusinessTeam().getCVRNumber()), httpRequest);
        }
        accountingRecordRepository.delete(accountingRecord);
        return new AccountingRecordResponse(accountingRecord);
    }

    public List<AccountingRecordResponse> getAccountingRecords(@RequestBody GetAccountingRecordRequest request, HttpServletRequest httpRequest) {
        if (request.getAccountingRecordCashId() == 0) {
            AccountingRecordCredit accountingRecordCredit = accountingRecordCreditRepository.findById(request.getAccountingRecordCreditId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AccountingRecordCredit not found"));
            List<AccountingRecord> accountingRecords = accountingRecordRepository.findAllByAccountingRecordCredit(accountingRecordCredit);
            return accountingRecords.stream().map(AccountingRecordResponse::new).toList();
        } else {
            AccountingRecordCash accountingRecordCash = accountingRecordCashRepository.findById(request.getAccountingRecordCashId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AccountingRecordCash not found"));
            List<AccountingRecord> accountingRecords = accountingRecordRepository.findAllByAccountingRecordCash(accountingRecordCash);
            return accountingRecords.stream().map(AccountingRecordResponse::new).toList();
        }
    }

}
