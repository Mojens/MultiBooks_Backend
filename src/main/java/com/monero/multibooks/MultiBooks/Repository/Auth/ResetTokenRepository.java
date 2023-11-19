package com.monero.multibooks.MultiBooks.Repository.Auth;

import com.monero.multibooks.MultiBooks.Entities.Auth.ResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;

public interface ResetTokenRepository  extends JpaRepository<ResetToken,String> {

    void deleteAllByTokenExpiryBefore(Instant expiryTime);
}
