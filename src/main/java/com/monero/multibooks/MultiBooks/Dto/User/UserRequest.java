package com.monero.multibooks.MultiBooks.Dto.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    String email;
    String password;

    public UserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

