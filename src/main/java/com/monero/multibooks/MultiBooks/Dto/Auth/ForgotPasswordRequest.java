package com.monero.multibooks.MultiBooks.Dto.Auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordRequest {

    String email;

    public ForgotPasswordRequest(String email) {
        this.email = email;
    }
}
