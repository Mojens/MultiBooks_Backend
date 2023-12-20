package com.monero.multibooks.MultiBooks.Dto.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.monero.multibooks.MultiBooks.Entities.User.User;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UpdateUserRequest {
    @JsonProperty("newEmail")
    String newEmail;
    @JsonProperty("oldEmail")
    String oldEmail;
}
