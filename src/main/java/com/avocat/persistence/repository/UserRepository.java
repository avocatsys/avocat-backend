package com.avocat.persistence.repository;

import com.avocat.persistence.entity.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserApp, UUID> {

    Optional<UserApp> findByUsername(String email);
}
