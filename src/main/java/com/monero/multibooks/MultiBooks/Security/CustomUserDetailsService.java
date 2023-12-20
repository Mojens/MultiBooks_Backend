package com.monero.multibooks.MultiBooks.Security;

import com.monero.multibooks.MultiBooks.Entities.User.User;
import com.monero.multibooks.MultiBooks.Repository.User.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService  {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Incorrect username or password"));
    }
}
