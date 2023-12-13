package com.monero.multibooks.MultiBooks.Dto.BusinessTeam;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BusinessTeamRequest {

    @JsonProperty("CVRNumber")
    int CVRNumber;
    @JsonProperty("VATNumber")
    String VATNumber;
    String companyName;
    String address;
    String city;
    int zipCode;
    String country;
    String phoneNumber;
    String email;
    String website;
    String ownerEmail;
    String accNumber;
    int regNumber;
    String bankName;
}
