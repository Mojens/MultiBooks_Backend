package com.monero.multibooks.MultiBooks.Service.BusinessTeam;

import com.monero.multibooks.MultiBooks.Repository.BusinessTeam.BusinessTeamRepository;
import org.springframework.stereotype.Service;

@Service
public class BusinessTeamService {



    private final BusinessTeamRepository businessTeamRepository;

    public BusinessTeamService(BusinessTeamRepository businessTeamRepository) {
        this.businessTeamRepository = businessTeamRepository;
    }
}
