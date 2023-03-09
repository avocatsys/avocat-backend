package com.avocat.persistence.repository;

import com.avocat.persistence.entity.UserApp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAppRepository extends JpaRepository<UserApp, UUID> {

    Optional<UserApp> findByUsername(String email);

    Optional<UserApp> findByUsernameAndBranchOffice_Id(@Param("email") String email, @Param("branchOfficeId") UUID branchOfficeId);

    Optional<UserApp> findByUsernameAndBranchOffice_Customer_Id(@Param("username") String username, @Param("customerId") UUID customerId);

    Page<UserApp> findAllByBranchOffice_Customer_Id(@Param("customerId") UUID customerId, Pageable pageable);

    @Query("""
            select ua from UserApp ua
            inner join BranchOffice bo on bo.id = ua.branchOffice.id
            inner join Customer c on c.id = bo.customer.id
            where ua.username = :username
            """)
    Optional<UserApp> findByUsernameAuthenticate(@Param("username") String username);

    @Modifying
    @Query("update UserApp ua set ua.linkForgot = :link where ua.id = :userId")
    void updateLinkForgot(@Param("link") String link, @Param("userId") UUID userId);

    Optional<UserApp> findByOid(@Param("oid") UUID oid);

    @Modifying
    @Query("update UserApp ua set ua.password = :password, situation = 'ACTIVE' where ua.oid = :oid")
    void resetPassword(@Param("password") String password, @Param("oid") UUID oid);

    @Modifying
    @Query("update UserApp ua set ua.oid = :newOid where ua.oid = :oldOid")
    void resetOid(@Param("oldOid") UUID oldOid, @Param("newOid") UUID newOid);
}
