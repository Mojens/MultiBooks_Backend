package com.monero.multibooks.MultiBooks.Service.Accounting;

import com.monero.multibooks.MultiBooks.Dto.Accounting.AccountingRecord.AccountingRecordRequest;
import com.monero.multibooks.MultiBooks.Dto.Accounting.AccountingRecord.AccountingRecordResponse;
import com.monero.multibooks.MultiBooks.Repository.Accounting.AccountingRecordRepository;
import com.monero.multibooks.MultiBooks.Repository.BusinessTeam.BusinessTeamRepository;
import com.monero.multibooks.MultiBooks.Repository.UserTeam.UserTeamRepository;
import com.monero.multibooks.MultiBooks.Service.Auth.AuthService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

@Service
public class AccountingRecordService {


    private final AccountingRecordRepository accountingRecordRepository;
    private final AuthService authService;
    private final BusinessTeamRepository businessTeamRepository;
    private final UserTeamRepository userTeamRepository;

    public AccountingRecordService(AccountingRecordRepository accountingRecordRepository,
                                   AuthService authService, BusinessTeamRepository businessTeamRepository,
                                   UserTeamRepository userTeamRepository) {
        this.accountingRecordRepository = accountingRecordRepository;
        this.authService = authService;
        this.businessTeamRepository = businessTeamRepository;
        this.userTeamRepository = userTeamRepository;
    }

    public AccountingRecordResponse createAccountingRecord(@RequestBody AccountingRecordRequest request, HttpServletRequest httpRequest){
        return null;
    }



}
