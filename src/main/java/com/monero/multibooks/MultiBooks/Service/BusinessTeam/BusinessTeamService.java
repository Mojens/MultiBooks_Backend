package com.monero.multibooks.MultiBooks.Service.BusinessTeam;

import com.monero.multibooks.MultiBooks.DomainService.Auth.AuthDomainService;
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
    private final AuthDomainService authDomainService;

    public BusinessTeamService(BusinessTeamRepository businessTeamRepository,
                               UserRepository userRepository,
                               UserTeamRepository userTeamRepository,
                               AuthService authService,
                               AuthDomainService authDomainService) {
        this.businessTeamRepository = businessTeamRepository;
        this.userRepository = userRepository;
        this.userTeamRepository = userTeamRepository;
        this.authService = authService;
        this.authDomainService = authDomainService;
    }


    public BusinessTeamResponse createBusinessTeam(@RequestBody BusinessTeamRequest request) {
        User foundUser = userRepository.findByEmail(request.getOwnerEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        BusinessTeam createdBusinessTeam = BusinessTeam.builder()
                .CVRNumber(request.getCVRNumber())
                .VATNumber(request.getVATNumber())
                .companyName(request.getCompanyName())
                .address(request.getAddress())
                .city(request.getCity())
                .zipCode(request.getZipCode())
                .country(request.getCountry())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .website(request.getWebsite())
                .teamOwner(foundUser)
                .accNumber(request.getAccNumber())
                .regNumber(request.getRegNumber())
                .bankName(request.getBankName())
                .build();

        BusinessTeam businessTeam = businessTeamRepository.save(createdBusinessTeam);

        List<BusinessTeam> businessTeams = foundUser.getBusinessTeams();
        businessTeams.add(businessTeam);
        foundUser.setBusinessTeams(businessTeams);
        userRepository.save(foundUser);

        UserTeam userTeam = new UserTeam(foundUser, businessTeam);
        userTeamRepository.save(userTeam);

        return new BusinessTeamResponse(businessTeam);
    }

    public ApiResponse setUserBusinessTeam(String mail, int CVRNumber, HttpServletRequest httpRequest) {
        User foundUser = userRepository.findByEmail(mail).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        BusinessTeam foundBusinessTeam = businessTeamRepository.findById(CVRNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Business team not found"));
        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(foundBusinessTeam);
        authDomainService.validateUserTeam(userTeams, httpRequest);
        if (userTeamRepository.existsByBusinessTeam(foundBusinessTeam)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Business team already Registered");
        }
        List<BusinessTeam> businessTeams = foundUser.getBusinessTeams();
        businessTeams.add(foundBusinessTeam);
        foundUser.setBusinessTeams(businessTeams);
        userRepository.save(foundUser);

        return new ApiResponse(null, "User has been successfully added to business team");
    }

    public ApiResponse addUserToBusinessTeam(String mail, int CVRNumber, HttpServletRequest httpRequest) {
        User foundUser = userRepository.findByEmail(mail).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        BusinessTeam foundBusinessTeam = businessTeamRepository.findById(CVRNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Business team not found"));
        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(foundBusinessTeam);
        authDomainService.validateUserTeam(userTeams, httpRequest);
        if (!userTeamRepository.existsByBusinessTeam(foundBusinessTeam)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Business team is not Registered");
        }
        Optional<UserTeam> userTeam = userTeamRepository.findByBusinessTeamAndUser(foundBusinessTeam, foundUser);
        if (userTeam.isPresent()) {
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
        User foundUser = userRepository.findByEmail(mail.toLowerCase()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        List<UserTeam> userTeams = userTeamRepository.findAllByUser(foundUser);
        return userTeams.stream().map(UserTeam::getBusinessTeam).map(BusinessTeamResponse::new).toList();
    }

    public BusinessTeamResponse getBusinessTeamById(@PathVariable int CVRNumber, HttpServletRequest httpRequest) {
        BusinessTeam foundTeam = businessTeamRepository.findById(CVRNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Business team not found"));
        List<UserTeam> userTeams = userTeamRepository.findAllByBusinessTeam(foundTeam);
        authDomainService.validateUserTeam(userTeams, httpRequest);
        return new BusinessTeamResponse(foundTeam);
    }

    public BusinessTeamResponse deleteBusinessTeamById(@PathVariable int CVRNumber, HttpServletRequest request) {
        BusinessTeam foundTeam = businessTeamRepository.findById(CVRNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Business team not found"));
        authService.validateUserAccess(foundTeam.getTeamOwner().getEmail(), request);
        businessTeamRepository.delete(foundTeam);
        return new BusinessTeamResponse(foundTeam);
    }

    public BusinessTeamResponse editBusinessTeamById(@RequestBody BusinessTeamRequest request, HttpServletRequest httpServletRequest) {
        BusinessTeam foundTeam = businessTeamRepository.findById(request.getCVRNumber()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Business team not found"));
        authService.validateUserAccess(foundTeam.getTeamOwner().getEmail(), httpServletRequest);
        foundTeam.setCVRNumber(request.getCVRNumber());
        foundTeam.setVATNumber(request.getVATNumber());
        foundTeam.setCompanyName(request.getCompanyName());
        foundTeam.setAddress(request.getAddress());
        foundTeam.setCity(request.getCity());
        foundTeam.setZipCode(request.getZipCode());
        foundTeam.setCountry(request.getCountry());
        foundTeam.setPhoneNumber(request.getPhoneNumber());
        foundTeam.setEmail(request.getEmail());
        foundTeam.setWebsite(request.getWebsite());
        foundTeam.setAccNumber(request.getAccNumber());
        foundTeam.setRegNumber(request.getRegNumber());
        foundTeam.setBankName(request.getBankName());
        businessTeamRepository.save(foundTeam);
        return new BusinessTeamResponse(foundTeam);
    }

    public boolean isTeamOwner(@PathVariable int CVRNumber, HttpServletRequest request) {
        BusinessTeam foundTeam = businessTeamRepository.findById(CVRNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Business team not found"));
        authService.validateUserAccess(foundTeam.getTeamOwner().getEmail(), request);
        return true;
    }

}
