package com.monero.multibooks.MultiBooks.Configuration;

import com.monero.multibooks.MultiBooks.Dto.Auth.LoginRequest;
import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import com.monero.multibooks.MultiBooks.Entities.User.User;
import com.monero.multibooks.MultiBooks.Entities.UserTeam.UserTeam;
import com.monero.multibooks.MultiBooks.Repository.BusinessTeam.BusinessTeamRepository;
import com.monero.multibooks.MultiBooks.Repository.User.UserRepository;
import com.monero.multibooks.MultiBooks.Repository.UserTeam.UserTeamRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;


@Controller
public class Setup implements ApplicationRunner {

    private final UserRepository userRepository;
    private final BusinessTeamRepository businessTeamRepository;
    private final UserTeamRepository userTeamRepository;

    public Setup(UserRepository userRepository, BusinessTeamRepository businessTeamRepository, UserTeamRepository userTeamRepository) {
        this.userRepository = userRepository;
        this.businessTeamRepository = businessTeamRepository;
        this.userTeamRepository = userTeamRepository;
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
                .CVRNumber(12345321)
                .VATNumber("DK12345321")
                .companyName("Monero ApS")
                .address("Testvej 1")
                .city("Testby")
                .zipCode(1234)
                .country("Denmark")
                .phoneNumber("12345678")
                .email("gg@hotmail.com")
                .website("www.monero.dk")
                .accNumber("1234567890")
                .regNumber(1234)
                .bankName("Danske Bank")
                .teamOwner(user3)
                .build();
        businessTeamRepository.save(team);
        UserTeam userTeam = new UserTeam(user3, team);
        userTeamRepository.save(userTeam);

        BusinessTeam team2 = BusinessTeam.builder()
                .CVRNumber(98765432)
                .VATNumber("DK98765432")
                .companyName("Konero ApS")
                .address("Testvej 1")
                .city("Testby")
                .zipCode(1234)
                .country("Denmark")
                .phoneNumber("12345678")
                .email("oo@hotmail.com")
                .website("www.konero.dk")
                .accNumber("1234567890")
                .regNumber(1234)
                .bankName("Danske Bank")
                .teamOwner(user2)
                .build();
        businessTeamRepository.save(team2);
     UserTeam userTeam2 = new UserTeam(user2, team2);
     userTeamRepository.save(userTeam2);
     UserTeam userTeam3 = new UserTeam(user3, team2);
     userTeamRepository.save(userTeam3);



    }
}