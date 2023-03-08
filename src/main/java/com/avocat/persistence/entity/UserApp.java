package com.avocat.persistence.entity;

import com.avocat.persistence.types.UserTypes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.Empty;
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
    @Column(name = "user_id")
    private UUID id;

    @Email(message = "invalid email format")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "invalid name format")
    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "situation")
    private UserTypes situation;

    @Column(name = "oid")
    private UUID oid;

    @Column(name = "link_forgot")
    private String linkForgot;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "users_privileges",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "privilege_id"))
    private Set<Privilege> privileges = new HashSet<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_office_id", referencedColumnName = "branch_office_id")
    private BranchOffice branchOffice;

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    private Group group;

    private UserApp(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.privileges = builder.privileges;
        this.name = builder.name;
        this.branchOffice = builder.branchOffice;
        this.situation = builder.situation;
        this.oid = builder.oid;
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
        private BranchOffice branchOffice;
        private String name;
        private UserTypes situation;
        private UUID oid;

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

        public Builder branchOffice(BranchOffice branchOffice) {
            this.branchOffice = branchOffice;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder situation(UserTypes situation) {
            this.situation = situation;
            return this;
        }

        public Builder oid(UUID oid) {
            this.oid = oid;
            return this;
        }
        public UserApp build() {
            return new UserApp(this);
        }
    }
}
