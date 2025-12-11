package com.rabin.backend.repo;

import com.rabin.backend.model.UserInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserInterestRepository extends JpaRepository<UserInterest,Long> {

    @Query("select ui.interestTag.id from UserInterest ui where ui.user.id= :userId")
    List<Long> findTagIdsByUserId(@Param("userId") Long userId);
}
