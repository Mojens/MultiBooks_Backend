package com.monero.multibooks.MultiBooks.Dto.Auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class LoginRequest {

    String email;
    String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
