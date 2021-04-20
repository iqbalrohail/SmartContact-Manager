package com.smart.repositories;

import com.smart.domain.UserDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserDomain , Integer> {

    @Query("select u from UserDomain u where u.userEmail = :userEmail")
    public UserDomain getUserByName(@Param("userEmail") String userEmail);
}
