package com.monero.multibooks.MultiBooks.Service.Accounting;

import com.monero.multibooks.MultiBooks.DomainService.Auth.AuthDomainService;
import com.monero.multibooks.MultiBooks.Dto.Accounting.AccountingRecordCash.AccountingRecordCashRequest;
import com.monero.multibooks.MultiBooks.Dto.Accounting.AccountingRecordCash.AccountingRecordCashResponse;
import com.monero.multibooks.MultiBooks.Entities.Accounting.AccountingRecordCash;
import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import com.monero.multibooks.MultiBooks.Repository.Accounting.AccountingRecordCashRepository;
import com.monero.multibooks.MultiBooks.Repository.Accounting.AccountingRecordRepository;
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
import javax.transaction.Transactional;

@Service
public class AccountingRecordCashService {

    private final AccountingRecordCashRepository accountingRecordCashRepository;
    private final AccountingRecordRepository accountingRecordRepository;
    private final BusinessTeamRepository businessTeamRepository;
    private final UserTeamService userTeamService;
    private final AuthDomainService authDomainService;

    public AccountingRecordCashService(AccountingRecordCashRepository accountingRecordCashRepository,
                                       UserTeamService userTeamService,
                                       AuthDomainService authDomainService,
                                       BusinessTeamRepository businessTeamRepository,
                                       AccountingRecordRepository accountingRecordRepository) {
        this.accountingRecordCashRepository = accountingRecordCashRepository;
        this.userTeamService = userTeamService;
        this.authDomainService = authDomainService;
        this.businessTeamRepository = businessTeamRepository;
        this.accountingRecordRepository = accountingRecordRepository;
    }


    public Page<AccountingRecordCashResponse> getAccountingRecordCash(@PathVariable int CVRNumber, Pageable pageable, HttpServletRequest httpRequest) {
        BusinessTeam businessTeam = businessTeamRepository.findById(CVRNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
        authDomainService.validateUserTeam(userTeamService.getUserTeams(businessTeam.getCVRNumber()), httpRequest);

        Page<AccountingRecordCash> accountingRecordCashPage = accountingRecordCashRepository.findAllByBusinessTeam(businessTeam, pageable);
        return accountingRecordCashPage.map(AccountingRecordCashResponse::new);
    }

    public AccountingRecordCashResponse createAccountingRecordCash(@RequestBody AccountingRecordCashRequest request, HttpServletRequest httpRequest) {
        BusinessTeam businessTeam = businessTeamRepository.findById(request.getBusinessTeamCVRNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
        authDomainService.validateUserTeam(userTeamService.getUserTeams(businessTeam.getCVRNumber()), httpRequest);
        AccountingRecordCash accountingRecordCash = AccountingRecordCash.builder()
                .businessTeam(businessTeam)
                .total(request.getTotal())
                .boughtFrom(request.getBoughtFrom())
                .documentDate(request.getDocumentDate())
                .holdings(request.getHoldings())
                .subTotalVat(request.getSubTotalVat())
                .subTotalNoVat(request.getSubTotalNoVat())
                .build();
        accountingRecordCashRepository.save(accountingRecordCash);
        return new AccountingRecordCashResponse(accountingRecordCash);
    }

    public AccountingRecordCashResponse updateAccountingRecordCash(@RequestBody AccountingRecordCashRequest request, HttpServletRequest httpRequest){
        AccountingRecordCash accountingRecordCash = accountingRecordCashRepository.findById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AccountingRecordCash not found"));
        authDomainService.validateUserTeam(userTeamService.getUserTeams(accountingRecordCash.getBusinessTeam().getCVRNumber()), httpRequest);
        accountingRecordCash.setHoldings(request.getHoldings());
        accountingRecordCash.setBoughtFrom(request.getBoughtFrom());
        accountingRecordCash.setDocumentDate(request.getDocumentDate());
        accountingRecordCash.setSubTotalNoVat(request.getSubTotalNoVat());
        accountingRecordCash.setSubTotalVat(request.getSubTotalVat());
        accountingRecordCash.setTotal(request.getTotal());
        accountingRecordCashRepository.save(accountingRecordCash);
        return new AccountingRecordCashResponse(accountingRecordCash);
    }

    public AccountingRecordCashResponse getAccountingRecordCashById(@PathVariable Long id, HttpServletRequest httpRequest) {
        AccountingRecordCash accountingRecordCash = accountingRecordCashRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AccountingRecordCash not found"));
        authDomainService.validateUserTeam(userTeamService.getUserTeams(accountingRecordCash.getBusinessTeam().getCVRNumber()), httpRequest);
        return new AccountingRecordCashResponse(accountingRecordCash);
    }

    @Transactional
    public AccountingRecordCashResponse deleteAccountingRecordCash(@PathVariable Long id, HttpServletRequest httpRequest) {
        AccountingRecordCash accountingRecordCash = accountingRecordCashRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "AccountingRecordCash not found"));
        authDomainService.validateUserTeam(userTeamService.getUserTeams(accountingRecordCash.getBusinessTeam().getCVRNumber()), httpRequest);
        accountingRecordRepository.deleteAllByAccountingRecordCash(accountingRecordCash);
        accountingRecordCashRepository.delete(accountingRecordCash);
        return new AccountingRecordCashResponse(accountingRecordCash);
    }

}
