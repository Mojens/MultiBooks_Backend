package com.monero.multibooks.MultiBooks.Dto.Auth;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class RegisterRequest {

    String email;
    String password;
    String confirmPassword;

    public RegisterRequest(String email, String password, String confirmPassword) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }


}
