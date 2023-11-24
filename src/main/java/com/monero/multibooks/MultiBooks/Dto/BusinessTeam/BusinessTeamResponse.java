package com.monero.multibooks.MultiBooks.Dto.BusinessTeam;

import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BusinessTeamResponse {

    private int CVRNumber;
    private String VATNumber;
    private String companyName;
    private String address;
    private String city;
    private int zipCode;
    private String country;
    private String phoneNumber;
    private String email;
    private String website;
    private String ownerEmail;

    public BusinessTeamResponse(BusinessTeam businessTeam) {
        this.CVRNumber = businessTeam.getCVRNumber();
        this.VATNumber = businessTeam.getVATNumber();
        this.companyName = businessTeam.getCompanyName();
        this.address = businessTeam.getAddress();
        this.city = businessTeam.getCity();
        this.zipCode = businessTeam.getZipCode();
        this.country = businessTeam.getCountry();
        this.phoneNumber = businessTeam.getPhoneNumber();
        this.email = businessTeam.getEmail();
        this.website = businessTeam.getWebsite();
        this.ownerEmail = businessTeam.getTeamOwner().getEmail();
    }
}
