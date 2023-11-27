package com.monero.multibooks.MultiBooks.Service.BusinessTeam;

import com.monero.multibooks.MultiBooks.Dto.BusinessTeam.BusinessTeamRequest;
import com.monero.multibooks.MultiBooks.Dto.BusinessTeam.BusinessTeamResponse;
import com.monero.multibooks.MultiBooks.Dto.Shared.ApiResponse;
import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import com.monero.multibooks.MultiBooks.Entities.User.User;
import com.monero.multibooks.MultiBooks.Entities.UserTeam.UserTeam;
import com.monero.multibooks.MultiBooks.Repository.BusinessTeam.BusinessTeamRepository;
import com.monero.multibooks.MultiBooks.Repository.User.UserRepository;
import com.monero.multibooks.MultiBooks.Repository.UserTeam.UserTeamRepository;
import com.monero.multibooks.MultiBooks.Service.Auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class BusinessTeamService {


    private final BusinessTeamRepository businessTeamRepository;
    private final UserRepository userRepository;
    private final UserTeamRepository userTeamRepository;
    private final AuthService authService;

    public BusinessTeamService(BusinessTeamRepository businessTeamRepository,
                               UserRepository userRepository,
                               UserTeamRepository userTeamRepository,
                               AuthService authService) {
        this.businessTeamRepository = businessTeamRepository;
        this.userRepository = userRepository;
        this.userTeamRepository = userTeamRepository;
        this.authService = authService;
    }


    public BusinessTeamResponse createBusinessTeam(@RequestBody BusinessTeamRequest request) {
        User foundUser = userRepository.findById(request.getOwnerEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        BusinessTeam createdBusinessTeam = businessTeamRepository.save(new BusinessTeam(request.getCVRNumber(),
                request.getVATNumber(), request.getCompanyName(), request.getAddress(), request.getCity(),
                request.getZipCode(), request.getCountry(), request.getPhoneNumber(), request.getEmail(),
                request.getWebsite()));
        createdBusinessTeam.setTeamOwner(foundUser);
        BusinessTeam businessTeam = businessTeamRepository.save(createdBusinessTeam);

        List<BusinessTeam> businessTeams = foundUser.getBusinessTeams();
        businessTeams.add(businessTeam);
        foundUser.setBusinessTeams(businessTeams);
        userRepository.save(foundUser);

        UserTeam userTeam = new UserTeam(foundUser, businessTeam);
        userTeamRepository.save(userTeam);

        return new BusinessTeamResponse(businessTeam);
    }

    public ApiResponse setUserBusinessTeam(String mail, int CVRNumber) {
        User foundUser = userRepository.findById(mail).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        BusinessTeam foundBusinessTeam = businessTeamRepository.findById(CVRNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Business team not found"));
        if (userTeamRepository.existsByBusinessTeam(foundBusinessTeam)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Business team already Registered");
        }
        List<BusinessTeam> businessTeams = foundUser.getBusinessTeams();
        businessTeams.add(foundBusinessTeam);
        foundUser.setBusinessTeams(businessTeams);
        userRepository.save(foundUser);

        return new ApiResponse(null, "User has been successfully added to business team");
    }

    public ApiResponse addUserToBusinessTeam(String mail, int CVRNumber){
        User foundUser = userRepository.findById(mail).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        BusinessTeam foundBusinessTeam = businessTeamRepository.findById(CVRNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Business team not found"));
        if (!userTeamRepository.existsByBusinessTeam(foundBusinessTeam)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Business team is not Registered");
        }
        Optional<UserTeam> userTeam = userTeamRepository.findByBusinessTeamAndUser(foundBusinessTeam, foundUser);
        if (userTeam.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User is already in business team");
        }
        List<BusinessTeam> businessTeams = foundUser.getBusinessTeams();
        businessTeams.add(foundBusinessTeam);
        foundUser.setBusinessTeams(businessTeams);
        userRepository.save(foundUser);

        UserTeam newUserTeam = new UserTeam(foundUser, foundBusinessTeam);
        userTeamRepository.save(newUserTeam);
        return new ApiResponse(null, "User has been successfully added to business team");
    }

    public List<BusinessTeamResponse> userApartOfBusinessTeam(@PathVariable String mail, HttpServletRequest request) {
        authService.validateUserAccess(mail, request);
        User foundUser = userRepository.findById(mail.toLowerCase()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        List<UserTeam> userTeams = userTeamRepository.findAllByUser(foundUser);
        return userTeams.stream().map(UserTeam::getBusinessTeam).map(BusinessTeamResponse::new).toList();
    }

    public BusinessTeamResponse getBusinessTeamById(@PathVariable int CVRNumber, HttpServletRequest request){
        BusinessTeam foundTeam = businessTeamRepository.findById(CVRNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Business team not found"));
        authService.validateUserAccess(foundTeam.getEmail(), request);
        return new BusinessTeamResponse(foundTeam);
    }

}
