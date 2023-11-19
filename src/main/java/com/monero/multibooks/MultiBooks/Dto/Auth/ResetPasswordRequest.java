package com.monero.multibooks.MultiBooks.Dto.Auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class ResetPasswordRequest {

    String resetToken;
    String password;
    String confirmPassword;

    public ResetPasswordRequest(String resetToken, String password, String confirmPassword) {
        this.resetToken = resetToken;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
}
