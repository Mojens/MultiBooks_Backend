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
    String resetToken;

    public UpdateUserRequest(String email, String resetToken) {
        this.email = email;
        this.resetToken = resetToken;
    }

    public UpdateUserRequest(User u) {
        this.email = u.getEmail();
        this.resetToken = u.getResetToken();
    }
}
