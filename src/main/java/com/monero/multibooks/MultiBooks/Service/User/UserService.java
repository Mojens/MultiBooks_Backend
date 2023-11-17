package com.monero.multibooks.MultiBooks.Service.User;

import com.monero.multibooks.MultiBooks.Dto.Auth.RegisterRequest;
import com.monero.multibooks.MultiBooks.Dto.Shared.ApiResponse;
import com.monero.multibooks.MultiBooks.Dto.User.UpdateUserRequest;
import com.monero.multibooks.MultiBooks.Dto.User.UserResponse;
import com.monero.multibooks.MultiBooks.Entities.User.User;
import com.monero.multibooks.MultiBooks.Repository.User.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        user.setResetToken(request.getResetToken());
        User updatedUser = userRepository.save(user);
        return new ApiResponse(updatedUser,"Your user has been successfully updated");
    }




}
