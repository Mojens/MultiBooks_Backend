package com.monero.multibooks.MultiBooks.Service.Accounting;

import com.monero.multibooks.MultiBooks.DomainService.Auth.AuthDomainService;
import com.monero.multibooks.MultiBooks.Dto.Accounting.AccountingRecordCredit.AccountingRecordCreditRequest;
import com.monero.multibooks.MultiBooks.Dto.Accounting.AccountingRecordCredit.AccountingRecordCreditResponse;
import com.monero.multibooks.MultiBooks.Entities.Accounting.AccountingRecordCredit;
import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import com.monero.multibooks.MultiBooks.Repository.Accounting.AccountingRecordCreditRepository;
import com.monero.multibooks.MultiBooks.Repository.BusinessTeam.BusinessTeamRepository;
import com.monero.multibooks.MultiBooks.Service.UserTeam.UserTeamService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@Service
public class AccountingRecordCreditService {

    private final AccountingRecordCreditRepository accountingRecordCreditRepository;
    private final BusinessTeamRepository businessTeamRepository;
    private final UserTeamService userTeamService;
    private final AuthDomainService authDomainService;

    public AccountingRecordCreditService(AccountingRecordCreditRepository accountingRecordCreditRepository,
                                         BusinessTeamRepository businessTeamRepository,
                                         UserTeamService userTeamService,
                                         AuthDomainService authDomainService) {
        this.accountingRecordCreditRepository = accountingRecordCreditRepository;
        this.businessTeamRepository = businessTeamRepository;
        this.userTeamService = userTeamService;
        this.authDomainService = authDomainService;
    }


    public Page<AccountingRecordCreditResponse> getAccountingRecordCredit(@PathVariable int CVRNumber, Pageable pageable, HttpServletRequest httpRequest) {
        BusinessTeam businessTeam = businessTeamRepository.findById(CVRNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
        authDomainService.validateUserTeam(userTeamService.getUserTeams(businessTeam.getCVRNumber()), httpRequest);

        Page<AccountingRecordCredit> accountingRecordCreditPage = accountingRecordCreditRepository.findAllByBusinessTeam(businessTeam, pageable);
        return accountingRecordCreditPage.map(AccountingRecordCreditResponse::new);
    }

    public AccountingRecordCreditResponse deleteAccountingRecordCredit(@PathVariable Long id, HttpServletRequest httpRequest) {
        AccountingRecordCredit accountingRecordCredit = accountingRecordCreditRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AccountingRecordCredit not found"));
        authDomainService.validateUserTeam(userTeamService.getUserTeams(accountingRecordCredit.getBusinessTeam().getCVRNumber()), httpRequest);
        accountingRecordCreditRepository.delete(accountingRecordCredit);
        return new AccountingRecordCreditResponse(accountingRecordCredit);
    }

    public AccountingRecordCreditResponse updateAccountingRecordCredit(@RequestBody AccountingRecordCreditRequest request, HttpServletRequest httpRequest) {
        AccountingRecordCredit accountingRecordCredit = accountingRecordCreditRepository.findById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AccountingRecordCredit not found"));
        authDomainService.validateUserTeam(userTeamService.getUserTeams(accountingRecordCredit.getBusinessTeam().getCVRNumber()), httpRequest);
        accountingRecordCredit.setBoughtFrom(request.getBoughtFrom());
        accountingRecordCredit.setDocumentDate(request.getDocumentDate());
        accountingRecordCredit.setDueDate(request.getDueDate());
        accountingRecordCredit.setValuta(request.getValuta());
        accountingRecordCredit.setSubTotalNoVat(request.getSubTotalNoVat());
        accountingRecordCredit.setSubTotalVat(request.getSubTotalVat());
        accountingRecordCredit.setTotal(request.getTotal());
        accountingRecordCreditRepository.save(accountingRecordCredit);
        return new AccountingRecordCreditResponse(accountingRecordCredit);
    }

    public AccountingRecordCreditResponse createAccountingRecordCredit(@RequestBody AccountingRecordCreditRequest request, HttpServletRequest httpRequest) {
        BusinessTeam businessTeam = businessTeamRepository.findById(request.getBusinessTeamCVRNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
        authDomainService.validateUserTeam(userTeamService.getUserTeams(businessTeam.getCVRNumber()), httpRequest);
        AccountingRecordCredit accountingRecordCredit = AccountingRecordCredit.builder()
                .businessTeam(businessTeam)
                .total(request.getTotal())
                .boughtFrom(request.getBoughtFrom())
                .documentDate(request.getDocumentDate())
                .dueDate(request.getDueDate())
                .valuta(request.getValuta())
                .subTotalVat(request.getSubTotalVat())
                .subTotalNoVat(request.getSubTotalNoVat())
                .build();
        accountingRecordCreditRepository.save(accountingRecordCredit);
        return new AccountingRecordCreditResponse(accountingRecordCredit);
    }

    public AccountingRecordCreditResponse getAccountingRecordCreditById(@PathVariable Long id, HttpServletRequest httpRequest) {
        AccountingRecordCredit accountingRecordCredit = accountingRecordCreditRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AccountingRecordCredit not found"));
        authDomainService.validateUserTeam(userTeamService.getUserTeams(accountingRecordCredit.getBusinessTeam().getCVRNumber()), httpRequest);
        return new AccountingRecordCreditResponse(accountingRecordCredit);
    }

}
