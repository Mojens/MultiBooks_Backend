package com.monero.multibooks.MultiBooks.Dto.Auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordRequest {

    String email;

    @JsonCreator
    public ForgotPasswordRequest(@JsonProperty("email") String email) {
        this.email = email;
    }
}
