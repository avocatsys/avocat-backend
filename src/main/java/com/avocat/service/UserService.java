package com.avocat.service;

import com.avocat.controller.password.dto.ForgotPasswordDto;
import com.avocat.controller.user.dto.UserAppDto;
import com.avocat.exceptions.InvalidJwtTokenException;
import com.avocat.exceptions.ResourceNotFoundException;
import com.avocat.persistence.entity.Privilege;
import com.avocat.persistence.entity.UserApp;
import com.avocat.persistence.repository.GroupRepository;
import com.avocat.persistence.repository.PrivilegeRepository;
import com.avocat.persistence.repository.UserAppRepository;
import com.avocat.persistence.types.PrivilegesTypes;
import com.avocat.persistence.types.UserTypes;
import com.avocat.security.jwt.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserEmailService userEmailService;

    @Autowired
    private BranchOfficeService branchOfficeService;

    @Autowired
    private UserAppRepository userAppRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Value("${token.jwt.secret}")
    private String jwtSecret;

    private SecretKey secretKey;

    @PostConstruct
    public void setUpSecretKey() {
        var secret = Base64.getEncoder().encodeToString(this.jwtSecret.getBytes());
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Transactional
    public UserAppDto create(UUID branchOfficeId, UserApp user) {
        user.setBranchOffice(branchOfficeService.getBranchOffice(branchOfficeId));
        user.setPrivileges(getPrivileges(user.getPrivileges()));
        user.setSituation(UserTypes.FORGOT_PASSWORD);
        user.setOid(UUID.randomUUID());
        user.setPassword(user.getPassword() == null ? null : new BCryptPasswordEncoder().encode(user.getPassword()));

        var userSaved = userAppRepository.save(user);
        userEmailService.sendEmailForgotPassword(user.getUsername());

        return UserAppDto.from(userSaved);
    }

    @Transactional
    public UserAppDto update(UUID branchOfficeId, UserApp user) {

        var branchOfficeResult = branchOfficeService.getBranchOffice(branchOfficeId);

        var userResult = userAppRepository.findById(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("user id: " + user.getId() + " not found"));

        if (user.getPrivileges().isEmpty() && userResult.getPrivileges().isEmpty()) {
            user.setPrivileges(privilegeRepository.findByName(PrivilegesTypes.ROLE_USER.name()));
        }

        userResult.setGroup(user.getGroup());
        userResult.setPrivileges(user.getPrivileges());
        userResult.setName(user.getName());
        userResult.setUsername(user.getUsername());
        userResult.setBranchOffice(branchOfficeResult);

        return UserAppDto.from(userAppRepository.save(userResult));
    }

    @Transactional
    public void delete(UUID userId) {
        userAppRepository.delete(getUser(userId));
    }

    public Page<UserAppDto> findAll(UUID branchOfficeId, Pageable pageable) {
        return userAppRepository.findAllByBranchOffice_Customer_Id(branchOfficeId, pageable).map(UserAppDto::from);
    }

    public UserAppDto findById(UUID userId) {
        return UserAppDto.from(getUser(userId));
    }

    public UserApp findByUsername(String email) {
        return userAppRepository.findByUsername(email)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
    }

    public Optional<UserApp> findByUsernameAndBranchOfficeId(String email, UUID branchOfficeId) {
        return userAppRepository.findByUsernameAndBranchOffice_Id(email, branchOfficeId);
    }

    private UserApp getUser(UUID id) {
        return userAppRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
    }

    private Set<Privilege> getPrivileges(Set<Privilege> privilegeSet) {
        Set<Privilege> privileges = new HashSet<>();

        for (Privilege p : privilegeSet) {
            var result = privilegeRepository.findById(p.getId());
            privileges.add(result.get());
        }
        return privileges;
    }

    public Optional<UserApp> findByUsernameAndBranchOfficeAndCustomer_Id(String username, UUID customerId) {
        return userAppRepository.findByUsernameAndBranchOffice_Customer_Id(username, customerId);
    }

    @Transactional
    public void resetPassword(ForgotPasswordDto forgotPasswordDto, String token) {
        if (jwtTokenProvider.validateToken(token)) {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(this.secretKey).build().parseClaimsJws(token);
            var oid = claims.getBody().getSubject();

           if(userAppRepository.findByOid(UUID.fromString(oid)).isPresent()) {
               userAppRepository.resetPassword(new BCryptPasswordEncoder().encode(forgotPasswordDto.password1()), UUID.fromString(oid));
               userAppRepository.resetOid(UUID.fromString(oid), UUID.randomUUID());
           } else {
              throw new InvalidJwtTokenException("resource not found in token");
           }

        } else {
            throw new InvalidJwtTokenException("invalid token jwt" + token);
        }
    }

    public void sendLinkToRecoverPassword(String email) {
        userEmailService.sendEmailForgotPassword(email);
    }
}
