package com.monero.multibooks.MultiBooks.Repository.UserTeam;

import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import com.monero.multibooks.MultiBooks.Entities.User.User;
import com.monero.multibooks.MultiBooks.Entities.UserTeam.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {


    List<UserTeam> findAllByUser(User user);
    List<UserTeam> findAllByBusinessTeam(BusinessTeam businessTeam);

    boolean existsByBusinessTeam(BusinessTeam businessTeam);

    Optional<UserTeam> findByBusinessTeamAndUser(BusinessTeam businessTeam, User user);
}
