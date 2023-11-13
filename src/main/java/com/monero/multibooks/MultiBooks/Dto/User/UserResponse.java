package com.monero.multibooks.MultiBooks.Dto.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

    private String email;
    private LocalDateTime created;

    public UserResponse(String email, LocalDateTime created) {
        this.email = email;
        this.created = created;
    }
}
