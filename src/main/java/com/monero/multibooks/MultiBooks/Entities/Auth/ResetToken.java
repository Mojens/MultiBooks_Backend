package com.monero.multibooks.MultiBooks.Entities.Auth;

import com.monero.multibooks.MultiBooks.Entities.User.User;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ResetToken {

    @Id
    @Column(nullable = false,length = 50,unique = true)
    private String token;

    private Instant tokenExpiry;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
