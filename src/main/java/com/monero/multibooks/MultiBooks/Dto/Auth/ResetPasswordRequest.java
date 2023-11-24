package com.monero.multibooks.MultiBooks.Dto.Auth;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordRequest {

    String resetToken;
    String password;
    String confirmPassword;
}
