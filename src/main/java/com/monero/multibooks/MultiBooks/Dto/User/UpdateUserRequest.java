package com.monero.multibooks.MultiBooks.Dto.User;

import com.monero.multibooks.MultiBooks.Entities.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class UpdateUserRequest {

    String email;

    public UpdateUserRequest(String email) {
        this.email = email;
    }

    public UpdateUserRequest(User u) {
        this.email = u.getEmail();
    }
}
