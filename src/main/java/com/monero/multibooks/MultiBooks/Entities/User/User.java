package com.monero.multibooks.MultiBooks.Entities.User;

import com.monero.multibooks.MultiBooks.Dto.Auth.LoginRequest;
import com.monero.multibooks.MultiBooks.Entities.Auth.ResetToken;
import com.monero.multibooks.MultiBooks.Entities.BusinessTeam.BusinessTeam;
import com.monero.multibooks.MultiBooks.Entities.UserTeam.UserTeam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
public class User implements UserDetails {

    @Id
    @Column(nullable = false,length = 50,unique = true)
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    @CreationTimestamp
    private LocalDateTime created;

    @UpdateTimestamp
    private LocalDateTime edited;

    @OneToMany(mappedBy = "user")
    private List<ResetToken> resetTokens;

    @OneToMany(mappedBy = "teamOwner")
    private List<BusinessTeam> businessTeams;

    @OneToMany(mappedBy = "user")
    private List<UserTeam> userTeams;

    @Transient
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void setPassword(String pw){
        this.password = passwordEncoder.encode(pw);
    }

    public User() {
    }

    public User(LoginRequest body) {
        this.email = body.getEmail();
        this.setPassword(body.getPassword());
    }

    public User(String email, String password){
        this.email = email;
        setPassword(password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
