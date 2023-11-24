package com.monero.multibooks.MultiBooks.Service.UserTeam;

import com.monero.multibooks.MultiBooks.Repository.BusinessTeam.BusinessTeamRepository;
import com.monero.multibooks.MultiBooks.Repository.User.UserRepository;
import com.monero.multibooks.MultiBooks.Repository.UserTeam.UserTeamRepository;
import org.springframework.stereotype.Service;

@Service
public class UserTeamService {


    private final UserTeamRepository userTeamRepository;
    private final UserRepository userRepository;
    private final BusinessTeamRepository businessTeamRepository;

    public UserTeamService(UserTeamRepository userTeamRepository, UserRepository userRepository, BusinessTeamRepository businessTeamRepository) {
        this.userTeamRepository = userTeamRepository;
        this.userRepository = userRepository;
        this.businessTeamRepository = businessTeamRepository;
    }
}
