package com.monero.multibooks.MultiBooks.Service.User;

import com.monero.multibooks.MultiBooks.Dto.Auth.RegisterRequest;
import com.monero.multibooks.MultiBooks.Dto.Shared.ApiResponse;
import com.monero.multibooks.MultiBooks.Dto.User.UpdateUserRequest;
import com.monero.multibooks.MultiBooks.Dto.User.UserResponse;
import com.monero.multibooks.MultiBooks.Entities.User.User;
import com.monero.multibooks.MultiBooks.Repository.User.UserRepository;
import com.monero.multibooks.MultiBooks.Service.Auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthService authService;

    public UserService(UserRepository userRepository,
                       AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
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
        return userRepository.findById(mail).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public ApiResponse updateUser(@RequestBody UpdateUserRequest request){
        User user = userRepository.findById(request.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setEmail(request.getEmail());
        User updatedUser = userRepository.save(user);
        return new ApiResponse(updatedUser,"Your user has been successfully updated");
    }

    public UserResponse getUser(String mail, HttpServletRequest request){
        this.authService.validateUserAccess(mail, request);
        User user = userRepository.findById(mail).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return new UserResponse(user);
    }




}
