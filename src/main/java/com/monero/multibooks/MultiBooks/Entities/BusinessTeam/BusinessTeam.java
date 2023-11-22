package com.monero.multibooks.MultiBooks.Entities.BusinessTeam;

import com.monero.multibooks.MultiBooks.Entities.User.User;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BusinessTeam {

    @Id
    @Column(name = "cvr_number", nullable = false)
    private int CVRNumber;

    @Column(name = "vat_number", nullable = false, unique = true)
    private String VATNumber;

    @Column(nullable = false, length = 100)
    private String companyName;

    @Column(length = 200)
    private String address;

    @Column(length = 50)
    private String city;

    @Column(name = "zip_code")
    private int zipCode;

    @Column(length = 50)
    private String country;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(length = 100, unique = true)
    private String email;

    @Column(length = 100)
    private String website;

    @OneToMany(mappedBy = "businessTeam")
    private List<User> users;
}
