package com.avocat.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class UserApp {

    @Id
    @GeneratedValue
    @Column(name = "user_id", nullable = false)
    private UUID id;

    @Email(message = "Formato de e-mail inválido.")
    @Column(nullable = false, unique = true)
    private String username;

    @NotEmpty(message = "Formato de senha inválido.")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_privileges",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "privilege_id"))
    private Set<Privilege> privileges;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    private Group group;

    private UserApp(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.privileges = builder.privileges;
        this.group = builder.group;
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
