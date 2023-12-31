package com.example.springsecuritydemo.security.service;

import com.example.springsecuritydemo.security.exception.AlreadyExistsException;
import com.example.springsecuritydemo.security.model.entity.AppUser;
import com.example.springsecuritydemo.security.model.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsManagerImpl implements UserDetailsManager {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return appUserRepository.findByLogin(login).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void createUser(UserDetails user) {
        AppUser appUser = (AppUser) user;
        try {
            appUserRepository.save(appUser);
        } catch (DataIntegrityViolationException e) {
            throw new AlreadyExistsException("User already exists");
        }
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var login = authentication.getName();
        AppUser appUser = appUserRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (passwordEncoder.matches(oldPassword, appUser.getPassword())) {
            appUser.setPass(passwordEncoder.encode(newPassword));
            appUserRepository.save(appUser);
        } else {
            throw new RuntimeException("Wrong password");
        }
    }

    @Override
    public boolean userExists(String username) {
        return appUserRepository.existsByLogin(username);
    }
}
