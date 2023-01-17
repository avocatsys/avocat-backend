package com.avocat.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class UserApp implements UserDetails {

    @Id
    @GeneratedValue
    @Column(name = "user_id", nullable = false)
    private UUID id;

    @Email(message = "invalid email format")
    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    @NotEmpty(message = "invalid password format")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_privileges",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "privilege_id"))
    private Set<Privilege> privileges;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_office_id", referencedColumnName = "branch_office_id")
    private BranchOffice branchOffice;

    private UserApp(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.privileges = builder.privileges;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Privilege privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege.getName()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;//todo Verificar uma maneira de manter esse atributo dinâmico.
    }

    public static class Builder {

        //mandatory
        private final String username;
        private final String password;

        //optional
        private Set<Privilege> privileges = new HashSet<>();
        private Group group = null;

        public Builder(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public Builder group(Group group) {
            this.group = group;
            return this;
        }

        public Builder privilege(Set<Privilege> privileges) {
            this.privileges = privileges;
            return this;
        }

        public UserApp build() {
            return new UserApp(this);
        }
    }
}
