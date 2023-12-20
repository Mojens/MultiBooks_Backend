package com.monero.multibooks.MultiBooks.Repository.User;

import com.monero.multibooks.MultiBooks.Entities.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Boolean existsByEmail(String email);

   Optional<User> findByEmail(String email);
}
