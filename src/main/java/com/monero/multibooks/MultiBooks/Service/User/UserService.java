package com.monero.multibooks.MultiBooks.Service.User;

import com.monero.multibooks.MultiBooks.Dto.Auth.RegisterRequest;
import com.monero.multibooks.MultiBooks.Dto.Shared.ApiResponse;
import com.monero.multibooks.MultiBooks.Dto.User.UpdateUserRequest;
import com.monero.multibooks.MultiBooks.Dto.User.UserResponse;
import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import com.monero.multibooks.MultiBooks.Entities.User.User;
import com.monero.multibooks.MultiBooks.Repository.BusinessTeam.BusinessTeamRepository;
import com.monero.multibooks.MultiBooks.Repository.User.UserRepository;
import com.monero.multibooks.MultiBooks.Service.Auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final BusinessTeamRepository businessTeamRepository;

    public UserService(UserRepository userRepository,
                       AuthService authService,
                       BusinessTeamRepository businessTeamRepository) {
        this.userRepository = userRepository;
        this.authService = authService;
        this.businessTeamRepository = businessTeamRepository;
    }



    public ApiResponse registerUser(@RequestBody RegisterRequest request){
        if(!request.getPassword().equals(request.getConfirmPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with that email already exists");
        }
        User userCreated = userRepository.save(new User(request.getEmail(), request.getPassword()));
        return new ApiResponse(new UserResponse(userCreated),"User has been successfully registered");
    }

    public User findUserByEmail(@RequestBody String mail){
        return userRepository.findByEmail(mail).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public UserResponse updateUser(@RequestBody UpdateUserRequest request, HttpServletRequest httpRequest){
        User user = userRepository.findByEmail(request.getOldEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        this.authService.validateUserAccess(user.getEmail(), httpRequest);
        user.setEmail(request.getNewEmail());
        User updatedUser = userRepository.save(user);
        List<BusinessTeam> userOwnedTeams = businessTeamRepository.findAllByTeamOwner(user);
        userOwnedTeams.forEach(businessTeam -> {
            businessTeam.setTeamOwner(updatedUser);
            businessTeamRepository.save(businessTeam);
        });
        return new UserResponse(updatedUser);
    }

    public UserResponse getUser(@PathVariable String mail, HttpServletRequest httpRequest){
        this.authService.validateUserAccess(mail, httpRequest);
        User user = userRepository.findByEmail(mail).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return new UserResponse(user);
    }




}
