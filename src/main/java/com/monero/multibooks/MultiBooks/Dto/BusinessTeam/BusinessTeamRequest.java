package com.monero.multibooks.MultiBooks.Dto.BusinessTeam;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class BusinessTeamRequest {

    int CVRNumber;
    String VATNumber;
    String companyName;
    String address;
    String city;
    int zipCode;
    String country;
    String phoneNumber;
    String email;
    String website;

    public BusinessTeamRequest() {
    }
}
