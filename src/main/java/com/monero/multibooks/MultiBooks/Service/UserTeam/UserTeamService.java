package com.monero.multibooks.MultiBooks.Service.UserTeam;

import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import com.monero.multibooks.MultiBooks.Entities.UserTeam.UserTeam;
import com.monero.multibooks.MultiBooks.Repository.BusinessTeam.BusinessTeamRepository;
import com.monero.multibooks.MultiBooks.Repository.User.UserRepository;
import com.monero.multibooks.MultiBooks.Repository.UserTeam.UserTeamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    public List<UserTeam> getUserTeams(int cvrNumber){
        BusinessTeam businessTeam = businessTeamRepository.findById(cvrNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
        return userTeamRepository.findAllByBusinessTeam(businessTeam);
    }
}
