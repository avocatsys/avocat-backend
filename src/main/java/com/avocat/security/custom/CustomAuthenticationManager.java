package com.avocat.security.custom;

import com.avocat.persistence.entity.UserApp;
import com.avocat.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        var username = authentication.getPrincipal() + "";
        var password = authentication.getCredentials() + "";

        UserDetails user = loadUserByUsername(username);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }

        if (!user.isEnabled()) {
            throw new DisabledException("User account is not active");
        }

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    public UserDetails loadUserByUsername(String username) {
        //@formatter:off
        Optional<UserApp> user = userRepository.findByUsername(username);

        if (user.isEmpty())
            throw new UsernameNotFoundException(username);

        return new UserApp.Builder(user.get().getUsername(), user.get()
                .getPassword())
                .privilege(user.get().getPrivileges())
                .build();
        //@formatter:on
    }
}
