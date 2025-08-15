package com.taxisimpledrive.taxiuserservice.repository;

import com.taxisimpledrive.taxiuserservice.model.User;
import com.taxisimpledrive.taxiuserservice.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, UUID> {
    Optional<VerificationToken> findByToken(String token);

    @Query("select v.user from VerificationToken v where v.token = :token")
    Optional<User> findUserByToken(String token);
}
