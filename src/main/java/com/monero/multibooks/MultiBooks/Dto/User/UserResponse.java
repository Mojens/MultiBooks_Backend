package com.monero.multibooks.MultiBooks.Dto.User;

import com.monero.multibooks.MultiBooks.Entities.User.User;
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

    public UserResponse(User u) {
        this.email = u.getEmail();
        this.created = u.getCreated();
    }
}
