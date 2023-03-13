package com.avocat.persistence.repository.process;

import com.avocat.persistence.entity.process.Forum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ForumRepository extends JpaRepository<Forum, UUID> {

    Page<Forum> findAllByCustomerId(UUID forumId, Pageable pageable);

    Optional<Forum> findByIdAndCustomerId(UUID forumId, UUID customerId);
}
