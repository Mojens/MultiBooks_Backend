package com.monero.multibooks.MultiBooks.Controllers.User;

import com.monero.multibooks.MultiBooks.Dto.Shared.ApiResponse;
import com.monero.multibooks.MultiBooks.Dto.User.UserResponse;
import com.monero.multibooks.MultiBooks.Service.User.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/users/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{mail}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable String mail){
        UserResponse user = userService.getUser(mail);
        return ResponseEntity.ok(new ApiResponse(user
                ,"User has been successfully retrieved"));
    }

}
