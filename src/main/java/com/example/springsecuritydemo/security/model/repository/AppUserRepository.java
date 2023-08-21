package com.example.springsecuritydemo.security.model.repository;

import com.example.springsecuritydemo.security.model.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByLogin(String login);
    boolean existsByLogin(String login);

}
