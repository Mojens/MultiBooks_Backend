package com.monero.multibooks.MultiBooks.Configuration;

import com.monero.multibooks.MultiBooks.Dto.Auth.LoginRequest;
import com.monero.multibooks.MultiBooks.Entities.User.User;
import com.monero.multibooks.MultiBooks.Repository.User.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;


@Controller
public class Setup implements ApplicationRunner {

    private final UserRepository userRepository;

    public Setup(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        User user1 = new User("mail","test123");
        userRepository.save(user1);

        LoginRequest request = new LoginRequest("mailo","test123");
        User user2 = new User(request);
        userRepository.save(user2);
    }
}