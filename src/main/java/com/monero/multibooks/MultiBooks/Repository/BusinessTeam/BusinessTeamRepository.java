package com.monero.multibooks.MultiBooks.Repository.BusinessTeam;

import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessTeamRepository extends JpaRepository<BusinessTeam, Integer> {
}
