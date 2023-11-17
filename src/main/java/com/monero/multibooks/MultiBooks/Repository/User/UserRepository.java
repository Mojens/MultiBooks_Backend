package com.monero.multibooks.MultiBooks.Repository.User;

import com.monero.multibooks.MultiBooks.Entities.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Boolean existsByEmail(String email);
}
