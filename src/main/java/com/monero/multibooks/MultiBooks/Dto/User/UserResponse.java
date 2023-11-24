package com.monero.multibooks.MultiBooks.Dto.User;

import com.monero.multibooks.MultiBooks.Dto.BusinessTeam.BusinessTeamResponse;
import com.monero.multibooks.MultiBooks.Entities.User.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

    private String email;
    private LocalDateTime created;
    private List<BusinessTeamResponse> ownedTeams;

    public UserResponse(User u) {
        this.email = u.getEmail();
        this.created = u.getCreated();
        if(u.getBusinessTeams() != null){
            this.ownedTeams = u.getBusinessTeams().stream().map(BusinessTeamResponse::new).toList();
        }
    }
}
