package com.monero.multibooks.MultiBooks.Controllers.User;

import com.monero.multibooks.MultiBooks.Dto.Shared.ApiResponse;
import com.monero.multibooks.MultiBooks.Dto.User.UpdateUserRequest;
import com.monero.multibooks.MultiBooks.Dto.User.UserResponse;
import com.monero.multibooks.MultiBooks.Service.User.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/users/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{mail}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable String mail, HttpServletRequest httpRequest){
        UserResponse user = userService.getUser(mail, httpRequest);
        return ResponseEntity.ok(new ApiResponse(user
                ,"User has been successfully retrieved"));
    }

    @PatchMapping("/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UpdateUserRequest request, HttpServletRequest httpRequest){
        UserResponse response = userService.updateUser(request, httpRequest);
        return ResponseEntity.ok(new ApiResponse(response,"User has been successfully updated"));
    }

}
