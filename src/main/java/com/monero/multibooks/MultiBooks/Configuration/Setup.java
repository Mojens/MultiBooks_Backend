package com.monero.multibooks.MultiBooks.Configuration;

import com.monero.multibooks.MultiBooks.Dto.Auth.LoginRequest;
import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import com.monero.multibooks.MultiBooks.Entities.User.User;
import com.monero.multibooks.MultiBooks.Repository.BusinessTeam.BusinessTeamRepository;
import com.monero.multibooks.MultiBooks.Repository.User.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;


@Controller
public class Setup implements ApplicationRunner {

    private final UserRepository userRepository;
    private final BusinessTeamRepository businessTeamRepository;

    public Setup(UserRepository userRepository, BusinessTeamRepository businessTeamRepository) {
        this.userRepository = userRepository;
        this.businessTeamRepository = businessTeamRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        User user1 = new User("mail", "test123");
        userRepository.save(user1);

        LoginRequest request = new LoginRequest("mailo", "test123");
        User user2 = new User(request);
        userRepository.save(user2);

        User user3 = new User("mohammadmurtada@outlook.dk", "test123");
        userRepository.save(user3);

        BusinessTeam team = BusinessTeam.builder()
                .CVRNumber(12345678)
                .VATNumber("DK12345678")
                .companyName("Monero ApS")
                .address("Testvej 1")
                .city("Testby")
                .zipCode(1234)
                .country("Denmark")
                .phoneNumber("12345678")
                .email("gg@hotmail.com")
                .website("www.monero.dk")
                .build();
        businessTeamRepository.save(team);
        user3.setBusinessTeam(team);
        userRepository.save(user3);


    }
}