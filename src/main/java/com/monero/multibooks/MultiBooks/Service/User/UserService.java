package com.monero.multibooks.MultiBooks.Service.User;

import com.monero.multibooks.MultiBooks.Dto.Shared.ApiResponse;
import com.monero.multibooks.MultiBooks.Dto.User.UserRequest;
import com.monero.multibooks.MultiBooks.Dto.User.UserResponse;
import com.monero.multibooks.MultiBooks.Entities.User.User;
import com.monero.multibooks.MultiBooks.Repository.User.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    public ApiResponse registerUser(@RequestBody UserRequest request){
        User userToCreate = new User(request.getEmail(), request.getPassword());
        User userCreated = userRepository.save(userToCreate);
        return new ApiResponse(new UserResponse(userCreated.getEmail(), userCreated.getCreated()),"User has been successfully registered");
    }




}
