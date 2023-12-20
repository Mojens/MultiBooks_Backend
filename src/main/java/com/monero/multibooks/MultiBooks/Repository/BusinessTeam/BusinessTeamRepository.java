package com.monero.multibooks.MultiBooks.Repository.BusinessTeam;

import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import com.monero.multibooks.MultiBooks.Entities.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessTeamRepository extends JpaRepository<BusinessTeam, Integer> {

    List<BusinessTeam> findAllByTeamOwner(User user);
}
