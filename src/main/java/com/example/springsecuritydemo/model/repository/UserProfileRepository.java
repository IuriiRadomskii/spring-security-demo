package com.example.springsecuritydemo.model.repository;

import com.example.springsecuritydemo.model.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    @Query("SELECT u FROM USER_PROFILE u WHERE u.appUser.login = :login")
    Optional<UserProfile> findProfileByLogin(@Param("login") String login);

}
