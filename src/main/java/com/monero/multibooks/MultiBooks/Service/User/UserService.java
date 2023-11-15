package com.monero.multibooks.MultiBooks.Service.User;

import com.monero.multibooks.MultiBooks.Dto.Shared.ApiResponse;
import com.monero.multibooks.MultiBooks.Dto.User.UpdateUserRequest;
import com.monero.multibooks.MultiBooks.Dto.User.UserRequest;
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



    public ApiResponse registerUser(@RequestBody UserRequest request){
        User userToCreate = new User(request.getEmail(), request.getPassword());
        User userCreated = userRepository.save(userToCreate);
        return new ApiResponse(new UserResponse(userCreated),"User has been successfully registered");
    }

    public User findUserByEmail(@RequestBody String mail){
        User user = userRepository.findById(mail).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return user;
    }

    public ApiResponse updateUser(@RequestBody UpdateUserRequest request){
        User user = userRepository.findById(request.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setResetToken(request.getResetToken());
        User updatedUser = userRepository.save(user);
        return new ApiResponse(updatedUser,"Your user has been successfully updated");
    }




}
