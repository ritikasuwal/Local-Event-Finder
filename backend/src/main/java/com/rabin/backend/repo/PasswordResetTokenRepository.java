package com.rabin.backend.repo;

import com.rabin.backend.model.PasswordResetToken;
import com.rabin.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Long> {
    Optional<PasswordResetToken> findByUser(User user);
}
