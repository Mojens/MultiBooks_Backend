package com.monero.multibooks.MultiBooks.Dto.Auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class ForgotPasswordRequest {

    String email;

    @JsonCreator
    public ForgotPasswordRequest(@JsonProperty("email") String email) {
        this.email = email;
    }
}
